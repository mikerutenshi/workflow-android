package com.workflow.presentation.view.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.workflow.R;
import com.workflow.data.model.GenericItemPaginationModel;
import com.workflow.data.model.GetWorkerQueryModel;
import com.workflow.data.model.WorkerModel;
import com.workflow.presentation.di.components.DaggerWorkerComponent;
import com.workflow.presentation.di.components.WorkerComponent;
import com.workflow.presentation.di.modules.ContextModule;
import com.workflow.presentation.di.modules.WorkerListModule;
import com.workflow.presentation.navigation.Navigator;
import com.workflow.presentation.presenter.WorkerListPresenter;
import com.workflow.presentation.view.WorkerView;
import com.workflow.presentation.view.activities.MainActivity;
import com.workflow.presentation.view.adapters.OnRecyclerObjectClickListener;
import com.workflow.presentation.view.adapters.WorkerAdapter;
import com.workflow.presentation.view.adapters.WorkflowRvOnScrollListener;
import com.workflow.presentation.view.itemdecoration.GridColumnDecoration;
import com.workflow.presentation.view.itemdecoration.VerticalSingleColumnDecoration;
import com.workflow.utils.ConnectionUtil;
import com.workflow.utils.Roles;
import com.workflow.utils.Sort;
import com.workflow.utils.WorkflowUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import timber.log.Timber;

import static com.workflow.utils.PreferenceUtils.PREF_ROLE;
import static com.workflow.utils.PreferenceUtils.PREF_WORKER_FILTER_STATUS;

/**
 * Created by Michael on 27/06/19.
 */

public class WorkerListFragment extends BaseFragment implements WorkerView {

    @BindView(R.id.rv_worker_list) RecyclerView recyclerView;
    @BindView(R.id.layout_alpha_progress_bar) ConstraintLayout progressBar;
    @BindView(R.id.vf_worker_list) ViewFlipper viewFlipper;
    @BindView(R.id.tv_worker_list_filter_stats) AppCompatTextView tvFilterStats;
    @BindView(R.id.srl_worker_list) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tv_worker_list_sort_stats) TextView tvSortStats;

    @BindString(R.string.no_connection) String strNoConnection;
    @BindString(R.string.filter_status) String strFilterStatus;
    @BindString(R.string.sort_status) String strSortStatus;
    @BindString(R.string.unauthorized_unless_admin_work) String strUnauthAdminPrice;

    @Inject Navigator navigator;
    @Inject WorkerAdapter adapter;
    @Inject GridColumnDecoration verticalSingleColumnDecoration;
    @Inject GridLayoutManager linearLayoutManager;
    @Inject WorkerListPresenter presenter;
    @Inject SharedPreferences sharedPreferences;

    private WorkerComponent component;
    private String workerName;
    private List<String> positions = new ArrayList<>();
    private String sortBy = Sort.SORT_BY_WORKER_POSITION;
    private String sortDirection = Sort.SORT_DIRECTION_ASC;

    @Override
    int setView() {
        return R.layout.fragment_worker_list;
    }

    @Override
    void initDagger() {
        if (component == null) {
            component = DaggerWorkerComponent.builder()
                    .mainComponent(((MainActivity) context).getMainComponent())
                    .workerListModule(new WorkerListModule(this))
                    .contextModule(new ContextModule(context))
                    .build();
        }
        component.inject(this);
    }

    @Override
    void afterViewCreated(Bundle savedInstanceState) {
        initRecyclerView();
        initSwipeRefresh();
    }

    @Override
    public void onResume() {
        super.onResume();

        adapter.setCountSelectedItems(0);
        initFilterStatus();

        Timber.d("mCurrentPageOnResumeCalled");
        presenter.resume();
    }

    @Override
    public void onPause() {
        presenter.pause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }
    @Override
    public void showLoading() {
        viewFlipper.setDisplayedChild(0);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showContent() {
        resetRefreshingStatus();
        viewFlipper.setDisplayedChild(0);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showDataEmptyView() {
        resetRefreshingStatus();
        viewFlipper.setDisplayedChild(1);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void resetRefreshingStatus() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void adapterClear() {
        adapter.clear();
    }

    @Override
    public int adapterSize() {
        return adapter.getItemCount();
    }

    @Override
    public void removeSelectedItems() {
        for (WorkerModel w : adapter.getSelectedItems()) {
            w.setSelected(false);
        }
        adapter.notifyDataSetChanged();
        adapter.setCountSelectedItems(0);
    }

    @Override
    public void resetSelectedItemCount() {
        adapter.setCountSelectedItems(0);
    }

    @Override
    public void refreshData() {
        if (((MainActivity) context).isSearching()) ((MainActivity) context).reExecSearchQuery();
        else presenter.refreshData();
    }

    @Override
    public List<String> getPositions() {
        return positions;
    }

    @Override
    public String getSortBy() {
        return sortBy;
    }

    @Override
    public String getSortDirection() {
        return sortDirection;
    }


    @Override
    public void showSnackBar(int type, String msg) {
        View view = (((MainActivity) context).getCoordinatorLayout());
        Snackbar snackbar = WorkflowUtils.generateSnackBar(view, type, msg);
        snackbar.show();
    }

    @Override
    public boolean isConnected() {
        if (!ConnectionUtil.isOnline(context)) {
            if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
            showSnackBar(WorkflowUtils.SNACK_BAR_WARNING, strNoConnection);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void renderItems(GenericItemPaginationModel<List<WorkerModel>> items, int page) {
        adapter.removeLoadingFooter();
        presenter.setIsLoading(false);
        if (page == 1) {
            adapter.setItems(items.getItems());
        } else {
            adapter.addAll(items.getItems());
        }

        if (presenter.getCurrentPage() >= items.getPaginationModel().getTotalPage()) {
            presenter.setIsLastPage(true);
        } else {
            adapter.addLoadingFooter();
            presenter.setIsLastPage(false);
        }
    }

    @Override
    public void adapterDeleteItem(List<WorkerModel> item) {
        adapter.removeAll(item);
        ((MainActivity) context).stopActionMode();
    }

    public void filterByName(String name) {
        showLoading();
        workerName = name.length() == 0 ? null : name;
        presenter.setCurrentPage(1);
        presenter.getWorkers(workerName, positions, sortBy, sortDirection);

    }

    public void showDeleteConfirmDialog() {
        ConfirmDeleteDialogFragment confirmDelete = ConfirmDeleteDialogFragment.newInstance(adapter.getCountSelectedItems());
        confirmDelete.setListener(new ConfirmDeleteDialogFragment.ConfirmDeleteListener() {
            @Override
            public void onConfirmClick() {
                presenter.deleteWorker(adapter.getSelectedItems());
                ((MainActivity) context).clearUserActionState();
            }

            @Override
            public void onCancelClick() {
                ((MainActivity) context).clearUserActionState();
            }
        });
        confirmDelete.show(getFragmentManager(), "ConfirmDeleteWorkerDialogFragment");
    }

    public List<WorkerModel> getSelectedItems() {
        return adapter.getSelectedItems();
    }

    public GetWorkerQueryModel compileFilters() {
        return new GetWorkerQueryModel(getPositions(), getSortBy(), getSortDirection());
    }

    public void invalidateData(GetWorkerQueryModel queryModel) {
        showLoading();
        positions = queryModel.getPositions();
        sortBy = queryModel.getSortBy();
        sortDirection = queryModel.getSortDirection();
        setTvFilterStats(queryModel);
        presenter.setCurrentPage(1);
        presenter.getWorkers(workerName, positions, sortBy, sortDirection);
    }

    private void setTvFilterStats(GetWorkerQueryModel queryModel) {
        StringBuilder sortBuilder = new StringBuilder();
        sortBuilder.append(WorkflowUtils.renderSort(context, queryModel.getSortBy()));
        sortBuilder.append(" ");
        sortBuilder.append(String.format("(%s)", WorkflowUtils.renderSort(context, queryModel.getSortDirection())));

        if (queryModel.getPositions().size() == 0) {
//            tvFilterStats.setText(Html.fromHtml(String.format(strSortStatus, sortBuilder)));
            tvFilterStats.setVisibility(View.GONE);
        } else if (queryModel.getPositions().size() == 6){
            tvFilterStats.setVisibility(View.VISIBLE);
            tvFilterStats.setText(WorkflowUtils.getRenderedPosition(context, "all"));
        } else {
            tvFilterStats.setVisibility(View.VISIBLE);
            StringBuilder filterBuilder = new StringBuilder();
            for (String pos : queryModel.getPositions()) {
                if (queryModel.getPositions().indexOf(pos) != 0) {
                    filterBuilder.append(", ");
                }
                filterBuilder.append(WorkflowUtils.getRenderedPosition(context, pos));
            }

//            tvFilterStats.setText(Html.fromHtml(String.format(strFilterStatus, filterBuilder, sortBuilder)));
            tvFilterStats.setText(filterBuilder);
        }
        tvSortStats.setText(sortBuilder);
    }

    private void initRecyclerView() {
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(verticalSingleColumnDecoration);

        OnRecyclerObjectClickListener listener = new OnRecyclerObjectClickListener<WorkerModel>() {
            @Override
            public void onItemClicked(int position, WorkerModel item) {
                navigator.navigateToWorkerDetail(context, item);
            }

            @Override
            public void onItemLongClick(int position, WorkerModel item) {

                //show unauthorized if you are admin_price
//                if (sharedPreferences.getString(PREF_ROLE, "").equals(Roles.ADMIN_PRICE)
//                    || sharedPreferences.getString(PREF_ROLE, "").equals(Roles.ADMIN_QA)) {
//                    showToastMessage(strUnauthAdminPrice);
//                    return;
//                }

                if (!((MainActivity) context).isActionMode()) {
                    adapter.clearSelectedItems();
                    ((MainActivity) context).showActionMode();
                }

                adapter.toggleItemSelected(position, item);

                if (adapter.getCountSelectedItems() == 0) {
                    ((MainActivity) context).stopActionMode();
                } else if (adapter.getCountSelectedItems() == 1) {
                    ((MainActivity) context).showActionModeEdit();
                    ((MainActivity) context).setActionModeTitle(adapter.getCountSelectedItems());
                } else {
                    ((MainActivity) context).hideActionModeEdit();
                    ((MainActivity) context).setActionModeTitle(adapter.getCountSelectedItems());
                }
            }

            @Override
            public void onItemSelected(int position, WorkerModel item) {
                if (((MainActivity) context).isActionMode()) {
                    adapter.toggleItemSelected(position, item);

                    //stop action bar when selected items count is 0 else show count
                    if (adapter.getCountSelectedItems() == 0) {
                        ((MainActivity) context).stopActionMode();
                    } else if (adapter.getCountSelectedItems() == 1) {
                        ((MainActivity) context).showActionModeEdit();
                        ((MainActivity) context).setActionModeTitle(adapter.getCountSelectedItems());
                    } else {
                        ((MainActivity) context).hideActionModeEdit();
                        ((MainActivity) context).setActionModeTitle(adapter.getCountSelectedItems());
                    }
                }
            }
        };
        adapter.setListener(listener);

        recyclerView.addOnScrollListener(new WorkflowRvOnScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                presenter.setIsLoading(true);
                presenter.incrementCurrentPage();
                presenter.getWorkers(workerName, positions, sortBy, sortDirection);
            }

            @Override
            public boolean isLastPage() {
                return presenter.isLastPage();
            }

            @Override
            public boolean isLoading() {
                return presenter.isLoading();
            }
        });
    }

    private void initFilterStatus() {
        String jsonQueryModel = ((MainActivity) context).getSharedPreferences().getString(PREF_WORKER_FILTER_STATUS, null);
        GetWorkerQueryModel queryModel;
        int filterCount = 0;
        if (jsonQueryModel != null) {
            Gson gson = new Gson();
            queryModel = gson.fromJson(jsonQueryModel, GetWorkerQueryModel.class);
            filterCount = queryModel.getPositions().size();

            positions = queryModel.getPositions();
            sortBy = queryModel.getSortBy();
            sortDirection = queryModel.getSortDirection();
            setTvFilterStats(queryModel);
        } else {
            setTvFilterStats(new GetWorkerQueryModel(new ArrayList<>(), sortBy, sortDirection));
        }
//        ((MainActivity) context).setFilterBadgeCount(String.valueOf(filterCount));
    }

    private void initSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener(this::refreshData);
    }
}

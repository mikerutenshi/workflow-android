package com.workflow.presentation.view.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ViewFlipper;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.workflow.R;
import com.workflow.data.model.GenericItemPaginationModel;
import com.workflow.data.model.WorkModel;
import com.workflow.presentation.di.components.DaggerWorkListComponent;
import com.workflow.presentation.di.components.WorkListComponent;
import com.workflow.presentation.di.modules.ContextModule;
import com.workflow.presentation.di.modules.WorkListModule;
import com.workflow.presentation.navigation.Navigator;
import com.workflow.presentation.presenter.WorkListPresenter;
import com.workflow.presentation.view.WorkView;
import com.workflow.presentation.view.activities.MainActivity;
import com.workflow.presentation.view.adapters.OnRecyclerObjectClickListener;
import com.workflow.presentation.view.adapters.WorkAdapter;
import com.workflow.presentation.view.adapters.WorkflowRvOnScrollListener;
import com.workflow.presentation.view.itemdecoration.VerticalSingleColumnDecoration;
import com.workflow.utils.ConnectionUtil;
import com.workflow.utils.Roles;
import com.workflow.utils.WorkflowUtils;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import timber.log.Timber;

import static com.workflow.utils.PreferenceUtils.PREF_ROLE;

/**
 * Created by Michael on 27/06/19.
 */

public class WorkListFragment extends BaseFragment implements WorkView {

    @BindView(R.id.rv_work_list) RecyclerView recyclerView;
    @BindView(R.id.layout_alpha_progress_bar) ConstraintLayout progressBar;
    @BindView(R.id.vf_work_list) ViewFlipper viewFlipper;
    @BindView(R.id.srl_work_list) SwipeRefreshLayout swipeRefreshLayout;

    @BindString(R.string.no_connection) String strNoConnection;
    @BindString(R.string.unauthorized_unless_admin_price) String strUnauthUnlessAdminPrice;

    @Inject Navigator navigator;
    @Inject WorkListPresenter presenter;
    @Inject WorkAdapter adapter;
    @Inject VerticalSingleColumnDecoration verticalSingleColumnDecoration;
    @Inject LinearLayoutManager linearLayoutManager;

    private WorkListComponent component;
    private String spkNo = null;

    @Override
    int setView() {
        return R.layout.fragment_work_list;
    }

    @Override
    void initDagger() {
        if (component == null) {
            component = DaggerWorkListComponent.builder()
                    .mainComponent(((MainActivity) context).getMainComponent())
                    .workListModule(new WorkListModule(this))
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
        Timber.d("mOnResumeCalled");
        Timber.d("mAdapterSize: %d", adapter.getItemCount());
        adapter.setCountSelectedItems(0);
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
    public void showDataEmpty() {
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
    public void removeSelectedItems() {

        for (WorkModel w : adapter.getSelectedItems()) {
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
    public void adapterClear() {
        adapter.clear();
    }

    @Override
    public int adapterSize() {
        return adapter.getItemCount();
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
    public void renderItems(GenericItemPaginationModel<List<WorkModel>> items, int page) {
        adapter.removeLoadingFooter();
        presenter.setIsLoading(false);
        if (page == 1) {
            adapter.setItems(items.getItems());
        } else {
            adapter.addAll(items.getItems());
        }

        if (Objects.equals(presenter.getCurrentPage(), items.getPaginationModel().getTotalPage())) {
            presenter.setIsLastPage(true);
        } else {
            adapter.addLoadingFooter();
            presenter.setIsLastPage(false);
        }
    }

    @Override
    public void adapterDeleteItem(List<WorkModel> param) {
        adapter.removeAll(param);
        ((MainActivity) context).stopActionMode();
    }

    public void filterBySpkNo(String spkNo) {
        showLoading();
        this.spkNo = spkNo;
        presenter.setCurrentPage(1);
        presenter.getWorks(this.spkNo);
    }

    public void showDeleteConfirmDialog() {
        ConfirmDeleteDialogFragment confirmDelete = ConfirmDeleteDialogFragment.newInstance(adapter.getCountSelectedItems());
        confirmDelete.setListener(new ConfirmDeleteDialogFragment.ConfirmDeleteListener() {
            @Override
            public void onConfirmClick() {
                presenter.deleteWork(adapter.getSelectedItems());
                ((MainActivity) context).clearUserActionState();
            }

            @Override
            public void onCancelClick() {
                ((MainActivity) context).clearUserActionState();
            }
        });
        confirmDelete.show(getFragmentManager(), "ConfirmDeleteWorkDialogFragment");
    }

    public List<WorkModel> getSelectedItems() {
        return adapter.getSelectedItems();
    }

    private void initRecyclerView() {
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(verticalSingleColumnDecoration);

        adapter.setListener(new OnRecyclerObjectClickListener<WorkModel>() {
            @Override
            public void onItemClicked(int position, WorkModel item) {

            }

            @Override
            public void onItemLongClick(int position, WorkModel item) {

//                if (((MainActivity) context).getSharedPreferences().getString(PREF_ROLE, "").equals(Roles.ADMIN_WORK)
//                        || ((MainActivity) context).getSharedPreferences().getString(PREF_ROLE, "").equals(Roles.ADMIN_QA)) {
//                    showToastMessage(strUnauthUnlessAdminPrice);
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
            public void onItemSelected(int position, WorkModel item) {
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
        });
        recyclerView.addOnScrollListener(new WorkflowRvOnScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                presenter.setIsLoading(true);
                presenter.incrementCurrentPage();
                presenter.getWorks(spkNo);
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

    private void initSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener(this::refreshData);
    }
}

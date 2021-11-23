package com.workflow.presentation.view.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.workflow.R;
import com.workflow.WorkflowApplication;
import com.workflow.data.model.CurrentWorkListModel;
import com.workflow.data.model.WorkModel;
import com.workflow.data.net.responses.GenericListResponseModel;
import com.workflow.presentation.di.components.CurrentWorkListComponent;
import com.workflow.presentation.di.components.DaggerCurrentWorkListComponent;
import com.workflow.presentation.di.modules.ContextModule;
import com.workflow.presentation.di.modules.CurrentWorkListModule;
import com.workflow.presentation.navigation.Navigator;
import com.workflow.presentation.presenter.CurrentWorkListPresenter;
import com.workflow.presentation.view.CurrentWorkListView;
import com.workflow.presentation.view.activities.MainActivity;
import com.workflow.presentation.view.adapters.CurrentWorkListAdapter;
import com.workflow.presentation.view.adapters.OnRecyclerObjectClickListener;
import com.workflow.presentation.view.adapters.WorkflowRvOnScrollListener;
import com.workflow.presentation.view.itemdecoration.VerticalSingleColumnDecoration;
import com.workflow.utils.ConnectionUtil;
import com.workflow.utils.Roles;
import com.workflow.utils.WorkflowUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import timber.log.Timber;

import static com.workflow.utils.PreferenceUtils.PREF_ROLE;
import static com.workflow.utils.PreferenceUtils.PREF_WORK_FILTER_STATUS;

public class CurrentWorkListFragment extends BaseFragment implements CurrentWorkListView {

    @BindView(R.id.layout_alpha_progress_bar) ConstraintLayout layoutProgressBar;
    @BindView(R.id.layout_data_empty) ConstraintLayout layoutDataEmpty;
    @BindView(R.id.rv_current_work_list) RecyclerView recyclerView;
    @BindView(R.id.srl_current_work_list) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tv_work_list_filter_stats) AppCompatTextView tvFilterStats;
    @BindView(R.id.tv_work_list_sort_stats) AppCompatTextView tvSortStats;

    @Inject LinearLayoutManager linearLayoutManager;
    @Inject VerticalSingleColumnDecoration verticalSingleColumnDecoration;
    @Inject SharedPreferences sharedPreferences;
    @Inject CurrentWorkListAdapter adapter;
    @Inject CurrentWorkListPresenter presenter;
    @Inject Navigator navigator;

    @BindString(R.string.no_connection) String strNoConnection;
    @BindString(R.string.unauthorized_unless_admin_price) String strUnauthAdminPrice;

    private CurrentWorkListComponent component;
    private HashMap<String, String> options = new HashMap<>();

    @Override
    int setView() {
        return R.layout.fragment_current_work_list;
    }

    @Override
    void initDagger() {
        if (component == null) {
            component = DaggerCurrentWorkListComponent.builder()
                    .applicationComponent(((WorkflowApplication) context.getApplicationContext()).getApplicationComponent())
                    .contextModule(new ContextModule(context))
                    .currentWorkListModule(new CurrentWorkListModule(this))
                    .build();
        }
        component.inject(this);
    }

    @Override
    void afterViewCreated(Bundle savedInstanceState) {
        initFilterStatus();
        initRecyclerView();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            presenter.resume();
        });
    }

    private void initRecyclerView() {
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(verticalSingleColumnDecoration);
        adapter.setListener(new OnRecyclerObjectClickListener<CurrentWorkListModel>() {
            @Override
            public void onItemClicked(int position, CurrentWorkListModel item) {
                navigator.navigateToWorkDetail(context, new WorkModel(
                        item.getId(),
                        item.getSpkNo(),
                        item.getArticleNo(),
                        item.getProductCategoryName(),
                        item.getQuantity(),
                        item.getCreatedAt(),
                        item.getProductId(),
                        item.getNotes()
                ));
            }

            @Override
            public void onItemLongClick(int position, CurrentWorkListModel item) {
                if (sharedPreferences.getString(PREF_ROLE, "").equals(Roles.ADMIN_WORK)) {
                    Toast.makeText(context, strUnauthAdminPrice, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!((MainActivity) context).isActionMode()) {
                    ((MainActivity) context).showActionMode();
                }

                adapter.toggleItemChecked(position, item);

                int selectedItemSize = adapter.getSelectedItems().size();
                ((MainActivity) context).setActionModeTitle(selectedItemSize);

                if (selectedItemSize == 0) {
                    ((MainActivity) context).stopActionMode();
                }
            }

            @Override
            public void onItemSelected(int position, CurrentWorkListModel item) {

            }
        });
        recyclerView.addOnScrollListener(new WorkflowRvOnScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                adapter.setLoading(true);
                adapter.setCurrentPage(adapter.getCurrentPage() + 1);
                presenter.getCurrentWorks();
            }

            @Override
            public boolean isLastPage() {
                return adapter.isLastPage();
            }

            @Override
            public boolean isLoading() {
                return adapter.isLoading();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Timber.d("onresumecalled");
        if (((MainActivity) context).isActionMode()) {
            ((MainActivity) context).stopActionMode();
        } else {
            presenter.resume();
            adapter.setCountSelectedItems(0);
            adapter.setItemCheckedPosition(-1);
        }
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
        layoutProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showContent() {
        if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
        layoutProgressBar.setVisibility(View.GONE);
        layoutDataEmpty.setVisibility(View.GONE);
    }

    @Override
    public void showSnackBar(int type, String msg) {
        View view = (((MainActivity) context).getCoordinatorLayout());
        WorkflowUtils.generateSnackBar(view, type, msg).show();
    }

    @Override
    public boolean isConnected() {
        if (!ConnectionUtil.isOnline(context)) {
            showSnackBar(WorkflowUtils.SNACK_BAR_WARNING, strNoConnection);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void renderItems(GenericListResponseModel<CurrentWorkListModel> items) {
        if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);

        adapter.removeLoadingFooter();
        adapter.setLoading(false);

        List<CurrentWorkListModel> currentWorkListModels = items.getItems();
        if (adapter.getCurrentPage() == 1) {
            adapter.setItems(currentWorkListModels);
        } else {
            adapter.addAll(currentWorkListModels);
        }

        if (currentWorkListModels.size() == 0) {
            showDataEmpty();
        }

        if (adapter.getCurrentPage() >= items.getMetaModel().getTotalPage()) {
            adapter.setLastPage(true);
        } else {
            adapter.addLoadingFooter();
            adapter.setLastPage(false); }
    }

    @Override
    public HashMap<String, String> getOptions() {
        return options;
    }

    @Override
    public CurrentWorkListAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void showDataEmpty() {
        adapter.clear();
        layoutDataEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void searchHandle(String keyword) {
        if (keyword.length() != 0) {
            options.put("search_keyword", keyword);
        } else {
            options.remove("search_keyword");
        }
        presenter.resume();
    }

    @Override
    public void filterHandle(HashMap<String, String> options) {
        this.options.putAll(options);
        setTvFilterStats(options);
        presenter.resume();
    }

    private void initFilterStatus() {
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        startCal.add(Calendar.DAY_OF_MONTH, -7);
        options.put("start_date", WorkflowUtils.apiSimpleDateFormat.format(startCal.getTime()));
        options.put("end_date", WorkflowUtils.apiSimpleDateFormat.format(endCal.getTime()));

        String optionsJson = sharedPreferences.getString(PREF_WORK_FILTER_STATUS, null);

        if (optionsJson != null) {
            java.lang.reflect.Type type = new TypeToken<HashMap<String, String>>(){}.getType();
            HashMap<String, String> mOptions = new Gson().fromJson(optionsJson, type);
//            options.put("start_date", mOptions.get("start_date"));
//            options.put("end_date", mOptions.get("end_date"));
            options.put("sort_by", mOptions.get("sort_by"));
            options.put("sort_direction", mOptions.get("sort_direction"));
        } else {
            options.put("sort_by", "spk_no");
            options.put("sort_direction", "asc");
        }
        setTvFilterStats(options);
    }

    public void resetSelectedItemCount() {
        adapter.setCountSelectedItems(0);
    }

    public List<CurrentWorkListModel> getSelectedItems() {
        return adapter.getSelectedItems();
    }

    public void showDeleteConfirmDialog() {
        ConfirmDeleteDialogFragment confirmDelete = ConfirmDeleteDialogFragment.newInstance(adapter.getSelectedItems().size());
        confirmDelete.setListener(new ConfirmDeleteDialogFragment.ConfirmDeleteListener() {
            @Override
            public void onConfirmClick() {
                CurrentWorkListModel currentWorkListModel = adapter.getSelectedItems().get(0);
                presenter.deleteWork(currentWorkListModel.getId());
                ((MainActivity) context).clearUserActionState();
            }

            @Override
            public void onCancelClick() {
                ((MainActivity) context).clearUserActionState();
            }
        });
        confirmDelete.show(getFragmentManager(), "ConfirmDeleteWorkDialogFragment");
    }

    public void refreshData() {
        presenter.resume();
    }

    private void setTvFilterStats(HashMap<String, String> opt) {
        StringBuilder sortBuilder = new StringBuilder();
        sortBuilder.append(WorkflowUtils.renderSort(context, opt.get("sort_by")));
        sortBuilder.append(" ");
        sortBuilder.append(String.format("(%s)", WorkflowUtils.renderSort(context, opt.get("sort_direction"))));
        tvSortStats.setText(sortBuilder);

        StringBuilder filterBuilder = new StringBuilder();
        filterBuilder.append(WorkflowUtils.formatSimpleDateToIndoDayMonth(opt.get("start_date")));
        filterBuilder.append(" - ");
        filterBuilder.append(WorkflowUtils.formatSimpleDateToIndoDayMonth(opt.get("end_date")));
        tvFilterStats.setText(filterBuilder);
    }
}

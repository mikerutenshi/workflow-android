package com.workflow.presentation.view.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.workflow.R;
import com.workflow.data.net.responses.GenericListResponseModel;
import com.workflow.presentation.presenter.WorkerDetailWorkerWorkPresenter;
import com.workflow.presentation.view.WorkerDetailWorkerWorkView;
import com.workflow.presentation.view.activities.WorkerDetailActivity;
import com.workflow.presentation.view.adapters.BaseViewHolder;
import com.workflow.presentation.view.adapters.OnRecyclerObjectClickListener;
import com.workflow.presentation.view.adapters.PaginationAdapter;
import com.workflow.presentation.view.adapters.WorkflowRvOnScrollListener;
import com.workflow.presentation.view.itemdecoration.VerticalSingleColumnDecoration;
import com.workflow.utils.ConnectionUtil;
import com.workflow.utils.Roles;
import com.workflow.utils.WorkflowUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import timber.log.Timber;

import static com.workflow.utils.PreferenceUtils.PREF_ROLE;
import static com.workflow.utils.PreferenceUtils.PREF_WORK_ASSIGNED_FILTER_STATUS;
import static com.workflow.utils.PreferenceUtils.PREF_WORK_DONE_FILTER_STATUS;

public abstract class WorkerWorkFragment<T> extends BaseFragment implements WorkerDetailWorkerWorkView<T> {

    @BindView(R.id.rv_simple_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.layout_alpha_progress_bar) ConstraintLayout layoutProgressBar;
    @BindView(R.id.layout_data_empty) ConstraintLayout layoutDataEmpty;
    @BindView(R.id.srl_simple_recycler_view) SwipeRefreshLayout swipeRefreshLayout;

    @Inject LinearLayoutManager linearLayoutManager;
    @Inject VerticalSingleColumnDecoration verticalSingleColumnDecoration;
    @Inject SharedPreferences sharedPreferences;

    @BindString(R.string.no_connection) String strNoConnection;
    @BindString(R.string.unauthorized_unless_admin_work) String strUnauthAdminPrice;

    abstract PaginationAdapter<T, OnRecyclerObjectClickListener<T>
        , BaseViewHolder<T, OnRecyclerObjectClickListener<T>>> adapter();
    abstract String filterPrefKey();
    abstract WorkerDetailWorkerWorkPresenter<T> presenter();

    private HashMap<String, String> options = new HashMap<>();

    @Override
    int setView() {
        return R.layout.fragment_simple_recycler_view;
    }

    @Override
    void afterViewCreated(Bundle savedInstanceState) {
        initRecyclerView();

        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (((WorkerDetailActivity) context).isSearching()) {
                searchWork();
            } else {
                presenter().resume();
                adapter().setCountSelectedItems(0);
            }
        });
    }

    private void initRecyclerView() {
        recyclerView.setAdapter(adapter());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(verticalSingleColumnDecoration);
        adapter().setListener(new OnRecyclerObjectClickListener<T>() {
            @Override
            public void onItemClicked(int position, T item) {
            }

            @Override
            public void onItemLongClick(int position, T item) {
//                if (sharedPreferences.getString(PREF_ROLE, "").equals(Roles.ADMIN_PRICE)) {
//                    Toast.makeText(context, strUnauthAdminPrice, Toast.LENGTH_SHORT).show();
//                    return;
//                }

                if (!((WorkerDetailActivity) context).isActionMode()) {
                    ((WorkerDetailActivity) context).showActionMode();
                }

                adapter().toggleItemChecked(position, item);

                int selectedItemSize = adapter().getSelectedItems().size();
                ((WorkerDetailActivity) context).setActionModeTitle(selectedItemSize);

                if (selectedItemSize == 0) {
                    ((WorkerDetailActivity) context).stopActionMode();
                }
            }

            @Override
            public void onItemSelected(int position, T item) {
            }
        });

        recyclerView.addOnScrollListener(new WorkflowRvOnScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                presenter().setIsLoading(true);
                presenter().incrementCurrentPage();
                presenter().getWorks();
            }

            @Override
            public boolean isLastPage() {
                return presenter().isLastPage();
            }

            @Override
            public boolean isLoading() {
                return presenter().isLoading();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        initFilterStatus();
        if (((WorkerDetailActivity) context).isActionMode()) {
            ((WorkerDetailActivity) context).stopActionMode();
        } else {
            presenter().resume();
            adapter().setCountSelectedItems(0);
            adapter().setItemCheckedPosition(-1);
        }

        String searchKeyword = ((WorkerDetailActivity) context).getSearchKeyword();
        if (searchKeyword != null && searchKeyword.length() != 0) {
            options.put("search_keyword", searchKeyword);
        } else {
            options.remove("search_keyword");
        }
    }

    @Override
    public void onPause() {
        presenter().pause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        presenter().destroy();
        super.onDestroy();
    }

    @Override
    public void renderItems(GenericListResponseModel<T> items, int page) {
        Timber.d("renderitemscalled");
        adapter().removeLoadingFooter();
        presenter().setIsLoading(false);
        if (page == 1) {
            adapter().setItems(items.getItems());
        } else {
            adapter().addAll(items.getItems());
        }

        if (items.getItems().size() == 0) {
            showDataEmpty();
        }

        if (presenter().getCurrentPage() >= items.getMetaModel().getTotalPage()) {
            presenter().setIsLastPage(true);
        } else {
            adapter().addLoadingFooter();
            presenter().setIsLastPage(false);
        }
    }

    @Override
    public void adapterDeleteItem(T params) {
        adapter().remove(params);
        ((WorkerDetailActivity) context).stopActionMode();
    }

    @Override
    public int getAdapterSelectedItemCount() {
        return adapter().getCountSelectedItems();
    }

    @Override
    public void adapterClear() {
        adapter().clear();
    }

    @Override
    public void deleteWork() {
        ConfirmDeleteDialogFragment confirmDelete = ConfirmDeleteDialogFragment.newInstance(
                adapter().getSelectedItems().size()
        );
        confirmDelete.setListener(new ConfirmDeleteDialogFragment.ConfirmDeleteListener() {
            @Override
            public void onConfirmClick() {
                presenter().deleteWorkerWork(adapter().getSelectedItems().get(0));
                ((WorkerDetailActivity) context).stopActionMode();
            }

            @Override
            public void onCancelClick() {
                ((WorkerDetailActivity) context).stopActionMode();
            }
        });

        if (getFragmentManager() != null) {
            confirmDelete.show(getFragmentManager(), "ConfirmDeleteWorkerWorkDialogFragment");
        }
    }

    @Override
    public void showDataEmpty() {
        adapter().clear();
        layoutDataEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void resetRefreshingStatus() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public HashMap<String, String> getOptions() {
        return options;
    }

    @Override
    public void showLoading() {
        layoutProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showContent() {
        resetRefreshingStatus();
        layoutDataEmpty.setVisibility(View.GONE);
        layoutProgressBar.setVisibility(View.GONE);
        Timber.d("showcontentcalled");
    }

    @Override
    public void showSnackBar(int type, String msg) {
        Snackbar snackbar = WorkflowUtils.generateSnackBar((WorkerDetailActivity) context, type, msg);
        snackbar.show();
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
    public void invalidateData() {
        adapter().setItemCheckedPosition(-1);
        showLoading();
        ((WorkerDetailActivity) context).setTvFilterStatus(options);
        presenter().setCurrentPage(1);
        presenter().getWorks();
    }

    @Override
    public void searchWork() {
        showLoading();
        presenter().setCurrentPage(1);
        String searchKeyword = ((WorkerDetailActivity) context).getSearchKeyword();

        if (searchKeyword != null && searchKeyword.length() != 0) {
            options.put("search_keyword", searchKeyword);
        } else {
            options.remove("search_keyword");
        }

        presenter().getWorks();
    }

    @Override
    public void showFilter(ArrayList<String> positionArrayList) {
        FilterSortAssignedWorkDialogFragment filterDialog =
                FilterSortAssignedWorkDialogFragment.newInstance(options, positionArrayList, filterPrefKey());
        if (getFragmentManager() != null) {
            filterDialog.show(getFragmentManager(), "FilterWorkAssignDialog");
        }
        filterDialog.setListener(filterState -> {
            options.clear();
            options.putAll(filterState);
            //remove these so they are not saved to pref
            filterState.remove("start_date");
            filterState.remove("end_date");
            filterState.remove("position");

            sharedPreferences.edit().putString(
                    filterPrefKey(), new Gson().toJson(filterState)).apply();
            invalidateData();
        });

    }

    private void initFilterStatus() {
        String optionsJson = sharedPreferences.getString(filterPrefKey(), null);

        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        startCal.add(Calendar.DAY_OF_MONTH, -7);

        if (optionsJson != null) {
            java.lang.reflect.Type type = new TypeToken<HashMap<String, String>>(){}.getType();
            HashMap<String, String> hashMap = new Gson().fromJson(optionsJson, type);

            options.putAll(hashMap);
            options.put("start_date", WorkflowUtils.apiSimpleDateFormat.format(startCal.getTime()));
            options.put("end_date", WorkflowUtils.apiSimpleDateFormat.format(endCal.getTime()));
        } else {
            if (filterPrefKey().equals(PREF_WORK_ASSIGNED_FILTER_STATUS)) {
                options.put("date_filter_type", "assigned_at");
            } else if (filterPrefKey().equals(PREF_WORK_DONE_FILTER_STATUS)) {
                options.put("date_filter_type", "done_at");
            }
            options.put("start_date", WorkflowUtils.apiSimpleDateFormat.format(startCal.getTime()));
            options.put("end_date", WorkflowUtils.apiSimpleDateFormat.format(endCal.getTime()));
            options.put("sort_by", "spk_no");
            options.put("sort_direction", "asc");
        }

        ((WorkerDetailActivity) context).setTvFilterStatus(options);
    }
}

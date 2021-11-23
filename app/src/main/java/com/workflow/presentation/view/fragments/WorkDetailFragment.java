package com.workflow.presentation.view.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.workflow.R;
import com.workflow.data.net.responses.GenericListResponseModel;
import com.workflow.presentation.presenter.WorkDetailPresenter;
import com.workflow.presentation.view.WorkDetailFragmentView;
import com.workflow.presentation.view.activities.WorkDetailActivity;
import com.workflow.presentation.view.adapters.BaseViewHolder;
import com.workflow.presentation.view.adapters.OnRecyclerObjectClickListener;
import com.workflow.presentation.view.adapters.PaginationAdapter;
import com.workflow.presentation.view.adapters.WorkflowRvOnScrollListener;
import com.workflow.presentation.view.itemdecoration.VerticalSingleColumnDecoration;
import com.workflow.utils.ConnectionUtil;
import com.workflow.utils.WorkflowUtils;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;

public abstract class WorkDetailFragment<T> extends BaseFragment implements WorkDetailFragmentView<T> {

    @BindView(R.id.rv_simple_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.layout_alpha_progress_bar) ConstraintLayout layoutProgressBar;
    @BindView(R.id.layout_data_empty) ConstraintLayout layoutDataEmpty;
    @BindView(R.id.srl_simple_recycler_view) SwipeRefreshLayout swipeRefreshLayout;

    @BindString(R.string.no_connection) String strNoConnection;

    @Inject LinearLayoutManager linearLayoutManager;
    @Inject VerticalSingleColumnDecoration verticalSingleColumnDecoration;

    abstract PaginationAdapter<T, OnRecyclerObjectClickListener<T>
            , BaseViewHolder<T, OnRecyclerObjectClickListener<T>>> adapter();
    abstract WorkDetailPresenter presenter();

    private HashMap<String, String> options = new HashMap<>();

    @Override
    int setView() {
        return R.layout.fragment_simple_recycler_view;
    }

    @Override
    void afterViewCreated(Bundle savedInstanceState) {
        if (getArguments() != null) {
            options.put("work_id", String.valueOf(getArguments().getInt("work_id")));
        }
        initRecyclerView();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            presenter().resume();
        });
    }

    @Override
    public void renderItems(GenericListResponseModel items) {
        adapter().removeLoadingFooter();
        adapter().setLoading(false);
        if (adapter().getCurrentPage() == 1) {
            adapter().setItems(items.getItems());
        } else {
            adapter().addAll(items.getItems());
        }

        if (items.getItems().size() == 0) {
            showDataEmpty();
        }

        if (adapter().getCurrentPage() >= items.getMetaModel().getTotalPage()) {
            adapter().setLastPage(true);
        } else {
            adapter().addLoadingFooter();
            adapter().setLastPage(false);
        }
    }

    @Override
    public void showDataEmpty() {
        adapter().clear();
        layoutDataEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void showContent() {
        if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
        layoutDataEmpty.setVisibility(View.GONE);
        layoutProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        layoutProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSnackBar(int type, String msg) {
        WorkflowUtils.generateSnackBar((WorkDetailActivity) context, type, msg).show();
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
    public void onResume() {
        super.onResume();
        presenter().resume();
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
    public HashMap<String, String> getOptions() {
        return options;
    }

    @Override
    public PaginationAdapter<T, OnRecyclerObjectClickListener<T>, BaseViewHolder<T, OnRecyclerObjectClickListener<T>>> getAdapter() {
        return adapter();
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
            }

            @Override
            public void onItemSelected(int position, T item) {
            }
        });

        recyclerView.addOnScrollListener(new WorkflowRvOnScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                adapter().setLoading(true);
                adapter().setCurrentPage(adapter().getCurrentPage() + 1);
                presenter().getWorks();
            }

            @Override
            public boolean isLastPage() {
                return adapter().isLastPage();
            }

            @Override
            public boolean isLoading() {
                return adapter().isLoading();
            }
        });
    }
}

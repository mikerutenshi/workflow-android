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
import com.workflow.data.model.ProductModel;
import com.workflow.domain.interactor.product.GetProducts;
import com.workflow.presentation.di.components.DaggerProductListComponent;
import com.workflow.presentation.di.components.ProductListComponent;
import com.workflow.presentation.di.modules.ContextModule;
import com.workflow.presentation.di.modules.ProductListModule;
import com.workflow.presentation.navigation.Navigator;
import com.workflow.presentation.presenter.ProductListPresenter;
import com.workflow.presentation.view.ProductView;
import com.workflow.presentation.view.activities.MainActivity;
import com.workflow.presentation.view.adapters.OnRecyclerObjectClickListener;
import com.workflow.presentation.view.adapters.ProductAdapter;
import com.workflow.presentation.view.adapters.WorkflowRvOnScrollListener;
import com.workflow.presentation.view.itemdecoration.VerticalSingleColumnDecoration;
import com.workflow.utils.ConnectionUtil;
import com.workflow.utils.Roles;
import com.workflow.utils.WorkflowUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;

import static com.workflow.utils.PreferenceUtils.PREF_ROLE;

/**
 * Created by Michael on 10/06/19.
 */

public class ProductListFragment extends BaseFragment implements ProductView {

    @BindView(R.id.rv_product_list) RecyclerView recyclerView;
    @BindView(R.id.layout_alpha_progress_bar) ConstraintLayout progressBar;
    @BindView(R.id.vf_product_list) ViewFlipper viewFlipper;
    @BindView(R.id.srl_product_list) SwipeRefreshLayout swipeRefreshLayout;
    @BindString(R.string.no_connection) String strNoConnection;
    @BindString(R.string.unauthorized_unless_superuser) String strUnauthUnlessSuperuser;

    @Inject GetProducts getProducts;
    @Inject Navigator navigator;
    @Inject ProductAdapter adapter;
    @Inject VerticalSingleColumnDecoration verticalSingleColumnDecoration;
    @Inject LinearLayoutManager linearLayoutManager;
    @Inject ProductListPresenter presenter;

    private ProductListComponent component;
    private String mArticleNo = null;

    @Override
    int setView() {
        return R.layout.fragment_product_list;
    }

    @Override
    void initDagger() {
        if (component == null) {
            component = DaggerProductListComponent.builder()
                    .mainComponent(((MainActivity) context).getMainComponent())
                    .productListModule(new ProductListModule(this))
                    .contextModule(new ContextModule(context))
                    .build();
        }
        component.inject(this);
    }

    @Override
    void afterViewCreated(Bundle savedInstanceState) {
        initAdapter();
        swipeRefreshLayout.setOnRefreshListener(this::refreshData);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.resume();
        adapter.setCountSelectedItems(0);
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
        for (ProductModel w : adapter.getSelectedItems()) {
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
    public void renderItems(GenericItemPaginationModel<List<ProductModel>> items, int page) {
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
    public void adapterDeleteItem(List<ProductModel> param) {
        adapter.removeAll(param);
        ((MainActivity) context).stopActionMode();
    }

    public void filterByArticleNo(String articleNo) {
        showLoading();
        mArticleNo = articleNo.length() == 0 ? null : articleNo;
        presenter.setCurrentPage(1);
        presenter.getProducts(mArticleNo);
    }

    public void showDeleteConfirmDialog() {
        ConfirmDeleteDialogFragment confirmDelete = ConfirmDeleteDialogFragment.newInstance(adapter.getCountSelectedItems());
        confirmDelete.setListener(new ConfirmDeleteDialogFragment.ConfirmDeleteListener() {
            @Override
            public void onConfirmClick() {
                presenter.deleteProduct(adapter.getSelectedItems());
                ((MainActivity) context).clearUserActionState();
            }

            @Override
            public void onCancelClick() {
                ((MainActivity) context).clearUserActionState();
            }
        });
        confirmDelete.show(getFragmentManager(), "ConfirmDeleteProductDialogFragment");
    }

    public List<ProductModel> getSelectedItems() {
        return adapter.getSelectedItems();
    }

    @Override
    public void refreshData() {
        if (((MainActivity) context).isSearching()) ((MainActivity) context).reExecSearchQuery();
        else presenter.refreshData();
    }

    private void initAdapter() {
        adapter.setListener(new OnRecyclerObjectClickListener<ProductModel>() {
            @Override
            public void onItemClicked(int position, ProductModel item) {

            }

            @Override
            public void onItemLongClick(int position, ProductModel item) {
                //probably temporary
//                if (((MainActivity) context).getSharedPreferences().getString(PREF_ROLE, "").equals(Roles.SUPER_USER))
//                {
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
//                } else {
//                    showToastMessage(strUnauthUnlessSuperuser);
//                }
            }

            @Override
            public void onItemSelected(int position, ProductModel item) {
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
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(verticalSingleColumnDecoration);
        recyclerView.addOnScrollListener(new WorkflowRvOnScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                presenter.setIsLoading(true);
                presenter.incrementCurrentPage();
                presenter.getProducts(mArticleNo);
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
}

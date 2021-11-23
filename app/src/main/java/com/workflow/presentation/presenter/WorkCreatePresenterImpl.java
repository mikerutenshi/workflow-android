package com.workflow.presentation.presenter;

import com.workflow.data.model.GenericItemPaginationModel;
import com.workflow.data.model.SearchPaginatedQueryModel;
import com.workflow.data.model.ListModel;
import com.workflow.data.model.ProductModel;
import com.workflow.data.model.WorkModel;
import com.workflow.domain.interactor.product.GetProducts;
import com.workflow.domain.interactor.work.PostWork;
import com.workflow.domain.interactor.work.UpdateWork;
import com.workflow.presentation.mapper.ListProductMapper;
import com.workflow.presentation.view.WorkCreateView;
import com.workflow.utils.WorkflowUtils;

import java.util.List;

/**
 * Created by Michael on 30/06/19.
 */

public class WorkCreatePresenterImpl implements WorkCreatePresenter {

    private final PostWork postWork;
    private final GetProducts getProducts;
    private final UpdateWork updateWork;
    private final ListProductMapper mapper;
    private final WorkCreateView view;

    public WorkCreatePresenterImpl(PostWork postWork, GetProducts getProducts, UpdateWork updateWork, ListProductMapper mapper, WorkCreateView view) {
        this.postWork = postWork;
        this.getProducts = getProducts;
        this.updateWork = updateWork;
        this.mapper = mapper;
        this.view = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        postWork.dispose();
    }

    @Override
    public void postWork(WorkModel param) {
        if (view.isConnected()) {
            view.showLoading();
            postWork.execute(new GenericObserver<String>(view) {
                @Override
                public void onSuccess(String s) {
                    super.onSuccess(s);
                    view.showSnackThenClearForm(WorkflowUtils.SNACK_BAR_SUCCESS, s);
                }
            }, param);
        }
    }

    @Override
    public void getProducts(SearchPaginatedQueryModel queryModel) {
        if (view.isConnected()) {
            getProducts.execute(new GenericObserver<GenericItemPaginationModel<List<ProductModel>>>(view) {
                @Override
                public void onSuccess(GenericItemPaginationModel<List<ProductModel>> model) {
                    super.onSuccess(model);
                    List<ListModel> list = mapper.transform(model.getItems());
                    GenericItemPaginationModel<List<ListModel>> items =
                            new GenericItemPaginationModel<>(list, model.getPaginationModel());
                    if (items.getItems().size() > 0) {
                        view.renderProducts(items);
                    } else {
                        view.showDialogDataEmpty();
                    }
                }

                @Override
                public void onError(Throwable e) {
                    view.showContent();
                    view.showDialogError(e.getLocalizedMessage());
                }
            }, queryModel);
        }
    }

    @Override
    public void updateWork(WorkModel workModel) {
        if (view.isConnected()) {
            view.showLoading();
            updateWork.execute(new GenericObserver<String>(view) {
                @Override
                public void onSuccess(String s) {
                    super.onSuccess(s);
                    view.showSnackBar(WorkflowUtils.SNACK_BAR_SUCCESS, s);
                }
            }, workModel);
        }
    }
}

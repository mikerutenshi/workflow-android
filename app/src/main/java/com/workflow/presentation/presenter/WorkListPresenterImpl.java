package com.workflow.presentation.presenter;

import com.workflow.data.model.GenericItemPaginationModel;
import com.workflow.data.model.SearchPaginatedQueryModel;
import com.workflow.data.model.WorkModel;
import com.workflow.domain.interactor.work.DeleteWork;
import com.workflow.domain.interactor.work.GetWorks;
import com.workflow.presentation.view.WorkView;
import com.workflow.utils.WorkflowUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 28/06/19.
 */

public class WorkListPresenterImpl implements WorkListPresenter {

    private final WorkView view;
    private final GetWorks getWorks;
    private final DeleteWork deleteWork;
    private int currentPage = 1;
    private int limit = 10;
    private boolean isLoading = false;
    private boolean isLastPage = false;

    public WorkListPresenterImpl(WorkView view, GetWorks getWorks, DeleteWork deleteWork) {
        this.view = view;
        this.getWorks = getWorks;
        this.deleteWork = deleteWork;
    }

    @Override
    public void resume() {
        currentPage = 1;
        if (view.isConnected()) {
            view.showLoading();
            getWorks(null);
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        getWorks.dispose();
    }

    @Override
    public void getWorks(String spkNo) {
        if (view.isConnected()) {
            SearchPaginatedQueryModel param = new SearchPaginatedQueryModel(spkNo, limit, currentPage);
            getWorks.execute(new GenericObserver<GenericItemPaginationModel<List<WorkModel>>>(view) {
                @Override
                public void onSuccess(GenericItemPaginationModel<List<WorkModel>> listGenericItemPaginationModel) {
                    super.onSuccess(listGenericItemPaginationModel);
                    if (listGenericItemPaginationModel.getItems().size() > 0) {
                        view.renderItems(listGenericItemPaginationModel, currentPage);
                    } else {
                        view.showDataEmpty();
                    }
                }
            }, param);
        }
    }

    @Override
    public void deleteWork(final List<WorkModel> param) {
        if (view.isConnected()) {
            view.showLoading();
            List<Integer> idsToDelete = new ArrayList<>();
            for (WorkModel workModel : param) {
                idsToDelete.add(workModel.getId());
            }
            deleteWork.execute(new GenericObserver<String>(view) {
                @Override
                public void onSuccess(String s) {
                    super.onSuccess(s);
//                    view.adapterDeleteItem(param);
                    refreshData();
                    view.showSnackBar(WorkflowUtils.SNACK_BAR_SUCCESS, s);
                }
            }, idsToDelete);
        }
    }

    @Override
    public Integer getCurrentPage() {
        return currentPage;
    }

    @Override
    public Integer getLimit() {
        return limit;
    }

    @Override
    public boolean isLoading() {
        return isLoading;
    }

    @Override
    public boolean isLastPage() {
        return isLastPage;
    }

    @Override
    public void setIsLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    @Override
    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    @Override
    public void incrementCurrentPage() {
        currentPage ++;
    }

    @Override
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    @Override
    public void refreshData() {
        currentPage = 1;
        if (view.isConnected()) {
            view.showLoading();
            getWorks(null);
        }
    }
}

package com.workflow.presentation.presenter;

import com.workflow.data.model.GenericItemPaginationModel;
import com.workflow.data.model.GetWorkerQueryModel;
import com.workflow.data.model.SearchPaginatedQueryModel;
import com.workflow.data.model.WorkerModel;
import com.workflow.domain.interactor.worker.DeleteWorker;
import com.workflow.domain.interactor.worker.GetWorkers;
import com.workflow.presentation.view.WorkerView;
import com.workflow.utils.Positions;
import com.workflow.utils.WorkflowUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 28/06/19.
 */

public class WorkerListPresenterImpl implements WorkerListPresenter {

    private final WorkerView view;
    private final GetWorkers getWorkers;
    private final DeleteWorker deleteWorker;

    private int currentPage = 1;
    private int limit = WorkflowUtils.LIMIT;
    private boolean isLoading = false;
    private boolean isLastPage = false;

    public WorkerListPresenterImpl(WorkerView view, GetWorkers getWorkers, DeleteWorker deleteWorker) {
        this.view = view;
        this.getWorkers = getWorkers;
        this.deleteWorker = deleteWorker;
    }

    @Override
    public void resume() {
        currentPage = 1;
        if (view.isConnected()) {
            view.showLoading();
            getWorkers(null, view.getPositions(), view.getSortBy(), view.getSortDirection());
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        getWorkers.dispose();
    }

    @Override
    public void getWorkers(String name, List<String> positions, String sortBy, String sortDirection) {
        if (view.isConnected()) {
            GetWorkerQueryModel params = new GetWorkerQueryModel(
                    name, limit, currentPage, positions, sortBy, sortDirection);
            getWorkers.execute(new GenericObserver<GenericItemPaginationModel<List<WorkerModel>>>(view) {
                @Override
                public void onSuccess(GenericItemPaginationModel<List<WorkerModel>> listGenericItemPaginationModel) {
                    if (listGenericItemPaginationModel.getItems().size() > 0) {
                        view.showContent();
                        view.renderItems(listGenericItemPaginationModel, currentPage);
                    } else {
                        view.showDataEmptyView();
                    }
                }
            }, params);
        }
    }

    @Override
    public void deleteWorker(final List<WorkerModel> param) {
        if (view.isConnected()) {
            view.showLoading();
            List<Integer> idsToDelete = new ArrayList<>();
            for (WorkerModel workerModel : param) {
                idsToDelete.add(workerModel.getId());
            }
            deleteWorker.execute(new GenericObserver<String>(view) {
                @Override
                public void onSuccess(String s) {
                    super.onSuccess(s);
                    refreshData();
//                    view.adapterDeleteItem(param);
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
        this.currentPage ++;
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
            getWorkers(null, view.getPositions(), view.getSortBy(), view.getSortDirection());
        }
    }
}

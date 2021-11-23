package com.workflow.presentation.presenter;

import com.workflow.data.model.DoneWorkListModel;
import com.workflow.data.model.RequestModel;
import com.workflow.data.net.responses.GenericListResponseModel;
import com.workflow.domain.interactor.done_work.DeleteDoneWork;
import com.workflow.domain.interactor.done_work.GetDoneWorks;
import com.workflow.presentation.view.WorkerDetailWorkerWorkView;
import com.workflow.utils.WorkflowUtils;

import java.util.HashMap;

public class WorkDonePresenterImpl implements WorkerDetailWorkerWorkPresenter<DoneWorkListModel> {
    private int currentPage = 1;
    private int limit = 10;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private final WorkerDetailWorkerWorkView view;
    private final GetDoneWorks getDoneWorks;
    private final DeleteDoneWork deleteDoneWork;
    private final Integer workerId;

    public WorkDonePresenterImpl(WorkerDetailWorkerWorkView view, GetDoneWorks getDoneWorks, DeleteDoneWork deleteDoneWork, Integer workerId) {
        this.view = view;
        this.getDoneWorks = getDoneWorks;
        this.deleteDoneWork = deleteDoneWork;
        this.workerId = workerId;
    }

    @Override
    public void getWorks() {
        if (view.isConnected()) {
            HashMap options = view.getOptions();
            if (options.get("page") != null) {
                options.remove("page");
            }
            if (options.get("limit") != null) {
                options.remove("limit");
            }
            options.put("page", String.valueOf(currentPage));
            options.put("limit", String.valueOf(limit));
            getDoneWorks.execute(new GenericObserver<GenericListResponseModel<DoneWorkListModel>>(view) {
                @Override
                public void onSuccess(GenericListResponseModel<DoneWorkListModel> responseModel) {
                    super.onSuccess(responseModel);
                    view.renderItems(responseModel, currentPage);
                }
            }, new RequestModel(workerId, options));
        }
    }

    @Override
    public void deleteWorkerWork(DoneWorkListModel selectedModel) {
        if (view.isConnected()) {
            view.showLoading();
            deleteDoneWork.execute(new GenericObserver<String>(view) {
                @Override
                public void onSuccess(String s) {
                    super.onSuccess(s);
                    refreshData();
                    view.showSnackBar(WorkflowUtils.SNACK_BAR_SUCCESS, s);
                }
            }, selectedModel.getId());
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
            getWorks();
        }
    }

    @Override
    public void resume() {
        currentPage = 1;
        if (view.isConnected()) {
            view.showLoading();
            getWorks();
        }

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        getDoneWorks.dispose();
        deleteDoneWork.dispose();
    }
}

package com.workflow.presentation.presenter;

import com.workflow.data.model.DoneWorkCreateModel;
import com.workflow.data.model.DoneWorkListModel;
import com.workflow.data.model.RequestModel;
import com.workflow.data.net.responses.GenericListResponseModel;
import com.workflow.domain.interactor.done_work.GetDoneables;
import com.workflow.domain.interactor.done_work.PostDoneWork;
import com.workflow.presentation.view.WorkerWorkAssignView;
import com.workflow.utils.WorkflowUtils;

import java.util.HashMap;

public class WorkDoPresenterImpl implements  WorkerWorkAssignPresenter<DoneWorkListModel>{

    private final WorkerWorkAssignView view;
    private final GetDoneables getDoneables;
    private final PostDoneWork postAssignedWork;
    private final Integer mWorkerId;
    private String mWorkerPos;

    private int currentPage = 1;
    private int limit = WorkflowUtils.LIMIT;
    private boolean isLoading = false;
    private boolean isLastPage = false;

    public WorkDoPresenterImpl(WorkerWorkAssignView view, GetDoneables getDoneables, PostDoneWork postAssignedWork, Integer mWorkerId, String mWorkerPos) {
        this.view = view;
        this.getDoneables = getDoneables;
        this.postAssignedWork = postAssignedWork;
        this.mWorkerId = mWorkerId;
        this.mWorkerPos = mWorkerPos;
    }

    @Override
    public void getWorks() {
        if (view.isConnected()) {
            HashMap<String, String> options = view.getOptions();
            options.put("position", mWorkerPos);
            options.put("page", String.valueOf(currentPage));
            options.put("limit", String.valueOf(limit));
            getDoneables.execute(new GenericObserver<GenericListResponseModel<DoneWorkListModel>>(view) {
                @Override
                public void onSuccess(GenericListResponseModel<DoneWorkListModel> responseModel) {
                    if (responseModel.getItems().size() > 0) {
                        view.showContent();
                        view.renderItems(responseModel, currentPage);
                    } else {
                        view.showDataEmpty();
                    }
                }
            }, new RequestModel(mWorkerId, options));
        }

    }

    @Override
    public void postWork(DoneWorkListModel selectedModel) {
//        Timber.d("WORK_ID %d", selectedModel.getWorkId());
//        Timber.d("WORKER_ID %d", mWorkerId);
//        Timber.d("WORKER_POSITION %s", mWorkerPos);
//        Timber.d("QUANTITY %d", selectedModel.getQuantityDone());
//        Timber.d("DONE_AT %s", selectedModel.getDoneAt());
//        Timber.d("ASSIGNED_WORK_ID %d", selectedModel.getId());
        if (view.isConnected()) {
            view.showLoading();
            postAssignedWork.execute(new GenericObserver<String>(view) {
                @Override
                public void onSuccess(String s) {
                    super.onSuccess(s);
                    view.showSnackBar(WorkflowUtils.SNACK_BAR_SUCCESS, s);
                    view.clearEtQuantity();
                    view.refreshRv();
//                    refreshData();
                }
            }, new DoneWorkCreateModel(
                    selectedModel.getWorkId(),
                    mWorkerId,
                    mWorkerPos,
                    selectedModel.getQuantityDone(),
                    selectedModel.getDoneAt(),
                    selectedModel.getId(),
                    selectedModel.getNotes()));
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
    public String getWorkerPos() {
        return mWorkerPos;
    }

    @Override
    public void setWorkerPos(String position) {
        this.mWorkerPos = position;
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
        getDoneables.dispose();
        postAssignedWork.dispose();
    }
}

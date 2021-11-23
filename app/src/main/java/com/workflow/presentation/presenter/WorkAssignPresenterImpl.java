package com.workflow.presentation.presenter;

import com.workflow.data.model.AssignedWorkCreateModel;
import com.workflow.data.model.AssignedWorkListModel;
import com.workflow.data.model.RequestModel;
import com.workflow.data.net.responses.GenericListResponseModel;
import com.workflow.domain.interactor.assigned_work.GetAssignedWorksSource;
import com.workflow.domain.interactor.assigned_work.PostAssignedWork;
import com.workflow.presentation.view.WorkerWorkAssignView;
import com.workflow.utils.WorkflowUtils;

import java.util.HashMap;

/**
 * Created by Michael on 06/07/19.
 */

public class WorkAssignPresenterImpl implements WorkerWorkAssignPresenter<AssignedWorkListModel> {

    private final WorkerWorkAssignView view;
    private final GetAssignedWorksSource getAssignedWorksSource;
    private final PostAssignedWork postAssignedWork;
    private final Integer mWorkerId;
    private String mWorkerPos;

    private int currentPage = 1;
    private int limit = 10;
    private boolean isLoading = false;
    private boolean isLastPage = false;

    public WorkAssignPresenterImpl(WorkerWorkAssignView view, GetAssignedWorksSource getAssignedWorksSource, PostAssignedWork postAssignedWork, Integer mWorkerId, String mWorkerPos) {
        this.view = view;
        this.getAssignedWorksSource = getAssignedWorksSource;
        this.postAssignedWork = postAssignedWork;
        this.mWorkerId = mWorkerId;
        this.mWorkerPos = mWorkerPos;
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
        getAssignedWorksSource.dispose();
        postAssignedWork.dispose();
    }

    @Override
    public void getWorks() {
        if (view.isConnected()) {
            HashMap<String, String> options = view.getOptions();
            options.put("position", mWorkerPos);
            options.put("page", String.valueOf(currentPage));
            options.put("limit", String.valueOf(limit));
            getAssignedWorksSource.execute(new GenericObserver<GenericListResponseModel<AssignedWorkListModel>>(view) {
                @Override
                public void onSuccess(GenericListResponseModel<AssignedWorkListModel> responseModel) {
                    if (responseModel.getItems().size() > 0) {
                        view.showContent();
                        view.renderItems(responseModel, currentPage);
                    } else {
                        view.showDataEmpty();
                    }
                }
            }, new RequestModel(null, options));
        }
    }

    @Override
    public void postWork(AssignedWorkListModel selectedModel) {
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
            }, new AssignedWorkCreateModel(
                    selectedModel.getWorkId(),
                    mWorkerId,
                    mWorkerPos,
                    selectedModel.getQuantityAssigned(),
                    selectedModel.getAssignedAt(),
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
}

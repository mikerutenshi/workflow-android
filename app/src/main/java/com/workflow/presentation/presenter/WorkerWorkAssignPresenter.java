package com.workflow.presentation.presenter;

import com.workflow.data.model.GetUnassignedWorkQueryModel;
import com.workflow.data.model.WorkModel;
import com.workflow.data.model.WorkerWorkModel;

import java.util.List;

/**
 * Created by Michael on 06/07/19.
 */

public interface WorkerWorkAssignPresenter<T> extends BasePresenter {
    void getWorks();
    void postWork(T selectedModel);

    Integer getCurrentPage();
    Integer getLimit();
    boolean isLoading();
    boolean isLastPage();
    String getWorkerPos();
    void setWorkerPos(String position);

    void setIsLoading(boolean isLoading);
    void setIsLastPage(boolean isLastPage);
    void incrementCurrentPage();
    void setCurrentPage(int currentPage);

    void refreshData();
}

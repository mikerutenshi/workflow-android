package com.workflow.presentation.presenter;

import com.workflow.data.model.WorkerWorkModel;

import java.util.List;

public interface WorkerDetailWorkerWorkPresenter<T> extends BasePresenter {
    void getWorks();
    void deleteWorkerWork(T selectedModel);

    Integer getCurrentPage();
    Integer getLimit();
    boolean isLoading();
    boolean isLastPage();

    void setIsLoading(boolean isLoading);
    void setIsLastPage(boolean isLastPage);
    void incrementCurrentPage();
    void setCurrentPage(int currentPage);

    void refreshData();
}

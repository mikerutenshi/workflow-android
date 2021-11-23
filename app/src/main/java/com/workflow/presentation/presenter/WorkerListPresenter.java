package com.workflow.presentation.presenter;

import com.workflow.data.model.WorkerModel;

import java.util.List;

/**
 * Created by Michael on 28/06/19.
 */

public interface WorkerListPresenter extends BasePresenter {
    void getWorkers(String name, List<String> positions, String sortBy, String sortDirection);
    void deleteWorker(List<WorkerModel> workerModels);

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

package com.workflow.presentation.presenter;

import com.workflow.data.model.WorkModel;

import java.util.List;

/**
 * Created by Michael on 28/06/19.
 */

public interface WorkListPresenter extends BasePresenter {
    void getWorks(String spkNo);
    void deleteWork(List<WorkModel> param);

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

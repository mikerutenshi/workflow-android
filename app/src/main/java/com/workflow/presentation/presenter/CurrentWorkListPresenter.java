package com.workflow.presentation.presenter;

import com.workflow.data.model.WorkModel;

import java.util.List;

public interface CurrentWorkListPresenter extends BasePresenter {
    void getCurrentWorks();
    void deleteWork(Integer workId);
}

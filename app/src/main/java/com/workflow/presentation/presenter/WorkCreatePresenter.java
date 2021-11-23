package com.workflow.presentation.presenter;

import com.workflow.data.model.SearchPaginatedQueryModel;
import com.workflow.data.model.WorkModel;

/**
 * Created by Michael on 30/06/19.
 */

public interface WorkCreatePresenter extends BasePresenter {
    void postWork(WorkModel param);
    void getProducts(SearchPaginatedQueryModel queryModel);
    void updateWork(WorkModel workModel);
}

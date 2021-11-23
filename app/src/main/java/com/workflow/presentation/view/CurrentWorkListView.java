package com.workflow.presentation.view;

import com.workflow.data.model.CurrentWorkListModel;
import com.workflow.data.net.responses.GenericListResponseModel;
import com.workflow.presentation.view.adapters.CurrentWorkListAdapter;

import java.util.HashMap;

public interface CurrentWorkListView extends BaseView {
    void renderItems(GenericListResponseModel<CurrentWorkListModel> items);
    HashMap<String, String> getOptions();
    CurrentWorkListAdapter getAdapter();
    void showDataEmpty();
    void searchHandle(String keyword);
    void filterHandle(HashMap<String, String> options);
}

package com.workflow.presentation.view;

import com.workflow.data.net.responses.GenericListResponseModel;

import java.util.ArrayList;
import java.util.HashMap;

public interface WorkerDetailWorkerWorkView<T> extends BaseView {

    void renderItems(GenericListResponseModel<T> items, int page);
    void adapterDeleteItem(T params);
    int getAdapterSelectedItemCount();
    void adapterClear();
    void showDataEmpty();
    void showFilter(ArrayList<String> positionArrayList);
    void searchWork();
    void deleteWork();
    void invalidateData();

    void resetRefreshingStatus();
    HashMap<String, String> getOptions();
}

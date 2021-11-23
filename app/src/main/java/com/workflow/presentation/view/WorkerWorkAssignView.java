package com.workflow.presentation.view;

import com.workflow.data.net.responses.GenericListResponseModel;

import java.util.HashMap;

/**
 * Created by Michael on 06/07/19.
 */

public interface WorkerWorkAssignView<T> extends BaseView {
    void renderItems(GenericListResponseModel<T> items, int page);
    void dismiss();
    void showDataEmpty();
    void refreshRv();
    T getSelectedItem();

    void resetRefreshingStatus();
    HashMap<String, String> getOptions();
    void clearEtQuantity();
}

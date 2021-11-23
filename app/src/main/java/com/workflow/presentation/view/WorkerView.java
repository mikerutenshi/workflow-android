package com.workflow.presentation.view;

import com.workflow.data.model.GenericItemPaginationModel;
import com.workflow.data.model.WorkerModel;

import java.util.List;

/**
 * Created by Michael on 28/06/19.
 */

public interface WorkerView extends BaseView {
    void renderItems(GenericItemPaginationModel<List<WorkerModel>> items, int page);
    void adapterDeleteItem(List<WorkerModel> item);
    void showDataEmptyView();
    void resetRefreshingStatus();
    void adapterClear();
    int adapterSize();
    void removeSelectedItems();
    void resetSelectedItemCount();
    void refreshData();

    List<String> getPositions();
    String getSortBy();
    String getSortDirection();
}

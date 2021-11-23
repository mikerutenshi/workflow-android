package com.workflow.presentation.view;

import com.workflow.data.model.GenericItemPaginationModel;
import com.workflow.data.model.WorkModel;

import java.util.List;

/**
 * Created by Michael on 28/06/19.
 */

public interface WorkView extends BaseView {
    void renderItems(GenericItemPaginationModel<List<WorkModel>> items, int page);
    void adapterDeleteItem(List<WorkModel> param);
    void showDataEmpty();
    void resetRefreshingStatus();
    void removeSelectedItems();
    void resetSelectedItemCount();
    void refreshData();

    void adapterClear();
    int adapterSize();
}

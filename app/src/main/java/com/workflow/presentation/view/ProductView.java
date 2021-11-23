package com.workflow.presentation.view;

import com.workflow.data.model.GenericItemPaginationModel;
import com.workflow.data.model.ProductModel;

import java.util.List;

/**
 * Created by Michael on 18/06/19.
 */

public interface ProductView extends BaseView {
    void renderItems(GenericItemPaginationModel<List<ProductModel>> items, int page);
    void adapterDeleteItem(List<ProductModel> param);
    void showDataEmpty();
    void resetRefreshingStatus();
    void removeSelectedItems();
    void resetSelectedItemCount();

    void adapterClear();
    int adapterSize();
    void refreshData();
}

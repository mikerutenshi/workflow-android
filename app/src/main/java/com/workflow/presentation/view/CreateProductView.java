package com.workflow.presentation.view;

import com.workflow.data.model.ListModel;

import java.util.List;

/**
 * Created by Michael on 21/06/19.
 */

public interface CreateProductView extends BaseView {
    void dismiss();
    void showSnackThenClearForm(int type, String msg);
    void renderProductCategories(List<ListModel> productCategories);
    int getAdapterSize();
}

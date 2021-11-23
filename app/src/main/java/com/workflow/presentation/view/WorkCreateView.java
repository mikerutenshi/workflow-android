package com.workflow.presentation.view;

import com.workflow.data.model.GenericItemPaginationModel;
import com.workflow.data.model.ListModel;

import java.util.List;

/**
 * Created by Michael on 30/06/19.
 */

public interface WorkCreateView extends BaseView {
    void dismiss();
    void renderProducts(GenericItemPaginationModel<List<ListModel>> items);
    void showDialogError(String err);
    void showDialogDataEmpty();
    void showSnackThenClearForm(int type, String msg);
}

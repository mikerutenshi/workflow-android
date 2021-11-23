package com.workflow.presentation.view;

/**
 * Created by Michael on 29/06/19.
 */

public interface WorkerCreateView extends BaseView {
    void dismiss();
    void showSnackThenClearForm(int type, String msg);
}

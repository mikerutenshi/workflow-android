package com.workflow.presentation.view;

/**
 * Created by Michael on 18/06/19.
 */

public interface BaseView {
    void showLoading();
    void showContent();
    void showSnackBar(int type, String msg);
    boolean isConnected();
}

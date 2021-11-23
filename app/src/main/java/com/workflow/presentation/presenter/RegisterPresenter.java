package com.workflow.presentation.presenter;

import com.workflow.data.model.UserModel;

public interface RegisterPresenter extends BasePresenter {
    void register(UserModel userModel);
}

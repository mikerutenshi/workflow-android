package com.workflow.presentation.view;

import com.workflow.data.model.UserModel;

public interface SignInView extends BaseView {
    void saveUser(UserModel userModel);
}

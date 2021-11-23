package com.workflow.presentation.presenter;

import com.workflow.data.model.SignInModel;

public interface SignInPresenter extends BasePresenter {
    void signIn(SignInModel signInModel);
}

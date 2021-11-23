package com.workflow.presentation.presenter;

import com.workflow.data.model.SignInModel;
import com.workflow.data.model.UserModel;
import com.workflow.domain.interactor.auth.SignIn;
import com.workflow.presentation.view.SignInView;

public class SignInPresenterImpl implements SignInPresenter {

    private final SignIn signIn;
    private final SignInView view;

    public SignInPresenterImpl(SignIn signIn, SignInView view) {
        this.signIn = signIn;
        this.view = view;
    }

    @Override
    public void signIn(SignInModel signInModel) {
        if (view.isConnected()) {
            view.showLoading();
            signIn.execute(new GenericObserver<UserModel>(view) {
                @Override
                public void onSuccess(UserModel userModel) {
                    super.onSuccess(userModel);
                    view.saveUser(userModel);
                }
            }, signInModel);
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        signIn.dispose();
    }
}

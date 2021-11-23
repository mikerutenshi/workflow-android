package com.workflow.presentation.di.modules;

import com.workflow.data.model.UserModel;
import com.workflow.domain.interactor.auth.Register;
import com.workflow.presentation.presenter.GenericObserver;
import com.workflow.presentation.presenter.RegisterPresenter;
import com.workflow.presentation.view.RegisterView;

import timber.log.Timber;

public class RegisterPresenterImpl implements RegisterPresenter {

    private final Register register;
    private final RegisterView view;

    public RegisterPresenterImpl(Register register, RegisterView view) {
        this.register = register;
        this.view = view;
    }

    @Override
    public void register(UserModel userModel) {
        if (view.isConnected()) {
            view.showLoading();
            register.execute(new GenericObserver<String>(view) {
                @Override
                public void onSuccess(String s) {
                    super.onSuccess(s);
                    view.onRegisterSuccess(s);
                }
            }, userModel);
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
        register.dispose();
    }
}

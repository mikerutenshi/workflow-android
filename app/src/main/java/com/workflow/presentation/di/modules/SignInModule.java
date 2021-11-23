package com.workflow.presentation.di.modules;

import com.workflow.domain.interactor.auth.SignIn;
import com.workflow.presentation.presenter.SignInPresenter;
import com.workflow.presentation.presenter.SignInPresenterImpl;
import com.workflow.presentation.view.SignInView;

import dagger.Module;
import dagger.Provides;

@Module
public class SignInModule {
    private final SignInView view;

    public SignInModule(SignInView view) {
        this.view = view;
    }

    @Provides
    SignInPresenter signInPresenter(SignIn signIn) {
        return new SignInPresenterImpl(signIn, view);
    }
}

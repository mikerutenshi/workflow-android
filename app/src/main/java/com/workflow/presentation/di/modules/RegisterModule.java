package com.workflow.presentation.di.modules;

import com.workflow.domain.interactor.auth.Register;
import com.workflow.presentation.presenter.RegisterPresenter;
import com.workflow.presentation.view.RegisterView;

import dagger.Module;
import dagger.Provides;

@Module
public class RegisterModule {
    private final RegisterView view;

    public RegisterModule(RegisterView view) {
        this.view = view;
    }

    @Provides
    RegisterPresenter registerPresenter(Register register) {
        return new RegisterPresenterImpl(register, view);
    }
}

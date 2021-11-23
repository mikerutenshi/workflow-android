package com.workflow.presentation.di.modules;

import com.workflow.domain.interactor.auth.SignOut;
import com.workflow.presentation.presenter.MainActivityPresenter;
import com.workflow.presentation.presenter.MainActivityPresenterImpl;
import com.workflow.presentation.view.MainActivityView;

import dagger.Module;
import dagger.Provides;

@Module(includes = AuthModule.class)
public class MainActivityModule {

    private final MainActivityView view;

    public MainActivityModule(MainActivityView view) {
        this.view = view;
    }

    @Provides
    MainActivityPresenter mainActivityPresenter(SignOut signOut) {
        return new MainActivityPresenterImpl(signOut, view);
    }
}

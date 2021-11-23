package com.workflow.presentation.presenter;

import com.workflow.domain.interactor.auth.SignOut;
import com.workflow.presentation.view.MainActivityView;

public class MainActivityPresenterImpl implements MainActivityPresenter {

    private final SignOut signOut;
    private final MainActivityView view;

    public MainActivityPresenterImpl(SignOut signOut, MainActivityView view) {
        this.signOut = signOut;
        this.view = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void signOut(String userName) {
        if (view.isConnected()) {
            view.showLoading();
            signOut.execute(new GenericObserver<String>(view) {
                @Override
                public void onSuccess(String s) {
                    super.onSuccess(s);
                    view.navigateToAuthPage();
                }
            }, userName);
        }
    }
}

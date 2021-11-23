package com.workflow.presentation.presenter;

import com.workflow.data.model.WorkerModel;
import com.workflow.domain.interactor.worker.PostWorker;
import com.workflow.domain.interactor.worker.UpdateWorker;
import com.workflow.presentation.view.WorkerCreateView;
import com.workflow.utils.WorkflowUtils;

/**
 * Created by Michael on 29/06/19.
 */

public class WorkerCreatePresenterImpl implements WorkerCreatePresenter {

    private final PostWorker postWorker;
    private final UpdateWorker updateWorker;
    private final WorkerCreateView view;

    public WorkerCreatePresenterImpl(PostWorker postWorker, UpdateWorker updateWorker, WorkerCreateView view) {
        this.postWorker = postWorker;
        this.updateWorker = updateWorker;
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
        postWorker.dispose();
    }

    @Override
    public void postWorker(WorkerModel param) {
        if (view.isConnected()) {
            view.showLoading();
            postWorker.execute(new GenericObserver<String>(view) {
                @Override
                public void onSuccess(String s) {
                    super.onSuccess(s);
                    view.showSnackThenClearForm(WorkflowUtils.SNACK_BAR_SUCCESS, s);
                }
            }, param);
        }
    }

    @Override
    public void updateWorker(WorkerModel workerModel) {
        if (view.isConnected()) {
            view.showLoading();
            updateWorker.execute(new GenericObserver<String>(view) {
                @Override
                public void onSuccess(String s) {
                    super.onSuccess(s);
                    view.showSnackBar(WorkflowUtils.SNACK_BAR_SUCCESS, s);
                }
            }, workerModel);
        }
    }
}

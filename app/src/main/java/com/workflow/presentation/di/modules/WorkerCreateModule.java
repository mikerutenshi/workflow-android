package com.workflow.presentation.di.modules;

import com.workflow.domain.interactor.worker.PostWorker;
import com.workflow.domain.interactor.worker.UpdateWorker;
import com.workflow.presentation.presenter.WorkerCreatePresenter;
import com.workflow.presentation.presenter.WorkerCreatePresenterImpl;
import com.workflow.presentation.view.WorkerCreateView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Michael on 29/06/19.
 */

@Module(includes = { WorkerModule.class })
public class WorkerCreateModule {

    private final WorkerCreateView view;

    public WorkerCreateModule(WorkerCreateView view) {
        this.view = view;
    }

    @Provides
    WorkerCreatePresenter workerCreatePresenter(PostWorker postWorker, UpdateWorker updateWorker) {
        return new WorkerCreatePresenterImpl(postWorker, updateWorker, view);
    }
}

package com.workflow.presentation.di.modules;

import android.content.Context;

import com.workflow.domain.interactor.worker.DeleteWorker;
import com.workflow.domain.interactor.worker.GetWorkers;
import com.workflow.presentation.presenter.WorkerListPresenter;
import com.workflow.presentation.presenter.WorkerListPresenterImpl;
import com.workflow.presentation.view.WorkerView;
import com.workflow.presentation.view.adapters.WorkerAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Michael on 28/06/19.
 */

@Module(includes = { WorkerModule.class, AdapterModule.class })
public class WorkerListModule {

    private final WorkerView view;

    public WorkerListModule(WorkerView view) {
        this.view = view;
    }

    @Provides
    WorkerAdapter workerAdapter(Context context) {
        return new WorkerAdapter(context);
    }

    @Provides
    WorkerListPresenter workerListPresenter(GetWorkers getWorkers, DeleteWorker deleteWorker) {
        return new WorkerListPresenterImpl(view, getWorkers, deleteWorker);
    }
}

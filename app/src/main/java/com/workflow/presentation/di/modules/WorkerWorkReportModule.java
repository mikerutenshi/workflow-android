package com.workflow.presentation.di.modules;

import android.content.Context;

import com.workflow.domain.interactor.worker_work.GetWorkerWorkReport;
import com.workflow.presentation.presenter.WorkerWorkReportPresenter;
import com.workflow.presentation.presenter.WorkerWorkReportPresenterImpl;
import com.workflow.presentation.view.WorkerWorkReportView;
import com.workflow.presentation.view.adapters.WorkerWorkReportAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Michael on 10/07/19.
 */

@Module(includes = ContextModule.class)
public class WorkerWorkReportModule {
    private final WorkerWorkReportView view;

    public WorkerWorkReportModule(WorkerWorkReportView view) {
        this.view = view;
    }

    @Provides
    WorkerWorkReportAdapter workerWorkReportAdapter(Context context) {
        return new WorkerWorkReportAdapter(context);
    }

    @Provides
    WorkerWorkReportPresenter presenter(GetWorkerWorkReport getWorkerWorkReport) {
        return new WorkerWorkReportPresenterImpl(view, getWorkerWorkReport);
    }
}

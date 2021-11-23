package com.workflow.presentation.di.modules;

import android.content.Context;

import com.workflow.domain.interactor.worker_work.GetWorkDetailReport;
import com.workflow.presentation.presenter.ReportWorkerDetailPresenter;
import com.workflow.presentation.presenter.ReportWorkerDetailPresenterImpl;
import com.workflow.presentation.view.ReportWorkerDetailView;
import com.workflow.presentation.view.adapters.ReportWorkerDetailAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Michael on 25/07/19.
 */

@Module
public class ReportWorkerDetailModule {

    private final ReportWorkerDetailView view;

    public ReportWorkerDetailModule(ReportWorkerDetailView view) {
        this.view = view;
    }

    @Provides
    ReportWorkerDetailAdapter workerDetailAdapter(Context context) {
        return new ReportWorkerDetailAdapter(context);
    }

    @Provides
    ReportWorkerDetailPresenter reportWorkerDetailPresenter(GetWorkDetailReport getWorkDetailReport) {
        return new ReportWorkerDetailPresenterImpl(getWorkDetailReport, view);
    }
}

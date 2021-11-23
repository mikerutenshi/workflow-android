package com.workflow.presentation.presenter;

import com.workflow.data.model.DateFilterModel;
import com.workflow.data.model.WorkerWorkReportModel;
import com.workflow.domain.interactor.worker_work.GetWorkerWorkReport;
import com.workflow.presentation.view.WorkerWorkReportView;

/**
 * Created by Michael on 10/07/19.
 */

public class WorkerWorkReportPresenterImpl implements WorkerWorkReportPresenter {

    private final WorkerWorkReportView view;
    private final GetWorkerWorkReport getWorkerWorkReport;

    public WorkerWorkReportPresenterImpl(WorkerWorkReportView view, GetWorkerWorkReport getWorkerWorkReport) {
        this.view = view;
        this.getWorkerWorkReport = getWorkerWorkReport;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        getWorkerWorkReport.dispose();
    }

    @Override
    public void getWorkerWorkReport(DateFilterModel dateFilterModel) {
        if (view.isConnected()) {
            view.showLoading();
            getWorkerWorkReport.execute(new GenericObserver<WorkerWorkReportModel>(view) {
                @Override
                public void onSuccess(WorkerWorkReportModel workerWorkReportModel) {
                    if (workerWorkReportModel.getReportListModels().size() > 0) {
                        view.showContent();
                        view.renderItems(workerWorkReportModel);
                    } else {
                        view.showDataEmptyView();
                    }
                }
            }, dateFilterModel);
        }
    }
}

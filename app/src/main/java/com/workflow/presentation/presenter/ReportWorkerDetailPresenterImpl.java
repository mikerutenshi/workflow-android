package com.workflow.presentation.presenter;

import com.workflow.data.model.ReportDetailDateFilterModel;
import com.workflow.data.model.ReportListModel;
import com.workflow.data.model.WorkDetailModel;
import com.workflow.domain.interactor.worker_work.GetWorkDetailReport;
import com.workflow.presentation.view.ReportWorkerDetailView;

import java.util.List;

/**
 * Created by Michael on 25/07/19.
 */

public class ReportWorkerDetailPresenterImpl implements ReportWorkerDetailPresenter {

    private final GetWorkDetailReport getWorkDetailReport;
    private final ReportWorkerDetailView view;
    private ReportListModel model;

    public ReportWorkerDetailPresenterImpl(GetWorkDetailReport getWorkDetailReport, ReportWorkerDetailView view) {
        this.getWorkDetailReport = getWorkDetailReport;
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
        getWorkDetailReport.dispose();
    }

    @Override
    public void getWorkDetailReport(ReportDetailDateFilterModel dateFilterModel) {
        if (view.isConnected()) {
            view.showLoading();
            getWorkDetailReport.execute(new GenericObserver<List<WorkDetailModel>>(view) {
                @Override
                public void onSuccess(List<WorkDetailModel> workDetailModels) {
                    super.onSuccess(workDetailModels);
                    view.showContent();
                    view.renderWorkDetail(workDetailModels);
                }
            }, dateFilterModel);
        }
    }
}

package com.workflow.presentation.presenter;

import com.workflow.data.model.RequestModel;
import com.workflow.data.model.SalaryReportDetailListModel;
import com.workflow.data.net.responses.GenericResponseModel;
import com.workflow.domain.interactor.salary_report.GetSalaryReportDetailList;
import com.workflow.presentation.view.SalaryReportDetailView;

public class SalaryReportDetailPresenterImpl implements SalaryReportDetailPresenter {

    private final GetSalaryReportDetailList getSalaryReportList;
    private final SalaryReportDetailView view;

    public SalaryReportDetailPresenterImpl(GetSalaryReportDetailList getSalaryReportList, SalaryReportDetailView view) {
        this.getSalaryReportList = getSalaryReportList;
        this.view = view;
    }

    @Override
    public void getSalaryReportDetail() {
        view.getOptions().put("page", String.valueOf(view.getAdapter().getCurrentPage()));
        view.getOptions().put("limit", String.valueOf(view.getAdapter().getLimit()));

        getSalaryReportList.execute(new GenericObserver<GenericResponseModel<SalaryReportDetailListModel>>(view) {
            @Override
            public void onSuccess(GenericResponseModel<SalaryReportDetailListModel> data) {
                super.onSuccess(data);
                view.renderData(data);
            }
        }, new RequestModel(view.getWorkerModel().getId(), view.getOptions()));
    }

    @Override
    public void resume() {
        refreshData();
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        getSalaryReportList.dispose();
    }

    private void refreshData() {
        view.getAdapter().setCurrentPage(1);
        if (view.isConnected()) {
            view.showLoading();
            getSalaryReportDetail();
        }
    }
}

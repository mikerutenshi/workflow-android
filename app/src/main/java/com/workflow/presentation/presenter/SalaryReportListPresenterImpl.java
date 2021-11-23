package com.workflow.presentation.presenter;

import com.workflow.data.model.RequestModel;
import com.workflow.data.model.SalaryReportListModel;
import com.workflow.data.net.responses.GenericResponseModel;
import com.workflow.domain.interactor.salary_report.GetSalaryReportList;
import com.workflow.presentation.view.SalaryReportListView;

public class SalaryReportListPresenterImpl implements SalaryReportListPresenter {
    private final GetSalaryReportList getSalaryReportList;
    private final SalaryReportListView view;

    public SalaryReportListPresenterImpl(GetSalaryReportList getSalaryReportList, SalaryReportListView view) {
        this.getSalaryReportList = getSalaryReportList;
        this.view = view;
    }

    @Override
    public void getSalaryReport() {
        view.getOptions().put("page", String.valueOf(view.getAdapter().getCurrentPage()));
        view.getOptions().put("limit", String.valueOf(view.getAdapter().getLimit()));

        getSalaryReportList.execute(new GenericObserver<GenericResponseModel<SalaryReportListModel>>(view) {
            @Override
            public void onSuccess(GenericResponseModel<SalaryReportListModel> data) {
                super.onSuccess(data);
                view.renderData(data);
            }
        }, new RequestModel(null, view.getOptions()));
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
            getSalaryReport();
        }
    }
}

package com.workflow.domain.interactor.salary_report;

import com.workflow.data.model.RequestModel;
import com.workflow.data.model.SalaryReportDetailListModel;
import com.workflow.data.net.responses.GenericResponseModel;
import com.workflow.domain.interactor.SingleUseCase;
import com.workflow.domain.repository.SalaryReportRepository;

import io.reactivex.Single;

public class GetSalaryReportDetailList extends SingleUseCase<GenericResponseModel<SalaryReportDetailListModel>, RequestModel> {
    private final SalaryReportRepository repository;

    public GetSalaryReportDetailList(SalaryReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<GenericResponseModel<SalaryReportDetailListModel>> buildUseCaseSingle(RequestModel params) {
        return repository.getSalaryReportDetailList(params);
    }
}

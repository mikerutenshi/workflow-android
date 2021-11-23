package com.workflow.domain.interactor.salary_report;

import com.workflow.data.model.RequestModel;
import com.workflow.data.model.SalaryReportListModel;
import com.workflow.data.net.responses.GenericResponseModel;
import com.workflow.domain.interactor.SingleUseCase;
import com.workflow.domain.repository.SalaryReportRepository;

import io.reactivex.Single;

public class GetSalaryReportList extends SingleUseCase<GenericResponseModel<SalaryReportListModel>, RequestModel> {

    private final SalaryReportRepository repository;

    public GetSalaryReportList(SalaryReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<GenericResponseModel<SalaryReportListModel>> buildUseCaseSingle(RequestModel params) {
        return repository.getSalaryReportList(params.getQueries());
    }
}

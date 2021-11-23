package com.workflow.data.repository;

import com.workflow.data.model.RequestModel;
import com.workflow.data.model.SalaryReportDetailListModel;
import com.workflow.data.model.SalaryReportListModel;
import com.workflow.data.net.responses.GenericResponseModel;
import com.workflow.data.repository.datasource.SalaryReportDataStoreFactory;
import com.workflow.domain.repository.SalaryReportRepository;

import java.util.Map;

import io.reactivex.Single;

public class SalaryReportDataRepository implements SalaryReportRepository {

    private final SalaryReportDataStoreFactory factory;

    public SalaryReportDataRepository(SalaryReportDataStoreFactory factory) {
        this.factory = factory;
    }

    @Override
    public Single<GenericResponseModel<SalaryReportListModel>> getSalaryReportList(Map<String, String> options) {
        return factory.create().getSalaryReportList(options);
    }

    @Override
    public Single<GenericResponseModel<SalaryReportDetailListModel>> getSalaryReportDetailList(RequestModel params) {
        return factory.create().getSalaryReportDetailList(params);
    }
}

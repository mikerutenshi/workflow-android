package com.workflow.domain.repository;

import com.workflow.data.model.RequestModel;
import com.workflow.data.model.SalaryReportDetailListModel;
import com.workflow.data.model.SalaryReportListModel;
import com.workflow.data.net.responses.GenericResponseModel;

import java.util.Map;

import io.reactivex.Single;

public interface SalaryReportRepository {
    Single<GenericResponseModel<SalaryReportListModel>> getSalaryReportList(Map<String, String> options);
    Single<GenericResponseModel<SalaryReportDetailListModel>> getSalaryReportDetailList(RequestModel params);
}

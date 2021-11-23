package com.workflow.data.repository;

import com.workflow.data.model.WorkDetailModel;
import com.workflow.data.model.ReportDetailDateFilterModel;
import com.workflow.data.repository.datasource.WorkDetailReportDataStoreFactory;
import com.workflow.domain.repository.WorkDetailReportRepository;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Michael on 25/07/19.
 */

public class WorkDetailReportDataRepository implements WorkDetailReportRepository {

    private final WorkDetailReportDataStoreFactory factory;

    public WorkDetailReportDataRepository(WorkDetailReportDataStoreFactory factory) {
        this.factory = factory;
    }

    @Override
    public Single<List<WorkDetailModel>> getWorkDetailReport(ReportDetailDateFilterModel filterModel) {
        return factory.create().getWorkDetailReport(filterModel);
    }
}

package com.workflow.data.repository;

import com.workflow.data.model.DateFilterModel;
import com.workflow.data.model.WorkerWorkReportModel;
import com.workflow.data.repository.datasource.WorkerWorkReportDataStoreFactory;
import com.workflow.domain.repository.WorkerWorkReportRepository;

import io.reactivex.Single;

/**
 * Created by Michael on 10/07/19.
 */

public class WorkerWorkReportDataRepository implements WorkerWorkReportRepository {

    private final WorkerWorkReportDataStoreFactory factory;

    public WorkerWorkReportDataRepository(WorkerWorkReportDataStoreFactory factory) {
        this.factory = factory;
    }
    @Override
    public Single<WorkerWorkReportModel> getWorkerWorkReport(DateFilterModel filterModel) {
        return factory.create().getWorkerWorkReport(filterModel);
    }
}

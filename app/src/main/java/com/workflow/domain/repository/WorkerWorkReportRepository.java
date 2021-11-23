package com.workflow.domain.repository;

import com.workflow.data.model.DateFilterModel;
import com.workflow.data.model.WorkerWorkReportModel;

import io.reactivex.Single;

/**
 * Created by Michael on 10/07/19.
 */

public interface WorkerWorkReportRepository {
    Single<WorkerWorkReportModel> getWorkerWorkReport(DateFilterModel filterModel);
}

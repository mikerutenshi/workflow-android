package com.workflow.data.repository.datasource;

import com.workflow.data.model.DateFilterModel;
import com.workflow.data.model.WorkerWorkReportModel;

import io.reactivex.Single;

/**
 * Created by Michael on 09/07/19.
 */

public interface WorkerWorkReportDataStore {
    Single<WorkerWorkReportModel> getWorkerWorkReport(DateFilterModel filter);
}

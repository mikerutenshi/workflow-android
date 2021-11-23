package com.workflow.data.repository.datasource;

/**
 * Created by Michael on 09/07/19.
 */

public class WorkerWorkReportDataStoreFactory {

    private final WorkerWorkReportCloudDataStore workerWorkReportCloudDataStore;

    public WorkerWorkReportDataStoreFactory(WorkerWorkReportCloudDataStore workerWorkReportCloudDataStore) {
        this.workerWorkReportCloudDataStore = workerWorkReportCloudDataStore;
    }

    public WorkerWorkReportDataStore create() {
        return workerWorkReportCloudDataStore;
    }
}

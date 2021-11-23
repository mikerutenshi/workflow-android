package com.workflow.data.repository.datasource;

/**
 * Created by Michael on 25/07/19.
 */

public class WorkDetailReportDataStoreFactory {

    private final WorkDetailReportCloudDataStore workDetailReportCloudDataStore;

    public WorkDetailReportDataStoreFactory(WorkDetailReportCloudDataStore workDetailReportCloudDataStore) {
        this.workDetailReportCloudDataStore = workDetailReportCloudDataStore;
    }

    public WorkDetailReportDataStore create() {
        return workDetailReportCloudDataStore;
    }
}

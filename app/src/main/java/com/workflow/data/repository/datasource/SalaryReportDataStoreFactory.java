package com.workflow.data.repository.datasource;

public class SalaryReportDataStoreFactory {

    private final SalaryReportListCloudDataStore cloudDataStore;

    public SalaryReportDataStoreFactory(SalaryReportListCloudDataStore cloudDataStore) {
        this.cloudDataStore = cloudDataStore;
    }

    public SalaryReportListDataStore create() {
        return cloudDataStore;
    }
}

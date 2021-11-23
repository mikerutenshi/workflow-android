package com.workflow.data.repository.datasource;

/**
 * Created by Michael on 27/06/19.
 */

public class WorkDataStoreFactory {
    private final WorkCloudDataStore workCloudDataStore;

    public WorkDataStoreFactory(WorkCloudDataStore workCloudDataStore) {
        this.workCloudDataStore = workCloudDataStore;
    }

    public WorkDataStore create() {
        return workCloudDataStore;
    }
}

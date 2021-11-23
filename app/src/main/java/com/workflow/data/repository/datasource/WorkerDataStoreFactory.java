package com.workflow.data.repository.datasource;

/**
 * Created by Michael on 27/06/19.
 */

public class WorkerDataStoreFactory {
    private final WorkerCloudDataStore workerCloudDataStore;

    public WorkerDataStoreFactory(WorkerCloudDataStore workerCloudDataStore) {
        this.workerCloudDataStore = workerCloudDataStore;
    }

    public WorkersDataStore create() {
        return workerCloudDataStore;
    }
}

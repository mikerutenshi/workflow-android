package com.workflow.data.repository.datasource;

/**
 * Created by Michael on 08/07/19.
 */

public class WorkerWorkDataStoreFactory {

    private final WorkerWorkCloudDataStore workerWorkCloudDataStore;

    public WorkerWorkDataStoreFactory(WorkerWorkCloudDataStore workerWorkCloudDataStore) {
        this.workerWorkCloudDataStore = workerWorkCloudDataStore;
    }

    public WorkerWorkDataStore create() {
        return workerWorkCloudDataStore;
    }
}

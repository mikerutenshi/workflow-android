package com.workflow.data.repository.datasource;

public class DoneWorkDataStoreFactory {

    private final DoneWorkCloudDataStore doneWorkCloudDataStore;

    public DoneWorkDataStoreFactory(DoneWorkCloudDataStore doneWorkCloudDataStore) {
        this.doneWorkCloudDataStore = doneWorkCloudDataStore;
    }

    public DoneWorkDataStore create() {
        return doneWorkCloudDataStore;
    }
}

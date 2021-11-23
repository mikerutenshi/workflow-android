package com.workflow.data.repository.datasource;

public class AssignedWorkDataStoreFactory {

    private final AssignedWorkCloudDataStore assignedWorkCloudDataStore;

    public AssignedWorkDataStoreFactory(AssignedWorkCloudDataStore assignedWorkCloudDataStore) {
        this.assignedWorkCloudDataStore = assignedWorkCloudDataStore;
    }

    public AssignedWorkDataStore create() {
        return assignedWorkCloudDataStore;
    }
}

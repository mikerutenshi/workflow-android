package com.workflow.data.repository.datasource;


public class AuthDataStoreFactory {
    private final AuthCloudDataStore authCloudDataStore;

    public AuthDataStoreFactory(AuthCloudDataStore authCloudDataStore) {
        this.authCloudDataStore = authCloudDataStore;
    }

    public AuthDataStore create() {
        return authCloudDataStore;
    }
}

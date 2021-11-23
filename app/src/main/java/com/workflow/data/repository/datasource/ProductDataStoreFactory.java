package com.workflow.data.repository.datasource;

/**
 * Created by Michael on 14/06/19.
 */

public class ProductDataStoreFactory {

    private final ProductCloudDataStore productCloudDataStore;

    public ProductDataStoreFactory(ProductCloudDataStore productCloudDataStore) {
        this.productCloudDataStore = productCloudDataStore;
    }

    public ProductDataStore create() {
        return productCloudDataStore;
    }
}

package com.workflow.data.repository.datasource;

public class ProductCategoryDataStoreFactory {

    private final ProductCategoryCloudDataStore productCategoryCloudDataStore;

    public ProductCategoryDataStoreFactory(ProductCategoryCloudDataStore productCategoryCloudDataStore) {
        this.productCategoryCloudDataStore = productCategoryCloudDataStore;
    }

    public ProductCategoryDataStore create() {
        return productCategoryCloudDataStore;
    }
}

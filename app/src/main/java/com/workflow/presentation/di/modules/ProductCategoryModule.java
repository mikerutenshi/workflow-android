package com.workflow.presentation.di.modules;

import com.workflow.data.repository.ProductCategoryDataRepository;
import com.workflow.data.repository.datasource.ProductCategoryCloudDataStore;
import com.workflow.data.repository.datasource.ProductCategoryDataStoreFactory;
import com.workflow.domain.interactor.product_category.GetProductCategories;
import com.workflow.domain.repository.ProductCategoryRepository;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = NetModule.class)
public class ProductCategoryModule {

    @Provides
    GetProductCategories getProductCategories(ProductCategoryRepository repository) {
        return new GetProductCategories(repository);
    }

    @Provides
    ProductCategoryRepository getRepository(ProductCategoryDataStoreFactory factory) {
        return new ProductCategoryDataRepository(factory);
    }

    @Provides
    ProductCategoryDataStoreFactory productCategoryDataStoreFactory(ProductCategoryCloudDataStore cloudDataStore) {
        return new ProductCategoryDataStoreFactory(cloudDataStore);
    }

    @Provides
    ProductCategoryCloudDataStore productCategoryCloudDataStore(Retrofit retrofit) {
        return new ProductCategoryCloudDataStore(retrofit);
    }
}

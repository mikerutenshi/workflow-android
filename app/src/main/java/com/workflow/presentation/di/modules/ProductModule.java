package com.workflow.presentation.di.modules;

import com.workflow.data.repository.ProductDataRepository;
import com.workflow.data.repository.datasource.ProductCloudDataStore;
import com.workflow.data.repository.datasource.ProductDataStoreFactory;
import com.workflow.domain.interactor.product.DeleteProduct;
import com.workflow.domain.interactor.product.GetProducts;
import com.workflow.domain.interactor.product.UpdateProduct;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Michael on 27/06/19.
 */

@Module(includes = NetModule.class)
public class ProductModule {

    @Provides
    GetProducts getProducts(ProductDataRepository repository) {
        return new GetProducts(repository);
    }

    @Provides
    DeleteProduct deleteProduct(ProductDataRepository repository) {
        return new DeleteProduct(repository);
    }

    @Provides
    UpdateProduct updateWorker(ProductDataRepository repository) {
        return new UpdateProduct(repository);
    }

    @Provides
    ProductDataRepository productDataRepository(ProductDataStoreFactory factory) {
        return new ProductDataRepository(factory);
    }

    @Provides
    ProductDataStoreFactory productDataStoreFactory(ProductCloudDataStore cloudDataStore) {
        return new ProductDataStoreFactory(cloudDataStore);
    }

    @Provides
    ProductCloudDataStore productCloudDataStore(Retrofit retrofit) {
        return new ProductCloudDataStore(retrofit);
    }
}

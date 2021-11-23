package com.workflow.presentation.di.modules;

import com.workflow.data.repository.WorkDataRepository;
import com.workflow.data.repository.datasource.WorkCloudDataStore;
import com.workflow.data.repository.datasource.WorkDataStoreFactory;
import com.workflow.domain.interactor.work.DeleteWork;
import com.workflow.domain.interactor.work.GetWorkDetailDone;
import com.workflow.domain.interactor.work.GetWorkList;
import com.workflow.domain.interactor.work.GetWorkDetailAssigned;
import com.workflow.domain.repository.WorkRepository;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = { NetModule.class })
public class CurrentWorkRepositoryModule {

    @Provides GetWorkList getWorkList(WorkRepository repository) {
        return new GetWorkList(repository);
    }
    @Provides GetWorkDetailAssigned getworkDetailAssigned(WorkRepository repository) {
        return new GetWorkDetailAssigned(repository);
    }
    @Provides GetWorkDetailDone getworkDetailDone(WorkRepository repository) {
        return new GetWorkDetailDone(repository);
    }
    @Provides DeleteWork deleteWork(WorkRepository repository) {
        return new DeleteWork(repository);
    }
    @Provides WorkRepository workRepository(WorkDataStoreFactory factory) {
        return new WorkDataRepository(factory);
    }
    @Provides WorkDataStoreFactory workDataStoreFactory(WorkCloudDataStore cloudDataStore) {
        return new WorkDataStoreFactory(cloudDataStore);
    }
    @Provides WorkCloudDataStore workCloudDataStore(Retrofit retrofit) {
        return new WorkCloudDataStore(retrofit);
    }
}

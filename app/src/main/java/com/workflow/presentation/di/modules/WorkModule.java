package com.workflow.presentation.di.modules;

import com.workflow.data.repository.WorkDataRepository;
import com.workflow.data.repository.datasource.WorkCloudDataStore;
import com.workflow.data.repository.datasource.WorkDataStoreFactory;
import com.workflow.domain.interactor.work.DeleteWork;
import com.workflow.domain.interactor.work.GetWorks;
import com.workflow.domain.interactor.work.GetWorksPerWorker;
import com.workflow.domain.interactor.work.GetWorksUnassigned;
import com.workflow.domain.interactor.work.PostWork;
import com.workflow.domain.interactor.work.UpdateWork;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Michael on 28/06/19.
 */

@Module(includes = NetModule.class )
public class WorkModule {

    @Provides
    GetWorks getWorks(WorkDataRepository repository) {
        return new GetWorks(repository);
    }

    @Provides
    GetWorksPerWorker getWorksPerWorker(WorkDataRepository repository) {
        return new GetWorksPerWorker(repository);
    }

    @Provides
    GetWorksUnassigned getWorksUnassigned(WorkDataRepository repository) {
        return new GetWorksUnassigned(repository);
    }

    @Provides
    PostWork postWork(WorkDataRepository repository) {
        return new PostWork(repository);
    }

    @Provides
    DeleteWork deleteWork(WorkDataRepository repository) {
        return new DeleteWork(repository);
    }

    @Provides
    UpdateWork updateWork(WorkDataRepository repository) {
        return new UpdateWork(repository);
    }

    @Provides
    WorkDataRepository workDataRepository(WorkDataStoreFactory factory) {
        return new WorkDataRepository(factory);
    }

    @Provides
    WorkDataStoreFactory workDataStoreFactory(WorkCloudDataStore cloudDataStore) {
        return new WorkDataStoreFactory(cloudDataStore);
    }

    @Provides
    WorkCloudDataStore workCloudDataStore(Retrofit retrofit) {
        return new WorkCloudDataStore(retrofit);
    }
}

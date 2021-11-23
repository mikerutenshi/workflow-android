package com.workflow.presentation.di.modules;

import com.workflow.data.repository.WorkerDataRepository;
import com.workflow.data.repository.datasource.WorkerCloudDataStore;
import com.workflow.data.repository.datasource.WorkerDataStoreFactory;
import com.workflow.domain.interactor.worker.DeleteWorker;
import com.workflow.domain.interactor.worker.GetWorkers;
import com.workflow.domain.interactor.worker.PostWorker;
import com.workflow.domain.interactor.worker.UpdateWorker;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Michael on 28/06/19.
 */

@Module(includes = NetModule.class)
public class WorkerModule {

    @Provides
    GetWorkers getWorkers(WorkerDataRepository repository) {
        return new GetWorkers(repository);
    }

    @Provides
    PostWorker postWorker(WorkerDataRepository repository) {
        return new PostWorker(repository);
    }

    @Provides
    DeleteWorker deleteWorker(WorkerDataRepository repository) {
        return new DeleteWorker(repository);
    }

    @Provides
    UpdateWorker updateWorker(WorkerDataRepository repository) {
        return new UpdateWorker(repository);
    }

    @Provides
    WorkerDataRepository workerDataRepository(WorkerDataStoreFactory factory) {
        return new WorkerDataRepository(factory);
    }

    @Provides
    WorkerDataStoreFactory workerDataStoreFactory(WorkerCloudDataStore cloudDataStore) {
        return new WorkerDataStoreFactory(cloudDataStore);
    }

    @Provides
    WorkerCloudDataStore workerCloudDataStore(Retrofit retrofit) {
        return new WorkerCloudDataStore(retrofit);
    }
}

package com.workflow.presentation.di.modules;

import com.workflow.data.repository.WorkerWorkDataRepository;
import com.workflow.data.repository.WorkerWorkReportDataRepository;
import com.workflow.data.repository.datasource.WorkerWorkCloudDataStore;
import com.workflow.data.repository.datasource.WorkerWorkDataStoreFactory;
import com.workflow.data.repository.datasource.WorkerWorkReportCloudDataStore;
import com.workflow.data.repository.datasource.WorkerWorkReportDataStoreFactory;
import com.workflow.domain.interactor.worker_work.DeleteWorkerWork;
import com.workflow.domain.interactor.worker_work.GetWorkerWork;
import com.workflow.domain.interactor.worker_work.GetWorkerWorkReport;
import com.workflow.domain.interactor.worker_work.PostWorkerWork;
import com.workflow.domain.repository.WorkerWorkReportRepository;
import com.workflow.domain.repository.WorkerWorkRepository;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Michael on 08/07/19.
 */

@Module(includes = NetModule.class)
public class WorkerWorkModule {

    @Provides
    PostWorkerWork postWorkerWork(WorkerWorkRepository workerWorkDataRepository) {
        return new PostWorkerWork(workerWorkDataRepository);
    }

    @Provides
    GetWorkerWorkReport getWorkerWorkReport(WorkerWorkReportRepository repository) {
        return new GetWorkerWorkReport(repository);
    }

    @Provides
    DeleteWorkerWork deleteWorkerWork(WorkerWorkRepository repository) {
        return new DeleteWorkerWork(repository);
    }

    @Provides
    GetWorkerWork getWorkerWork(WorkerWorkRepository repository) {
        return new GetWorkerWork(repository);
    }

    @Provides
    WorkerWorkRepository workerWorkDataRepository(WorkerWorkDataStoreFactory factory) {
        return new WorkerWorkDataRepository(factory);
    }

    @Provides
    WorkerWorkDataStoreFactory workerWorkDataStoreFactory(WorkerWorkCloudDataStore cloudDataStore) {
        return new WorkerWorkDataStoreFactory(cloudDataStore);
    }

    @Provides
    WorkerWorkCloudDataStore workerWorkCloudDataStore(Retrofit retrofit) {
        return new WorkerWorkCloudDataStore(retrofit);
    }

    @Provides
    WorkerWorkReportRepository workerWorkReportRepository(WorkerWorkReportDataStoreFactory factory) {
        return new WorkerWorkReportDataRepository(factory);
    }

    @Provides
    WorkerWorkReportDataStoreFactory workerWorkReportDataStoreFactory(WorkerWorkReportCloudDataStore cloudDataStore) {
        return new WorkerWorkReportDataStoreFactory(cloudDataStore);
    }

    @Provides
    WorkerWorkReportCloudDataStore workerWorkReportCloudDataStore(Retrofit retrofit) {
        return new WorkerWorkReportCloudDataStore(retrofit);
    }
}

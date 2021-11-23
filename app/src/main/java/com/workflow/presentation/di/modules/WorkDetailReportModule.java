package com.workflow.presentation.di.modules;

import com.workflow.data.repository.WorkDetailReportDataRepository;
import com.workflow.data.repository.datasource.WorkDetailReportCloudDataStore;
import com.workflow.data.repository.datasource.WorkDetailReportDataStoreFactory;
import com.workflow.domain.interactor.worker_work.GetWorkDetailReport;
import com.workflow.domain.repository.WorkDetailReportRepository;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Michael on 25/07/19.
 */

@Module(includes = NetModule.class)
public class WorkDetailReportModule {

    @Provides
    GetWorkDetailReport getWorkDetailReport(WorkDetailReportRepository repository) {
        return new GetWorkDetailReport(repository);
    }

    @Provides
    WorkDetailReportRepository workDetailReportRepository(WorkDetailReportDataStoreFactory factory) {
        return new WorkDetailReportDataRepository(factory);
    }

    @Provides
    WorkDetailReportDataStoreFactory workDetailReportDataStoreFactory(WorkDetailReportCloudDataStore cloudDataStore) {
        return new WorkDetailReportDataStoreFactory(cloudDataStore);
    }

    @Provides
    WorkDetailReportCloudDataStore workDetailReportCloudDataStore(Retrofit retrofit) {
        return new WorkDetailReportCloudDataStore(retrofit);
    }
}

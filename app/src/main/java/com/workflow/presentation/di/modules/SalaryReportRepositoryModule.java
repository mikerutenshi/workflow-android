package com.workflow.presentation.di.modules;

import com.workflow.data.repository.SalaryReportDataRepository;
import com.workflow.data.repository.datasource.SalaryReportDataStoreFactory;
import com.workflow.data.repository.datasource.SalaryReportListCloudDataStore;
import com.workflow.domain.interactor.salary_report.GetSalaryReportDetailList;
import com.workflow.domain.interactor.salary_report.GetSalaryReportList;
import com.workflow.domain.repository.SalaryReportRepository;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = NetModule.class)
public class SalaryReportRepositoryModule {
    @Provides GetSalaryReportList getSalaryReportList(SalaryReportRepository repository) {
        return new GetSalaryReportList(repository);
    }

    @Provides
    GetSalaryReportDetailList getSalaryReportDetailList(SalaryReportRepository repository) {
        return new GetSalaryReportDetailList(repository);
    }

    @Provides SalaryReportRepository salaryReportRepository(SalaryReportDataStoreFactory factory) {
        return new SalaryReportDataRepository(factory);
    }

    @Provides SalaryReportDataStoreFactory salaryReportDataStoreFactory(SalaryReportListCloudDataStore cloudDataStore) {
        return new SalaryReportDataStoreFactory(cloudDataStore);
    }

    @Provides SalaryReportListCloudDataStore salaryReportListCloudDataStore(Retrofit retrofit) {
        return new SalaryReportListCloudDataStore(retrofit);
    }
}

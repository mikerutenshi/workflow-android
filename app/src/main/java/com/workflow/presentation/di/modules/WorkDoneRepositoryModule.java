package com.workflow.presentation.di.modules;

import com.workflow.data.repository.DoneWorkDataRepository;
import com.workflow.data.repository.datasource.DoneWorkCloudDataStore;
import com.workflow.data.repository.datasource.DoneWorkDataStoreFactory;
import com.workflow.domain.interactor.done_work.DeleteDoneWork;
import com.workflow.domain.interactor.done_work.GetDoneWorks;
import com.workflow.domain.interactor.done_work.GetDoneables;
import com.workflow.domain.interactor.done_work.PostDoneWork;
import com.workflow.domain.repository.DoneWorkRepository;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = NetModule.class)
public class WorkDoneRepositoryModule {
    @Provides
    PostDoneWork postDoneWork(DoneWorkRepository repository) {
        return new PostDoneWork(repository);
    }

    @Provides GetDoneWorks getDoneWorks(DoneWorkRepository repository) {
        return new GetDoneWorks(repository);
    }

    @Provides GetDoneables getDoneables(DoneWorkRepository repository) {
        return new GetDoneables(repository);
    }

    @Provides DeleteDoneWork deleteDoneWork(DoneWorkRepository repository) {
        return new DeleteDoneWork(repository);
    }

    @Provides DoneWorkRepository doneWorkRepository(DoneWorkDataStoreFactory factory) {
        return new DoneWorkDataRepository(factory);
    }

    @Provides DoneWorkDataStoreFactory doneWorkDataStoreFactory(
            DoneWorkCloudDataStore cloudDataStore) {
        return new DoneWorkDataStoreFactory(cloudDataStore);
    }

    @Provides DoneWorkCloudDataStore doneWorkCloudDataStore(Retrofit retrofit) {
        return new DoneWorkCloudDataStore(retrofit);
    }
}

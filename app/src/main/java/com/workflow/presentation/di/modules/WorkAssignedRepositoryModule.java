package com.workflow.presentation.di.modules;

import com.workflow.data.repository.AssignedWorkDataRepository;
import com.workflow.data.repository.datasource.AssignedWorkCloudDataStore;
import com.workflow.data.repository.datasource.AssignedWorkDataStoreFactory;
import com.workflow.domain.interactor.assigned_work.DeleteAssignedWork;
import com.workflow.domain.interactor.assigned_work.GetAssignedWorks;
import com.workflow.domain.interactor.assigned_work.GetAssignedWorksSource;
import com.workflow.domain.interactor.assigned_work.PostAssignedWork;
import com.workflow.domain.repository.AssignedWorkRepository;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = NetModule.class)
public class WorkAssignedRepositoryModule {
    @Provides PostAssignedWork postAssignedWork(AssignedWorkRepository repository) {
        return new PostAssignedWork(repository);
    }

    @Provides GetAssignedWorks getAssignedWorks(AssignedWorkRepository repository) {
        return new GetAssignedWorks(repository);
    }

    @Provides GetAssignedWorksSource getAssignedWorksSource(AssignedWorkRepository repository) {
        return new GetAssignedWorksSource(repository);
    }

    @Provides DeleteAssignedWork deleteAssignedWork(AssignedWorkRepository repository) {
        return new DeleteAssignedWork(repository);
    }

    @Provides AssignedWorkRepository assignedWorkRepository(AssignedWorkDataStoreFactory factory) {
        return new AssignedWorkDataRepository(factory);
    }

    @Provides AssignedWorkDataStoreFactory assignedWorkDataStoreFactory(
            AssignedWorkCloudDataStore cloudDataStore) {
        return new AssignedWorkDataStoreFactory(cloudDataStore);
    }

    @Provides AssignedWorkCloudDataStore assignedWorkCloudDataStore(Retrofit retrofit) {
        return new AssignedWorkCloudDataStore(retrofit);
    }
}

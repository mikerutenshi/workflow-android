package com.workflow.presentation.di.modules;

import com.workflow.data.repository.AuthDataRepository;
import com.workflow.data.repository.datasource.AuthCloudDataStore;
import com.workflow.data.repository.datasource.AuthDataStoreFactory;
import com.workflow.domain.interactor.auth.Register;
import com.workflow.domain.interactor.auth.SignIn;
import com.workflow.domain.interactor.auth.SignOut;
import com.workflow.domain.repository.AuthRepository;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = NetModule.class)
public class AuthModule {
    @Provides
    SignIn signIn(AuthRepository repository) {
        return new SignIn(repository);
    }

    @Provides
    Register register(AuthRepository repository) {
        return new Register(repository);
    }

    @Provides
    SignOut signOut(AuthRepository repository) {
        return new SignOut(repository);
    }

    @Provides
    AuthRepository authRepository(AuthDataStoreFactory factory) {
        return new AuthDataRepository(factory);
    }

    @Provides
    AuthDataStoreFactory factory(AuthCloudDataStore cloudDataStore) {
        return new AuthDataStoreFactory(cloudDataStore);
    }

    @Provides
    AuthCloudDataStore authCloudDataStore(Retrofit retrofit) {
        return new AuthCloudDataStore(retrofit);
    }
}

package com.workflow.data.repository;

import com.workflow.data.model.RefreshTokenModel;
import com.workflow.data.model.RefreshedTokenModel;
import com.workflow.data.model.SignInModel;
import com.workflow.data.model.UserModel;
import com.workflow.data.repository.datasource.AuthDataStoreFactory;
import com.workflow.domain.repository.AuthRepository;

import io.reactivex.Single;

public class AuthDataRepository implements AuthRepository {
    private final AuthDataStoreFactory factory;

    public AuthDataRepository(AuthDataStoreFactory factory) {
        this.factory = factory;
    }

    @Override
    public Single<UserModel> user(SignInModel signInModel) {
        return factory.create().user(signInModel);
    }

    @Override
    public Single<String> register(UserModel userModel) {
        return factory.create().register(userModel);
    }

    @Override
    public Single<RefreshedTokenModel> refreshToken(RefreshTokenModel refreshTokenModel) {
        return factory.create().refreshToken(refreshTokenModel);
    }

    @Override
    public Single<String> signOut(String userName) {
        return factory.create().signOut(userName);
    }
}

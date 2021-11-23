package com.workflow.data.repository.datasource;

import com.workflow.data.model.RefreshTokenModel;
import com.workflow.data.model.RefreshedTokenModel;
import com.workflow.data.model.SignInModel;
import com.workflow.data.model.UserModel;
import com.workflow.data.net.responses.GenericResponse;

import io.reactivex.Single;

public interface AuthDataStore {
    Single<UserModel> user(SignInModel signInModel);
    Single<String> register(UserModel userModel);
    Single<RefreshedTokenModel> refreshToken(RefreshTokenModel refreshTokenModel);
    Single<String> signOut(String userName);
}

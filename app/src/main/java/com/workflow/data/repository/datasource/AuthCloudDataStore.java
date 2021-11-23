package com.workflow.data.repository.datasource;

import com.workflow.data.model.RefreshTokenModel;
import com.workflow.data.model.RefreshedTokenModel;
import com.workflow.data.model.SignInModel;
import com.workflow.data.model.UserModel;
import com.workflow.data.model.UserNameModel;
import com.workflow.data.net.responses.GenericResponse;
import com.workflow.data.net.services.AuthService;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;

public class AuthCloudDataStore implements AuthDataStore {

    private final Retrofit retrofit;

    public AuthCloudDataStore(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    @Override
    public Single<UserModel> user(SignInModel signInModel) {
        return retrofit.create(AuthService.class).signIn(signInModel).map(new Function<GenericResponse<UserModel>, UserModel>() {
            @Override
            public UserModel apply(GenericResponse<UserModel> userModelGenericResponse) throws Exception {
                return userModelGenericResponse.getData();
            }
        });
    }

    @Override
    public Single<String> register(UserModel userModel) {
        return retrofit.create(AuthService.class).register(userModel).map(new Function<GenericResponse<String>, String>() {
            @Override
            public String apply(GenericResponse<String> stringGenericResponse) throws Exception {
                return stringGenericResponse.getMessage();
            }
        });
    }

    @Override
    public Single<RefreshedTokenModel> refreshToken(RefreshTokenModel refreshTokenModel) {
        return retrofit.create(AuthService.class).refreshToken(refreshTokenModel).map(new Function<GenericResponse<RefreshedTokenModel>, RefreshedTokenModel>() {
            @Override
            public RefreshedTokenModel apply(GenericResponse<RefreshedTokenModel> refreshedTokenModelGenericResponse) throws Exception {
                return refreshedTokenModelGenericResponse.getData();
            }
        });
    }

    @Override
    public Single<String> signOut(String userName) {
        return retrofit.create(AuthService.class).signOut(new UserNameModel(userName)).map(new Function<GenericResponse, String>() {
            @Override
            public String apply(GenericResponse genericResponse) throws Exception {
                return genericResponse.getMessage();
            }
        });
    }
}

package com.workflow.data.net.services;

import com.workflow.data.model.RefreshTokenModel;
import com.workflow.data.model.SignInModel;
import com.workflow.data.model.RefreshedTokenModel;
import com.workflow.data.model.UserModel;
import com.workflow.data.model.UserNameModel;
import com.workflow.data.net.responses.GenericResponse;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("v1/users/authenticate")
    Single<GenericResponse<UserModel>> signIn(@Body SignInModel signInModel);

    @POST("v1/users")
    Single<GenericResponse<String>> register(@Body UserModel userModel);

    @POST("v1/users/token")
    Single<GenericResponse<RefreshedTokenModel>> refreshToken(@Body RefreshTokenModel refreshTokenModel);

    @POST("v1/users/signout")
    Single<GenericResponse> signOut(@Body UserNameModel userNameModel);
}

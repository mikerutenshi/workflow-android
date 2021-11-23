package com.workflow.domain.interactor.auth;

import com.workflow.data.model.SignInModel;
import com.workflow.data.model.UserModel;
import com.workflow.domain.interactor.SingleUseCase;
import com.workflow.domain.repository.AuthRepository;

import io.reactivex.Single;

public class SignIn extends SingleUseCase<UserModel, SignInModel> {
    private final AuthRepository repository;

    public SignIn(AuthRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<UserModel> buildUseCaseSingle(SignInModel params) {
        return repository.user(params);
    }
}

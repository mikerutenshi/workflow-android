package com.workflow.domain.interactor.auth;

import com.workflow.data.model.UserModel;
import com.workflow.domain.interactor.SingleUseCase;
import com.workflow.domain.repository.AuthRepository;

import io.reactivex.Single;

public class Register extends SingleUseCase<String, UserModel> {

    private final AuthRepository repository;

    public Register(AuthRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<String> buildUseCaseSingle(UserModel params) {
        return repository.register(params);
    }
}

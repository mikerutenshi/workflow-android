package com.workflow.domain.interactor.auth;

import com.workflow.domain.interactor.SingleUseCase;
import com.workflow.domain.repository.AuthRepository;

import io.reactivex.Single;

public class SignOut extends SingleUseCase<String, String> {

    private final AuthRepository repository;

    public SignOut(AuthRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<String> buildUseCaseSingle(String userName) {
        return repository.signOut(userName);
    }
}

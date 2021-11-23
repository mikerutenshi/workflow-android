package com.workflow.domain.interactor.work;

import com.workflow.domain.interactor.SingleUseCase;
import com.workflow.domain.repository.WorkRepository;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Michael on 12/07/19.
 */

public class DeleteWork extends SingleUseCase<String, List<Integer>> {

    private final WorkRepository repository;

    public DeleteWork(WorkRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<String> buildUseCaseSingle(List<Integer> params) {
        return repository.deleteWork(params);
    }
}

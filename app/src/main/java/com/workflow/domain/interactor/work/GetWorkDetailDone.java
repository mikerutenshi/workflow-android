package com.workflow.domain.interactor.work;

import com.workflow.data.model.CurrentWorkDetailDoneModel;
import com.workflow.data.net.responses.GenericListResponseModel;
import com.workflow.domain.interactor.SingleUseCase;
import com.workflow.domain.repository.WorkRepository;

import java.util.HashMap;

import io.reactivex.Single;

public class GetWorkDetailDone extends SingleUseCase<GenericListResponseModel<CurrentWorkDetailDoneModel>, HashMap<String, String>> {

    private final WorkRepository repository;

    public GetWorkDetailDone(WorkRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<GenericListResponseModel<CurrentWorkDetailDoneModel>> buildUseCaseSingle(HashMap<String, String> params) {
        return repository.getWorkDetailDone(params);
    }
}

package com.workflow.domain.interactor.work;

import com.workflow.data.model.CurrentWorkListModel;
import com.workflow.data.net.responses.GenericListResponseModel;
import com.workflow.domain.interactor.SingleUseCase;
import com.workflow.domain.repository.WorkRepository;

import java.util.HashMap;

import io.reactivex.Single;

public class GetWorkList extends SingleUseCase<GenericListResponseModel<CurrentWorkListModel>, HashMap<String, String>> {
    private final WorkRepository repository;

    public GetWorkList(WorkRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<GenericListResponseModel<CurrentWorkListModel>> buildUseCaseSingle(HashMap<String, String> params) {
        return repository.getWorkList(params);
    }
}

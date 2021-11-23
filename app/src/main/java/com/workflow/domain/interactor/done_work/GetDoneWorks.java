package com.workflow.domain.interactor.done_work;

import com.workflow.data.model.DoneWorkListModel;
import com.workflow.data.model.RequestModel;
import com.workflow.data.net.responses.GenericListResponseModel;
import com.workflow.domain.interactor.SingleUseCase;
import com.workflow.domain.repository.DoneWorkRepository;

import io.reactivex.Single;

public class GetDoneWorks extends SingleUseCase<GenericListResponseModel<DoneWorkListModel>, RequestModel> {
    private final DoneWorkRepository repository;

    public GetDoneWorks(DoneWorkRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<GenericListResponseModel<DoneWorkListModel>> buildUseCaseSingle(RequestModel params) {
        return repository.getDoneWorks(params);
    }
}

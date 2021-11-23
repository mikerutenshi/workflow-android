package com.workflow.data.repository;

import com.workflow.data.model.DoneWorkCreateModel;
import com.workflow.data.model.DoneWorkListModel;
import com.workflow.data.model.RequestModel;
import com.workflow.data.net.responses.GenericListResponseModel;
import com.workflow.data.repository.datasource.DoneWorkDataStoreFactory;
import com.workflow.domain.repository.DoneWorkRepository;

import io.reactivex.Single;

public class DoneWorkDataRepository implements DoneWorkRepository {
    private final DoneWorkDataStoreFactory factory;

    public DoneWorkDataRepository(DoneWorkDataStoreFactory factory) {
        this.factory = factory;
    }

    @Override
    public Single<String> createDoneWork(DoneWorkCreateModel doneWorkCreateModel) {
        return factory.create().createDoneWork(doneWorkCreateModel);
    }

    @Override
    public Single<GenericListResponseModel<DoneWorkListModel>> getDoneWorks(RequestModel requestModel) {
        return factory.create().getDoneWorks(requestModel);
    }

    @Override
    public Single<GenericListResponseModel<DoneWorkListModel>> getDoneables(RequestModel requestModel) {
        return factory.create().getDoneables(requestModel);
    }

    @Override
    public Single<String> deleteDoneWork(Integer id) {
        return factory.create().deleteDoneWork(id);
    }
}

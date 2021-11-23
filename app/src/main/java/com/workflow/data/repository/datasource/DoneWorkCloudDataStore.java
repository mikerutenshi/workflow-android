package com.workflow.data.repository.datasource;

import com.workflow.data.model.DoneWorkCreateModel;
import com.workflow.data.model.DoneWorkListModel;
import com.workflow.data.model.RequestModel;
import com.workflow.data.net.responses.GenericListResponseModel;
import com.workflow.data.net.responses.GenericResponse;
import com.workflow.data.net.services.DoneWorkService;

import io.reactivex.Single;
import retrofit2.Retrofit;

public class DoneWorkCloudDataStore implements DoneWorkDataStore {

    private final Retrofit retrofit;

    public DoneWorkCloudDataStore(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    @Override
    public Single<String> createDoneWork(DoneWorkCreateModel doneWorkCreateModel) {
        return retrofit.create(DoneWorkService.class).create(doneWorkCreateModel).map(GenericResponse::getMessage);
    }

    @Override
    public Single<GenericListResponseModel<DoneWorkListModel>> getDoneWorks(RequestModel requestModel) {
        return retrofit.create(DoneWorkService.class).getAll(requestModel.getPath(), requestModel.getQueries());
    }

    @Override
    public Single<GenericListResponseModel<DoneWorkListModel>> getDoneables(RequestModel requestModel) {
        return retrofit.create(DoneWorkService.class).getDoneables(requestModel.getPath(), requestModel.getQueries());
    }

    @Override
    public Single<String> deleteDoneWork(Integer id) {
        return retrofit.create(DoneWorkService.class).delete(id).map(GenericResponse::getMessage);
    }
}

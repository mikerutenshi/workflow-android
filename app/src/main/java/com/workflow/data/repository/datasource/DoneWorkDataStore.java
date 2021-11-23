package com.workflow.data.repository.datasource;

import com.workflow.data.model.DoneWorkCreateModel;
import com.workflow.data.model.DoneWorkListModel;
import com.workflow.data.model.RequestModel;
import com.workflow.data.net.responses.GenericListResponseModel;

import io.reactivex.Single;

public interface DoneWorkDataStore {
    Single<String> createDoneWork(DoneWorkCreateModel doneWorkCreateModel);
    Single<GenericListResponseModel<DoneWorkListModel>> getDoneWorks(RequestModel requestModel);
    Single<GenericListResponseModel<DoneWorkListModel>> getDoneables(RequestModel requestModel);
    Single<String> deleteDoneWork(Integer id);
}

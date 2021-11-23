package com.workflow.domain.repository;

import com.workflow.data.model.CreateWorkerWorkModel;
import com.workflow.data.model.GenericItemPaginationModel;
import com.workflow.data.model.RequestModel;
import com.workflow.data.model.WorkerWorkDeleteModel;
import com.workflow.data.model.WorkerWorkModel;
import com.workflow.data.model.WorkerWorkQueryModel;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Michael on 08/07/19.
 */

public interface WorkerWorkRepository {
    Single<String> createWorkerWork(CreateWorkerWorkModel param);
    Single<String> deleteWorkerWork(WorkerWorkDeleteModel params);
    Single<GenericItemPaginationModel<List<WorkerWorkModel>>> getWorkerWork(RequestModel requestModel);
}

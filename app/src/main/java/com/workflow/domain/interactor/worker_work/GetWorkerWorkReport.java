package com.workflow.domain.interactor.worker_work;

import com.workflow.data.model.DateFilterModel;
import com.workflow.data.model.WorkerWorkReportModel;
import com.workflow.domain.interactor.SingleUseCase;
import com.workflow.domain.repository.WorkerWorkReportRepository;

import io.reactivex.Single;

/**
 * Created by Michael on 10/07/19.
 */

public class GetWorkerWorkReport extends SingleUseCase<WorkerWorkReportModel, DateFilterModel> {

    private final WorkerWorkReportRepository repository;

    public GetWorkerWorkReport(WorkerWorkReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<WorkerWorkReportModel> buildUseCaseSingle(DateFilterModel params) {
        return repository.getWorkerWorkReport(params);
    }
}

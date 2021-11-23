package com.workflow.domain.interactor.worker_work;

import com.workflow.data.model.WorkDetailModel;
import com.workflow.data.model.ReportDetailDateFilterModel;
import com.workflow.domain.interactor.SingleUseCase;
import com.workflow.domain.repository.WorkDetailReportRepository;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Michael on 25/07/19.
 */

public class GetWorkDetailReport extends SingleUseCase<List<WorkDetailModel>, ReportDetailDateFilterModel> {

    private final WorkDetailReportRepository repository;

    public GetWorkDetailReport(WorkDetailReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<List<WorkDetailModel>> buildUseCaseSingle(ReportDetailDateFilterModel params) {
        return repository.getWorkDetailReport(params);
    }
}

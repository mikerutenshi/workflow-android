package com.workflow.data.repository.datasource;

import com.workflow.data.model.WorkDetailModel;
import com.workflow.data.model.ReportDetailDateFilterModel;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Michael on 25/07/19.
 */

public interface WorkDetailReportDataStore {
    Single<List<WorkDetailModel>> getWorkDetailReport(ReportDetailDateFilterModel filterModel);
}

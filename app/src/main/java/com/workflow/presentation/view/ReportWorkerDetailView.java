package com.workflow.presentation.view;

import com.workflow.data.model.ReportListModel;
import com.workflow.data.model.WorkDetailModel;

import java.util.List;

/**
 * Created by Michael on 25/07/19.
 */

public interface ReportWorkerDetailView extends BaseView {
    void renderWorkDetail(List<WorkDetailModel> workDetailModels);
    void renderWorkerDetailHeader(ReportListModel model);
}

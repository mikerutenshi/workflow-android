package com.workflow.presentation.view;

import com.workflow.data.model.WorkerWorkReportModel;

/**
 * Created by Michael on 10/07/19.
 */

public interface WorkerWorkReportView extends BaseView {
    void renderItems(WorkerWorkReportModel item);
    void showDataEmptyView();
    void resetRefreshingStatus();
}

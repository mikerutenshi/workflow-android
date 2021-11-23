package com.workflow.presentation.mapper;

import com.workflow.data.model.WorkModel;
import com.workflow.data.model.WorkerWorkModel;

/**
 * Created by Michael on 08/07/19.
 */

public class WorkerWorkMapper extends InterModelMapper<WorkerWorkModel, WorkModel> {
    @Override
    public WorkerWorkModel transform(WorkModel workModel) {
        return null;
    }

    @Override
    public WorkerWorkModel transform(WorkModel workModel, Integer attachId, String attachPos, String attachCreatedAt) {
        return new WorkerWorkModel(attachId, workModel.getId(), attachPos, attachCreatedAt);
    }
}

package com.workflow.presentation.di.modules;

import android.content.Context;

import com.workflow.domain.interactor.assigned_work.DeleteAssignedWork;
import com.workflow.domain.interactor.assigned_work.GetAssignedWorks;
import com.workflow.presentation.presenter.WorkAssignedPresenterImpl;
import com.workflow.presentation.view.WorkerDetailWorkerWorkView;
import com.workflow.presentation.view.adapters.AssignedWorkAdapter;

import dagger.Module;
import dagger.Provides;

@Module(includes = AdapterModule.class)
public class WorkAssignedModule {
    private final WorkerDetailWorkerWorkView view;
    private final Integer workerId;

    public WorkAssignedModule(WorkerDetailWorkerWorkView view, Integer workerId) {
        this.view = view;
        this.workerId = workerId;
    }

    @Provides
    AssignedWorkAdapter assignedWorkAdapter(Context context) {
        return new AssignedWorkAdapter(context);
    }

    @Provides
    WorkAssignedPresenterImpl workerDetailPresenter(GetAssignedWorks getAssignedWorks, DeleteAssignedWork deleteAssignedWork) {
        return new WorkAssignedPresenterImpl(view, getAssignedWorks , deleteAssignedWork, workerId);
    }
}

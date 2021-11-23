package com.workflow.presentation.di.modules;

import androidx.appcompat.app.AppCompatActivity;

import com.workflow.presentation.presenter.WorkerDetailPresenter;
import com.workflow.presentation.presenter.WorkerDetailPresenterImpl;
import com.workflow.presentation.view.WorkerDetailView;
import com.workflow.presentation.view.adapters.WorkerWorkCollectionAdapter;
import com.workflow.presentation.view.fragments.WorkAssignedFragment;
import com.workflow.presentation.view.fragments.WorkDoneFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Michael on 01/07/19.
 */

@Module()
public class WorkerDetailModule {

    private final WorkerDetailView view;
    private final Integer workerId;
    private final AppCompatActivity activity;

    public WorkerDetailModule(WorkerDetailView view, Integer workerId, AppCompatActivity activity) {
        this.view = view;
        this.workerId = workerId;
        this.activity = activity;
    }

    @Provides
    WorkerWorkCollectionAdapter workerWorkCollectionAdapter(WorkAssignedFragment workAssignedFragment, WorkDoneFragment workDoneFragment) {
        return new WorkerWorkCollectionAdapter(activity, workAssignedFragment, workDoneFragment);
    }

    @Provides
    WorkerDetailPresenter workerDetailPresenter() {
        return new WorkerDetailPresenterImpl();
    }

    @Provides
    WorkAssignedFragment workAssignedFragment() {
        return WorkAssignedFragment.newInstance(workerId);
    }

    @Provides
    WorkDoneFragment workDoneFragment() {
        return WorkDoneFragment.newInstance(workerId);
    }
}

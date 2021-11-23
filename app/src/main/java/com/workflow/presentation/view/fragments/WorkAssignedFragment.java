package com.workflow.presentation.view.fragments;

import android.os.Bundle;

import com.workflow.WorkflowApplication;
import com.workflow.data.model.AssignedWorkListModel;
import com.workflow.presentation.di.components.DaggerWorkAssignedComponent;
import com.workflow.presentation.di.components.WorkAssignedComponent;
import com.workflow.presentation.di.modules.ContextModule;
import com.workflow.presentation.di.modules.WorkAssignedModule;
import com.workflow.presentation.presenter.WorkAssignedPresenterImpl;
import com.workflow.presentation.presenter.WorkerDetailWorkerWorkPresenter;
import com.workflow.presentation.view.adapters.AssignedWorkAdapter;
import com.workflow.presentation.view.adapters.BaseViewHolder;
import com.workflow.presentation.view.adapters.OnRecyclerObjectClickListener;
import com.workflow.presentation.view.adapters.PaginationAdapter;

import javax.inject.Inject;

import static com.workflow.utils.PreferenceUtils.PREF_WORK_ASSIGNED_FILTER_STATUS;

public class WorkAssignedFragment extends WorkerWorkFragment<AssignedWorkListModel> {
    private WorkAssignedComponent component;
    @Inject AssignedWorkAdapter adapter;
    @Inject WorkAssignedPresenterImpl presenter;


    @Override
    PaginationAdapter<AssignedWorkListModel, OnRecyclerObjectClickListener<AssignedWorkListModel>, BaseViewHolder<AssignedWorkListModel, OnRecyclerObjectClickListener<AssignedWorkListModel>>> adapter() {
        return adapter;
    }

    @Override
    String filterPrefKey() {
        return PREF_WORK_ASSIGNED_FILTER_STATUS;
    }

    @Override
    WorkerDetailWorkerWorkPresenter<AssignedWorkListModel> presenter() {
        return presenter;
    }

    @Override
    void initDagger() {
        Integer workerId = null;
        if (getArguments() != null) {
            workerId = getArguments().getInt("worker_id");
        }
        if (component == null) {
            component = DaggerWorkAssignedComponent.builder()
                    .applicationComponent(((WorkflowApplication) context.getApplicationContext()).getApplicationComponent())
                    .workAssignedModule(new WorkAssignedModule(this, workerId))
                    .contextModule(new ContextModule(context))
                    .build();
        }
        component.inject(this);
    }

    public static WorkAssignedFragment newInstance(Integer workerId) {

        Bundle args = new Bundle();
        args.putInt("worker_id", workerId);

        WorkAssignedFragment fragment = new WorkAssignedFragment();
        fragment.setArguments(args);
        return fragment;
    }
}

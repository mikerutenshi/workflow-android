package com.workflow.presentation.view.fragments;

import android.os.Bundle;

import com.workflow.R;
import com.workflow.WorkflowApplication;
import com.workflow.data.model.DoneWorkListModel;
import com.workflow.presentation.di.components.DaggerWorkDoneComponent;
import com.workflow.presentation.di.components.WorkDoneComponent;
import com.workflow.presentation.di.modules.ContextModule;
import com.workflow.presentation.di.modules.WorkDoneModule;
import com.workflow.presentation.presenter.WorkDonePresenterImpl;
import com.workflow.presentation.presenter.WorkerDetailWorkerWorkPresenter;
import com.workflow.presentation.view.adapters.DoneWorkAdapter;
import com.workflow.presentation.view.adapters.PaginationAdapter;
import com.workflow.presentation.view.adapters.WorkerWorkAdapter;

import javax.inject.Inject;

import static com.workflow.utils.PreferenceUtils.PREF_WORK_DONE_FILTER_STATUS;

public class WorkDoneFragment extends WorkerWorkFragment {

    private WorkDoneComponent component;
    @Inject DoneWorkAdapter adapter;
    @Inject WorkDonePresenterImpl presenter;

    @Override
    void initDagger() {
        Integer workerId = null;
        if (getArguments() != null) {
            workerId = getArguments().getInt("worker_id");
        }
        if (component == null) {
            component = DaggerWorkDoneComponent.builder()
                    .applicationComponent(((WorkflowApplication) context.getApplicationContext()).getApplicationComponent())
                    .workDoneModule(new WorkDoneModule(this, workerId))
                    .contextModule(new ContextModule(context))
                    .build();
        }
        component.inject(this);
    }

    @Override
    PaginationAdapter adapter() {
        return adapter;
    }

    @Override
    String filterPrefKey() {
        return PREF_WORK_DONE_FILTER_STATUS;
    }

    @Override
    WorkerDetailWorkerWorkPresenter<DoneWorkListModel> presenter() {
        return presenter;
    }

    public static WorkDoneFragment newInstance(Integer workerId) {

        Bundle args = new Bundle();
        args.putInt("worker_id", workerId);

        WorkDoneFragment fragment = new WorkDoneFragment();
        fragment.setArguments(args);
        return fragment;
    }
}

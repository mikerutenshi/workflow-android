package com.workflow.presentation.view.fragments;

import android.os.Bundle;

import com.workflow.WorkflowApplication;
import com.workflow.data.model.CurrentWorkDetailAssignedModel;
import com.workflow.presentation.di.components.CurrentWorkDetailAssignedComponent;
import com.workflow.presentation.di.components.DaggerCurrentWorkDetailAssignedComponent;
import com.workflow.presentation.di.modules.ContextModule;
import com.workflow.presentation.di.modules.CurrentWorkDetailAssignedModule;
import com.workflow.presentation.presenter.WorkDetailAssignedPresenterImpl;
import com.workflow.presentation.presenter.WorkDetailPresenter;
import com.workflow.presentation.view.adapters.BaseViewHolder;
import com.workflow.presentation.view.adapters.CurrentWorkDetailAssignedAdapter;
import com.workflow.presentation.view.adapters.OnRecyclerObjectClickListener;
import com.workflow.presentation.view.adapters.PaginationAdapter;

import javax.inject.Inject;

public class CurrentWorkDetailAssignedFragment extends WorkDetailFragment<CurrentWorkDetailAssignedModel> {

    @Inject CurrentWorkDetailAssignedAdapter adapter;
    @Inject WorkDetailAssignedPresenterImpl presenter;
    private CurrentWorkDetailAssignedComponent component;

    @Override
    PaginationAdapter<CurrentWorkDetailAssignedModel, OnRecyclerObjectClickListener<CurrentWorkDetailAssignedModel>, BaseViewHolder<CurrentWorkDetailAssignedModel, OnRecyclerObjectClickListener<CurrentWorkDetailAssignedModel>>> adapter() {
        return adapter;
    }

    @Override
    WorkDetailPresenter presenter() {
        return presenter;
    }

    @Override
    void initDagger() {
        if (component == null) {
            component = DaggerCurrentWorkDetailAssignedComponent.builder()
                    .applicationComponent(((WorkflowApplication) context.getApplicationContext()).getApplicationComponent())
                    .contextModule(new ContextModule(context))
                    .currentWorkDetailAssignedModule(new CurrentWorkDetailAssignedModule(this))
                    .build();
        }
        component.inject(this);
    }

    public static CurrentWorkDetailAssignedFragment newInstance(Integer workId) {

        Bundle args = new Bundle();
        args.putInt("work_id", workId);

        CurrentWorkDetailAssignedFragment fragment = new CurrentWorkDetailAssignedFragment();
        fragment.setArguments(args);
        return fragment;
    }
}

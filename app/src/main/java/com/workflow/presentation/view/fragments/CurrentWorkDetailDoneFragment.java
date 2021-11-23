package com.workflow.presentation.view.fragments;

import android.os.Bundle;

import com.workflow.WorkflowApplication;
import com.workflow.data.model.CurrentWorkDetailDoneModel;
import com.workflow.presentation.di.components.CurrentWorkDetailDoneComponent;
import com.workflow.presentation.di.components.DaggerCurrentWorkDetailDoneComponent;
import com.workflow.presentation.di.modules.ContextModule;
import com.workflow.presentation.di.modules.CurrentWorkDetailDoneModule;
import com.workflow.presentation.presenter.WorkDetailDonePresenterImpl;
import com.workflow.presentation.presenter.WorkDetailPresenter;
import com.workflow.presentation.view.adapters.BaseViewHolder;
import com.workflow.presentation.view.adapters.CurrentWorkDetailDoneAdapter;
import com.workflow.presentation.view.adapters.OnRecyclerObjectClickListener;
import com.workflow.presentation.view.adapters.PaginationAdapter;

import javax.inject.Inject;

public class CurrentWorkDetailDoneFragment extends WorkDetailFragment<CurrentWorkDetailDoneModel> {

    @Inject CurrentWorkDetailDoneAdapter adapter;
    @Inject WorkDetailDonePresenterImpl presenter;
    private CurrentWorkDetailDoneComponent component;

    @Override
    PaginationAdapter<CurrentWorkDetailDoneModel, OnRecyclerObjectClickListener<CurrentWorkDetailDoneModel>, BaseViewHolder<CurrentWorkDetailDoneModel, OnRecyclerObjectClickListener<CurrentWorkDetailDoneModel>>> adapter() {
        return adapter;
    }

    @Override
    WorkDetailPresenter presenter() {
        return presenter;
    }

    @Override
    void initDagger() {
        if (component == null) {
            component = DaggerCurrentWorkDetailDoneComponent.builder()
                    .applicationComponent(((WorkflowApplication) context.getApplicationContext()).getApplicationComponent())
                    .contextModule(new ContextModule(context))
                    .currentWorkDetailDoneModule(new CurrentWorkDetailDoneModule(this))
                    .build();
        }
        component.inject(this);
    }

    public static CurrentWorkDetailDoneFragment newInstance(Integer workId) {

        Bundle args = new Bundle();
        args.putInt("work_id", workId);

        CurrentWorkDetailDoneFragment fragment = new CurrentWorkDetailDoneFragment();
        fragment.setArguments(args);
        return fragment;
    }
}

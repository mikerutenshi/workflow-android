package com.workflow.presentation.di.modules;

import android.content.Context;

import com.workflow.domain.interactor.work.GetWorkDetailAssigned;
import com.workflow.presentation.presenter.WorkDetailAssignedPresenterImpl;
import com.workflow.presentation.view.WorkDetailFragmentView;
import com.workflow.presentation.view.adapters.CurrentWorkDetailAssignedAdapter;

import dagger.Module;
import dagger.Provides;

@Module(includes = { AdapterModule.class })
public class CurrentWorkDetailAssignedModule {

    private final WorkDetailFragmentView view;

    public CurrentWorkDetailAssignedModule(WorkDetailFragmentView view) {
        this.view = view;
    }

    @Provides
    CurrentWorkDetailAssignedAdapter adapter(Context context) {
        return new CurrentWorkDetailAssignedAdapter(context);
    }

    @Provides
    WorkDetailAssignedPresenterImpl workDetailAssignedPresenter(GetWorkDetailAssigned getWorkDetailAssigned) {
        return new WorkDetailAssignedPresenterImpl(view, getWorkDetailAssigned);
    }
}

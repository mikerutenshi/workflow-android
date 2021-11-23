package com.workflow.presentation.di.modules;

import android.content.Context;

import com.workflow.domain.interactor.work.DeleteWork;
import com.workflow.domain.interactor.work.GetWorks;
import com.workflow.presentation.presenter.WorkListPresenter;
import com.workflow.presentation.presenter.WorkListPresenterImpl;
import com.workflow.presentation.view.WorkView;
import com.workflow.presentation.view.adapters.WorkAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Michael on 28/06/19.
 */

@Module(includes = { WorkModule.class, AdapterModule.class })
public class WorkListModule {

    private final WorkView view;

    public WorkListModule(WorkView view) {
        this.view = view;
    }

    @Provides
    WorkAdapter workAdapter(Context context) {
        return new WorkAdapter(context);
    }

    @Provides
    WorkListPresenter workListPresenter(GetWorks getWorks, DeleteWork deleteWork) {
        return new WorkListPresenterImpl(view, getWorks, deleteWork);
    }
}

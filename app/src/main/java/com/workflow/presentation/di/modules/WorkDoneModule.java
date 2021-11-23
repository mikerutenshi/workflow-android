package com.workflow.presentation.di.modules;

import android.content.Context;

import com.workflow.domain.interactor.done_work.DeleteDoneWork;
import com.workflow.domain.interactor.done_work.GetDoneWorks;
import com.workflow.presentation.presenter.WorkDonePresenterImpl;
import com.workflow.presentation.view.WorkerDetailWorkerWorkView;
import com.workflow.presentation.view.adapters.DoneWorkAdapter;

import dagger.Module;
import dagger.Provides;

@Module(includes = AdapterModule.class)
public class WorkDoneModule {
    private final WorkerDetailWorkerWorkView view;
    private final Integer workerId;

    public WorkDoneModule(WorkerDetailWorkerWorkView view, Integer workerId) {
        this.view = view;
        this.workerId = workerId;
    }

    @Provides
    DoneWorkAdapter workAdapter(Context context) {
        return new DoneWorkAdapter(context);
    }

    @Provides
    WorkDonePresenterImpl workDonePresenter(GetDoneWorks getDoneWorks, DeleteDoneWork deleteDoneWork) {
        return new WorkDonePresenterImpl(view, getDoneWorks, deleteDoneWork, workerId);
    }
}

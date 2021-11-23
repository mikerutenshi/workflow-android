package com.workflow.presentation.di.modules;

import android.content.Context;

import com.workflow.domain.interactor.done_work.GetDoneables;
import com.workflow.domain.interactor.done_work.PostDoneWork;
import com.workflow.presentation.presenter.WorkDoPresenterImpl;
import com.workflow.presentation.view.WorkerWorkAssignView;
import com.workflow.presentation.view.adapters.WorkDoneableAdapter;

import dagger.Module;
import dagger.Provides;

@Module(includes = { AdapterModule.class })
public class WorkDoModule {
    private final WorkerWorkAssignView view;
    private final Integer mWorkerId;
    private final String mWorkerPosition;

    public WorkDoModule(WorkerWorkAssignView view, Integer mWorkerId, String mWorkerPosition) {
        this.view = view;
        this.mWorkerId = mWorkerId;
        this.mWorkerPosition = mWorkerPosition;
    }

    @Provides
    WorkDoneableAdapter workDoneableAdapter(Context context) {
        return new WorkDoneableAdapter(context);
    }

    @Provides
    WorkDoPresenterImpl workDoPresenter(
            GetDoneables getWorks, PostDoneWork postDoneWork) {
        return new WorkDoPresenterImpl(
                view, getWorks, postDoneWork, mWorkerId, mWorkerPosition);
    }
}

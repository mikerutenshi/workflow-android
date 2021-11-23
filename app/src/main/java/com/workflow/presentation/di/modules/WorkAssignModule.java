package com.workflow.presentation.di.modules;

import android.content.Context;

import com.workflow.domain.interactor.assigned_work.GetAssignedWorksSource;
import com.workflow.domain.interactor.assigned_work.PostAssignedWork;
import com.workflow.presentation.presenter.WorkAssignPresenterImpl;
import com.workflow.presentation.view.WorkerWorkAssignView;
import com.workflow.presentation.view.adapters.WorkAssignableAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Michael on 06/07/19.
 */

@Module(includes = { AdapterModule.class })
public class WorkAssignModule {

    private final WorkerWorkAssignView view;
    private final Integer mWorkerId;
    private final String mWorkerPosition;

    public WorkAssignModule(WorkerWorkAssignView view, Integer mWorkerId, String mWorkerPosition) {
        this.view = view;
        this.mWorkerId = mWorkerId;
        this.mWorkerPosition = mWorkerPosition;
    }

    @Provides
    WorkAssignableAdapter workAssignAdapter(Context context) {
        return new WorkAssignableAdapter(context);
    }

    @Provides
    WorkAssignPresenterImpl workAssignPresenter(
            GetAssignedWorksSource getWorks, PostAssignedWork postAssignedWork) {
        return new WorkAssignPresenterImpl(
                view, getWorks, postAssignedWork, mWorkerId, mWorkerPosition);
    }
}

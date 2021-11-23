package com.workflow.presentation.di.modules;

import androidx.appcompat.app.AppCompatActivity;

import com.workflow.presentation.view.adapters.WorkDetailCollectionAdapter;
import com.workflow.presentation.view.fragments.CurrentWorkDetailAssignedFragment;
import com.workflow.presentation.view.fragments.CurrentWorkDetailDoneFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class CurrentWorkDetailModule {
    private final Integer workId;
    private final AppCompatActivity activity;

    public CurrentWorkDetailModule(Integer workId, AppCompatActivity activity) {
        this.workId = workId;
        this.activity = activity;
    }

    @Provides
    WorkDetailCollectionAdapter workDetailCollectionAdapter(CurrentWorkDetailAssignedFragment workAssignedFragment, CurrentWorkDetailDoneFragment workDoneFragment) {
        return new WorkDetailCollectionAdapter(activity, workAssignedFragment, workDoneFragment);
    }

    @Provides CurrentWorkDetailAssignedFragment workDetailAssignedFragment() {
        return CurrentWorkDetailAssignedFragment.newInstance(workId);
    }

    @Provides CurrentWorkDetailDoneFragment workDetailDoneFragment() {
        return CurrentWorkDetailDoneFragment.newInstance(workId);
    }
}

package com.workflow.presentation.di.modules;

import android.content.Context;

import com.workflow.domain.interactor.work.GetWorkDetailDone;
import com.workflow.presentation.presenter.WorkDetailDonePresenterImpl;
import com.workflow.presentation.view.WorkDetailFragmentView;
import com.workflow.presentation.view.adapters.CurrentWorkDetailDoneAdapter;

import dagger.Module;
import dagger.Provides;

@Module(includes = { AdapterModule.class })
public class CurrentWorkDetailDoneModule {
    private final WorkDetailFragmentView view;

    public CurrentWorkDetailDoneModule(WorkDetailFragmentView view) {
        this.view = view;
    }

    @Provides
    CurrentWorkDetailDoneAdapter adapter(Context context) {
        return new CurrentWorkDetailDoneAdapter(context);
    }

    @Provides
    WorkDetailDonePresenterImpl workDetailDonePresenter(GetWorkDetailDone getWorkDetailDone) {
        return new WorkDetailDonePresenterImpl(view, getWorkDetailDone);
    }
}

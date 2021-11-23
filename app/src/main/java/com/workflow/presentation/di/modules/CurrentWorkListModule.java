package com.workflow.presentation.di.modules;

import android.content.Context;

import com.workflow.domain.interactor.work.DeleteWork;
import com.workflow.domain.interactor.work.GetWorkList;
import com.workflow.presentation.presenter.CurrentWorkListPresenter;
import com.workflow.presentation.presenter.CurrentWorkListPresenterImpl;
import com.workflow.presentation.view.CurrentWorkListView;
import com.workflow.presentation.view.adapters.CurrentWorkListAdapter;

import dagger.Module;
import dagger.Provides;

@Module(includes = { AdapterModule.class })
public class CurrentWorkListModule {

    private final CurrentWorkListView view;

    public CurrentWorkListModule(CurrentWorkListView view) {
        this.view = view;
    }

    @Provides CurrentWorkListAdapter adapter(Context context) {
        return new CurrentWorkListAdapter(context);
    }

    @Provides CurrentWorkListPresenter currentWorkListPresenter(GetWorkList getWorkList,
                                                                DeleteWork deleteWork) {
        return new CurrentWorkListPresenterImpl(view, getWorkList, deleteWork);
    }
}

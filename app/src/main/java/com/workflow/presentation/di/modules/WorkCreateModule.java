package com.workflow.presentation.di.modules;

import com.workflow.domain.interactor.product.GetProducts;
import com.workflow.domain.interactor.work.PostWork;
import com.workflow.domain.interactor.work.UpdateWork;
import com.workflow.presentation.mapper.ListProductMapper;
import com.workflow.presentation.presenter.WorkCreatePresenter;
import com.workflow.presentation.presenter.WorkCreatePresenterImpl;
import com.workflow.presentation.view.WorkCreateView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Michael on 30/06/19.
 */

@Module(includes = { WorkModule.class })
public class WorkCreateModule {

    private final WorkCreateView view;

    public WorkCreateModule(WorkCreateView view) {
        this.view = view;
    }

    @Provides
    WorkCreatePresenter workCreatePresenter(PostWork postWork, GetProducts getProducts, UpdateWork updateWork, ListProductMapper mapper) {
        return new WorkCreatePresenterImpl(postWork, getProducts, updateWork, mapper, view);
    }
}

package com.workflow.presentation.presenter;

import com.workflow.data.model.CurrentWorkDetailAssignedModel;
import com.workflow.data.net.responses.GenericListResponseModel;
import com.workflow.domain.interactor.work.GetWorkDetailAssigned;
import com.workflow.presentation.view.WorkDetailFragmentView;

public class WorkDetailAssignedPresenterImpl implements WorkDetailPresenter {

    private final WorkDetailFragmentView<CurrentWorkDetailAssignedModel> view;
    private final GetWorkDetailAssigned getWorkDetailAssigned;

    public WorkDetailAssignedPresenterImpl(WorkDetailFragmentView view, GetWorkDetailAssigned getWorkDetailAssigned) {
        this.view = view;
        this.getWorkDetailAssigned = getWorkDetailAssigned;
    }

    @Override
    public void getWorks() {
        if (view.isConnected()) {
            view.getOptions().put("sort_by", "position");
            view.getOptions().put("sort_direction", "asc");
            view.getOptions().put("page", String.valueOf(view.getAdapter().getCurrentPage()));
            view.getOptions().put("limit", String.valueOf(view.getAdapter().getLimit()));

            getWorkDetailAssigned.execute(new GenericObserver<GenericListResponseModel<CurrentWorkDetailAssignedModel>>(view) {
                @Override
                public void onSuccess(GenericListResponseModel<CurrentWorkDetailAssignedModel> responseModel) {
                    super.onSuccess(responseModel);
                    view.renderItems(responseModel);
                }
            }, view.getOptions());
        }
    }

    @Override
    public void resume() {
        refreshData();
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        getWorkDetailAssigned.dispose();
    }

    private void refreshData() {
        view.getAdapter().setCurrentPage(1);
        if (view.isConnected()) {
            view.showLoading();
            getWorks();
        }
    }
}

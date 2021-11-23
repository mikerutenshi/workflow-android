package com.workflow.presentation.presenter;

import com.workflow.data.model.CurrentWorkDetailDoneModel;
import com.workflow.data.net.responses.GenericListResponseModel;
import com.workflow.domain.interactor.work.GetWorkDetailDone;
import com.workflow.presentation.view.WorkDetailFragmentView;

public class WorkDetailDonePresenterImpl implements WorkDetailPresenter {
    private final WorkDetailFragmentView<CurrentWorkDetailDoneModel> view;
    private final GetWorkDetailDone getWorkDetailAssigned;

    public WorkDetailDonePresenterImpl(WorkDetailFragmentView view, GetWorkDetailDone getWorkDetailAssigned) {
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

            getWorkDetailAssigned.execute(new GenericObserver<GenericListResponseModel<CurrentWorkDetailDoneModel>>(view) {
                @Override
                public void onSuccess(GenericListResponseModel<CurrentWorkDetailDoneModel> responseModel) {
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

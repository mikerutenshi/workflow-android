package com.workflow.presentation.presenter;

import com.workflow.data.model.CurrentWorkListModel;
import com.workflow.data.model.WorkModel;
import com.workflow.data.net.responses.GenericListResponseModel;
import com.workflow.domain.interactor.work.DeleteWork;
import com.workflow.domain.interactor.work.GetWorkList;
import com.workflow.presentation.view.CurrentWorkListView;
import com.workflow.utils.WorkflowUtils;

import java.util.ArrayList;
import java.util.List;

public class CurrentWorkListPresenterImpl implements CurrentWorkListPresenter {
    private final CurrentWorkListView view;
    private final GetWorkList getWorkList;
    private final DeleteWork deleteWork;

    public CurrentWorkListPresenterImpl(CurrentWorkListView view, GetWorkList getWorkList, DeleteWork deleteWork) {
        this.view = view;
        this.getWorkList = getWorkList;
        this.deleteWork = deleteWork;
    }

    @Override
    public void getCurrentWorks() {
        if (view.isConnected()) {
            view.getOptions().put("page", String.valueOf(view.getAdapter().getCurrentPage()));
            view.getOptions().put("limit", String.valueOf(view.getAdapter().getLimit()));

            getWorkList.execute(new GenericObserver<GenericListResponseModel<CurrentWorkListModel>>(view) {
                @Override
                public void onSuccess(GenericListResponseModel<CurrentWorkListModel> responseModel) {
                    super.onSuccess(responseModel);
                    view.renderItems(responseModel);
                }
            }, view.getOptions());
        }
    }

    @Override
    public void deleteWork(Integer workId) {
        if (view.isConnected()) {
            view.showLoading();
            List<Integer> idsToDelete = new ArrayList<>();
            idsToDelete.add(workId);
            deleteWork.execute(new GenericObserver<String>(view) {
                @Override
                public void onSuccess(String s) {
                    super.onSuccess(s);
//                    view.adapterDeleteItem(param);
                    refreshData();
                    view.showSnackBar(WorkflowUtils.SNACK_BAR_SUCCESS, s);
                }
            }, idsToDelete);
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
        getWorkList.dispose();
    }

    private void refreshData() {
        view.getAdapter().setCurrentPage(1);
        if (view.isConnected()) {
            view.showLoading();
            getCurrentWorks();
        }
    }
}

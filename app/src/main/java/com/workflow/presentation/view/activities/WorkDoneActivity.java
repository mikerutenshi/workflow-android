package com.workflow.presentation.view.activities;

import android.content.Context;
import android.content.Intent;

import com.workflow.WorkflowApplication;
import com.workflow.data.model.DoneWorkListModel;
import com.workflow.presentation.di.components.DaggerWorkAssignComponent;
import com.workflow.presentation.di.components.DaggerWorkDoComponent;
import com.workflow.presentation.di.components.WorkDoComponent;
import com.workflow.presentation.di.modules.ContextModule;
import com.workflow.presentation.di.modules.WorkDoModule;
import com.workflow.presentation.presenter.WorkDoPresenterImpl;
import com.workflow.presentation.presenter.WorkerWorkAssignPresenter;
import com.workflow.presentation.view.adapters.BaseViewHolder;
import com.workflow.presentation.view.adapters.OnRecyclerObjectClickListener;
import com.workflow.presentation.view.adapters.PaginationAdapter;
import com.workflow.presentation.view.adapters.WorkDoneableAdapter;
import com.workflow.utils.DateUtils;
import com.workflow.utils.WorkflowUtils;

import javax.inject.Inject;

import static com.workflow.presentation.navigation.Navigator.PARCELABLE_WORKER_MODEL;
import static com.workflow.utils.PreferenceUtils.PREF_WORK_DO_FILTER_STATUS;

public class WorkDoneActivity extends WorkerWorkAssignActivity<DoneWorkListModel> {

    @Inject WorkDoneableAdapter adapter;
    @Inject WorkDoPresenterImpl presenter;

    private WorkDoComponent component;

    @Override
    PaginationAdapter<DoneWorkListModel, OnRecyclerObjectClickListener<DoneWorkListModel>, BaseViewHolder<DoneWorkListModel, OnRecyclerObjectClickListener<DoneWorkListModel>>> adapter() {
        return adapter;
    }

    @Override
    String filterPrefKey() {
        return PREF_WORK_DO_FILTER_STATUS;
    }

    @Override
    WorkerWorkAssignPresenter<DoneWorkListModel> presenter() {
        return presenter;
    }

    @Override
    void toggleItemChecked(int position, DoneWorkListModel item) {
        adapter.clearSelectedItems();
        if (adapter.getItem(position).isChecked()) {

            adapter.getItem(position).toggle();
            adapter.notifyDataSetChanged();
            adapter.setItemCheckedPosition(-1);
            etQuantity.setText("");
            etNotes.setText(null);
        } else {
            adapter.addSelectedItem(item);
            if (adapter.getItemCheckedPosition() != -1) {
                adapter.getItem(adapter.getItemCheckedPosition()).toggle();
                adapter.getItem(position).toggle();
                adapter.notifyDataSetChanged();
                adapter.setItemCheckedPosition(position);
            } else {
                adapter.getItem(position).toggle();
                adapter.notifyDataSetChanged();
                adapter.setItemCheckedPosition(position);
            }
            etQuantityMaxValue = item.getQuantityRemaining();
            etQuantity.setText(String.valueOf(item.getQuantityRemaining()));
            etNotes.setText(item.getNotes());
        }
    }

    @Override
    void postWork(DoneWorkListModel item) {
        item.setDoneAt(DateUtils.clientToServer(calendar, DateUtils.TYPE_DATE_TIME));
        if (etQuantity.getText() != null) {
            item.setQuantityDone(Integer.valueOf(etQuantity.getText().toString()));
        }
        String notes = etNotes.getText() == null ? null : etNotes.getText().toString();
        item.setNotes(notes);

        presenter.postWork(item);
    }

    @Override
    public void showSnackBar(int type, String msg) {
        if (type == WorkflowUtils.SNACK_BAR_SUCCESS) {
            msg = String.format(strAssignmentSuccess
                    , etQuantity.getText() == null ? null : etQuantity.getText().toString()
                    , getSelectedItem().getSpkNo()
                    , getSelectedItem().getArticleNo()
                    , WorkflowUtils.renderWorked(this, presenter().getWorkerPos())
                    , mWorkerModel.getName());
        }
        WorkflowUtils.generateSnackBar(this, type, msg).show();
    }

    @Override
    void initDagger() {
        mWorkerModel = getIntent().getParcelableExtra(PARCELABLE_WORKER_MODEL);

        if (component == null) {
            component = DaggerWorkDoComponent.builder()
                    .applicationComponent(((WorkflowApplication) getApplicationContext()).getApplicationComponent())
                    .workDoModule(new WorkDoModule(this, mWorkerModel.getId(), mWorkerModel.getPositions().get(0)))
                    .contextModule(new ContextModule(this))
                    .build();
        }
        component.inject(this);
    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, WorkDoneActivity.class);
    }
}

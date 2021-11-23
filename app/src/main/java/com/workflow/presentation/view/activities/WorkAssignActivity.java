package com.workflow.presentation.view.activities;

import android.content.Context;
import android.content.Intent;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.workflow.R;
import com.workflow.WorkflowApplication;
import com.workflow.data.model.AssignedWorkListModel;
import com.workflow.presentation.di.components.DaggerWorkAssignComponent;
import com.workflow.presentation.di.components.WorkAssignComponent;
import com.workflow.presentation.di.modules.ContextModule;
import com.workflow.presentation.di.modules.WorkAssignModule;
import com.workflow.presentation.presenter.WorkAssignPresenterImpl;
import com.workflow.presentation.presenter.WorkerWorkAssignPresenter;
import com.workflow.presentation.view.adapters.BaseViewHolder;
import com.workflow.presentation.view.adapters.OnRecyclerObjectClickListener;
import com.workflow.presentation.view.adapters.PaginationAdapter;
import com.workflow.presentation.view.adapters.WorkAssignableAdapter;
import com.workflow.utils.DateUtils;
import com.workflow.utils.WorkflowUtils;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;

import static com.workflow.presentation.navigation.Navigator.PARCELABLE_WORKER_MODEL;
import static com.workflow.utils.PreferenceUtils.PREF_WORK_ASSIGN_FILTER_STATUS;

public class WorkAssignActivity extends WorkerWorkAssignActivity<AssignedWorkListModel> {

    @BindView(R.id.til_worker_work_date) TextInputLayout tilAssignDate;
    @BindString(R.string.work_assign_date) String strWorkAssignDateHint;

    @Inject WorkAssignableAdapter adapter;
    @Inject WorkAssignPresenterImpl presenter;

    private WorkAssignComponent component;

    @Override
    PaginationAdapter<AssignedWorkListModel, OnRecyclerObjectClickListener<AssignedWorkListModel>, BaseViewHolder<AssignedWorkListModel, OnRecyclerObjectClickListener<AssignedWorkListModel>>> adapter() {
        return adapter;
    }

    @Override
    String filterPrefKey() {
        return PREF_WORK_ASSIGN_FILTER_STATUS;
    }

    @Override
    WorkerWorkAssignPresenter<AssignedWorkListModel> presenter() {
        return presenter;
    }

    @Override
    void toggleItemChecked(int position, AssignedWorkListModel item) {
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
    void postWork(AssignedWorkListModel item) {
        item.setAssignedAt(DateUtils.clientToServer(calendar, DateUtils.TYPE_DATE_TIME));
        if (etQuantity.getText() != null) {
            item.setQuantityAssigned(Integer.valueOf(etQuantity.getText().toString()));
        }
        String notes = etNotes.getText() == null ? null : etNotes.getText().toString();
        item.setNotes(notes);

        presenter.postWork(item);
    }

    @Override
    void initDagger() {
        mWorkerModel = getIntent().getParcelableExtra(PARCELABLE_WORKER_MODEL);

        if (component == null) {
            component = DaggerWorkAssignComponent.builder()
                    .applicationComponent(((WorkflowApplication) getApplicationContext()).getApplicationComponent())
                    .workAssignModule(new WorkAssignModule(this, mWorkerModel.getId(), mWorkerModel.getPositions().get(0)))
                    .contextModule(new ContextModule(this))
                    .build();
        }
        component.inject(this);
        tilAssignDate.setHint(strWorkAssignDateHint);
    }


    @Override
    public void showSnackBar(int type, String msg) {
        if (type == WorkflowUtils.SNACK_BAR_SUCCESS) {
            msg = String.format(strAssignmentSuccess
                    , etQuantity.getText() == null ? null : etQuantity.getText().toString()
                    , getSelectedItem().getSpkNo()
                    , getSelectedItem().getArticleNo()
                    , WorkflowUtils.renderWorking(this, presenter().getWorkerPos())
                    , mWorkerModel.getName());
        }
        Snackbar snackbar = WorkflowUtils.generateSnackBar(this, type, msg);
//        snackbar.addCallback(new Snackbar.Callback() {
//            @Override
//            public void onDismissed(Snackbar transientBottomBar, int event) {
//                super.onDismissed(transientBottomBar, event);
//
//                if (type != WorkflowUtils.SNACK_BAR_ERROR) {
//                    dismiss();
//                }
//            }
//        });
        snackbar.show();
    }


    public static Intent getCallingIntent(Context context) {
        return new Intent(context, WorkAssignActivity.class);
    }
}

package com.workflow.presentation.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.workflow.R;
import com.workflow.WorkflowApplication;
import com.workflow.data.model.WorkerModel;
import com.workflow.presentation.di.components.DaggerWorkerCreateComponent;
import com.workflow.presentation.di.components.WorkerCreateComponent;
import com.workflow.presentation.di.modules.WorkerCreateModule;
import com.workflow.presentation.presenter.WorkerCreatePresenter;
import com.workflow.presentation.view.WorkerCreateView;
import com.workflow.utils.ConnectionUtil;
import com.workflow.utils.KeyboardUtils;
import com.workflow.utils.Positions;
import com.workflow.utils.WorkflowUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

import static com.workflow.presentation.navigation.Navigator.PARCELABLE_WORKER_MODEL;

/**
 * Created by Michael on 28/06/19.
 */

public class WorkerCreateActivity extends BaseActivity implements WorkerCreateView {

    @BindView(R.id.et_worker_create_name) EditText etName;
    @BindView(R.id.til_worker_create_name) TextInputLayout tilName;
    @BindView(R.id.layout_alpha_progress_bar) ConstraintLayout progressBar;
    @BindView(R.id.cb_worker_create_drawer) AppCompatCheckBox cbDrawer;
    @BindView(R.id.cb_worker_create_sewer) AppCompatCheckBox cbSewer;
    @BindView(R.id.cb_worker_create_assembler) AppCompatCheckBox cbAssembler;
    @BindView(R.id.cb_worker_create_sole_stitcher) AppCompatCheckBox cbSoleStitcher;
    @BindView(R.id.cb_worker_create_lining_drawer) AppCompatCheckBox cbLiningDrawer;
    @BindView(R.id.cb_worker_create_insole_stitcher) AppCompatCheckBox cbInsoleStitcher;
    @BindView(R.id.til_worker_create_error_choose_position) TextInputLayout tilErrorChoosePosition;

    @BindString(R.string.worker_create_position_hint) String strPositionHint;
    @BindString(R.string.field_required) String strFieldRequired;
    @BindString(R.string.validation_choose_a_position) String strChoosePosition;
    @BindString(R.string.worker_edit_title) String strEditTitle;
    @BindString(R.string.no_connection) String strNoConnection;

    @Inject WorkerCreatePresenter presenter;

    private static final String POS_DRAWER = "drawer";
    private static final String POS_SEWER = "sewer";
    private static final String POS_ASSEMBLER = "assembler";
    private static final String POS_SOLE_STITCHER = "sole_stitcher";
    private WorkerCreateComponent component;
    private WorkerModel workerModel;

    @Override
    int setView() {
        return R.layout.activity_worker_create_coordinator;
    }

    @Override
    void initDagger() {
        if (component == null) {
            component = DaggerWorkerCreateComponent.builder()
                    .applicationComponent(((WorkflowApplication) getApplication()).getApplicationComponent())
                    .workerCreateModule(new WorkerCreateModule(this))
                    .build();
        }
        component.inject(this);
    }

    @Override
    void afterViewCreated(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.outline_arrow_back_ios_white_24);
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            workerModel = bundle.getParcelable(PARCELABLE_WORKER_MODEL);
            setTitle(strEditTitle);
        }

        KeyboardUtils.requestFocusShowKeyboard(WorkerCreateActivity.this, etName);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (workerModel != null) {
            seedForm(workerModel);
        }

        presenter.resume();
    }

    @Override
    protected void onPause() {
        presenter.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showContent() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showSnackBar(int type, String msg) {
        Snackbar snackbar = WorkflowUtils.generateSnackBar(this, type, msg);
        snackbar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
                dismiss();
            }
        });
        snackbar.show();
    }

    @Override
    public boolean isConnected() {
        if (!ConnectionUtil.isOnline(this)) {
            showSnackBar(WorkflowUtils.SNACK_BAR_WARNING, strNoConnection);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void dismiss() {
        finish();
    }

    @Override
    public void showSnackThenClearForm(int type, String msg) {
        Snackbar snackbar = WorkflowUtils.generateSnackBar(this, type, msg);
        snackbar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);

                etName.setText(null);
                cbDrawer.setChecked(false);
                cbLiningDrawer.setChecked(false);
                cbSewer.setChecked(false);
                cbAssembler.setChecked(false);
                cbSoleStitcher.setChecked(false);
                cbInsoleStitcher.setChecked(false);
                KeyboardUtils.requestFocusShowKeyboard(WorkerCreateActivity.this, etName);
            }
        });
        snackbar.show();
    }

    @OnClick(R.id.btn_worker_create_save)
    void onSaveClick() {
        if (isFormValid()) {
            if (workerModel == null) {
                presenter.postWorker(gatherFormData());
            } else {
                presenter.updateWorker(gatherFormData());
            }
        }
    }

    private WorkerModel gatherFormData() {
        List<String> positions = new ArrayList<>();

        if (cbDrawer.isChecked()) {
            positions.add(POS_DRAWER);
        }
        if (cbLiningDrawer.isChecked()) {
            positions.add(Positions.LINING_DRAWER);
        }
        if (cbSewer.isChecked()) {
            positions.add(POS_SEWER);
        }
        if (cbAssembler.isChecked()) {
            positions.add(POS_ASSEMBLER);
        }
        if (cbSoleStitcher.isChecked()) {
            positions.add(POS_SOLE_STITCHER);
        }
        if (cbInsoleStitcher.isChecked()) {
            positions.add(Positions.INSOLE_STITCHER);
        }

        if (workerModel != null) {
            return new WorkerModel(workerModel.getId()
                    , etName.getText().toString()
                    , positions);
        } else {
            return new WorkerModel(etName.getText().toString()
                    , positions);
        }
    }

//    private String getPositionKey(int pos) {
//        switch (pos) {
//            case 1: return "drawer";
//            case 2: return "sewer";
//            case 3: return "assembler";
//            case 4: return "sole_stitcher";
//            default: return null;
//        }
//    }

    private boolean isFormValid() {
        boolean isValid = true;

        tilErrorChoosePosition.setErrorEnabled(false);
        tilName.setErrorEnabled(false);

        if (!cbDrawer.isChecked() && !cbSewer.isChecked()
                && !cbAssembler.isChecked() && !cbSoleStitcher.isChecked()
                && !cbLiningDrawer.isChecked() && !cbInsoleStitcher.isChecked()) {
            isValid = false;
            tilErrorChoosePosition.setError(strChoosePosition);
            tilErrorChoosePosition.setErrorEnabled(true);
        }

        if (etName.getText().toString().isEmpty()) {
            isValid = false;
            tilName.setError(String.format(strFieldRequired, tilName.getHint()));
            tilName.setErrorEnabled(true);
        }

        return isValid;
    }

    private void seedForm(WorkerModel workerModel) {
        etName.setText(workerModel.getName());

        for (String pos : workerModel.getPositions()) {
            switch (pos) {
                case Positions.DRAWER:
                    cbDrawer.setChecked(true);
                    break;
                case Positions.SEWER:
                    cbSewer.setChecked(true);
                    break;
                case Positions.ASSEMBLER:
                    cbAssembler.setChecked(true);
                    break;
                case Positions.SOLE_STITCHER:
                    cbSoleStitcher.setChecked(true);
                    break;
                case Positions.LINING_DRAWER:
                    cbLiningDrawer.setChecked(true);
                    break;
                case Positions.INSOLE_STITCHER:
                    cbInsoleStitcher.setChecked(true);
                    break;
                default:
                    break;
            }
        }
    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, WorkerCreateActivity.class);
    }
}

package com.workflow.presentation.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.workflow.R;
import com.workflow.WorkflowApplication;
import com.workflow.data.model.GenericItemPaginationModel;
import com.workflow.data.model.ListModel;
import com.workflow.data.model.SearchPaginatedQueryModel;
import com.workflow.data.model.WorkModel;
import com.workflow.presentation.di.components.DaggerWorkCreateComponent;
import com.workflow.presentation.di.components.WorkCreateComponent;
import com.workflow.presentation.di.modules.ContextModule;
import com.workflow.presentation.di.modules.ValidatorModule;
import com.workflow.presentation.di.modules.WorkCreateModule;
import com.workflow.presentation.presenter.WorkCreatePresenter;
import com.workflow.presentation.view.WorkCreateView;
import com.workflow.presentation.view.fragments.SearchableSpinnerDialogFragment;
import com.workflow.utils.ConnectionUtil;
import com.workflow.utils.KeyboardUtils;
import com.workflow.utils.WorkflowUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import timber.log.Timber;

import static com.workflow.presentation.navigation.Navigator.PARCELABLE_WORK_MODEL;

/**
 * Created by Michael on 28/06/19.
 */

public class WorkCreateActivity extends BaseActivity implements WorkCreateView, Validator.ValidationListener {

    @BindView(R.id.et_work_create_spk_no) EditText etSpkNo;
    @BindView(R.id.et_work_create_article_no) EditText etArticleNo;
    @BindView(R.id.et_work_create_quantity) EditText etQty;
    @BindView(R.id.til_work_create_spk_no) @NotEmpty TextInputLayout tilSpkNo;
    @BindView(R.id.til_work_create_article_no) @NotEmpty TextInputLayout tilArticleNo;
    @BindView(R.id.til_work_create_quantity) @NotEmpty TextInputLayout tilQty;
    @BindView(R.id.layout_alpha_progress_bar) ConstraintLayout progressBar;
    @BindView(R.id.et_work_create_notes) TextInputEditText etNotes;

    @BindString(R.string.field_required) String strFieldRequired;
    @BindString(R.string.this_field) String strThisField;
    @BindString(R.string.work_edit_title) String strEditTitle;
    @BindString(R.string.no_connection) String strNoConnection;

    @Inject Validator validator;
    @Inject WorkCreatePresenter presenter;

    private WorkCreateComponent component;
    private SearchableSpinnerDialogFragment.DialogListener dialogListener;
    private SearchableSpinnerDialogFragment spinnerDialogFragment;
    private Integer mProductId = null;
    private WorkModel workModel;

    @Override
    int setView() {
        return R.layout.activity_work_create_coordinator;
    }

    @Override
    void initDagger() {
        if (component == null) {
            component = DaggerWorkCreateComponent.builder()
                    .applicationComponent(((WorkflowApplication) getApplication()).getApplicationComponent())
                    .workCreateModule(new WorkCreateModule(this))
                    .contextModule(new ContextModule(this))
                    .validatorModule(new ValidatorModule(this, this))
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
            workModel = bundle.getParcelable(PARCELABLE_WORK_MODEL);
            setTitle(strEditTitle);
        }

        KeyboardUtils.requestFocusShowKeyboard(WorkCreateActivity.this, etSpkNo);
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

        if (workModel != null) {
            seedForm(workModel);
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
    public void renderProducts(GenericItemPaginationModel<List<ListModel>> items) {
        spinnerDialogFragment.showContent();
        spinnerDialogFragment.renderItems(items);
    }

    @Override
    public void showDialogError(String err) {
        spinnerDialogFragment.showError(err);
    }

    @Override
    public void showDialogDataEmpty() {
        spinnerDialogFragment.showDataEmpty();
    }

    @Override
    public void showSnackThenClearForm(int type, String msg) {
        Snackbar snackbar = WorkflowUtils.generateSnackBar(this, type, msg);
        snackbar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);

                String stringSpkNo = etSpkNo.getText().toString();
                String currentSpkNo = "0";
                if (!stringSpkNo.isEmpty()) {
                    Integer prevSpkNo = Integer.valueOf(stringSpkNo);
                    currentSpkNo = String.valueOf(prevSpkNo + 1);
                }
                etSpkNo.setText(currentSpkNo);
                etSpkNo.setSelection(etSpkNo.getText().length());
                etArticleNo.setText(null);
                etQty.setText(null);
                etNotes.setText(null);
                KeyboardUtils.requestFocusShowKeyboard(WorkCreateActivity.this, etSpkNo);
            }
        });
        snackbar.show();
    }

    @Override
    public void onValidationSucceeded() {
        if (workModel == null) {
            presenter.postWork(gatherFormData());
        } else {
            presenter.updateWork(gatherFormData());
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
//            String message = error.getCollatedErrorMessage(this);

            if (view instanceof TextInputLayout) {
                String fieldName =
                        (((TextInputLayout) view).getHint() == null) ? strThisField : ((TextInputLayout) view).getHint().toString();
                ((TextInputLayout) view).setError(String.format(strFieldRequired, fieldName));
            } else {
                //todo
            }
        }
    }

    @OnClick(R.id.btn_work_create_save)
    void onSaveClick() {
        validator.validate();
    }

    @OnFocusChange(R.id.et_work_create_article_no)
    void onArticleNoFocus(boolean focused) {
        if (focused) {
            dialogListener = new SearchableSpinnerDialogFragment.DialogListener() {
                @Override
                public void onItemClick(int position, ListModel listModel) {
//                showMessage(String.format("Product ID chosen: %s", listModel.getId()));
                    etArticleNo.setText(listModel.getName());
                    mProductId = Integer.valueOf(listModel.getId());
                    etQty.requestFocus();
                }

                @Override
                public void getFilteredList(SearchPaginatedQueryModel queryModel) {
                    presenter.getProducts(queryModel);
                }

                @Override
                public void onDismiss() {
                    Timber.d("mOnDismissCalled");

//                    KeyboardUtils.requestFocusShowKeyboard(WorkCreateActivity.this, etQty);
                }

                @Override
                public void onCancel() {
                    KeyboardUtils.requestFocusShowKeyboard(WorkCreateActivity.this, etSpkNo);
                }
            };
            int width = (int)(getResources().getDisplayMetrics().widthPixels * 0.95);
            int height = (int)(getResources().getDisplayMetrics().heightPixels * 0.70);
            spinnerDialogFragment = new SearchableSpinnerDialogFragment(height, width);
            spinnerDialogFragment.setListener(dialogListener);
//            spinnerDialogFragment.setCancelable(false);
            spinnerDialogFragment.show(getSupportFragmentManager(), "ProductSpinnerDialogFragment");
        }
    }

    private WorkModel gatherFormData() {
        String notes = null;
        if (!etNotes.getText().toString().isEmpty()) {
            notes = etNotes.getText().toString();
        }

        if (workModel != null) {
            return new WorkModel(workModel.getId()
                    , Integer.valueOf(etSpkNo.getText().toString())
                    , mProductId
                    , Integer.valueOf(etQty.getText().toString())
                    , notes);
        } else {
            return new WorkModel(Integer.valueOf(etSpkNo.getText().toString())
                    , mProductId
                    , Integer.valueOf(etQty.getText().toString())
                    , notes);
        }
    }

    private void seedForm(WorkModel workModel) {
        etSpkNo.setText(String.valueOf(workModel.getSpkNo()));
        String productCategory = WorkflowUtils.renderProductCategory(this, Integer.valueOf(workModel.getProductCategoryId()));
        etArticleNo.setText(String.format("%1s (%2s)", workModel.getArticleNo(), productCategory));
        etQty.setText(String.valueOf(workModel.getQty()));
        mProductId = workModel.getProductId();

        if (workModel.getNotes() != null) {
            etNotes.setText(workModel.getNotes());
        }
    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, WorkCreateActivity.class);
    }
}

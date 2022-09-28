package com.workflow.presentation.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.workflow.R;
import com.workflow.WorkflowApplication;
import com.workflow.data.model.ListModel;
import com.workflow.data.model.ProductModel;
import com.workflow.domain.interactor.product.PostProduct;
import com.workflow.presentation.di.components.DaggerProductCreateComponent;
import com.workflow.presentation.di.components.ProductCreateComponent;
import com.workflow.presentation.di.modules.ContextModule;
import com.workflow.presentation.di.modules.ProductCreateModule;
import com.workflow.presentation.di.modules.ValidatorModule;
import com.workflow.presentation.presenter.ProductCreatePresenter;
import com.workflow.presentation.view.CreateProductView;
import com.workflow.presentation.view.adapters.ActvArrayAdapter;
import com.workflow.utils.ConnectionUtil;
import com.workflow.utils.KeyboardUtils;
import com.workflow.utils.WorkflowUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.workflow.presentation.navigation.Navigator.PARCELABLE_PRODUCT_MODEL;

/**
 * Created by Michael on 19/06/19.
 */

public class ProductCreateActivity extends BaseActivity implements CreateProductView, Validator.ValidationListener {

    @BindView(R.id.et_create_product_article_no) EditText etArticleNo;
//    @BindView(R.id.et_create_product_color) EditText etColor;
    @BindView(R.id.et_create_product_drawing_cost) EditText etDrawingCost;
    @BindView(R.id.et_create_product_sewing_cost) EditText etSewingCost;
    @BindView(R.id.et_create_product_assembling_cost) EditText etAssemblingCost;
    @BindView(R.id.et_create_product_sole_stitching_cost) EditText etSoleStitchingCost;
    @BindView(R.id.et_create_product_lining_drawing_cost) EditText etLiningDrawingCost;
    @BindView(R.id.et_create_product_insole_stitching_cost) EditText etInsoleStitchingCost;
    @BindView(R.id.layout_alpha_progress_bar) ConstraintLayout progressBar;
    @BindView(R.id.actv_product_create_category) AutoCompleteTextView actvCategory;

    @BindView(R.id.til_create_product_category)
    @NotEmpty(messageResId = R.string.field_required_simple) TextInputLayout tilRole;
    @BindView(R.id.til_create_product_article_no)
    @NotEmpty(messageResId = R.string.field_required_simple)
    @Length(max = 7, messageResId = R.string.validation_article_no_length)
    TextInputLayout tilArticleNo;
    @BindView(R.id.til_create_product_drawing_cost)
    @NotEmpty(messageResId = R.string.field_required_simple)
    @Length(min = 3, messageResId = R.string.field_length_min_3)
    TextInputLayout tilDrawingCost;
    @BindView(R.id.til_create_product_lining_drawing_cost)
    @NotEmpty(messageResId = R.string.field_required_simple)
    @Length(min = 3, messageResId = R.string.field_length_min_3)
    TextInputLayout tilLiningDrawingCost;
    @BindView(R.id.til_create_product_sewing_cost)
    @NotEmpty(messageResId = R.string.field_required_simple)
    @Length(min = 3, messageResId = R.string.field_length_min_3)
    TextInputLayout tilSewingCost;
    @BindView(R.id.til_create_product_assembling_cost)
    @NotEmpty(messageResId = R.string.field_required_simple)
    @Length(min = 3, messageResId = R.string.field_length_min_3)
    TextInputLayout tilAssemblingCost;
    @BindView(R.id.til_create_product_sole_stitching_cost) TextInputLayout tilSoleStitchingCost;
    @BindView(R.id.til_create_product_insole_stitching_cost) TextInputLayout tilInsoleStitchingCost;

    @BindString(R.string.field_required) String strFieldRequired;
    @BindString(R.string.this_field) String strThisField;
    @BindString(R.string.product_edit_title) String strEditTitle;
    @BindString(R.string.no_connection) String strNoConnection;

    @Inject PostProduct postProduct;
    @Inject ProductCreatePresenter presenter;
    @Inject Validator validator;
    @Inject ActvArrayAdapter categoryAdapter;

    private ProductCreateComponent component;
    private ProductModel productModel;

    @Override
    int setView() {
        return R.layout.activity_product_create_coordinator;
    }

    @Override
    void initDagger() {
        if (component == null) {
            component = DaggerProductCreateComponent.builder()
                    .applicationComponent(((WorkflowApplication) getApplication()).getApplicationComponent())
                    .productCreateModule(new ProductCreateModule(this))
                    .validatorModule(new ValidatorModule(this, this))
                    .contextModule(new ContextModule(this))
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
            productModel = bundle.getParcelable(PARCELABLE_PRODUCT_MODEL);
            setTitle(strEditTitle);
        }

        KeyboardUtils.requestFocusShowKeyboard(ProductCreateActivity.this, etArticleNo);

        actvCategory.setAdapter(categoryAdapter);
        actvCategory.setOnItemClickListener((parent, view, position, id) -> {
            categoryAdapter.setChosenItem(((ListModel) parent.getItemAtPosition(position)).getId());
        });
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

                etArticleNo.setText(null);
                etDrawingCost.setText(null);
                etSewingCost.setText(null);
                etAssemblingCost.setText(null);
                etSoleStitchingCost.setText(null);
                etLiningDrawingCost.setText(null);
                etInsoleStitchingCost.setText(null);
                actvCategory.setText(null, false);
                KeyboardUtils.requestFocusShowKeyboard(ProductCreateActivity.this, etArticleNo);
            }
        });
        snackbar.show();
    }

    @Override
    public void renderProductCategories(List<ListModel> productCategories) {
        for (ListModel category : productCategories) {
            category.setName(WorkflowUtils.renderProductCategory(this, Integer.valueOf(category.getId())));
        }
        categoryAdapter.addAll(productCategories);

        if (productModel != null) {
            seedForm(productModel);
        }
    }

    @Override
    public int getAdapterSize() {
        return categoryAdapter.getCount();
    }

    @Override
    public void onValidationSucceeded() {
        if (productModel == null) {
            presenter.postProduct(gatherFormData());
        } else {
            presenter.updateProduct(gatherFormData());
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
//                ((TextInputLayout) view).setError(String.format(strFieldRequired, fieldName));
                ((TextInputLayout) view).setError(error.getCollatedErrorMessage(this));
            } else {
                //todo
            }
        }
    }

    @OnClick(R.id.btn_product_create_save)
    void onSaveClick() {
        validator.validate();
    }

    private ProductModel gatherFormData() {
        Integer soleStitchingCost = etSoleStitchingCost.getText().toString().isEmpty() ? 0
                : Integer.valueOf(etSoleStitchingCost.getText().toString());

        Integer insoleStitchingCost = etInsoleStitchingCost.getText().toString().isEmpty() ? 0
                : Integer.valueOf(etInsoleStitchingCost.getText().toString());

        if (productModel != null) {
            return new ProductModel(productModel.getId()
                    , etArticleNo.getText().toString()
                    , Integer.valueOf(etDrawingCost.getText().toString())
                    , Integer.valueOf(etSewingCost.getText().toString())
                    , Integer.valueOf(etAssemblingCost.getText().toString())
                    , soleStitchingCost
                    , insoleStitchingCost
                    , Integer.valueOf(etLiningDrawingCost.getText().toString())
                    , Integer.valueOf(categoryAdapter.getChosenItem()));
        } else {
            return new ProductModel(etArticleNo.getText().toString()
                    , Integer.valueOf(etDrawingCost.getText().toString())
                    , Integer.valueOf(etSewingCost.getText().toString())
                    , Integer.valueOf(etAssemblingCost.getText().toString())
                    , soleStitchingCost
                    , insoleStitchingCost
                    , Integer.valueOf(etLiningDrawingCost.getText().toString())
                    , Integer.valueOf(categoryAdapter.getChosenItem()));
        }
    }

    private void seedForm(ProductModel productModel) {
        etArticleNo.setText(productModel.getArticleNo());
        etDrawingCost.setText(String.valueOf(productModel.getDrawingCost()));
        etLiningDrawingCost.setText(String.valueOf(productModel.getLiningDrawingCost()));
        etSewingCost.setText(String.valueOf(productModel.getSewingCost()));
        etAssemblingCost.setText(String.valueOf(productModel.getAssemblingCost()));
        etInsoleStitchingCost.setText(String.valueOf(productModel.getInsoleStitchingCost()));
        etSoleStitchingCost.setText(String.valueOf(productModel.getSoleStitchingCost()));
        actvCategory.setText(WorkflowUtils.renderProductCategory(this, productModel.getProductCategoryId()), false);
        categoryAdapter.setChosenItem(String.valueOf(productModel.getProductCategoryId()));
    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, ProductCreateActivity.class);
    }
}

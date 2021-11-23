package com.workflow.presentation.view.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Select;
import com.workflow.R;
import com.workflow.data.model.ListModel;
import com.workflow.data.model.UserModel;
import com.workflow.presentation.di.components.DaggerRegisterComponent;
import com.workflow.presentation.di.components.RegisterComponent;
import com.workflow.presentation.di.modules.RegisterModule;
import com.workflow.presentation.di.modules.ValidatorModule;
import com.workflow.presentation.presenter.RegisterPresenter;
import com.workflow.presentation.view.RegisterView;
import com.workflow.presentation.view.activities.AuthActivity;
import com.workflow.utils.ConnectionUtil;
import com.workflow.utils.Roles;
import com.workflow.utils.WorkflowUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class RegisterFragment extends BaseFragment implements RegisterView, Validator.ValidationListener {


    @BindView(R.id.actv_register_role) AutoCompleteTextView actvRole;
    @BindView(R.id.et_register_username) TextInputEditText etUsername;
    @BindView(R.id.et_register_first_name) TextInputEditText etFirstName;
    @BindView(R.id.et_register_last_name) TextInputEditText etLastName;
    @BindView(R.id.et_register_password) TextInputEditText etPassword;
    @BindView(R.id.et_register_password_confirm) TextInputEditText etPasswordConfirm;
    @BindView(R.id.layout_alpha_progress_bar) ConstraintLayout progressBar;

    @BindView(R.id.til_register_role) @NotEmpty TextInputLayout tilRole;
    @BindView(R.id.til_register_username) @NotEmpty TextInputLayout tilUsername;
    @BindView(R.id.til_register_first_name) @NotEmpty TextInputLayout tilFirstName;
    @BindView(R.id.til_register_last_name) TextInputLayout tilLastName;

    @BindView(R.id.til_register_password)
    @NotEmpty
    @Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE
            , messageResId = R.string.validation_invalid_password_suggestion)
    TextInputLayout tilPassword;

    @BindView(R.id.til_register_password_confirm)
    @NotEmpty
    @ConfirmPassword(messageResId = R.string.validation_password_not_match)
    TextInputLayout tilPasswordConfirm;

    @BindString(R.string.register_role_hint) String strRoleHint;
    @BindString(R.string.register_role_admin_work) String strRoleAdminWork;
    @BindString(R.string.register_role_admin_price) String strRoleAdminPrice;
    @BindString(R.string.register_role_quality_assurance) String strRoleQA;
    @BindString(R.string.this_field) String strThisField;
    @BindString(R.string.field_required) String strFieldRequired;
    @BindString(R.string.no_connection) String strNoConnection;

    @Inject Validator validator;
    @Inject RegisterPresenter presenter;

    private RegisterComponent component;
    private String keySelectedRole;

    @Override
    int setView() {
        return R.layout.fragment_register;
    }

    @Override
    void initDagger() {

        if (component == null) {
            component = DaggerRegisterComponent.builder()
                    .authComponent(((AuthActivity) context).getAuthComponent())
                    .registerModule(new RegisterModule(this))
                    .validatorModule(new ValidatorModule(this, this))
                    .build();
        }
        component.inject(this);
    }

    @Override
    void afterViewCreated(Bundle savedInstanceState) {
        List<String> roles = new ArrayList<>();
        roles.add(strRoleAdminPrice);
        roles.add(strRoleAdminWork);
        roles.add(strRoleQA);
        List<ListModel> rolesModel = new ArrayList<>(Arrays.asList(
                new ListModel(Roles.ADMIN_PRICE, strRoleAdminPrice),
                new ListModel(Roles.ADMIN_WORK, strRoleAdminWork),
                new ListModel(Roles.ADMIN_QA, strRoleQA)
        ));

        actvRole.setAdapter(new ArrayAdapter<ListModel>(context, android.R.layout.simple_spinner_dropdown_item, rolesModel));
        actvRole.setOnItemClickListener((parent, view, position, id) -> {
            keySelectedRole = ((ListModel) parent.getItemAtPosition(position)).getId();
        });
    }

    @Override
    public void onRegisterSuccess(String message) {
        showSnackBar(WorkflowUtils.SNACK_BAR_SUCCESS, message);
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
    public void showSnackBar(final int type, String msg) {
        Snackbar snackbar = WorkflowUtils.generateSnackBar((AuthActivity) context, type, msg);
        snackbar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);

                if (type == WorkflowUtils.SNACK_BAR_SUCCESS) {
                    ((AuthActivity) context).getBackToSignInPage();
                }
            }
        });
        snackbar.show();
    }

    @Override
    public boolean isConnected() {
        if (!ConnectionUtil.isOnline(context)) {
            showSnackBar(WorkflowUtils.SNACK_BAR_WARNING, strNoConnection);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onValidationSucceeded() {
        UserModel userModel = gatherFormData();
        presenter.register(userModel);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            List failedRules = error.getFailedRules();
            Rule rule = (Rule) failedRules.get(0);
            String message = rule.getMessage(context);

            if ("This field is required".equals(message)) {
                String fieldName =
                        (((TextInputLayout) view).getHint() == null) ? strThisField : ((TextInputLayout) view).getHint().toString();
                message = String.format(strFieldRequired, fieldName);
            }

            if (view instanceof TextInputLayout) {
                ((TextInputLayout) view).setError(message);
            } else if (view instanceof Spinner) {
                TextView tvError = ((TextView) ((Spinner) view).getSelectedView());
                tvError.setError("");
                tvError.setTextColor(Color.RED);
                tvError.setText(message);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        showKeyboard();
        presenter.resume();
    }

    @Override
    public void onPause() {
        presenter.pause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }

    @OnClick(R.id.btn_register)
    void register() {
        validator.validate();
    }

    private UserModel gatherFormData() {
        String username = etUsername.getText().toString().toLowerCase();
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().length() == 0 ?
                null : etLastName.getText().toString();
        String password = etPassword.getText().toString();
        return new UserModel(username, firstName, lastName, password, keySelectedRole);
    }

    private String getPositionKey(int pos) {
        switch (pos) {
            case 1: return Roles.ADMIN_PRICE;
            case 2: return Roles.ADMIN_WORK;
            case 3: return Roles.ADMIN_QA;
            default: return null;
        }
    }

    private void showKeyboard() {
        etUsername.post(new Runnable() {
            @Override
            public void run() {
                etUsername.requestFocus();
                InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imgr.showSoftInput(etUsername, InputMethodManager.SHOW_IMPLICIT);
            }
        });
    }
}

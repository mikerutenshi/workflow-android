package com.workflow.presentation.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.workflow.BuildConfig;
import com.workflow.R;
import com.workflow.data.model.SignInModel;
import com.workflow.data.model.UserModel;
import com.workflow.domain.interactor.auth.SignIn;
import com.workflow.presentation.di.components.DaggerSignInComponent;
import com.workflow.presentation.di.components.SignInComponent;
import com.workflow.presentation.di.modules.SignInModule;
import com.workflow.presentation.di.modules.ValidatorModule;
import com.workflow.presentation.navigation.Navigator;
import com.workflow.presentation.presenter.SignInPresenter;
import com.workflow.presentation.view.SignInView;
import com.workflow.presentation.view.activities.AuthActivity;
import com.workflow.utils.ConnectionUtil;
import com.workflow.utils.WorkflowUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.workflow.utils.PreferenceUtils.PREF_NAME;
import static com.workflow.utils.PreferenceUtils.PREF_REFRESH_TOKEN;
import static com.workflow.utils.PreferenceUtils.PREF_ROLE;
import static com.workflow.utils.PreferenceUtils.PREF_ACCESS_TOKEN;
import static com.workflow.utils.PreferenceUtils.PREF_USERNAME;

public class SignInFragment extends BaseFragment implements SignInView, Validator.ValidationListener {

    @BindView(R.id.et_sign_in_username) TextInputEditText etUsername;
    @BindView(R.id.et_sign_in_password) TextInputEditText etPassword;
    @BindView(R.id.til_sign_in_username) @NotEmpty TextInputLayout tilUsername;
    @BindView(R.id.til_sign_in_password) @NotEmpty TextInputLayout tilPassword;
    @BindView(R.id.layout_alpha_progress_bar) ConstraintLayout progressBar;
    @BindView(R.id.tv_sign_in_version) AppCompatTextView tvVersion;

    @BindString(R.string.this_field) String strThisField;
    @BindString(R.string.field_required) String strFieldRequired;
    @BindString(R.string.no_connection) String strNoConnection;

    @Inject SignIn signIn;
    @Inject SignInPresenter presenter;
    @Inject Navigator navigator;
    @Inject Validator validator;

    private SignInComponent component;
    private SharedPreferences sharedPreferences;

    @Override
    int setView() {
        return R.layout.fragment_sign_in;
    }

    @Override
    void initDagger() {
        if (component == null) {
            component = DaggerSignInComponent.builder()
                    .authComponent(((AuthActivity) context).getAuthComponent())
                    .signInModule(new SignInModule(this))
                    .validatorModule(new ValidatorModule(this, this))
                    .build();
        }
        component.inject(this);

    }

    @Override
    void afterViewCreated(Bundle savedInstanceState) {
        tvVersion.setText(BuildConfig.VERSION_NAME);
        this.sharedPreferences = ((AuthActivity) context).getSharedPreferences();
        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEND
                        || (event != null
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                        && event.getAction() == KeyEvent.ACTION_DOWN)) {
                    validator.validate();

                    // return true indicates handled
                    return true;
                }

                return false;
            }
        });
    }

    @Override
    public void saveUser(UserModel userModel) {
        String accessToken = "Bearer " + userModel.getAccessToken();
        String lastName = userModel.getLastName() == null ? "" : userModel.getLastName();
        String name = userModel.getFirstName() + " " + lastName;
        Timber.d("mRefreshToken %s", userModel.getRefreshToken());
        sharedPreferences.edit().putString(PREF_ACCESS_TOKEN, accessToken).apply();
        sharedPreferences.edit().putString(PREF_REFRESH_TOKEN, userModel.getRefreshToken()).apply();
        sharedPreferences.edit().putString(PREF_ROLE, userModel.getRole()).apply();
        sharedPreferences.edit().putString(PREF_NAME, name).apply();
        sharedPreferences.edit().putString(PREF_USERNAME, userModel.getUsername()).apply();
        navigator.navigateToMain(context);
        ((AuthActivity) context).finish();
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
        Snackbar snackbar = WorkflowUtils.generateSnackBar((AuthActivity) context, type, msg);
        snackbar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
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
        String username = etUsername.getText() != null ? etUsername.getText().toString().toLowerCase() : null;
        String password = etPassword.getText() != null ? etPassword.getText().toString() : null;
        SignInModel param = new SignInModel(username, password);
        presenter.signIn(param);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(context);

            if ("This field is required".equals(message)) {
                String fieldName =
                        (((TextInputLayout) view).getHint() == null) ? strThisField : ((TextInputLayout) view).getHint().toString();
                message = String.format(strFieldRequired, fieldName);
            }

            if (view instanceof TextInputLayout) {
                ((TextInputLayout) view).setError(message);
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

    @OnClick(R.id.btn_sign_in_register)
    void register() {
        ((AuthActivity) context).openRegisterPage();
    }

    @OnClick(R.id.btn_sign_in)
    void signIn() {
        validator.validate();
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

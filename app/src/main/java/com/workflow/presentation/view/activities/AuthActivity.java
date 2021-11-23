package com.workflow.presentation.view.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.workflow.R;
import com.workflow.WorkflowApplication;
import com.workflow.presentation.di.components.AuthComponent;
import com.workflow.presentation.di.components.DaggerAuthComponent;
import com.workflow.presentation.view.fragments.RegisterFragment;
import com.workflow.presentation.view.fragments.SignInFragment;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import timber.log.Timber;

public class AuthActivity extends BaseActivity {

    @BindString(R.string.sign_in_button) String strSignIn;
    @BindString(R.string.register_button) String strRegister;

    @Inject SharedPreferences sharedPreferences;

    private static final String TAG_SIGN_IN = "TAG_SIGN_IN";
    private static final String TAG_REGISTER = "TAG_REGISTER";

    private AuthComponent component;

    @Override
    int setView() {
        return R.layout.activity_auth;
    }

    @Override
    void initDagger() {

        if (component == null) {
            component = DaggerAuthComponent.builder()
                    .applicationComponent(((WorkflowApplication) getApplication()).getApplicationComponent())
                    .build();
        }
        component.inject(this);
    }

    @Override
    void afterViewCreated(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        navigateTo(new SignInFragment(), TAG_SIGN_IN, false);
        getSupportActionBar().setTitle(strSignIn);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment currentFragment = fm.findFragmentById(R.id.container_auth_items);

        if (currentFragment.getTag().equals(TAG_REGISTER)) {
            Timber.d("get_back_sign_in_called");
            getBackToSignInPage();
        } else {
            Timber.d("super_called");
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getBackToSignInPage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public AuthComponent getAuthComponent() {
        return component;
    }

    public void openRegisterPage() {
        navigateTo(new RegisterFragment(), TAG_REGISTER, true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.outline_arrow_back_ios_white_24);
        getSupportActionBar().setTitle(strRegister);
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void getBackToSignInPage() {
        getSupportFragmentManager().popBackStackImmediate(TAG_REGISTER, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportActionBar().setTitle(strSignIn);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    private void navigateTo(Fragment fragment, String TAG, boolean addToBackStack) {
        if (addToBackStack) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_auth_items, fragment, TAG)
                    .addToBackStack(TAG)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_auth_items, fragment, TAG)
                    .commit();
        }
    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, AuthActivity.class);
    }
}

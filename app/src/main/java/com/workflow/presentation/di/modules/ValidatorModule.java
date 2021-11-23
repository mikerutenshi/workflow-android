package com.workflow.presentation.di.modules;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.Validator;
import com.workflow.presentation.view.adapters.TextInputLayoutAdapter;
import com.workflow.presentation.view.adapters.TextInputLayoutAdapterInt;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Michael on 29/06/19.
 */

@Module
public class ValidatorModule {
    private AppCompatActivity activity = null;
    private Fragment fragment = null;
    private final Validator.ValidationListener listener;

    public ValidatorModule(AppCompatActivity activity, Validator.ValidationListener listener) {
        this.activity = activity;
        this.listener = listener;
    }

    public ValidatorModule(Fragment fragment, Validator.ValidationListener listener) {
        this.fragment = fragment;
        this.listener = listener;
    }

    @Provides
    Validator validator() {

        Validator validator;

        if (activity == null) {
            validator = new Validator(fragment);
        } else {
            validator = new Validator(activity);
        }

        validator.setValidationListener(listener);
        validator.registerAdapter(TextInputLayout.class, new TextInputLayoutAdapter());
        validator.setViewValidatedAction(new Validator.ViewValidatedAction() {
            @Override
            public void onAllRulesPassed(View view) {
                if (view instanceof TextInputLayout) {
                    ((TextInputLayout) view).setError("");
                    ((TextInputLayout) view).setErrorEnabled(false);
                }
            }
        });

        return validator;
    }
}

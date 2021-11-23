package com.workflow.presentation.di.modules;

import android.content.Context;

import com.workflow.presentation.view.adapters.SpinnerAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class SpinnerDialogModule {
    @Provides
    SpinnerAdapter spinnerAdapter(Context context) {
        return new SpinnerAdapter(context);
    }
}

package com.workflow.presentation.di.components;

import com.workflow.presentation.di.modules.AdapterModule;
import com.workflow.presentation.di.modules.SpinnerDialogModule;
import com.workflow.presentation.view.fragments.SpinnerDialogFragment;

import dagger.Component;

@Component(modules = { SpinnerDialogModule.class, AdapterModule.class })
public interface SpinnerDialogComponent {
    void inject(SpinnerDialogFragment fragment);
}

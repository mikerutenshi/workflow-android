package com.workflow.presentation.di.components;

import com.workflow.presentation.di.interfaces.PerFragment;
import com.workflow.presentation.di.modules.AuthModule;
import com.workflow.presentation.di.modules.SignInModule;
import com.workflow.presentation.di.modules.ValidatorModule;
import com.workflow.presentation.view.fragments.SignInFragment;

import dagger.Component;

@PerFragment
@Component(dependencies = AuthComponent.class, modules = { SignInModule.class, AuthModule.class, ValidatorModule.class})
public interface SignInComponent {
    void inject(SignInFragment fragment);
}

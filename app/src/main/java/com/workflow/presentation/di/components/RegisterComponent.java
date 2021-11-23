package com.workflow.presentation.di.components;

import com.workflow.presentation.di.interfaces.PerFragment;
import com.workflow.presentation.di.modules.AuthModule;
import com.workflow.presentation.di.modules.RegisterModule;
import com.workflow.presentation.di.modules.ValidatorModule;
import com.workflow.presentation.view.fragments.RegisterFragment;

import dagger.Component;

@PerFragment
@Component(dependencies = AuthComponent.class, modules = { AuthModule.class, ValidatorModule.class, RegisterModule.class })
public interface RegisterComponent {
    void inject(RegisterFragment registerFragment);
}

package com.workflow.presentation.di.components;

import com.workflow.presentation.di.interfaces.PerActivity;
import com.workflow.presentation.di.modules.ProductMapperModule;
import com.workflow.presentation.di.modules.ProductModule;
import com.workflow.presentation.di.modules.ValidatorModule;
import com.workflow.presentation.di.modules.WorkCreateModule;
import com.workflow.presentation.di.modules.WorkModule;
import com.workflow.presentation.view.activities.WorkCreateActivity;

import dagger.Component;

/**
 * Created by Michael on 30/06/19.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = { WorkCreateModule.class, ProductModule.class, ValidatorModule.class, ProductMapperModule.class })
public interface WorkCreateComponent {
    void inject(WorkCreateActivity activity);
}

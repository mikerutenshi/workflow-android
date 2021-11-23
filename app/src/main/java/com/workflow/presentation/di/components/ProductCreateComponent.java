package com.workflow.presentation.di.components;

import com.workflow.presentation.di.interfaces.PerActivity;
import com.workflow.presentation.di.interfaces.ProductScope;
import com.workflow.presentation.di.modules.ProductCreateModule;
import com.workflow.presentation.di.modules.ProductListModule;
import com.workflow.presentation.view.activities.ProductCreateActivity;
import com.workflow.presentation.view.fragments.ProductListFragment;

import dagger.Component;

/**
 * Created by Michael on 27/06/19.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ProductCreateModule.class)
public interface ProductCreateComponent {
    void inject(ProductCreateActivity activity);
}

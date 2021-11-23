package com.workflow.presentation.di.components;

import com.workflow.presentation.di.interfaces.PerFragment;
import com.workflow.presentation.di.modules.ProductListModule;
import com.workflow.presentation.di.modules.ProductModule;
import com.workflow.presentation.view.fragments.ProductListFragment;

import dagger.Component;

/**
 * Created by Michael on 28/06/19.
 */

@PerFragment
@Component(dependencies = MainComponent.class, modules = { ProductModule.class, ProductListModule.class })
public interface ProductListComponent {
    void inject(ProductListFragment fragment);
}

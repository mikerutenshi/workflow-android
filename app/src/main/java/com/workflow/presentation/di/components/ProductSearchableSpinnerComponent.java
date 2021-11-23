package com.workflow.presentation.di.components;

import com.workflow.presentation.di.modules.ProductListModule;
import com.workflow.presentation.di.modules.ProductModule;
import com.workflow.presentation.view.fragments.SearchableSpinnerDialogFragment;

import dagger.Component;

/**
 * Created by Michael on 04/07/19.
 */

@Component(modules = { ProductModule.class, ProductListModule.class })
public interface ProductSearchableSpinnerComponent {
    // void inject(SearchableSpinnerDialogFragment fragment);
}

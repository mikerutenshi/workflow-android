package com.workflow.presentation.di.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Michael on 28/06/19.
 */

@Module
public class ContextModule {

    private final Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    Context context() {
        return this.context;
    }
}

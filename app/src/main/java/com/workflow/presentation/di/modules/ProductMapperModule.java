package com.workflow.presentation.di.modules;

import android.content.Context;

import com.workflow.presentation.mapper.ListProductMapper;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Michael on 05/07/19.
 */

@Module(includes = { ContextModule.class })
public class ProductMapperModule {

    @Provides
    ListProductMapper mapper(Context context) {
        return new ListProductMapper(context);
    }
}

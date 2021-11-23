package com.workflow.presentation.di.modules;

import com.workflow.presentation.mapper.ListProductMapper;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Michael on 05/07/19.
 */

@Module
public class ProductMapperModule {

    @Provides
    ListProductMapper mapper() {
        return new ListProductMapper();
    }
}

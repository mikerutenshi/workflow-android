package com.workflow.presentation.di.modules;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.workflow.R;
import com.workflow.presentation.view.itemdecoration.GridColumnDecoration;
import com.workflow.presentation.view.itemdecoration.VerticalSingleColumDecorationLargeBot;
import com.workflow.presentation.view.itemdecoration.VerticalSingleColumnDecoration;
import com.workflow.presentation.view.itemdecoration.VerticalSingleColumnDecorationNoTop;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Michael on 28/06/19.
 */

@Module(includes = ContextModule.class)
public class AdapterModule {

    @Provides
    LinearLayoutManager linearLayoutManager(Context context) {
        return new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
    }

    @Provides
    GridLayoutManager gridLayoutManager(Context context) {
        return new GridLayoutManager(context,2, RecyclerView.VERTICAL, false);
    }

    @Provides
    VerticalSingleColumnDecoration verticalSingleColumnDecoration(Context context) {
        return new VerticalSingleColumnDecoration(context, R.dimen.activity_horizontal_margin);
    }

    @Provides
    VerticalSingleColumDecorationLargeBot verticalSingleColumDecorationLargeBot(Context context) {
        return new VerticalSingleColumDecorationLargeBot(context, R.dimen.activity_horizontal_margin, R.dimen.margin_72);
    }

    @Provides
    VerticalSingleColumnDecorationNoTop verticalSingleColumnDecorationNoTop(Context context) {
        return new VerticalSingleColumnDecorationNoTop(context, R.dimen.activity_horizontal_margin);
    }

    @Provides
    GridColumnDecoration gridColumnDecoration(Context context) {
        return new GridColumnDecoration(context, R.dimen.activity_horizontal_margin, 2);
    }
}

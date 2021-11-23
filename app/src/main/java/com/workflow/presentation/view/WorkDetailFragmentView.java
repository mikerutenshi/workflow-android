package com.workflow.presentation.view;

import com.workflow.data.net.responses.GenericListResponseModel;
import com.workflow.presentation.view.adapters.BaseViewHolder;
import com.workflow.presentation.view.adapters.OnRecyclerObjectClickListener;
import com.workflow.presentation.view.adapters.PaginationAdapter;

import java.util.HashMap;

public interface WorkDetailFragmentView<T> extends BaseView {
    void renderItems(GenericListResponseModel<T> items);
    void showDataEmpty();
    HashMap<String, String> getOptions();
    PaginationAdapter<T, OnRecyclerObjectClickListener<T>
            , BaseViewHolder<T, OnRecyclerObjectClickListener<T>>> getAdapter();
}

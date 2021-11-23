package com.workflow.presentation.view.adapters;

public interface OnRecyclerObjectClickListener<T> extends BaseRecyclerListener {
    void onItemClicked(int position, T item);
    void onItemLongClick(int position, T item);
    void onItemSelected(int position, T item);
}

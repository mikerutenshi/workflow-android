package com.workflow.presentation.view.adapters;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Checkable;

import androidx.annotation.NonNull;

import com.workflow.data.model.MultiChoiceable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 18/07/19.
 */

public abstract class PaginationAdapter<T, L extends BaseRecyclerListener, VH extends BaseViewHolder<T, L>>
        extends GenericRecyclerAdapter<T, L , VH> {

    static final int ITEM = 0;
    static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    private int countSelectedItems = 0;
    T placeholderItem;
    private List<T> selectedItems;
    private int itemCheckedPosition = -1;

    private int currentPage = 1;
    private int limit = 10;
    private boolean isLoading = false;
    private boolean isLastPage = false;

    public PaginationAdapter(Context context) {
        super(context);
        selectedItems = new ArrayList<>();
    }

    public void clearSelectedItems() {
        selectedItems.clear();
    }

    public void toggleItemSelected(int position, T payload) {
        MultiChoiceable item = (MultiChoiceable) getItem(position);
        item.toggleIsSelected();

        if (item.isSelected()) {
            countSelectedItems ++;
            selectedItems.add(payload);
        } else {
            countSelectedItems --;
            selectedItems.remove(payload);
        }

        notifyItemChanged(position);
    }

    @NonNull
    @Override
    public abstract VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    public abstract void initPlaceHolderItem();

    @Override
    public int getItemViewType(int position) {
        int viewType;

        if (position == getItemCount() - 1 && isLoadingAdded) {
            viewType = LOADING;
        } else {
            viewType = ITEM;
        }

        return viewType;
    }

    public void addLoadingFooter() {
        initPlaceHolderItem();
        add(placeholderItem);
        isLoadingAdded = true;
    }

    public void removeLoadingFooter() {
        if (isLoadingAdded) {
            isLoadingAdded = false;

            if (getItems().size() > 0) {
                int position = getItems().size() - 1;
                T item = getItem(position);

                if (item != null) {
                    remove(item);
                    notifyItemRemoved(position + 1);
                }
            }
        }
    }

    public List<T> getSelectedItems() {
        return selectedItems;
    }

    public int getCountSelectedItems() {

        return countSelectedItems;
    }

    public void setCountSelectedItems(int countSelectedItems) {
        this.countSelectedItems = countSelectedItems;
    }

    public void setItemCheckedPosition(int itemCheckedPosition) {
        this.itemCheckedPosition = itemCheckedPosition;
    }

    public int getItemCheckedPosition() {
        return itemCheckedPosition;
    }

    public void toggleItemChecked(int position, T item) {
        clearSelectedItems();
        Checkable currentItem = (Checkable) getItem(position);
        if (currentItem.isChecked()) {
            currentItem.setChecked(false);
            notifyDataSetChanged();
            clearSelectedItems();
            setItemCheckedPosition(-1);
        } else {
            if (getItemCheckedPosition() != -1) {
                Checkable previousItem = (Checkable) getItem(getItemCheckedPosition());
                previousItem.setChecked(false);
                currentItem.setChecked(true);
                notifyDataSetChanged();
                setItemCheckedPosition(position);
                addSelectedItem(item);
            } else {
                currentItem.setChecked(true);
                notifyDataSetChanged();
                setItemCheckedPosition(position);
                addSelectedItem(item);
            }
        }
    }


    public void addSelectedItem(T item) {
        this.selectedItems.add(item);
    }

    void incrementCountSelectedItems() {
        countSelectedItems ++;
    }

    void decrementCountSelectedItems() {
        countSelectedItems --;
    }

    void setLoadingAdded(boolean loadingAdded) {
        isLoadingAdded = loadingAdded;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }
}

package com.workflow.presentation.view.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.workflow.data.model.ListModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import timber.log.Timber;

public class ActvArrayAdapter extends ArrayAdapter<ListModel> {

    private String chosenItem;
    private List<ListModel> items = new ArrayList<>();

    public ActvArrayAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public String getChosenItem() {
        return chosenItem;
    }

    public void setChosenItem(String chosenItem) {
        this.chosenItem = chosenItem;
    }

    public String getName(String id) {
        String name = null;
        for (ListModel item : items) {
            if (item.getId().equals(id)) {
                name = item.getName();
            }
        }
        return name;
    }

    public void addItems(List<ListModel> items) {
        this.items.addAll(items);
        this.addAll(items);
    }
}

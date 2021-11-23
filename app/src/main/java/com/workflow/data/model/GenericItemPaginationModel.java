package com.workflow.data.model;

/**
 * Created by Michael on 19/07/19.
 */

public class GenericItemPaginationModel<T> {
    private T items;
    private MetaModel paginationModel;

    public GenericItemPaginationModel(T items, MetaModel paginationModel) {
        this.items = items;
        this.paginationModel = paginationModel;
    }

    public T getItems() {
        return items;
    }

    public MetaModel getPaginationModel() {
        return paginationModel;
    }
}

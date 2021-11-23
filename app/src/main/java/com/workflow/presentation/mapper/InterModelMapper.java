package com.workflow.presentation.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Michael on 05/07/19.
 */

public abstract class InterModelMapper<Output, Input> {
    public abstract Output transform(Input input);
    public abstract Output transform(
            Input input, Integer attachId, String attachPos, String attachCreatedAt);

    public List<Output> transform(Collection<Input> collection) {
        List<Output> list = new ArrayList<>();
        Output output;
        for (Input input : collection) {
            output = transform(input);
            if (output != null) {
                list.add(output);
            }
        }

        return list;
    }

    public List<Output> transform(
            Collection<Input> collection, Integer attachId, String attachPos, String attachCreatedAt) {
        List<Output> list = new ArrayList<>();
        Output output;
        for (Input input : collection) {
            output = transform(input, attachId, attachPos, attachCreatedAt);
            if (output != null) {
                list.add(output);
            }
        }

        return list;
    }
}

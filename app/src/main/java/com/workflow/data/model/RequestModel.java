package com.workflow.data.model;

import java.util.HashMap;
import java.util.Map;

public class RequestModel {
    Integer path;
    HashMap<String, String> queries;

    public RequestModel(Integer path, HashMap<String, String> queries) {
        this.path = path;
        this.queries = queries;
    }

    public Integer getPath() {
        return path;
    }

    public HashMap<String, String> getQueries() {
        return queries;
    }
}

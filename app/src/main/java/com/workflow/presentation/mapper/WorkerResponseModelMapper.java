package com.workflow.presentation.mapper;

import com.workflow.data.model.ResponseWorkerModel;
import com.workflow.data.model.WorkerModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import timber.log.Timber;

public class WorkerResponseModelMapper extends InterModelMapper<WorkerModel, ResponseWorkerModel> {
    @Override
    public WorkerModel transform(ResponseWorkerModel responseWorkerModel) {
        String removeOpenCurlyBrace = responseWorkerModel.getPosition().replace("{", "");
        String removeCloseCurlyBrace = removeOpenCurlyBrace.replace("}", "");
        List<String> items = Arrays.asList(removeCloseCurlyBrace.split("\\s*,\\s*"));
        return new WorkerModel(responseWorkerModel.getId(), responseWorkerModel.getName(), items);
    }

    @Override
    public WorkerModel transform(ResponseWorkerModel responseWorkerModel, Integer attachId, String attachPos, String attachCreatedAt) {
        return null;
    }
}

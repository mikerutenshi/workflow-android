package com.workflow.presentation.presenter;

import com.workflow.data.model.ErrorModel;
import com.workflow.presentation.view.BaseView;
import com.workflow.utils.WorkflowUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.observers.DisposableSingleObserver;
import timber.log.Timber;

/**
 * Created by Michael on 18/06/19.
 */

public abstract class GenericObserver<T> extends DisposableSingleObserver<T> {

    private final BaseView view;

    public GenericObserver(BaseView view) {
        this.view = view;
    }

    @Override
    public void onSuccess(T t) {
        view.showContent();
    }

    @Override
    public void onError(Throwable e) {
        String errorMessage = null;

        if (e instanceof SocketTimeoutException) {
            errorMessage = "Koneksi kehabisan waktu, coba kembali";
        } else if (e.getLocalizedMessage().toLowerCase().contains("refresh token is expired")) {
            Timber.d("start auth activity");
            return;
        } else {
            errorMessage = e.getLocalizedMessage();
        }

        view.showContent();
        view.showSnackBar(WorkflowUtils.SNACK_BAR_ERROR, errorMessage);

//        // when error is httpException
//        if (e instanceof HttpException) {
//
//            // when error contains json
//            try {
//                ResponseBody jsonResponse = ((HttpException) e).response().errorBody();
//
//                Converter<ResponseBody, ErrorModel> converter = ServiceGenerator.retrofit.responseBodyConverter(ErrorModel.class, new Annotation[0]);
//
//                if (jsonResponse != null) {
//                    errorModel = converter.convert(jsonResponse);
//                }
//
//                if (errorModel != null) {
//                    view.showSnackBar(WorkflowUtils.SNACK_BAR_ERROR, errorModel.getMessage());
//                }
//            } catch (IOException ex) {
//                ex.printStackTrace();
//
//                // when error is common
//                if (((HttpException) e).response().code() == 500) {
//                    view.showSnackBar(WorkflowUtils.SNACK_BAR_ERROR, "Terjadi error pada server");
//                    return;
//                } else if (((HttpException) e).response().code() == 404) {
//                    view.showSnackBar(WorkflowUtils.SNACK_BAR_ERROR, "Endpoint server tidak ditemukan");
//                    return;
//                }
//            }
//        } else if (e instanceof SocketTimeoutException) {
//            view.showSnackBar(WorkflowUtils.SNACK_BAR_ERROR, "Waktu koneksi habis");
//        } else if (e instanceof IOException) {
//            view.showSnackBar(WorkflowUtils.SNACK_BAR_ERROR, "Waktu habis");
//        } else {
//            view.showSnackBar(WorkflowUtils.SNACK_BAR_ERROR, e.getLocalizedMessage());
//        }
    }
}

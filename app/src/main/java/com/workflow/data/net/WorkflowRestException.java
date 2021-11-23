package com.workflow.data.net;

import java.io.IOException;

/**
 * Created by Michael on 14/06/19.
 */

public class WorkflowRestException extends IOException {
    public static final int ERROR_UNKNOWN = 6666;
    public static final int CANCELED = 6969;
    public static final int INVALID_RESPONSE = 666;
    public static final int UPGRADE_REQUIRED = 426;
    public static final int INVALID_JWT = 16;
    public static final int BAD_GATEWAY = 502;

    private int mErrorCode;
    private String mMessage;

    public WorkflowRestException(int errorCode, String message) {
        super(message);
        mMessage = message;
        mErrorCode = errorCode;
    }

    public int getErrorCode() {
        return mErrorCode;
    }

    public void setErrorCode(int errorCode) {
        mErrorCode = errorCode;
    }

    @Override public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }
}

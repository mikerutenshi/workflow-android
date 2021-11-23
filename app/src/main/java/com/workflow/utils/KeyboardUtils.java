package com.workflow.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class KeyboardUtils {

    public static void requestFocusShowKeyboard(Context context, EditText editText) {
        editText.post(new Runnable() {
            @Override
            public void run() {
                editText.requestFocus();
                InputMethodManager imgr = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imgr.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        });
    }
}

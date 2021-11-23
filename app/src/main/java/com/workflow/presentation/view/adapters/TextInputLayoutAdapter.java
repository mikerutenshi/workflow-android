package com.workflow.presentation.view.adapters;


import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.adapter.ViewDataAdapter;
import com.mobsandgeeks.saripaar.exception.ConversionException;

/**
 * Created by Michael on 29/06/19.
 */

public class TextInputLayoutAdapter implements ViewDataAdapter<TextInputLayout, String> {
    @Override
    public String getData(TextInputLayout view) throws ConversionException {
        return view.getEditText().getText().toString();
    }
}

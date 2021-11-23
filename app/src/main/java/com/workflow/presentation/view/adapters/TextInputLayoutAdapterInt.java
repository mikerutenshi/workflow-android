package com.workflow.presentation.view.adapters;

import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.adapter.ViewDataAdapter;
import com.mobsandgeeks.saripaar.exception.ConversionException;

public class TextInputLayoutAdapterInt implements ViewDataAdapter<TextInputLayout, Integer> {
    @Override
    public Integer getData(TextInputLayout view) throws ConversionException {
        return Integer.valueOf(view.getEditText().getText().toString());
    }
}

package com.workflow.presentation.view.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.workflow.R;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Michael on 22/07/19.
 */

public class ConfirmDeleteDialogFragment extends DialogFragment {

    public static final String EXTRA_ITEM_NAME = "EXTRA_ITEM_NAME";
    public static final String EXTRA_COUNT_DELETE_ITEMS = "EXTRA_COUNT_DELETE_ITEMS";

    @BindView(R.id.tv_confirm_delete_item) TextView tvQuestion;
    @BindView(R.id.btn_confirm_delete_cancel) Button btnCancel;
    @BindView(R.id.btn_confirm_delete_ok) Button btnOk;
    @BindString(R.string.confirm_delete_question) String strConfirmDelete;

    private AlertDialog dialog;
    private int mCountDeleteItems;
    private ConfirmDeleteListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        readBundle(getArguments());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog_confirm_delete,null);
        builder.setView(view);
        ButterKnife.bind(this, view);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        tvQuestion.setText(String.format(strConfirmDelete, mCountDeleteItems));

        initListener();

        return dialog;
    }

    public void setListener(ConfirmDeleteListener listener) {
        this.listener = listener;
    }

    private void initListener() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCancelClick();
                dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onConfirmClick();
                dismiss();
            }
        });
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            mCountDeleteItems = bundle.getInt(EXTRA_COUNT_DELETE_ITEMS);
        }
    }

    public interface ConfirmDeleteListener {
        void onConfirmClick();
        void onCancelClick();
    }

    public static ConfirmDeleteDialogFragment newInstance(int countItemsDelete) {
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_COUNT_DELETE_ITEMS, countItemsDelete);
        ConfirmDeleteDialogFragment fragment = new ConfirmDeleteDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}

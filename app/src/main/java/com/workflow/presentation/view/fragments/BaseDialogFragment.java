package com.workflow.presentation.view.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.view.View;

import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by Michael on 03/07/19.
 */

public abstract class BaseDialogFragment extends DialogFragment {
    Context context;
    private AlertDialog dialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(setView(),null);
        builder.setView(view);
        ButterKnife.bind(this, view);

        dialog = builder.create();

        initDagger();
        afterViewCreated(savedInstanceState);

        return dialog;
    }

    @Override
    public void onDestroyView() {
        Timber.d("ON_DESTROY_VIEW_CALLED");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Timber.d("ON_DESTROY_CALLED");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Timber.d("ON_DETACH_CALLED");
        super.onDestroy();
        context = null;
        super.onDetach();
    }

    //    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setStyle(STYLE_NORMAL, android.R.style.Theme_Holo_Light_Dialog);
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        final View view = inflater.inflate(setView(), container, false);
//        ButterKnife.bind(this, view);
//        initDagger();
//
//        afterViewCreated(savedInstanceState);
//
////        initPresenter();
//
//        return view;
//    }

    abstract int setView();
    abstract void initDagger();
    abstract void afterViewCreated(Bundle savedInstanceState);
}

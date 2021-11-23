package com.workflow.presentation.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.workflow.presentation.view.activities.MainActivity;
import com.workflow.utils.ConnectionUtil;
import com.workflow.utils.WorkflowUtils;

import butterknife.ButterKnife;

/**
 * Created by Michael on 10/06/19.
 */

abstract class BaseFragment extends Fragment {
    Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(setView(), container, false);
        ButterKnife.bind(this, view);
        initDagger();

        afterViewCreated(savedInstanceState);

//        initPresenter();

        return view;
    }

    @Override
    public void onDetach() {
        context = null;
        super.onDetach();
    }

    abstract int setView();
    abstract void initDagger();
    abstract void afterViewCreated(Bundle savedInstanceState);
//    abstract void initPresenter();

    void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}

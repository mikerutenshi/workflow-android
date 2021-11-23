package com.workflow.presentation.view.activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by Michael on 10/06/19.
 */

abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(setView());
        ButterKnife.bind(this);
        initDagger();
        afterViewCreated(savedInstanceState);

//        initPresenter();
    }

    abstract int setView();
    abstract void initDagger();
    abstract void afterViewCreated(Bundle savedInstanceState);
//    abstract void initPresenter();
}

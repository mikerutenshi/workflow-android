package com.workflow.presentation.di.modules;

import android.content.Context;

import com.workflow.domain.interactor.salary_report.GetSalaryReportDetailList;
import com.workflow.presentation.presenter.SalaryReportDetailPresenter;
import com.workflow.presentation.presenter.SalaryReportDetailPresenterImpl;
import com.workflow.presentation.view.SalaryReportDetailView;
import com.workflow.presentation.view.adapters.SalaryReportDetailListAdapter;

import dagger.Module;
import dagger.Provides;

@Module(includes = { AdapterModule.class })
public class SalaryReportDetailModule {
    private final SalaryReportDetailView view;

    public SalaryReportDetailModule(SalaryReportDetailView view) {
        this.view = view;
    }

    @Provides SalaryReportDetailListAdapter adapter(Context context) {
        return new SalaryReportDetailListAdapter(context);
    }

    @Provides SalaryReportDetailPresenter presenter(GetSalaryReportDetailList getSalaryReportDetailList) {
        return new SalaryReportDetailPresenterImpl(getSalaryReportDetailList, view);
    }
}

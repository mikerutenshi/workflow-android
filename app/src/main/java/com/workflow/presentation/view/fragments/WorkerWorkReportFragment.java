package com.workflow.presentation.view.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.workflow.R;
import com.workflow.data.model.DateFilterModel;
import com.workflow.data.model.ReportDetailDateFilterModel;
import com.workflow.data.model.ReportListModel;
import com.workflow.data.model.WorkerWorkReportModel;
import com.workflow.presentation.di.components.DaggerWorkerWorkReportComponent;
import com.workflow.presentation.di.components.WorkerWorkReportComponent;
import com.workflow.presentation.di.modules.ContextModule;
import com.workflow.presentation.di.modules.WorkerWorkReportModule;
import com.workflow.presentation.navigation.Navigator;
import com.workflow.presentation.presenter.WorkerWorkReportPresenter;
import com.workflow.presentation.view.WorkerWorkReportView;
import com.workflow.presentation.view.activities.MainActivity;
import com.workflow.presentation.view.adapters.OnRecyclerObjectClickListener;
import com.workflow.presentation.view.adapters.WorkerWorkReportAdapter;
import com.workflow.presentation.view.itemdecoration.VerticalSingleColumnDecoration;
import com.workflow.utils.ConnectionUtil;
import com.workflow.utils.WorkflowUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Michael on 09/07/19.
 */

public class WorkerWorkReportFragment extends BaseFragment implements WorkerWorkReportView {

    @BindView(R.id.tv_worker_work_report_total_cost) TextView tvTotalCost;
    @BindView(R.id.tv_worker_work_report_total_qty) TextView tvTotalQty;
    @BindView(R.id.rv_worker_work_report_list) RecyclerView recyclerView;
    @BindView(R.id.layout_data_empty) ConstraintLayout layoutDataEmpty;
    @BindView(R.id.layout_alpha_progress_bar) ConstraintLayout progressBar;
    @BindView(R.id.et_report_start_date) TextInputEditText etStartDate;
    @BindView(R.id.et_report_end_date) TextInputEditText etEndDate;
    @BindView(R.id.srl_worker_work_report) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.clpb_progress_bar) ContentLoadingProgressBar contentLoadingProgressBar;

    @BindString(R.string.report_total_cost) String strTotalCost;
    @BindString(R.string.report_total_qty) String strTotalQty;
    @BindString(R.string.no_connection) String strNoConnection;

    @Inject WorkerWorkReportPresenter presenter;
    @Inject WorkerWorkReportAdapter adapter;
    @Inject VerticalSingleColumnDecoration decoration;
    @Inject LinearLayoutManager linearLayoutManager;
    @Inject Navigator navigator;

    private WorkerWorkReportComponent component;
    private Calendar startCal = Calendar.getInstance();
    private Calendar endCal = Calendar.getInstance();
    private Locale indonesian = new Locale("in", "ID");
    private SimpleDateFormat indoDateFormat = new SimpleDateFormat("dd MMMM yyyy", indonesian);
    private SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    public DateFilterModel dateFilterModel;

    @Override
    int setView() {
        return R.layout.fragment_worker_work_report;
    }

    @Override
    void initDagger() {
        if (component == null) {
            component = DaggerWorkerWorkReportComponent.builder()
                    .mainComponent(((MainActivity) context).getMainComponent())
                    .workerWorkReportModule(new WorkerWorkReportModule(this))
                    .contextModule(new ContextModule(context))
                    .build();
        }
        component.inject(this);
    }

    @Override
    void afterViewCreated(Bundle savedInstanceState) {
        startCal.add(Calendar.DAY_OF_MONTH, -7);
        String startDate = indoDateFormat.format(startCal.getTime());
        String endDate = indoDateFormat.format(endCal.getTime());
        etStartDate.setText(startDate);
        etEndDate.setText(endDate);

        String startDateApi = apiDateFormat.format(startCal.getTime());
        String endDateApi = apiDateFormat.format(endCal.getTime());
        dateFilterModel = new DateFilterModel(startDateApi, endDateApi);

        initAdapter();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            onResume();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getWorkerWorkReport(dateFilterModel);
    }

    @Override
    public void onPause() {
        presenter.pause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        layoutDataEmpty.setVisibility(View.GONE);

        if (swipeRefreshLayout.isRefreshing()) contentLoadingProgressBar.hide();
    }

    @Override
    public void showContent() {
        resetRefreshingStatus();
        progressBar.setVisibility(View.GONE);
        layoutDataEmpty.setVisibility(View.GONE);
    }

    @Override
    public void showDataEmptyView() {
        resetRefreshingStatus();
        adapter.clear();
        progressBar.setVisibility(View.GONE);
        layoutDataEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void resetRefreshingStatus() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
            contentLoadingProgressBar.show();
        }
    }

    @Override
    public void showSnackBar(int type, String msg) {
        View view = (((MainActivity) context).getCoordinatorLayout());
        Snackbar snackbar = WorkflowUtils.generateSnackBar(view, type, msg);
        snackbar.show();
    }

    @Override
    public boolean isConnected() {
        if (!ConnectionUtil.isOnline(context)) {
            if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
            showSnackBar(WorkflowUtils.SNACK_BAR_WARNING, strNoConnection);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void renderItems(WorkerWorkReportModel item) {
        List<ReportListModel> reportListModels = item.getReportListModels();

        tvTotalCost.setText(String.format(strTotalCost
                , WorkflowUtils.convertRupiah(item.getSumModel().getTotalCost())));
        tvTotalQty.setText(String.format(strTotalQty, item.getSumModel().getTotalQty()));

        adapter.setItems(reportListModels);
    }

    @OnClick(R.id.et_report_start_date)
    void showStartDatePicker() {
        int mYear = startCal.get(Calendar.YEAR);
        int mMonth = startCal.get(Calendar.MONTH);
        int mDay = startCal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener startDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                startCal.set(year, month, dayOfMonth);
                String startDate = indoDateFormat.format(startCal.getTime());
                etStartDate.setText(startDate);
                dateFilterModel.setStartDate(apiDateFormat.format(startCal.getTime()));
                presenter.getWorkerWorkReport(dateFilterModel);
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context, startDateListener, mYear, mMonth,mDay);
        datePickerDialog.show();
    }

    @OnClick(R.id.et_report_end_date)
    void showEndDatePicker() {
        int mYear = endCal.get(Calendar.YEAR);
        int mMonth = endCal.get(Calendar.MONTH);
        int mDay = endCal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener endDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                endCal.set(year, month, dayOfMonth);
                String endDate = indoDateFormat.format(endCal.getTime());
                etEndDate.setText(endDate);
                dateFilterModel.setEndDate(apiDateFormat.format(endCal.getTime()));
                presenter.getWorkerWorkReport(dateFilterModel);
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context, endDateListener, mYear, mMonth,mDay);
        datePickerDialog.show();
    }
    private void initAdapter() {
        adapter.setListener(new OnRecyclerObjectClickListener<ReportListModel>() {
            @Override
            public void onItemClicked(int position, ReportListModel item) {
                ReportDetailDateFilterModel dateFilterModel = new ReportDetailDateFilterModel(
                        WorkerWorkReportFragment.this.dateFilterModel.getStartDate()
                        , WorkerWorkReportFragment.this.dateFilterModel.getEndDate()
                        , item.getWorkerId()
                        , item.getPosition()
                );
                navigator.navigateToReportWorkerDetail(context, item, dateFilterModel);
            }

            @Override
            public void onItemLongClick(int position, ReportListModel item) {

            }

            @Override
            public void onItemSelected(int position, ReportListModel item) {

            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(decoration);
    }
}

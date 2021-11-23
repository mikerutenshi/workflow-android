package com.workflow.presentation.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.workflow.R;
import com.workflow.WorkflowApplication;
import com.workflow.data.model.ReportDetailDateFilterModel;
import com.workflow.data.model.ReportListModel;
import com.workflow.data.model.WorkDetailModel;
import com.workflow.presentation.di.components.DaggerWorkDetailReportComponent;
import com.workflow.presentation.di.components.WorkDetailReportComponent;
import com.workflow.presentation.di.modules.ContextModule;
import com.workflow.presentation.di.modules.ReportWorkerDetailModule;
import com.workflow.presentation.presenter.ReportWorkerDetailPresenter;
import com.workflow.presentation.view.ReportWorkerDetailView;
import com.workflow.presentation.view.adapters.ReportWorkerDetailAdapter;
import com.workflow.presentation.view.itemdecoration.VerticalSingleColumnDecoration;
import com.workflow.presentation.view.itemdecoration.VerticalSingleColumnDecorationNoTop;
import com.workflow.utils.ConnectionUtil;
import com.workflow.utils.WorkflowUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;

import static com.workflow.presentation.navigation.Navigator.PARCELABLE_REPORT_DETAIL_DATE_FILTER;
import static com.workflow.presentation.navigation.Navigator.PARCELABLE_REPORT_LIST_MODEL;

/**
 * Created by Michael on 25/07/19.
 */

public class ReportWorkerDetailActivity extends BaseActivity implements ReportWorkerDetailView {

    @BindView(R.id.rv_report_worker_detail_work_list) RecyclerView recyclerView;
    @BindView(R.id.tv_report_worker_detail_name) TextView tvName;
    @BindView(R.id.tv_report_worker_detail_position) AppCompatTextView tvPosition;
    @BindView(R.id.tv_report_worker_detail_subtotal_cost) TextView tvTotalCost;
    @BindView(R.id.tv_report_worker_detail_subtotal_qty) TextView tvTotalQty;
    @BindView(R.id.layout_alpha_progress_bar) ConstraintLayout progressBar;
    @BindView(R.id.srl_report_worker_detail) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.clpb_progress_bar) ContentLoadingProgressBar contentLoadingProgressBar;

    @BindString(R.string.worker_list_name) String strName;
    @BindString(R.string.worker_list_position) String strPosition;
    @BindString(R.string.report_total_worked_qty) String strTotalQty;
    @BindString(R.string.report_detail_total_qty_unit) String strQtyUnit;
    @BindString(R.string.report_detail_total_cost) String strTotalCost;
    @BindString(R.string.no_connection) String strNoConnection;

    @Inject ReportWorkerDetailAdapter adapter;
    @Inject VerticalSingleColumnDecoration verticalSingleColumnDecoration;
    @Inject LinearLayoutManager linearLayoutManager;
    @Inject ReportWorkerDetailPresenter presenter;

    private WorkDetailReportComponent component;
    private ReportListModel reportListModel;
    private ReportDetailDateFilterModel dateFilterModel;

    @Override
    int setView() {
        return R.layout.activity_report_worker_detail;
    }

    @Override
    void initDagger() {
        if (component == null) {
            component = DaggerWorkDetailReportComponent.builder()
                    .applicationComponent(((WorkflowApplication) getApplication()).getApplicationComponent())
                    .reportWorkerDetailModule(new ReportWorkerDetailModule(this))
                    .contextModule(new ContextModule(this))
                    .build();
        }
        component.inject(this);
    }

    @Override
    void afterViewCreated(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.outline_arrow_back_ios_white_24);
        initRecyclerView();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            onResume();
        });

        dateFilterModel = getIntent().getExtras().getParcelable(PARCELABLE_REPORT_DETAIL_DATE_FILTER);
        reportListModel = getIntent().getExtras().getParcelable(PARCELABLE_REPORT_LIST_MODEL);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);

        if (swipeRefreshLayout.isRefreshing()) contentLoadingProgressBar.hide();
    }

    @Override
    public void showContent() {

        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
            contentLoadingProgressBar.show();
        }

        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showSnackBar(int type, String msg) {
        Snackbar snackbar = WorkflowUtils.generateSnackBar(this, type, msg);
        snackbar.show();
    }

    @Override
    public boolean isConnected() {
        if (!ConnectionUtil.isOnline(this)) {
            if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
            showSnackBar(WorkflowUtils.SNACK_BAR_WARNING, strNoConnection);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void renderWorkDetail(List<WorkDetailModel> workDetailModels) {
        adapter.setItems(workDetailModels);
    }

    @Override
    public void renderWorkerDetailHeader(ReportListModel model) {
        tvName.setText(model.getName());
        tvPosition.setText(WorkflowUtils.getRenderedPosition(this, model.getPosition()));
        tvTotalCost.setText(WorkflowUtils.convertRupiah(model.getTotalCost()));
        tvTotalQty.setText(String.format(strQtyUnit, model.getTotalQty()));
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (reportListModel != null) {
            renderWorkerDetailHeader(reportListModel);
        }
        presenter.getWorkDetailReport(dateFilterModel);
    }

    @Override
    protected void onPause() {
        presenter.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }

    private void initRecyclerView() {
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(verticalSingleColumnDecoration);
    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, ReportWorkerDetailActivity.class);
    }
}

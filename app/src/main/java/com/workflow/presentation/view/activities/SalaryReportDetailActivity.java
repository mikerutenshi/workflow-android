package com.workflow.presentation.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.textfield.TextInputLayout;
import com.workflow.R;
import com.workflow.WorkflowApplication;
import com.workflow.data.model.DateFilterModel;
import com.workflow.data.model.ListModel;
import com.workflow.data.model.SalaryReportDetailListModel;
import com.workflow.data.model.WorkerModel;
import com.workflow.data.net.responses.GenericResponseModel;
import com.workflow.presentation.di.components.DaggerSalaryReportDetailComponent;
import com.workflow.presentation.di.components.SalaryReportDetailComponent;
import com.workflow.presentation.di.modules.ContextModule;
import com.workflow.presentation.di.modules.SalaryReportDetailModule;
import com.workflow.presentation.presenter.SalaryReportDetailPresenter;
import com.workflow.presentation.view.SalaryReportDetailView;
import com.workflow.presentation.view.adapters.OnRecyclerObjectClickListener;
import com.workflow.presentation.view.adapters.SalaryReportDetailListAdapter;
import com.workflow.presentation.view.adapters.WorkflowRvOnScrollListener;
import com.workflow.presentation.view.itemdecoration.VerticalSingleColumnDecoration;
import com.workflow.utils.ConnectionUtil;
import com.workflow.utils.WorkflowUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;

import static com.workflow.presentation.navigation.Navigator.PARCELABLE_DATE_FILTER_MODEL;
import static com.workflow.presentation.navigation.Navigator.PARCELABLE_WORKER_MODEL;

public class SalaryReportDetailActivity extends BaseActivity implements SalaryReportDetailView {

    @BindView(R.id.layout_alpha_progress_bar) ConstraintLayout layoutProgressBar;
    @BindView(R.id.layout_data_empty) ConstraintLayout layoutDataEmpty;
    @BindView(R.id.tv_salary_report_detail_total_cost) AppCompatTextView tvTotalCost;
    @BindView(R.id.tv_salary_report_detail_total_quantity) AppCompatTextView tvTotalQuantity;
    @BindView(R.id.tv_salary_report_detail_name) AppCompatTextView tvName;
    @BindView(R.id.tv_salary_report_detail_position) AppCompatTextView tvPosition;
    @BindView(R.id.actv_salary_report_position) AutoCompleteTextView actvPosition;
    @BindView(R.id.til_salary_report_position) TextInputLayout tilPosition;
    @BindView(R.id.rv_salary_report_detail_list) RecyclerView recyclerView;
    @BindView(R.id.srl_salary_report_detail) SwipeRefreshLayout swipeRefreshLayout;

    @BindString(R.string.no_connection) String strNoConnection;
    @BindString(R.string.salary_report_detail_total_quantity) String strTotalQuantity;
    @BindString(R.string.position_all) String strPositionAll;

    @Inject SalaryReportDetailListAdapter adapter;
    @Inject LinearLayoutManager linearLayoutManager;
    @Inject VerticalSingleColumnDecoration verticalSingleColumnDecoration;
    @Inject SalaryReportDetailPresenter presenter;

    private SalaryReportDetailComponent component;
    private HashMap<String, String> options = new HashMap<>();
    private WorkerModel workerModel;

    @Override
    int setView() {
        return R.layout.activity_salary_report_detail;
    }

    @Override
    void initDagger() {
        if (component == null) {
            component = DaggerSalaryReportDetailComponent.builder()
                    .applicationComponent(((WorkflowApplication) getApplicationContext()).getApplicationComponent())
                    .salaryReportDetailModule(new SalaryReportDetailModule(this))
                    .contextModule(new ContextModule(this))
                    .build();
        }
        component.inject(this);
    }

    @Override
    void afterViewCreated(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.outline_arrow_back_ios_white_24);

        workerModel = getIntent().getParcelableExtra(PARCELABLE_WORKER_MODEL);
        DateFilterModel dateFilterModel = getIntent().getParcelableExtra(PARCELABLE_DATE_FILTER_MODEL);

        tvName.setText(workerModel.getName());
        initOptions(dateFilterModel);
        initDropdownPosition();
        initRecyclerView();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            presenter.resume();
        });
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
    public SalaryReportDetailListAdapter getAdapter() {
        return adapter;
    }

    @Override
    public HashMap<String, String> getOptions() {
        return options;
    }

    @Override
    public WorkerModel getWorkerModel() {
        return workerModel;
    }

    @Override
    public void renderData(GenericResponseModel<SalaryReportDetailListModel> res) {
        tvTotalCost.setText(WorkflowUtils.convertRupiah(res.getData().getTotalCost()));
        tvTotalQuantity.setText(String.format(strTotalQuantity, res.getData().getTotalQuantity()));

        adapter.removeLoadingFooter();
        adapter.setLoading(false);

        if (adapter.getCurrentPage() == 1) {
            adapter.setItems(res.getData().getList());
        } else {
            adapter.addAll(res.getData().getList());
        }

        if (res.getData().getList().size() == 0) {
            showDataEmpty();
        }

        if (adapter.getCurrentPage() >= res.getMeta().getTotalPage()) {
            adapter.setLastPage(true);
        } else {
            adapter.addLoadingFooter();
            adapter.setLastPage(false);
        }
    }

    @Override
    public void showLoading() {
        layoutProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showContent() {
        if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
        layoutDataEmpty.setVisibility(View.GONE);
        layoutProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showSnackBar(int type, String msg) {
        WorkflowUtils.generateSnackBar(this, type, msg).show();
    }

    @Override
    public boolean isConnected() {
        if (!ConnectionUtil.isOnline(this)) {
            showSnackBar(WorkflowUtils.SNACK_BAR_WARNING, strNoConnection);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void showDataEmpty() {
        adapter.clear();
        layoutDataEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.resume();
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
        recyclerView.addOnScrollListener(new WorkflowRvOnScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                adapter.setLoading(true);
                adapter.setCurrentPage(adapter.getCurrentPage() + 1);
                presenter.getSalaryReportDetail();
            }

            @Override
            public boolean isLastPage() {
                return adapter.isLastPage();
            }

            @Override
            public boolean isLoading() {
                return adapter.isLoading();
            }
        });

        adapter.setListener(new OnRecyclerObjectClickListener<SalaryReportDetailListModel.Item>() {
            @Override
            public void onItemClicked(int position, SalaryReportDetailListModel.Item item) {
            }

            @Override
            public void onItemLongClick(int position, SalaryReportDetailListModel.Item item) {

            }

            @Override
            public void onItemSelected(int position, SalaryReportDetailListModel.Item item) {

            }
        });
    }

    private void initOptions(DateFilterModel dateFilterModel) {
        options.put("start_date", dateFilterModel.getStartDate());
        options.put("end_date", dateFilterModel.getEndDate());
        options.put("sort_by", "spk_no");
        options.put("sort_direction", "asc");
    }

    private void initDropdownPosition() {
        if (workerModel.getPositions().size() > 1) {
            List<ListModel> positions = new ArrayList<>();
            positions.add(new ListModel("all", strPositionAll));
            for (String p : workerModel.getPositions()) {
                positions.add(new ListModel(p, WorkflowUtils.getRenderedPosition(this, p)));
            }

            actvPosition.setAdapter(new ArrayAdapter<ListModel>(this, android.R.layout.simple_spinner_dropdown_item, positions));
            actvPosition.setOnItemClickListener((parent, view, position, id) -> {
                String itemId = ((ListModel) parent.getItemAtPosition(position)).getId();
                if (itemId.equals("all")) {
                    options.remove("position");
                } else {
                    options.put("position",(itemId));
                }
                presenter.resume();
            });
            actvPosition.setText(positions.get(0).getName(), false);

            tilPosition.setVisibility(View.VISIBLE);
            tvPosition.setVisibility(View.GONE);
        } else {
            tvPosition.setText(WorkflowUtils.getRenderedPosition(this, workerModel.getPositions().get(0)));

            tilPosition.setVisibility(View.GONE);
            tvPosition.setVisibility(View.VISIBLE);
        }
    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, SalaryReportDetailActivity.class);
    }
}

package com.workflow.presentation.view.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.workflow.R;
import com.workflow.WorkflowApplication;
import com.workflow.data.model.DateFilterModel;
import com.workflow.data.model.ReportDetailDateFilterModel;
import com.workflow.data.model.SalaryReportListModel;
import com.workflow.data.model.WorkerModel;
import com.workflow.data.net.responses.GenericResponseModel;
import com.workflow.presentation.di.components.DaggerSalaryReportListComponent;
import com.workflow.presentation.di.components.SalaryReportListComponent;
import com.workflow.presentation.di.modules.ContextModule;
import com.workflow.presentation.di.modules.SalaryReportListModule;
import com.workflow.presentation.navigation.Navigator;
import com.workflow.presentation.presenter.SalaryReportListPresenter;
import com.workflow.presentation.view.SalaryReportListView;
import com.workflow.presentation.view.activities.MainActivity;
import com.workflow.presentation.view.adapters.OnRecyclerObjectClickListener;
import com.workflow.presentation.view.adapters.SalaryReportListAdapter;
import com.workflow.presentation.view.adapters.WorkflowRvOnScrollListener;
import com.workflow.presentation.view.itemdecoration.VerticalSingleColumnDecoration;
import com.workflow.utils.ConnectionUtil;
import com.workflow.utils.DateUtils;
import com.workflow.utils.Positions;
import com.workflow.utils.WorkflowUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

import static com.workflow.utils.WorkflowUtils.apiSimpleDateFormat;
import static com.workflow.utils.WorkflowUtils.indoDateFormat;

public class SalaryReportListFragment extends BaseFragment implements SalaryReportListView {

    private static final String DATE_START = "date_start";
    private static final String DATE_END = "date_end";

    @BindView(R.id.layout_alpha_progress_bar) ConstraintLayout layoutProgressBar;
    @BindView(R.id.layout_data_empty) ConstraintLayout layoutDataEmpty;
    @BindView(R.id.tv_salary_report_total_cost) AppCompatTextView tvTotalCost;
    @BindView(R.id.tv_item_salary_report_total_qty) AppCompatTextView tvTotalQty;
    @BindView(R.id.tv_field_report_qty_draw) AppCompatTextView tvQtyDraw;
    @BindView(R.id.tv_field_report_qty_lining_draw) AppCompatTextView tvQtyLiningDraw;
    @BindView(R.id.tv_field_report_qty_sew) AppCompatTextView tvQtySew;
    @BindView(R.id.tv_field_report_qty_assemble) AppCompatTextView tvQtyAssemble;
    @BindView(R.id.tv_field_report_qty_outsole_stitch) AppCompatTextView tvQtyOutsoleStitch;
    @BindView(R.id.tv_field_report_qty_insole_stitch) AppCompatTextView tvQtyInsoleStitch;
    @BindView(R.id.rv_salary_report_list) RecyclerView recyclerView;
    @BindView(R.id.et_salary_report_start_date) TextInputEditText etStartDate;
    @BindView(R.id.et_salary_report_end_date) TextInputEditText etEndDate;
    @BindView(R.id.srl_salary_report) SwipeRefreshLayout swipeRefreshLayout;

    @BindString(R.string.report_total_qty) String strQuantity;
    @BindString(R.string.no_connection) String strNoConnection;

    @Inject SalaryReportListAdapter adapter;
    @Inject LinearLayoutManager linearLayoutManager;
    @Inject VerticalSingleColumnDecoration verticalSingleColumnDecoration;
    @Inject SalaryReportListPresenter presenter;
    @Inject Navigator navigator;

    private SalaryReportListComponent component;
    private HashMap<String, String> options = new HashMap<>();
    private Calendar calendarStart = Calendar.getInstance();
    private Calendar calendarEnd = Calendar.getInstance();

    @Override
    int setView() {
        return R.layout.fragment_salary_report_list;
    }

    @Override
    void initDagger() {
        if (component == null) {
            component = DaggerSalaryReportListComponent.builder()
                    .applicationComponent(((WorkflowApplication) context.getApplicationContext()).getApplicationComponent())
                    .salaryReportListModule(new SalaryReportListModule(this))
                    .contextModule(new ContextModule(context))
                    .build();
        }
        component.inject(this);
    }

    @Override
    void afterViewCreated(Bundle savedInstanceState) {
        initDates();
        initOptions();
        initRecyclerView();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            presenter.resume();
        });
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
        View view = (((MainActivity) context).getCoordinatorLayout());
        WorkflowUtils.generateSnackBar(view, type, msg).show();
    }

    @Override
    public boolean isConnected() {
        if (!ConnectionUtil.isOnline(context)) {
            showSnackBar(WorkflowUtils.SNACK_BAR_WARNING, strNoConnection);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public SalaryReportListAdapter getAdapter() {
        return adapter;
    }

    @Override
    public HashMap<String, String> getOptions() {
        return options;
    }

    @Override
    public void renderData(GenericResponseModel<SalaryReportListModel> data) {
        List<SalaryReportListModel.Quantity> quantities = data.getData().getQuantitiesModels();
        Integer totalCost = data.getData().getTotalCost();
        Integer totalQty = 0;
        List<SalaryReportListModel.Item> items = data.getData().getList();

        tvQtyDraw.setText("-");
        tvQtyLiningDraw.setText("-");
        tvQtySew.setText("-");
        tvQtyAssemble.setText("-");
        tvQtyOutsoleStitch.setText("-");
        tvQtyInsoleStitch.setText("-");

        for (SalaryReportListModel.Quantity quantity : quantities) {
            switch (quantity.getPosition()) {
                case Positions.DRAWER:
                    tvQtyDraw.setText(quantity.getQuantity().toString());
                    break;
                case Positions.LINING_DRAWER:
                    tvQtyLiningDraw.setText(quantity.getQuantity().toString());
                    break;
                case Positions.SEWER:
                    tvQtySew.setText(quantity.getQuantity().toString());
                    break;
                case Positions.ASSEMBLER:
                    tvQtyAssemble.setText(quantity.getQuantity().toString());
                    break;
                case Positions.SOLE_STITCHER:
                    tvQtyOutsoleStitch.setText(quantity.getQuantity().toString());
                    break;
                case Positions.INSOLE_STITCHER:
                    tvQtyInsoleStitch.setText(quantity.getQuantity().toString());
                    break;
                default:
                    break;
            }
            totalQty = totalQty + quantity.getQuantity();
        }

        tvTotalQty.setText(String.format(strQuantity, totalQty));
        tvTotalCost.setText(WorkflowUtils.convertRupiah(totalCost));

        adapter.removeLoadingFooter();
        adapter.setLoading(false);

        if (adapter.getCurrentPage() == 1) {
            adapter.setItems(items);
        } else {
            adapter.addAll(items);
        }

        if (items.size() == 0) {
            showDataEmpty();
        }

        if (adapter.getCurrentPage() >= data.getMeta().getTotalPage()) {
            adapter.setLastPage(true);
        } else {
            adapter.addLoadingFooter();
            adapter.setLastPage(false);
        }
    }

    @Override
    public void showDataEmpty() {
        adapter.clear();
        layoutDataEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.resume();
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

    @OnClick(R.id.et_salary_report_start_date)
    void showStartDatePicker(TextInputEditText editText) {
        showDatePicker(editText, calendarStart, DATE_START);
    }

    @OnClick(R.id.et_salary_report_end_date)
    void showEndDatePicker(TextInputEditText editText) {
        showDatePicker(editText, calendarEnd, DATE_END);
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
                presenter.getSalaryReport();
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

        adapter.setListener(new OnRecyclerObjectClickListener<SalaryReportListModel.Item>() {
            @Override
            public void onItemClicked(int position, SalaryReportListModel.Item item) {
                navigator.navigateToSalaryDetail(context,
                        new WorkerModel(item.getWorkerId(), item.getName(), item.getPositions()),
                        new DateFilterModel(DateUtils.clientToServer(calendarStart, DateUtils.TYPE_DATE),
                                DateUtils.clientToServer(calendarEnd, DateUtils.TYPE_DATE))
                );
            }

            @Override
            public void onItemLongClick(int position, SalaryReportListModel.Item item) {

            }

            @Override
            public void onItemSelected(int position, SalaryReportListModel.Item item) {

            }
        });
    }

    private void showDatePicker(TextInputEditText editText, Calendar calendar, String dateType) {
        DatePickerDialog.OnDateSetListener dateListener = (view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth);
            editText.setText(indoDateFormat.format(calendar.getTime()));
            if (dateType.equals(DATE_START)) {
                options.put("start_date", apiSimpleDateFormat.format(calendar.getTime()));
            } else if (dateType.equals(DATE_END)) {
                options.put("end_date", apiSimpleDateFormat.format(calendar.getTime()));
            }
            presenter.resume();
        };
        new DatePickerDialog(
                context, dateListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    private void initDates() {
        calendarStart.add(Calendar.DAY_OF_MONTH, -7);

        etStartDate.setText(indoDateFormat.format(calendarStart.getTime()));
        etEndDate.setText(indoDateFormat.format(calendarEnd.getTime()));
    }

    private void initOptions() {
        options.put("start_date", apiSimpleDateFormat.format(calendarStart.getTime()));
        options.put("end_date", apiSimpleDateFormat.format(calendarEnd.getTime()));
        options.put("sort_by", "name");
        options.put("sort_direction", "asc");
    }
}

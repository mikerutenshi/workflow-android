package com.workflow.presentation.view.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;

import androidx.appcompat.widget.AppCompatRadioButton;

import com.google.android.material.textfield.TextInputEditText;
import com.workflow.R;
import com.workflow.data.model.ListModel;
import com.workflow.utils.Sort;
import com.workflow.utils.WorkflowUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

import static com.workflow.presentation.navigation.Navigator.EXTRA_WORKER_WORK_FILTER_TYPE;
import static com.workflow.presentation.navigation.Navigator.SERIALIZABLE_WORK_ASSIGN;

public class FilterSortWorkAssignDialogFragment extends BaseDialogFragment {

    @BindView(R.id.et_filter_sort_work_assign_start_date) TextInputEditText etStartDate;
    @BindView(R.id.et_filter_sort_work_assign_end_date) TextInputEditText etEndDate;
    @BindView(R.id.actv_filter_sort_work_assign_sort_by) AutoCompleteTextView actvSortBy;
    @BindView(R.id.rb_filter_work_assign_sort_direction_asc) AppCompatRadioButton rbAsc;
    @BindView(R.id.rb_filter_work_assign_sort_direction_desc) AppCompatRadioButton rbDesc;

    @BindString(R.string.filter_sort_by_article_no) String strSortByArticleNo;
    @BindString(R.string.filter_sort_by_spk_no) String strSortBySpkNo;
    @BindString(R.string.filter_sort_by_date) String strSortByDate;

    private Calendar startCal = Calendar.getInstance();
    private Calendar endCal = Calendar.getInstance();
    private WorkAssignFilterListener listener;
    private String keySelectedSortBy;

    @Override
    int setView() {
        return R.layout.fragment_dialog_filter_sort_work_assign;
    }

    @Override
    void initDagger() {

    }

    @Override
    void afterViewCreated(Bundle savedInstanceState) {
        initDropdown();
        readBundle(getArguments());
    }

    @OnClick(R.id.et_filter_sort_work_assign_start_date)
    void showStartDatePicker() {
        int mYear = startCal.get(Calendar.YEAR);
        int mMonth = startCal.get(Calendar.MONTH);
        int mDay = startCal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener startDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                startCal.set(year, month, dayOfMonth);
                String startDate = WorkflowUtils.getIndoDateFormat().format(startCal.getTime());
                etStartDate.setText(startDate);
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context, startDateListener, mYear, mMonth,mDay);
        datePickerDialog.show();
    }

    @OnClick(R.id.et_filter_sort_work_assign_end_date)
    void showEndDatePicker() {
        int mYear = endCal.get(Calendar.YEAR);
        int mMonth = endCal.get(Calendar.MONTH);
        int mDay = endCal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener endDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                endCal.set(year, month, dayOfMonth);
                String endDate = WorkflowUtils.getIndoDateFormat().format(endCal.getTime());
                etEndDate.setText(endDate);
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context, endDateListener, mYear, mMonth,mDay);
        datePickerDialog.show();
    }

    @OnClick(R.id.btn_filter_sort_work_assign_apply)
    void applyFilter() {
        HashMap<String, String> filterStatus = new HashMap<>();
        filterStatus.put("start_date", WorkflowUtils.apiSimpleDateFormat.format(startCal.getTime()));
        filterStatus.put("end_date", WorkflowUtils.apiSimpleDateFormat.format(endCal.getTime()));
        filterStatus.put("sort_by", keySelectedSortBy);
        filterStatus.put("sort_direction", compileSortDirection());
        listener.onApplyClick(filterStatus);
        dismiss();
    }

    public void setListener(WorkAssignFilterListener listener) {
        this.listener = listener;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            HashMap<String, String> options = (HashMap<String, String>) bundle.getSerializable(SERIALIZABLE_WORK_ASSIGN);
            if (options != null) {
                initFilterState(options);
            } else {
                initDates();
                actvSortBy.setText(strSortBySpkNo, false);
                checkSortDirection(Sort.SORT_DIRECTION_ASC);
            }
        }
    }

    private void initFilterState(HashMap<String,String> options) {
        Date date = null;
        if (options.get("start_date") != null) {
            try {
                date = WorkflowUtils.apiSimpleDateFormat.parse(options.get("start_date"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            startCal.setTime(date);
        }

        if (options.get("end_date") != null) {
            try {
                date = WorkflowUtils.apiSimpleDateFormat.parse(options.get("end_date"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        endCal.setTime(date);
        etStartDate.setText(WorkflowUtils.getIndoDateFormat().format(startCal.getTime()));
        etEndDate.setText(WorkflowUtils.getIndoDateFormat().format(endCal.getTime()));

        String sortBy = options.get("sort_by");
        if (sortBy != null) {
            keySelectedSortBy = sortBy;
            switch (sortBy) {
                case Sort.SORT_BY_SPK_NO:
                    actvSortBy.setText(strSortBySpkNo, false);
                    break;
                case Sort.SORT_BY_ARTICLE_NO:
                    actvSortBy.setText(strSortByArticleNo, false);
                    break;
                case Sort.SORT_BY_DATE:
                    actvSortBy.setText(strSortByDate, false);
                    break;
                default:
                    break;
            }
        }

        String sortDirection = options.get("sort_direction");
        if (sortDirection != null) {
            checkSortDirection(sortDirection);
        }
    }

    private void initDropdown() {
        List<ListModel> sortBys = new ArrayList<>(Arrays.asList(
                new ListModel(Sort.SORT_BY_SPK_NO, strSortBySpkNo),
                new ListModel(Sort.SORT_BY_ARTICLE_NO, strSortByArticleNo),
                new ListModel(Sort.SORT_BY_DATE, strSortByDate)
        ));

        actvSortBy.setAdapter(new ArrayAdapter<ListModel>(context, android.R.layout.simple_spinner_dropdown_item, sortBys));
        actvSortBy.setOnItemClickListener((parent, view, position, id) -> {
            keySelectedSortBy = ((ListModel) parent.getItemAtPosition(position)).getId();
        });
    }

    private void initDates() {
        startCal.add(Calendar.DAY_OF_MONTH, -7);
        etStartDate.setText(WorkflowUtils.getIndoDateFormat().format(startCal.getTime()));
        etEndDate.setText(WorkflowUtils.getIndoDateFormat().format(endCal.getTime()));
    }

    private String compileSortDirection() {
        if (rbDesc.isChecked()) {
            return Sort.SORT_DIRECTION_DESC;
        } else {
            return Sort.SORT_DIRECTION_ASC;
        }
    }

    private void checkSortDirection(String sortDirection) {
        if (sortDirection.equals(Sort.SORT_DIRECTION_ASC)) {
            rbAsc.setChecked(true);
        } else {
            rbDesc.setChecked(true);
        }
    }

    public interface WorkAssignFilterListener {
        void onApplyClick(HashMap<String, String> filterState);
    }

    public static FilterSortWorkAssignDialogFragment newInstance(HashMap<String, String> options) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(SERIALIZABLE_WORK_ASSIGN, options);
        FilterSortWorkAssignDialogFragment fragment = new FilterSortWorkAssignDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

import static com.workflow.presentation.navigation.Navigator.EXTRA_WORKER_POSITION;
import static com.workflow.presentation.navigation.Navigator.EXTRA_WORKER_WORK_FILTER_TYPE;
import static com.workflow.presentation.navigation.Navigator.SERIALIZABLE_ASSIGNED_WORK;
import static com.workflow.utils.Filter.FILTER_BY_ASSIGNED_AT;
import static com.workflow.utils.Filter.FILTER_BY_CREATED_AT;
import static com.workflow.utils.Filter.FILTER_BY_DONE_AT;
import static com.workflow.utils.Filter.FILTER_POSITION_ALL;
import static com.workflow.utils.PreferenceUtils.PREF_WORK_ASSIGNED_FILTER_STATUS;
import static com.workflow.utils.PreferenceUtils.PREF_WORK_DONE_FILTER_STATUS;
import static com.workflow.utils.Sort.SORT_BY_ARTICLE_NO;
import static com.workflow.utils.Sort.SORT_BY_ASSIGNED_AT;
import static com.workflow.utils.Sort.SORT_BY_DATE;
import static com.workflow.utils.Sort.SORT_BY_DONE_AT;
import static com.workflow.utils.Sort.SORT_BY_SPK_NO;

public class FilterSortAssignedWorkDialogFragment extends BaseDialogFragment {

    @BindView(R.id.actv_filter_assigned_work_date_type) AutoCompleteTextView actvFilterDateType;
    @BindView(R.id.actv_filter_assigned_work_sort_by) AutoCompleteTextView actvSortBy;
    @BindView(R.id.actv_filter_assigned_work_position) AutoCompleteTextView actvPosition;
    @BindView(R.id.rb_filter_assigned_work_sort_direction_asc) AppCompatRadioButton rbAsc;
    @BindView(R.id.rb_filter_assigned_work_sort_direction_desc) AppCompatRadioButton rbDesc;
    @BindView(R.id.et_filter_sort_assigned_work_start_date) TextInputEditText etStartDate;
    @BindView(R.id.et_filter_sort_assigned_work_end_date) TextInputEditText etEndDate;

    @BindString(R.string.filter_sort_by_date) String strFilterSortByDate;
    @BindString(R.string.filter_sort_by_done_at) String strFilterSortByDoneAt;
    @BindString(R.string.filter_sort_by_assigned_at) String strFilterSortByAssignedAt;
    @BindString(R.string.filter_sort_by_article_no) String strSortByArticleNo;
    @BindString(R.string.filter_sort_by_spk_no) String strSortBySpkNo;
    @BindString(R.string.filter_date_type_hint) String strDateTypeHint;
    @BindString(R.string.sort_title) String strSortByHint;
    @BindString(R.string.sort_position) String strPositionHint;
    @BindString(R.string.position_all) String strPositionAll;

    private AssignedWorkFilterListener listener;
    private Calendar startCal = Calendar.getInstance();
    private Calendar endCal = Calendar.getInstance();
    private int selectedDateTypePosition;
    private String keySelectedSortBy;
    private String keySelectedFilterDateType;
    private String keySelectedFilterPosition;

    @Override
    int setView() {
        return R.layout.fragment_dialog_filter_sort_assigned_work;
    }

    @Override
    void initDagger() {

    }

    @Override
    void afterViewCreated(Bundle savedInstanceState) {
        readBundle(getArguments());
    }

    @OnClick(R.id.et_filter_sort_assigned_work_start_date)
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

    @OnClick(R.id.et_filter_sort_assigned_work_end_date)
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

    @OnClick(R.id.btn_filter_sort_assigned_work_apply)
    void onApplyClick() {
        HashMap<String, String> filterStatus = new HashMap<>();

        filterStatus.put("date_filter_type", keySelectedFilterDateType);
        filterStatus.put("start_date", WorkflowUtils.apiSimpleDateFormat.format(startCal.getTime()));
        filterStatus.put("end_date", WorkflowUtils.apiSimpleDateFormat.format(endCal.getTime()));
        if (!keySelectedFilterPosition.equals(FILTER_POSITION_ALL)) {
            filterStatus.put("position", keySelectedFilterPosition);
        }
        filterStatus.put("sort_by", keySelectedSortBy);
        filterStatus.put("sort_direction", compileSortDirection());
        listener.onApplyClick(filterStatus);
        dismiss();
    }

    public void setListener(AssignedWorkFilterListener listener) {
        this.listener = listener;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            HashMap<String, String> options = (HashMap<String, String>) bundle.getSerializable(SERIALIZABLE_ASSIGNED_WORK);
            ArrayList<String> positions = bundle.getStringArrayList(EXTRA_WORKER_POSITION);

            if (positions != null) {
                initDropdowns(positions, bundle.getString(EXTRA_WORKER_WORK_FILTER_TYPE));
            }

            if (options != null) {
                initFilterStatus(options);
            }
        }
    }

    private void initFilterStatus(HashMap<String, String> options) {

        String dateFilterType = options.get("date_filter_type");
        if (dateFilterType != null) {
            switch (dateFilterType) {
                case FILTER_BY_CREATED_AT:
                    actvFilterDateType.setText(strFilterSortByDate, false);
                    keySelectedFilterDateType = FILTER_BY_CREATED_AT;
                    break;
                case FILTER_BY_ASSIGNED_AT:
                    actvFilterDateType.setText(strFilterSortByAssignedAt, false);
                    keySelectedFilterDateType = FILTER_BY_ASSIGNED_AT;
                    break;
                case FILTER_BY_DONE_AT:
                    actvFilterDateType.setText(strFilterSortByDoneAt, false);
                    keySelectedFilterDateType = FILTER_BY_DONE_AT;
                    break;
                default:
                    break;
            }
        }

        String filterPositionKey = options.get("position");
        if (filterPositionKey != null) {
            keySelectedFilterPosition = filterPositionKey;
            actvPosition.setText(WorkflowUtils.getRenderedPosition(context, filterPositionKey), false);;
        } else {
            keySelectedFilterPosition = FILTER_POSITION_ALL;
            actvPosition.setText(strPositionAll, false);;
        }

        String sortBy = options.get("sort_by");
        if (sortBy != null) {
            switch (sortBy) {
                case SORT_BY_SPK_NO:
                    actvSortBy.setText(strSortBySpkNo, false);
                    keySelectedSortBy = SORT_BY_SPK_NO;
                    break;
                case SORT_BY_ARTICLE_NO:
                    actvSortBy.setText(strSortByArticleNo, false);
                    keySelectedSortBy = SORT_BY_ARTICLE_NO;
                    break;
                case SORT_BY_DATE:
                    actvSortBy.setText(strFilterSortByDate, false);
                    keySelectedSortBy = SORT_BY_DATE;
                    break;
                case SORT_BY_ASSIGNED_AT:
                    actvSortBy.setText(strFilterSortByAssignedAt, false);
                    keySelectedSortBy = SORT_BY_ASSIGNED_AT;
                    break;
                case SORT_BY_DONE_AT:
                    actvSortBy.setText(strFilterSortByDoneAt, false);
                    keySelectedSortBy = SORT_BY_DONE_AT;
                    break;
                default:
                    break;
            }
        }

        String sortDirection = options.get("sort_direction");
        if (sortDirection != null) {
            checkSortDirection(sortDirection);
        }

        if (options.get("start_date") != null) {
            try {
                startCal.setTime((new SimpleDateFormat("yyyy-MM-dd").parse(options.get("start_date"))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (options.get("end_date") != null) {
            try {
                endCal.setTime((new SimpleDateFormat("yyyy-MM-dd").parse(options.get("end_date"))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        etStartDate.setText(WorkflowUtils.getIndoDateFormat().format(startCal.getTime()));
        etEndDate.setText(WorkflowUtils.getIndoDateFormat().format(endCal.getTime()));
    }

    private void initDropdowns(ArrayList<String> workerPositions, String filterType) {
        List<ListModel> dateTypes = null;
        List<ListModel> sortBys = null;
        switch (filterType) {
            case PREF_WORK_ASSIGNED_FILTER_STATUS: {
                dateTypes = new ArrayList<>(Arrays.asList(
                        new ListModel(FILTER_BY_CREATED_AT, strFilterSortByDate),
                        new ListModel(FILTER_BY_ASSIGNED_AT, strFilterSortByAssignedAt)
                ));

                sortBys = new ArrayList<>(Arrays.asList(
                        new ListModel(SORT_BY_SPK_NO, strSortBySpkNo),
                        new ListModel(SORT_BY_ARTICLE_NO, strSortByArticleNo),
                        new ListModel(SORT_BY_DATE, strFilterSortByDate),
                        new ListModel(SORT_BY_ASSIGNED_AT, strFilterSortByAssignedAt)
                ));
                break;
            }
            case PREF_WORK_DONE_FILTER_STATUS: {
                dateTypes = new ArrayList<>(Arrays.asList(
                        new ListModel(FILTER_BY_CREATED_AT, strFilterSortByDate),
                        new ListModel(FILTER_BY_DONE_AT, strFilterSortByDoneAt)
                ));

                sortBys = new ArrayList<>(Arrays.asList(
                        new ListModel(SORT_BY_SPK_NO, strSortBySpkNo),
                        new ListModel(SORT_BY_ARTICLE_NO, strSortByArticleNo),
                        new ListModel(SORT_BY_DATE, strFilterSortByDate),
                        new ListModel(SORT_BY_DONE_AT, strFilterSortByDoneAt)
                ));
                break;
            }
        }

        List<ListModel> positionModels = new ArrayList<>();
        //first add all position item
        positionModels.add(new ListModel(FILTER_POSITION_ALL, strPositionAll));
        for (String pos : workerPositions) {
            positionModels.add(new ListModel(pos, WorkflowUtils.getRenderedPosition(context, pos)));
        }

        actvPosition.setAdapter(new ArrayAdapter<ListModel>(context,
                android.R.layout.simple_spinner_dropdown_item, positionModels));
        actvPosition.setOnItemClickListener((parent, view, position, id) -> {
            keySelectedFilterPosition = ((ListModel) parent.getItemAtPosition(position)).getId();
        });

        actvFilterDateType.setAdapter(new ArrayAdapter<ListModel>(context,
                android.R.layout.simple_spinner_dropdown_item, dateTypes));
        actvFilterDateType.setOnItemClickListener((parent, view, position, id) -> {
            keySelectedFilterDateType = ((ListModel) parent.getItemAtPosition(position)).getId();
        });

        actvSortBy.setAdapter(new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_dropdown_item, sortBys));
        actvSortBy.setOnItemClickListener((parent, view, position, id) -> {
            keySelectedSortBy = ((ListModel) parent.getItemAtPosition(position)).getId();
        });

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

    public interface AssignedWorkFilterListener {
        void onApplyClick(HashMap<String, String> filterState);
    }

    public static FilterSortAssignedWorkDialogFragment newInstance(HashMap<String, String> options,
                                                                   ArrayList<String> positions,
                                                                   String filterType) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(SERIALIZABLE_ASSIGNED_WORK, options);
        bundle.putStringArrayList(EXTRA_WORKER_POSITION, positions);
        bundle.putString(EXTRA_WORKER_WORK_FILTER_TYPE, filterType);
        FilterSortAssignedWorkDialogFragment fragment = new FilterSortAssignedWorkDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}

package com.workflow.presentation.view.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatRadioButton;

import com.google.android.material.button.MaterialButton;
import com.workflow.R;
import com.workflow.data.model.GetWorkerQueryModel;
import com.workflow.data.model.ListModel;
import com.workflow.utils.Positions;
import com.workflow.utils.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.workflow.presentation.navigation.Navigator.PARCELABLE_WORKER_FILTER;
import static com.workflow.utils.Sort.SORT_BY_WORKER_NAME;
import static com.workflow.utils.Sort.SORT_BY_WORKER_POSITION;

public class FilterSortWorkerDialogFragment extends BaseDialogFragment {

    @BindView(R.id.cb_filter_sort_worker_drawer) AppCompatCheckBox cbDrawer;
    @BindView(R.id.cb_filter_sort_worker_sewer) AppCompatCheckBox cbSewer;
    @BindView(R.id.cb_filter_sort_worker_assembler) AppCompatCheckBox cbAssembler;
    @BindView(R.id.cb_filter_sort_worker_sole_stitcher) AppCompatCheckBox cbSoleStitcher;
    @BindView(R.id.cb_filter_sort_worker_lining_drawer) AppCompatCheckBox cbLiningDrawer;
    @BindView(R.id.cb_filter_sort_worker_insole_stitcher) AppCompatCheckBox cbInsoleStitcher;

    @BindView(R.id.rb_filter_worker_sort_direction_asc) AppCompatRadioButton rbAsc;
    @BindView(R.id.rb_filter_worker_sort_direction_desc) AppCompatRadioButton rbDesc;
    @BindView(R.id.actv_filter_worker_sort_by) AutoCompleteTextView actvSortBy;
    @BindView(R.id.btn_filter_sort_worker_apply) MaterialButton btnApply;

    @BindString(R.string.sort_position) String strSortByPosition;
    @BindString(R.string.sort_name) String strSortByName;

    private WorkerApplyFilterListener listener;
    private String keySelectedSortBy;

    @Override
    int setView() {
        return R.layout.fragment_dialog_filter_sort_worker;
    }

    @Override
    void initDagger() {

    }

    @Override
    void afterViewCreated(Bundle savedInstanceState) {
        readBundle(getArguments());
    }

    public void setListener(WorkerApplyFilterListener listener) {
        this.listener = listener;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            GetWorkerQueryModel queryModel = bundle.getParcelable(PARCELABLE_WORKER_FILTER);
            if (queryModel != null) {
                initFilterState(queryModel);
            }
        }
    }

    @OnClick(R.id.btn_filter_sort_worker_apply)
    void applyFilter() {
        Timber.d("mKeySelectedSortBy: %s", keySelectedSortBy);
        listener.onApplyClick(new GetWorkerQueryModel(
                compileCheckedPositionCheckBoxes()
                , keySelectedSortBy
                , compileSortDirection()));
        dismiss();
    }

    private void initFilterState(GetWorkerQueryModel initialState) {
        turnOffPositionCheckBoxes();
        for (String position : initialState.getPositions()) {
            turnPositionCheckBoxes(position);
        }
        initDropdownSortBy(initialState.getSortBy());
        checkSortDirection(initialState.getSortDirection());
    }

    private void turnPositionCheckBoxes(String position) {
        switch (position) {
            case Positions.DRAWER:
                cbDrawer.setChecked(true);
                break;
            case Positions.SEWER:
                cbSewer.setChecked(true);
                break;
            case Positions.ASSEMBLER:
                cbAssembler.setChecked(true);
                break;
            case Positions.SOLE_STITCHER:
                cbSoleStitcher.setChecked(true);
                break;
            case Positions.LINING_DRAWER:
                cbLiningDrawer.setChecked(true);
                break;
            case Positions.INSOLE_STITCHER:
                cbInsoleStitcher.setChecked(true);
                break;
            default:
                break;
        }
    }

    private List<String> compileCheckedPositionCheckBoxes() {
        List<String> positions = new ArrayList<>();

        if (cbDrawer.isChecked()) {
            positions.add(Positions.DRAWER);
        }
        if (cbLiningDrawer.isChecked()) {
            positions.add(Positions.LINING_DRAWER);
        }
        if (cbSewer.isChecked()) {
            positions.add(Positions.SEWER);
        }
        if (cbAssembler.isChecked()) {
            positions.add(Positions.ASSEMBLER);
        }
        if (cbSoleStitcher.isChecked()) {
            positions.add(Positions.SOLE_STITCHER);
        }
        if (cbInsoleStitcher.isChecked()) {
            positions.add(Positions.INSOLE_STITCHER);
        }

        return positions;
    }

    private String compileSortDirection() {
        if (rbDesc.isChecked()) {
            return Sort.SORT_DIRECTION_DESC;
        } else {
            return Sort.SORT_DIRECTION_ASC;
        }
    }

    private void initDropdownSortBy(String sortBy) {
        List<ListModel> sortBys = new ArrayList<>(
                Arrays.asList(new ListModel(SORT_BY_WORKER_POSITION, strSortByPosition),
                        new ListModel(SORT_BY_WORKER_NAME, strSortByName)));

        ArrayAdapter<ListModel> arrayAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_dropdown_item, sortBys);
        actvSortBy.setAdapter(arrayAdapter);
        actvSortBy.setOnItemClickListener((parent, view, position, id) -> {
            keySelectedSortBy = ((ListModel) parent.getItemAtPosition(position)).getId();
            Timber.d("mKeySelectedSortByOnItemClick: %s", keySelectedSortBy);
        });

        if (sortBy != null) {
            if (sortBy.equals(SORT_BY_WORKER_NAME)) {
                keySelectedSortBy = SORT_BY_WORKER_NAME;
                actvSortBy.setText(strSortByName, false);
            } else if (sortBy.equals(Sort.SORT_BY_WORKER_POSITION)) {
                keySelectedSortBy = SORT_BY_WORKER_POSITION;
                actvSortBy.setText(strSortByPosition, false);
            }
        }
    }

    private void turnOffPositionCheckBoxes() {
        cbDrawer.setChecked(false);
        cbSewer.setChecked(false);
        cbAssembler.setChecked(false);
        cbSoleStitcher.setChecked(false);
        cbLiningDrawer.setChecked(false);
        cbInsoleStitcher.setChecked(false);
    }

    private void checkSortDirection(String sortDirection) {
        if (sortDirection.equals(Sort.SORT_DIRECTION_ASC)) {
            rbAsc.setChecked(true);
        } else {
            rbDesc.setChecked(true);
        }
    }

    public interface WorkerApplyFilterListener {
        void onApplyClick(GetWorkerQueryModel filterState);
    }

    public static FilterSortWorkerDialogFragment newInstance(GetWorkerQueryModel getWorkerQueryModel) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(PARCELABLE_WORKER_FILTER, getWorkerQueryModel);
        FilterSortWorkerDialogFragment fragment = new FilterSortWorkerDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}

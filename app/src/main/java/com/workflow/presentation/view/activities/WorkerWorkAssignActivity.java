package com.workflow.presentation.view.activities;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.workflow.R;
import com.workflow.data.model.ListModel;
import com.workflow.data.model.WorkerModel;
import com.workflow.data.net.responses.GenericListResponseModel;
import com.workflow.presentation.di.components.WorkAssignComponent;
import com.workflow.presentation.presenter.WorkerWorkAssignPresenter;
import com.workflow.presentation.view.WorkerWorkAssignView;
import com.workflow.presentation.view.adapters.BaseViewHolder;
import com.workflow.presentation.view.adapters.OnRecyclerObjectClickListener;
import com.workflow.presentation.view.adapters.PaginationAdapter;
import com.workflow.presentation.view.adapters.WorkflowRvOnScrollListener;
import com.workflow.presentation.view.fragments.FilterSortWorkAssignDialogFragment;
import com.workflow.presentation.view.itemdecoration.VerticalSingleColumDecorationLargeBot;
import com.workflow.utils.ConnectionUtil;
import com.workflow.utils.WorkflowUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.workflow.utils.PreferenceUtils.PREF_WORK_ASSIGN_FILTER_STATUS;

/**
 * Created by Michael on 06/07/19.
 */

public abstract class WorkerWorkAssignActivity<T> extends BaseActivity implements WorkerWorkAssignView<T> {

    @BindView(R.id.rv_assign_work_list) RecyclerView recyclerView;
    @BindView(R.id.progress_bar_worker_work) ConstraintLayout pbView;
    @BindView(R.id.et_worker_work_date) TextInputEditText etDate;
    @BindView(R.id.til_worker_work_date) TextInputLayout tilDate;
    @BindView(R.id.tv_worker_work_filter_stats) AppCompatTextView tvFilterStats;
    @BindView(R.id.tv_worker_work_sort_stats) AppCompatTextView tvSortStats;
    @BindView(R.id.layout_worker_work_data_empty) ConstraintLayout clDataEmpty;
    @BindView(R.id.srl_worker_work) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.clpb_progress_bar) ContentLoadingProgressBar contentLoadingProgressBar;
    @BindView(R.id.actv_worker_work_position) AutoCompleteTextView actvPosition;
    @BindView(R.id.tv_worker_work_name) AppCompatTextView tvName;
    @BindView(R.id.tv_worker_work_position) AppCompatTextView tvPosition;
    @BindView(R.id.til_worker_work_position) TextInputLayout tilPosition;
    @BindView(R.id.et_worker_work_quantity) TextInputEditText etQuantity;
    @BindView(R.id.til_worker_work_quantity) TextInputLayout tilQuantity;
    @BindView(R.id.et_worker_work_notes) TextInputEditText etNotes;

    @BindString(R.string.no_connection) String strNoConnection;
    @BindString(R.string.worker_work_date_later_warning) String strDateLaterWarning;
    @BindString(R.string.search_work) String strSearchWork;
    @BindString(R.string.filter_status) String strFilterStatus;
    @BindString(R.string.sort_status) String strSortStatus;
    @BindString(R.string.worker_work_nothing_chosen_warning) String strNothingChosen;
    @BindString(R.string.work_assign_successful) String strAssignmentSuccess;
    @BindString(R.string.field_required_simple) String strFieldRequired;
    @BindString(R.string.work_assign_quantity_exceed) String strQuantityExceed;
    @BindString(R.string.work_assign_quantity_too_small) String strQuantityTooSmall;

    @Inject VerticalSingleColumDecorationLargeBot verticalSingleColumnDecoration;
    @Inject LinearLayoutManager linearLayoutManager;
    @Inject SharedPreferences sharedPreferences;

    abstract PaginationAdapter<T, OnRecyclerObjectClickListener<T>
                , BaseViewHolder<T, OnRecyclerObjectClickListener<T>>> adapter();
    abstract String filterPrefKey();
    abstract WorkerWorkAssignPresenter<T> presenter();
    abstract void toggleItemChecked(int position, T item);
    abstract void postWork(T item);

    private final Locale indonesian = new Locale("in", "ID");
    private final SimpleDateFormat indoDateFormat = new SimpleDateFormat("dd MMMM yyyy", indonesian);

    private WorkAssignComponent component;
    Calendar calendar = Calendar.getInstance();
    private SearchView searchView;
    private Timer timer = null;
    int etQuantityMaxValue = 0;

    private HashMap<String, String> options = new HashMap<>();
    WorkerModel mWorkerModel;


    @Override
    int setView() {
        return R.layout.activity_worker_work;
    }

    @Override
    void afterViewCreated(Bundle savedInstanceState) {
        initFilterStatus();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.outline_arrow_back_ios_white_24);
        initAdapter();

        tvName.setText(mWorkerModel.getName());

        if (mWorkerModel.getPositions().size() > 1) {
            tilPosition.setVisibility(View.VISIBLE);
            tvPosition.setVisibility(View.GONE);
            initDropdown(mWorkerModel.getPositions());
        } else {
            tilPosition.setVisibility(View.GONE);
            tvPosition.setVisibility(View.VISIBLE);
            tvPosition.setText(WorkflowUtils.getRenderedPosition(
                    this, mWorkerModel.getPositions().get(0)));
        }

        etDate.setText(indoDateFormat.format(calendar.getTime()));
        swipeRefreshLayout.setOnRefreshListener(this::refreshRv);
        etQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    int quantity = Integer.valueOf(s.toString());
                    if (quantity > etQuantityMaxValue) {
                        tilQuantity.setError(strQuantityExceed);
                        tilQuantity.setErrorEnabled(true);
                    } else {
                        tilQuantity.setErrorEnabled(false);
                    }
                } else {
                    tilQuantity.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        menu.findItem(R.id.action_filter).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                FilterSortWorkAssignDialogFragment filterDialog =
                        FilterSortWorkAssignDialogFragment.newInstance(options);
                filterDialog.show(getSupportFragmentManager(), "FilterWorkAssignDialog");
                filterDialog.setListener(new FilterSortWorkAssignDialogFragment.WorkAssignFilterListener() {
                    @Override
                    public void onApplyClick(HashMap<String, String> filterState) {
                        options = filterState;
                        Gson gson = new Gson();
                        sharedPreferences.edit().putString(filterPrefKey()
                                , gson.toJson(filterState)).apply();
//                        setFilterBadgeCount(String.valueOf(1));
                        invalidateData();

                    }
                });
                return true;
            }
        });
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
        searchView.setQueryHint(strSearchWork);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchView.clearFocus();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                if (timer != null) {
                    timer.cancel();
                }

                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                searchWork(s);
                            }
                        });
                    }
                }, 600);

                return false;
            }
        });
        searchView.setOnCloseListener(() -> {
            timer = null;
            options.remove("search_keyword");
            return false;
        });

        return true;
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
    public void onBackPressed() {

        if (searchView != null && !searchView.isIconified()) {
            searchView.onActionViewCollapsed();
            return;
        }

        super.onBackPressed();
    }

    @Override
    public void showLoading() {
        pbView.setVisibility(View.VISIBLE);
        clDataEmpty.setVisibility(View.GONE);

        if (swipeRefreshLayout.isRefreshing()) contentLoadingProgressBar.hide();
    }

    @Override
    public void showContent() {
        resetRefreshingStatus();
        pbView.setVisibility(View.GONE);
        clDataEmpty.setVisibility(View.GONE);
    }

    @Override
    public void showDataEmpty() {
        resetRefreshingStatus();
        adapter().clear();
        pbView.setVisibility(View.GONE);
        clDataEmpty.setVisibility(View.VISIBLE);
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
    public void renderItems(GenericListResponseModel<T> items, int page) {
        adapter().removeLoadingFooter();
        presenter().setIsLoading(false);
        if (page == 1) {
            adapter().setItems(items.getItems());
        } else {
            adapter().addAll(items.getItems());
        }

        if (presenter().getCurrentPage() >= items.getMetaModel().getTotalPage()) {
            presenter().setIsLastPage(true);
        } else {
            adapter().addLoadingFooter();
            presenter().setIsLastPage(false);
        }
    }

    @Override
    public void dismiss() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter().resume();
    }

    @Override
    protected void onPause() {
        presenter().pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        presenter().destroy();
        super.onDestroy();
    }

    @Override
    public void refreshRv() {
        adapter().setItemCheckedPosition(-1);
        adapter().clearSelectedItems();
        etQuantity.setText("");
        if (isSearching()) {
            searchWork(searchView.getQuery().toString());
        } else {
            presenter().refreshData();
        }
    }

    @Override
    public void resetRefreshingStatus() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
            contentLoadingProgressBar.show();
        }
    }

    @Override
    public T getSelectedItem() {
        if (adapter().getSelectedItems().size() > 0) {
            return adapter().getSelectedItems().get(0);
        } else {
            return null;
        }
    }

    @Override
    public HashMap<String, String> getOptions() {
        return options;
    }

    @Override
    public void clearEtQuantity() {
        etQuantity.setText("");
        etNotes.setText(null);
    }

    @OnClick(R.id.et_worker_work_date)
    void showDatePicker() {
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                String date = indoDateFormat.format(calendar.getTime());
                etDate.setText(date);

                tilDate.setErrorEnabled(false);
                if (calendar.after(Calendar.getInstance())) {
//                    showSnackBar(WorkflowUtils.SNACK_BAR_ERROR, strDateLaterWarning);
                    tilDate.setError(strDateLaterWarning);
                    tilDate.setErrorEnabled(true);
                }
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, dateSetListener, mYear, mMonth,mDay);
        datePickerDialog.show();
    }

    @OnClick(R.id.btn_assign_worker_work)
    void saveWorks() {
        if (getSelectedItem() == null) {
            showSnackBar(WorkflowUtils.SNACK_BAR_ERROR, strNothingChosen);
            return;
        }
        if (tilDate.isErrorEnabled()) {
            return;
        }
        if (tilQuantity.isErrorEnabled()) {
            return;
        }
        if (etQuantity.getText().toString().isEmpty()) {
            tilQuantity.setErrorEnabled(true);
            tilQuantity.setError(strFieldRequired);
            return;
        } else if (Integer.valueOf(etQuantity.getText().toString()) == 0) {
            tilQuantity.setErrorEnabled(true);
            tilQuantity.setError(strQuantityTooSmall);
            return;
        }
        postWork(getSelectedItem());
    }

    private void initAdapter() {
        recyclerView.setAdapter(adapter());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(verticalSingleColumnDecoration);
        adapter().setListener(new OnRecyclerObjectClickListener<T>() {
            @Override
            public void onItemClicked(int position, T item) {
                toggleItemChecked(position, item);
            }

            @Override
            public void onItemLongClick(int position, T item) {

            }

            @Override
            public void onItemSelected(int position, T item) {

            }
        });
        recyclerView.addOnScrollListener(new WorkflowRvOnScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                presenter().setIsLoading(true);
                presenter().incrementCurrentPage();
                presenter().getWorks();
            }

            @Override
            public boolean isLastPage() {
                return presenter().isLastPage();
            }

            @Override
            public boolean isLoading() {
                return presenter().isLoading();
            }
        });
    }

    private void searchWork(String searchKeyword) {
        showLoading();
        presenter().setCurrentPage(1);

        if (searchKeyword.length() != 0) {
            options.put("search_keyword", searchKeyword);
        } else {
            options.remove("search_keyword");
        }

        presenter().getWorks();
    }

//    private void setFilterBadgeCount(String count) {
//        if (menuItemFilter != null) {
//            LayerDrawable icon = (LayerDrawable) menuItemFilter.getIcon();
//            CountDrawable badge;
//            // Reuse drawable if possible
//            Drawable reuse = icon.findDrawableByLayerId(R.id.ic_action_filter_count);
//            if (reuse != null && reuse instanceof CountDrawable) {
//                badge = (CountDrawable) reuse;
//            } else {
//                badge = new CountDrawable(this);
//            }
//
//            badge.setCount(count);
//            icon.mutate();
//            icon.setDrawableByLayerId(R.id.ic_action_filter_count, badge);
//
//        }
//    }

    private void initFilterStatus() {
        String jsonQueryModel = sharedPreferences.getString(filterPrefKey(), null);
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        startCal.add(Calendar.DAY_OF_MONTH, -7);
        if (jsonQueryModel != null) {
            java.lang.reflect.Type type = new TypeToken<HashMap<String, String>>(){}.getType();
            HashMap<String, String> hashMap = new Gson().fromJson(jsonQueryModel, type);
            options.putAll(hashMap);
//            options.put("start_date", WorkflowUtils.apiSimpleDateFormat.format(startCal.getTime()));
//            options.put("end_date", WorkflowUtils.apiSimpleDateFormat.format(endCal.getTime()));
        } else {
            options.put("start_date", WorkflowUtils.apiSimpleDateFormat.format(startCal.getTime()));
            options.put("end_date", WorkflowUtils.apiSimpleDateFormat.format(endCal.getTime()));
            options.put("sort_by", "spk_no");
            options.put("sort_direction", "asc");
        }
        setTvFilterStats(options);
    }

    private void invalidateData() {
        showLoading();
        setTvFilterStats(options);
        presenter().setCurrentPage(1);
        presenter().getWorks();
    }

    private void setTvFilterStats(Map<String, String> options) {
        StringBuilder sortBuilder = new StringBuilder();
        sortBuilder.append(WorkflowUtils.renderSort(this, options.get("sort_by")));
        sortBuilder.append(String.format(" (%s)",
                WorkflowUtils.renderSort(this, options.get("sort_direction"))));

        if (options.get("start_date") != null && options.get("end_date") != null) {
            StringBuilder filterBuilder = new StringBuilder();
            filterBuilder.append(WorkflowUtils.formatSimpleDateToIndoDayMonth(options.get("start_date")));
            filterBuilder.append(" - ");
            filterBuilder.append(WorkflowUtils.formatSimpleDateToIndoDayMonth(options.get("end_date")));

            tvFilterStats.setVisibility(View.VISIBLE);
            tvFilterStats.setText(filterBuilder);
            tvSortStats.setText(sortBuilder);
        } else {
            tvFilterStats.setVisibility(View.GONE);
            tvSortStats.setText(sortBuilder);
        }
    }

    private boolean isSearching() {
        return searchView != null && !searchView.isIconified() && !searchView.getQuery().toString().isEmpty();
    }

    private void initDropdown(List<String> pos) {
        List<ListModel> positions = new ArrayList<>();
        for (String p : pos) {
            positions.add(new ListModel(p, WorkflowUtils.getRenderedPosition(this, p)));
            Timber.d("mPosition: %s", p);
        }
        actvPosition.setAdapter(new ArrayAdapter<ListModel>(this, android.R.layout.simple_spinner_dropdown_item, positions));
        actvPosition.setOnItemClickListener((parent, view, position, id) -> {
            presenter().setWorkerPos(((ListModel) parent.getItemAtPosition(position)).getId());
            refreshRv();
        });
        actvPosition.setText(WorkflowUtils.getRenderedPosition(this, mWorkerModel.getPositions().get(0)), false);
    }
}

//package com.workflow.presentation.view.activities;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Build;
//import android.os.Bundle;
//import android.text.InputType;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.view.ActionMode;
//import androidx.appcompat.widget.AppCompatTextView;
//import androidx.appcompat.widget.SearchView;
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.core.widget.ContentLoadingProgressBar;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//import androidx.viewpager2.widget.ViewPager2;
//
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.snackbar.Snackbar;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.workflow.R;
//import com.workflow.WorkflowApplication;
//import com.workflow.data.model.GenericItemPaginationModel;
//import com.workflow.data.model.ListModel;
//import com.workflow.data.model.WorkerModel;
//import com.workflow.data.model.WorkerWorkModel;
//import com.workflow.presentation.di.components.DaggerWorkerDetailComponent;
//import com.workflow.presentation.di.components.WorkerDetailComponent;
//import com.workflow.presentation.di.modules.ContextModule;
//import com.workflow.presentation.di.modules.WorkerDetailModule;
//import com.workflow.presentation.navigation.Navigator;
//import com.workflow.presentation.presenter.WorkerDetailPresenter;
//import com.workflow.presentation.view.WorkerDetailView;
//import com.workflow.presentation.view.adapters.OnRecyclerObjectClickListener;
//import com.workflow.presentation.view.adapters.WorkerWorkAdapter;
//import com.workflow.presentation.view.adapters.WorkerWorkCollectionAdapter;
//import com.workflow.presentation.view.adapters.WorkflowRvOnScrollListener;
//import com.workflow.presentation.view.fragments.ConfirmDeleteDialogFragment;
//import com.workflow.presentation.view.fragments.FilterSortAssignedWorkDialogFragment;
//import com.workflow.presentation.view.fragments.SpinnerDialogFragment;
//import com.workflow.presentation.view.itemdecoration.VerticalSingleColumnDecoration;
//import com.workflow.utils.ConnectionUtil;
//import com.workflow.utils.Positions;
//import com.workflow.utils.Roles;
//import com.workflow.utils.WorkflowUtils;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import javax.inject.Inject;
//
//import butterknife.BindString;
//import butterknife.BindView;
//import butterknife.OnClick;
//import timber.log.Timber;
//
//import static com.workflow.presentation.navigation.Navigator.PARCELABLE_WORKER_MODEL;
//import static com.workflow.utils.PreferenceUtils.PREF_WORK_DONE_FILTER_STATUS;
//import static com.workflow.utils.PreferenceUtils.PREF_ROLE;
//
//public class WorkerDetailActivityBackup extends BaseActivity {
////    @BindView(R.id.progress_bar_worker_detail_content) ConstraintLayout pbContent;
//    @BindView(R.id.tv_worker_detail_name) TextView tvHeaderName;
//    @BindView(R.id.tv_worker_detail_position) TextView tvHeaderPosition;
//    //    @BindView(R.id.rv_worker_detail_work_list) RecyclerView recyclerView;
//    @BindView(R.id.layout_worker_detail_data_empty) ConstraintLayout layoutDataEmpty;
//    @BindView(R.id.tv_assigned_work_filter_stats) AppCompatTextView tvFilterStats;
//    @BindView(R.id.fab_worker_detail_assign_work) FloatingActionButton fabAssignWork;
//    //    @BindView(R.id.srl_worker_detail) SwipeRefreshLayout swipeRefreshLayout;
//    @BindView(R.id.clpb_progress_bar) ContentLoadingProgressBar contentLoadingProgressBar;
//    @BindView(R.id.tv_assigned_work_sort_stats) TextView tvSortStats;
//    @BindView(R.id.vp_worker_detail) ViewPager2 vpWorkerDetail;
//
//    @BindString(R.string.worker_list_name) String strHeaderName;
//    @BindString(R.string.worker_list_position) String strHeaderPosition;
//    @BindString(R.string.action_mode_item_chosen) String strItemChosen;
//    @BindString(R.string.position_drawer) String strDrawer;
//    @BindString(R.string.position_sewer) String strSewer;
//    @BindString(R.string.position_assembler) String strAssembler;
//    @BindString(R.string.position_sole_stitcher) String strSoleStitcher;
//    @BindString(R.string.position_lining_drawer) String strLiningDrawer;
//    @BindString(R.string.position_insole_stitcher) String strInsoleStitcher;
//    @BindString(R.string.no_connection) String strNoConnection;
//    @BindString(R.string.search_work) String strSearchHint;
//    @BindString(R.string.filter_status) String strFilterStatus;
//    @BindString(R.string.filter_sort_by_date) String strFilterCreatedAt;
//    @BindString(R.string.filter_sort_by_done_at) String strFilterDoneAt;
//    @BindString(R.string.unauthorized_unless_admin_work) String strUnauthAdminPrice;
//
//    @Inject WorkerWorkAdapter adapter;
//    @Inject LinearLayoutManager linearLayoutManager;
//    @Inject VerticalSingleColumnDecoration verticalSingleColumnDecoration;
//    @Inject WorkerDetailPresenter presenter;
//    @Inject Navigator navigator;
//    @Inject SharedPreferences sharedPreferences;
//
//    private WorkerDetailComponent component;
//    private WorkerModel mWorkerModel;
//    private boolean isActionMode = false;
//    private SearchView searchView;
//
//    private ActionMode actionMode = null;
//    private Timer timer = null;
//
//    private HashMap<String, String> options = new HashMap<>();
//
//    @Override
//    int setView() {
//        return R.layout.activity_worker_detail;
//    }
//
//    @Override
//    void initDagger() {
//        mWorkerModel = getIntent().getParcelableExtra(PARCELABLE_WORKER_MODEL);
//        Integer workerId = (mWorkerModel == null) ? null : mWorkerModel.getId();
//
//        if (component == null) {
//            component = DaggerWorkerDetailComponent.builder()
//                    .applicationComponent(((WorkflowApplication) getApplication()).getApplicationComponent())
//                    .workerDetailModule(new WorkerDetailModule(this, workerId))
//                    .contextModule(new ContextModule(this))
//                    .build();
//        }
//        component.inject(this);
//    }
//
//    @Override
//    void afterViewCreated(Bundle savedInstanceState) {
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        if (sharedPreferences.getString(PREF_ROLE, "").equals(Roles.ADMIN_PRICE)) {
//            fabAssignWork.hide();
//        } else {
//            fabAssignWork.show();
//        }
//
//        initHeader(mWorkerModel);
////        initPositions();
//        initFilterStatus();
////        initRecyclerView();
////        swipeRefreshLayout.setOnRefreshListener(() -> {
////            if (isSearching()) searchWork(searchView.getQuery().toString());
////            else onResume();
////        });
//        initViewPager();
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.options_menu, menu);
//        menu.findItem(R.id.action_filter).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                ArrayList<String> positionArrayList = new ArrayList<>(mWorkerModel.getPositions());
//
//                FilterSortAssignedWorkDialogFragment filterDialog =
//                        FilterSortAssignedWorkDialogFragment.newInstance(options, positionArrayList);
//                filterDialog.show(getSupportFragmentManager(), "FilterWorkAssignDialog");
//                filterDialog.setListener(new FilterSortAssignedWorkDialogFragment.AssignedWorkFilterListener() {
//                    @Override
//                    public void onApplyClick(HashMap<String, String> filterState) {
//                        options.clear();
//                        options.putAll(filterState);
//                        //remove these so they are not saved to pref
//                        filterState.remove("start_date");
//                        filterState.remove("end_date");
//                        filterState.remove("position");
//
//                        sharedPreferences.edit().putString(
//                                PREF_WORK_DONE_FILTER_STATUS, new Gson().toJson(filterState)).apply();
//                        invalidateData();
//                    }
//                });
//                return true;
//            }
//        });
//        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
//        searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
//        searchView.setQueryHint(strSearchHint);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                searchView.clearFocus();
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//
//                if (timer != null) {
//                    timer.cancel();
//                }
//
//                timer = new Timer();
//                timer.schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                searchWork(s);
//                            }
//                        });
//                    }
//                }, 600);
//
//                return false;
//            }
//        });
//        searchView.setOnCloseListener(() -> {
//            timer = null;
//            return false;
//        });
//
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    @Override
//    public void onBackPressed() {
//
//        if (searchView != null && !searchView.isIconified()) {
//            searchView.onActionViewCollapsed();
//            return;
//        }
//
//        super.onBackPressed();
//    }
//
//    @Override
//    public void showLoading() {
//        layoutDataEmpty.setVisibility(View.GONE);
//        pbContent.setVisibility(View.VISIBLE);
//
//        // original activity
////        if (swipeRefreshLayout.isRefreshing()) contentLoadingProgressBar.hide();
//    }
//
//    @Override
//    public void showContent() {
//        resetRefreshingStatus();
//        layoutDataEmpty.setVisibility(View.GONE);
//        pbContent.setVisibility(View.GONE);
//    }
//
//    @Override
//    public void showDataEmpty() {
//        resetRefreshingStatus();
//        adapter.clear();
//        layoutDataEmpty.setVisibility(View.VISIBLE);
//        pbContent.setVisibility(View.GONE);
//    }
//
//    @Override
//    public void resetRefreshingStatus() {
//        // original activity
////        if (swipeRefreshLayout.isRefreshing()) {
////            swipeRefreshLayout.setRefreshing(false);
////            contentLoadingProgressBar.show();
////        }
//    }
//
//    @Override
//    public HashMap<String, String> getOptions() {
//        return options;
//    }
//
//    @Override
//    public int adapterSize() {
//        return adapter.getItemCount();
//    }
//
//    @Override
//    public void showSnackBar(int type, String msg) {
//        Snackbar snackbar = WorkflowUtils.generateSnackBar(this, type, msg);
//        snackbar.show();
//    }
//
//    @Override
//    public boolean isConnected() {
//        if (!ConnectionUtil.isOnline(this)) {
//            // original activity
////            if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
//            showSnackBar(WorkflowUtils.SNACK_BAR_WARNING, strNoConnection);
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    @Override
//    public void renderItems(GenericItemPaginationModel<List<WorkerWorkModel>> items, int page) {
//        adapter.removeLoadingFooter();
//        presenter.setIsLoading(false);
//        if (page == 1) {
//            adapter.setItems(items.getItems());
//        } else {
//            adapter.addAll(items.getItems());
//        }
//
//        if (presenter.getCurrentPage() >= items.getPaginationModel().getTotalPage()) {
//            presenter.setIsLastPage(true);
//        } else {
//            adapter.addLoadingFooter();
//            presenter.setIsLastPage(false);
//        }
//    }
//
//    @Override
//    public void dismiss() {
//        finish();
//    }
//
//    @Override
//    public void adapterDeleteItem(List<WorkerWorkModel> params) {
//        adapter.remove(params.get(0));
//        stopActionMode();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        presenter.resume();
//        adapter.setCountSelectedItems(0);
//    }
//
//    @Override
//    protected void onPause() {
//        presenter.pause();
//        super.onPause();
//    }
//
//    @Override
//    protected void onDestroy() {
//        presenter.destroy();
////        sharedPreferences.edit().remove(PREF_WORK_DONE_FILTER_STATUS).apply();
//        super.onDestroy();
//    }
//
//    @OnClick(R.id.fab_worker_detail_assign_work)
//    void navigateToAssignWork() {
//
////        if (positions.size() > 1) {
////            SpinnerDialogFragment dialog = SpinnerDialogFragment.newInstance(positions);
////            dialog.show(getSupportFragmentManager(), "SelectPositionDialog");
////            dialog.setDialogListener(new SpinnerDialogFragment.DialogListener() {
////                @Override
////                public void onItemClick(int position, ListModel listModel) {
////                    navigator.navigateToAssignWork(
////                            dialog.getContext(), mWorkerModel.getId(), listModel.getId(), mWorkerModel.getName());
////                    dialog.dismiss();
////                    Timber.d("mWorkerPosition: %s", listModel.getId());
////                }
////            });
////        } else {
//        adapter.clear();
//        navigator.navigateToAssignWork(
//                this, mWorkerModel);
////        }
//    }
//
//    public void showActionMode() {
//        actionMode = startSupportActionMode(new ActionMode.Callback() {
//            @Override
//            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
//                getMenuInflater().inflate(R.menu.cab_options_menu, menu);
//                isActionMode = true;
//                return true;
//            }
//
//            @Override
//            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//                    // This is to highlight the status bar and distinguish it from the action bar,
//                    // as the action bar while in the action mode is colored app_green_dark
//                    getWindow().setStatusBarColor(getResources().getColor(R.color.secondaryDarkColor));
//                }
//
//                //hide edit in this page
//                MenuItem edit = menu.findItem(R.id.action_edit);
//                edit.setVisible(false);
//                edit.setEnabled(false);
//                return false;
//            }
//
//            @Override
//            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
//                switch (menuItem.getItemId()) {
//                    case R.id.action_delete:
//                        showDeleteConfirmDialog();
//                        return true;
//                    default:
//                        return false;
//                }
//            }
//
//            @Override
//            public void onDestroyActionMode(ActionMode actionMode) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//                    // This is to highlight the status bar and distinguish it from the action bar,
//                    // as the action bar while in the action mode is colored app_green_dark
//                    getWindow().setStatusBarColor(getResources().getColor(R.color.primaryDarkColor));
//                }
//                onResume();
//                isActionMode = false;
//            }
//        });
//    }
//
//    public boolean isActionMode() {
//        return isActionMode;
//    }
//
//    public void setActionModeTitle(int itemCount) {
//        if (actionMode != null) {
//            actionMode.setTitle(String.format(strItemChosen, itemCount));
//            Timber.d("%d barang terpilih", itemCount);
//        }
//    }
//
//    public void stopActionMode() {
//        if (actionMode != null) {
//            actionMode.finish();
//            actionMode = null;
//        }
//    }
//
//    private boolean isSearching() {
//        return searchView != null && !searchView.isIconified() && !searchView.getQuery().toString().isEmpty();
//    }
//
//    private void showDeleteConfirmDialog() {
//        ConfirmDeleteDialogFragment confirmDelete = ConfirmDeleteDialogFragment.newInstance(adapter.getCountSelectedItems());
//        confirmDelete.setListener(new ConfirmDeleteDialogFragment.ConfirmDeleteListener() {
//            @Override
//            public void onConfirmClick() {
//                presenter.deleteWorkerWork(adapter.getSelectedItems());
//                clearUserActionState();
//            }
//
//            @Override
//            public void onCancelClick() {
//                clearUserActionState();
//            }
//        });
//        confirmDelete.show(getSupportFragmentManager(), "ConfirmDeleteWorkerWorkDialogFragment");
//    }
//
//    private void clearUserActionState() {
//        stopActionMode();
//        onResume();
//    }
//
//    private void initHeader(WorkerModel param) {
//        tvHeaderName.setText(param.getName());
//
//        List<String> renderPositions = new ArrayList<>();
//        for (String pos : param.getPositions()) {
//            renderPositions.add(WorkflowUtils.getRenderedPosition(this, pos));
//        }
//        tvHeaderPosition.setText(WorkflowUtils.toCSV(renderPositions));
//    }
//
//    // original activity
////    private void initRecyclerView() {
////        recyclerView.setAdapter(adapter);
////        recyclerView.setLayoutManager(linearLayoutManager);
////        recyclerView.addItemDecoration(verticalSingleColumnDecoration);
////        adapter.setListener(new OnRecyclerObjectClickListener<WorkerWorkModel>() {
////            @Override
////            public void onItemClicked(int position, WorkerWorkModel item) {
////
////            }
////
////            @Override
////            public void onItemLongClick(int position, WorkerWorkModel item) {
////                if (sharedPreferences.getString(PREF_ROLE, "").equals(Roles.ADMIN_PRICE)) {
////                    Toast.makeText(WorkerDetailActivity.this, strUnauthAdminPrice, Toast.LENGTH_SHORT).show();
////                    return;
////                }
////
////                if (!isActionMode()) {
////                    adapter.clearSelectedItems();
////                    showActionMode();
////                }
////
////                adapter.toggleItemSelected(position, item);
////
////                if (adapter.getCountSelectedItems() == 0) {
////                    stopActionMode();
////                } else if (adapter.getCountSelectedItems() == 1) {
////                    setActionModeTitle(adapter.getCountSelectedItems());
////                } else if (adapter.getCountSelectedItems() > 1) {
////                    adapter.toggleItemSelected(position, item);
////                }
////            }
////
////            @Override
////            public void onItemSelected(int position, WorkerWorkModel item) {
////                if (isActionMode) {
////                    adapter.toggleItemSelected(position, item);
////
////                    if (adapter.getCountSelectedItems() == 0) {
////                        stopActionMode();
////                    } else if (adapter.getCountSelectedItems() == 1) {
////                        setActionModeTitle(adapter.getCountSelectedItems());
////                    } else if (adapter.getCountSelectedItems() > 1) {
////                        adapter.toggleItemSelected(position, item);
////                    }
////                }
////            }
////        });
////
////        recyclerView.addOnScrollListener(new WorkflowRvOnScrollListener(linearLayoutManager) {
////            @Override
////            protected void loadMoreItems() {
////                presenter.setIsLoading(true);
////                presenter.incrementCurrentPage();
////                presenter.getWorks();
////            }
////
////            @Override
////            public boolean isLastPage() {
////                return presenter.isLastPage();
////            }
////
////            @Override
////            public boolean isLoading() {
////                return presenter.isLoading();
////            }
////        });
////    }
//
////    private void initPositions() {
////        for (String pos : mWorkerModel.getPositions()) {
////            String readablePos;
////            switch (pos) {
////                case "drawer":
////                    readablePos = strDrawer;
////                    break;
////                case "sewer":
////                    readablePos = strSewer;
////                    break;
////                case "assembler":
////                    readablePos = strAssembler;
////                    break;
////                case "sole_stitcher":
////                    readablePos = strSoleStitcher;
////                    break;
////                case Positions.LINING_DRAWER:
////                    readablePos = strLiningDrawer;
////                    break;
////                case Positions.INSOLE_STITCHER:
////                    readablePos = strInsoleStitcher;
////                    break;
////                default:
////                    readablePos = null;
////                    break;
////            }
////            positions.add(new ListModel(pos, readablePos));
////        }
////    }
//
//    private void initFilterStatus() {
//        String optionsJson = sharedPreferences.getString(PREF_WORK_DONE_FILTER_STATUS, null);
//
//        Calendar startCal = Calendar.getInstance();
//        Calendar endCal = Calendar.getInstance();
//        startCal.add(Calendar.DAY_OF_MONTH, -7);
//
//        if (optionsJson != null) {
//            java.lang.reflect.Type type = new TypeToken<HashMap<String, String>>(){}.getType();
//            HashMap<String, String> hashMap = new Gson().fromJson(optionsJson, type);
//
//            options.putAll(hashMap);
//            options.put("start_date", WorkflowUtils.serverDate.format(startCal.getTime()));
//            options.put("end_date", WorkflowUtils.serverDate.format(endCal.getTime()));
//        } else {
//            options.put("date_filter_type", "done_at");
//            options.put("start_date", WorkflowUtils.serverDate.format(startCal.getTime()));
//            options.put("end_date", WorkflowUtils.serverDate.format(endCal.getTime()));
//            options.put("sort_by", "spk_no");
//            options.put("sort_direction", "asc");
//        }
//
////        options.put("position", mWorkerModel.getPositions().get(0));
//
//        setTvFilterStatus(options);
//    }
//
//    private void setTvFilterStatus(HashMap<String, String> options) {
//        StringBuilder sortBuilder = new StringBuilder();
//        sortBuilder.append(WorkflowUtils.renderSort(this, options.get("sort_by")));
//        sortBuilder.append(" ");
//        sortBuilder.append(String.format("(%s)", WorkflowUtils.renderSort(this, options.get("sort_direction"))));
//
//        StringBuilder filterBuilder = new StringBuilder();
//        String dateFilterType;
//
//        switch (options.get("date_filter_type")) {
//            case "created_at":
//                dateFilterType = strFilterCreatedAt;
//                break;
//            case "done_at":
//                dateFilterType = strFilterDoneAt;
//                break;
//            default:
//                dateFilterType = strFilterCreatedAt;
//                break;
//        }
//
//        filterBuilder.append(dateFilterType);
//        filterBuilder.append(" | ");
//        filterBuilder.append(WorkflowUtils.formatSimpleDateToIndoDayMonth(options.get("start_date")));
//        filterBuilder.append(" - ");
//        filterBuilder.append(WorkflowUtils.formatSimpleDateToIndoDayMonth(options.get("end_date")));
//        filterBuilder.append(" | ");
//
//        if (options.get("position") != null) {
//            filterBuilder.append(WorkflowUtils.getRenderedPosition(this, options.get("position")));
//        }
//
////        tvFilterStats.setText(Html.fromHtml(String.format(strFilterStatus, filterBuilder, sortBuilder)));
//        tvFilterStats.setText(filterBuilder);
//        tvSortStats.setText(sortBuilder);
//    }
//
//    private void invalidateData() {
//        showLoading();
//        setTvFilterStatus(options);
//        presenter.setCurrentPage(1);
//        presenter.getWorks();
//    }
//
//    private void searchWork(String searchKeyword) {
//        showLoading();
//        presenter.setCurrentPage(1);
//        options.put("search_keyword", searchKeyword);
//        presenter.getWorks();
//    }
//
//    private void initViewPager() {
//        vpWorkerDetail.setAdapter(new WorkerWorkCollectionAdapter(this));
//    }
//
//    public static Intent getCallingIntent(Context context) {
//        return new Intent(context, WorkerDetailActivity.class);
//    }
//}

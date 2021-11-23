package com.workflow.presentation.view.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.workflow.R;
import com.workflow.WorkflowApplication;
import com.workflow.data.model.WorkerModel;
import com.workflow.presentation.di.components.DaggerWorkerDetailComponent;
import com.workflow.presentation.di.components.WorkerDetailComponent;
import com.workflow.presentation.di.modules.WorkerDetailModule;
import com.workflow.presentation.navigation.Navigator;
import com.workflow.presentation.presenter.WorkerDetailPresenter;
import com.workflow.presentation.view.WorkerDetailView;
import com.workflow.presentation.view.adapters.WorkerWorkCollectionAdapter;
import com.workflow.presentation.view.fragments.WorkAssignedFragment;
import com.workflow.presentation.view.fragments.WorkDoneFragment;
import com.workflow.presentation.view.fragments.WorkerWorkFragment;
import com.workflow.utils.ConnectionUtil;
import com.workflow.utils.Roles;
import com.workflow.utils.WorkflowUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.workflow.presentation.navigation.Navigator.PARCELABLE_WORKER_MODEL;
import static com.workflow.utils.PreferenceUtils.PREF_ROLE;

/**
 * Created by Michael on 01/07/19.
 */

public class WorkerDetailActivity extends BaseActivity implements WorkerDetailView {

    @BindView(R.id.tv_worker_detail_name) TextView tvHeaderName;
    @BindView(R.id.tv_worker_detail_position) TextView tvHeaderPosition;
    @BindView(R.id.tv_assigned_work_filter_stats) AppCompatTextView tvFilterStats;
    @BindView(R.id.fab_worker_detail_assign_work) FloatingActionButton fabAssignWork;
    @BindView(R.id.tv_assigned_work_sort_stats) TextView tvSortStats;
    @BindView(R.id.vp_worker_detail) ViewPager2 vpWorkerDetail;
    @BindView(R.id.tl_worker_detail) TabLayout tlWorkerDetail;

    @BindString(R.string.worker_list_name) String strHeaderName;
    @BindString(R.string.worker_list_position) String strHeaderPosition;
    @BindString(R.string.action_mode_item_chosen) String strItemChosen;
    @BindString(R.string.position_drawer) String strDrawer;
    @BindString(R.string.position_sewer) String strSewer;
    @BindString(R.string.position_assembler) String strAssembler;
    @BindString(R.string.position_sole_stitcher) String strSoleStitcher;
    @BindString(R.string.position_lining_drawer) String strLiningDrawer;
    @BindString(R.string.position_insole_stitcher) String strInsoleStitcher;
    @BindString(R.string.no_connection) String strNoConnection;
    @BindString(R.string.search_work) String strSearchHint;
    @BindString(R.string.filter_status) String strFilterStatus;
    @BindString(R.string.filter_sort_by_date) String strFilterCreatedAt;
    @BindString(R.string.filter_sort_by_assigned_at) String strFilterAssignedAt;
    @BindString(R.string.filter_sort_by_done_at) String strFilterDoneAt;
    @BindString(R.string.unauthorized_unless_admin_work) String strUnauthAdminPrice;
    @BindString(R.string.worker_detail_header_on_progress) String strHeaderOnProgress;
    @BindString(R.string.worker_detail_header_done) String strHeaderDone;

    @Inject WorkerDetailPresenter presenter;
    @Inject Navigator navigator;
    @Inject SharedPreferences sharedPreferences;
    @Inject WorkerWorkCollectionAdapter pagerAdapter;

    private WorkerDetailComponent component;
    private WorkerModel mWorkerModel;
    private SearchView searchView;

    private ActionMode actionMode = null;
    private Timer timer = null;
    private String searchKeyword;

    @Override
    int setView() {
        return R.layout.activity_worker_detail;
    }

    @Override
    void initDagger() {
        mWorkerModel = getIntent().getParcelableExtra(PARCELABLE_WORKER_MODEL);
        Integer workerId = (mWorkerModel == null) ? null : mWorkerModel.getId();

        if (component == null) {
            component = DaggerWorkerDetailComponent.builder()
                    .applicationComponent(((WorkflowApplication) getApplication()).getApplicationComponent())
                    .workerDetailModule(new WorkerDetailModule(this, workerId, this))
                    .build();
        }
        component.inject(this);
    }

    @Override
    void afterViewCreated(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.outline_arrow_back_ios_white_24);

//        if (sharedPreferences.getString(PREF_ROLE, "").equals(Roles.ADMIN_PRICE)) {
//            fabAssignWork.hide();
//        } else {
//            fabAssignWork.show();
//        }

        initHeader(mWorkerModel);
        initViewPager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        menu.findItem(R.id.action_filter).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                getCurrentFragment().showFilter(new ArrayList<>(mWorkerModel.getPositions()));

                return true;
            }
        });
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
        searchView.setQueryHint(strSearchHint);
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
                                WorkerDetailActivity.this.searchKeyword = s;
                                getCurrentFragment().searchWork();
                            }
                        });
                    }
                }, 600);

                return false;
            }
        });
        searchView.setOnCloseListener(() -> {
            timer = null;
            searchKeyword = null;
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
    }

    @Override
    public void showContent() {
    }

    @Override
    public void showSnackBar(int type, String msg) {
        Snackbar snackbar = WorkflowUtils.generateSnackBar(this, type, msg);
        snackbar.show();
    }

    @Override
    public boolean isConnected() {
        if (!ConnectionUtil.isOnline(this)) {
            // original activity
//            if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
            showSnackBar(WorkflowUtils.SNACK_BAR_WARNING, strNoConnection);
            return false;
        } else {
            return true;
        }
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

    @OnClick(R.id.fab_worker_detail_assign_work)
    void navigateToAssignWork() {
        getCurrentFragment().adapterClear();
        if (getCurrentFragment() instanceof WorkAssignedFragment) {
            navigator.navigateToAssignWork(this, mWorkerModel);
        } else if (getCurrentFragment() instanceof WorkDoneFragment) {
            navigator.navigateToDoneWork(this, mWorkerModel);
        }
    }

    public void showActionMode() {
        actionMode = startSupportActionMode(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                getMenuInflater().inflate(R.menu.cab_options_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    // This is to highlight the status bar and distinguish it from the action bar,
                    // as the action bar while in the action mode is colored app_green_dark
                    getWindow().setStatusBarColor(getResources().getColor(R.color.secondaryDarkColor));
                }

                //hide edit in this page
                MenuItem edit = menu.findItem(R.id.action_edit);
                edit.setVisible(false);
                edit.setEnabled(false);
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_delete:
                        getCurrentFragment().deleteWork();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    // This is to highlight the status bar and distinguish it from the action bar,
                    // as the action bar while in the action mode is colored app_green_dark
                    getWindow().setStatusBarColor(getResources().getColor(R.color.primaryDarkColor));
                }
                WorkerDetailActivity.this.actionMode = null;
                getCurrentFragment().invalidateData();
            }
        });
    }

    public boolean isActionMode() {
        return actionMode != null;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setActionModeTitle(int itemCount) {
        if (actionMode != null) {
            actionMode.setTitle(String.format(strItemChosen, itemCount));
            Timber.d("%d barang terpilih", itemCount);
        }
    }

    public void stopActionMode() {
        if (actionMode != null) {
            actionMode.finish();
            actionMode = null;
        }
    }

    public boolean isSearching() {
        return searchView != null && !searchView.isIconified() && !searchView.getQuery().toString().isEmpty();
    }

    public String getSearchQueryText() {
        return searchView.getQuery().toString();
    }

    private void initHeader(WorkerModel param) {
        tvHeaderName.setText(param.getName());

        List<String> renderPositions = new ArrayList<>();
        for (String pos : param.getPositions()) {
            renderPositions.add(WorkflowUtils.getRenderedPosition(this, pos));
        }
        tvHeaderPosition.setText(WorkflowUtils.toCSV(renderPositions));
    }

    public void setTvFilterStatus(Map<String, String> options) {
        StringBuilder sortBuilder = new StringBuilder();
        sortBuilder.append(WorkflowUtils.renderSort(this, options.get("sort_by")));
        sortBuilder.append(" ");
        sortBuilder.append(String.format("(%s)", WorkflowUtils.renderSort(this, options.get("sort_direction"))));

        StringBuilder filterBuilder = new StringBuilder();
        String dateFilterType = null;

        if (options.get("date_filter_type") != null) {
            switch (options.get("date_filter_type")) {
                case "created_at":
                    default:
                    dateFilterType = strFilterCreatedAt;
                    break;
                case "assigned_at":
                    dateFilterType = strFilterAssignedAt;
                    break;
                case "done_at":
                    dateFilterType = strFilterDoneAt;
                    break;
            }
        }

        filterBuilder.append(dateFilterType);
        filterBuilder.append(" | ");
        filterBuilder.append(WorkflowUtils.formatSimpleDateToIndoDayMonth(options.get("start_date")));
        filterBuilder.append(" - ");
        filterBuilder.append(WorkflowUtils.formatSimpleDateToIndoDayMonth(options.get("end_date")));

        if (options.get("position") != null) {
            filterBuilder.append(" | ");
            filterBuilder.append(WorkflowUtils.getRenderedPosition(this, options.get("position")));
        }

//        tvFilterStats.setText(Html.fromHtml(String.format(strFilterStatus, filterBuilder, sortBuilder)));
        tvFilterStats.setText(filterBuilder);
        tvSortStats.setText(sortBuilder);
    }

    private void initViewPager() {
//        vpWorkerDetail.setUserInputEnabled(false);
        vpWorkerDetail.setAdapter(pagerAdapter);
        String[] tabNames = {strHeaderOnProgress, strHeaderDone};
        new TabLayoutMediator(tlWorkerDetail, vpWorkerDetail, (tab, position) -> tab.setText(tabNames[position])).attach();
    }

    private WorkerWorkFragment getCurrentFragment() {
        return pagerAdapter.getCurrentFragment(vpWorkerDetail.getCurrentItem());
    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, WorkerDetailActivity.class);
    }
}

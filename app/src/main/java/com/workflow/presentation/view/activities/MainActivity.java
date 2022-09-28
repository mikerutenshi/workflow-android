package com.workflow.presentation.view.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.workflow.BuildConfig;
import com.workflow.R;
import com.workflow.WorkflowApplication;
import com.workflow.data.model.CurrentWorkListModel;
import com.workflow.data.model.GetWorkerQueryModel;
import com.workflow.data.model.ProductModel;
import com.workflow.data.model.WorkModel;
import com.workflow.data.model.WorkerModel;
import com.workflow.presentation.di.components.DaggerMainComponent;
import com.workflow.presentation.di.components.MainComponent;
import com.workflow.presentation.di.modules.MainActivityModule;
import com.workflow.presentation.navigation.Navigator;
import com.workflow.presentation.presenter.MainActivityPresenter;
import com.workflow.presentation.view.CountDrawable;
import com.workflow.presentation.view.MainActivityView;
import com.workflow.presentation.view.fragments.CurrentWorkListFragment;
import com.workflow.presentation.view.fragments.FilterSortWorkAssignDialogFragment;
import com.workflow.presentation.view.fragments.FilterSortWorkerDialogFragment;
import com.workflow.presentation.view.fragments.ProductListFragment;
import com.workflow.presentation.view.fragments.SalaryReportListFragment;
import com.workflow.presentation.view.fragments.WorkListFragment;
import com.workflow.presentation.view.fragments.WorkerListFragment;
import com.workflow.presentation.view.fragments.WorkerWorkReportFragment;
import com.workflow.utils.ConnectionUtil;
import com.workflow.utils.Roles;
import com.workflow.utils.WorkflowUtils;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.workflow.utils.PreferenceUtils.PREF_WORK_ASSIGNED_FILTER_STATUS;
import static com.workflow.utils.PreferenceUtils.PREF_WORK_DONE_FILTER_STATUS;
import static com.workflow.utils.PreferenceUtils.PREF_NAME;
import static com.workflow.utils.PreferenceUtils.PREF_REFRESH_TOKEN;
import static com.workflow.utils.PreferenceUtils.PREF_ROLE;
import static com.workflow.utils.PreferenceUtils.PREF_ACCESS_TOKEN;
import static com.workflow.utils.PreferenceUtils.PREF_USERNAME;
import static com.workflow.utils.PreferenceUtils.PREF_WORKER_FILTER_STATUS;
import static com.workflow.utils.PreferenceUtils.PREF_WORK_ASSIGN_FILTER_STATUS;
import static com.workflow.utils.PreferenceUtils.PREF_WORK_FILTER_STATUS;

public class MainActivity extends BaseActivity
        implements MainActivityView, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.main_layout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.tv_main_version) AppCompatTextView tvVersion;
    @BindView(R.id.progress_bar_main_activity) ConstraintLayout progressBar;

    @BindString(R.string.product_list_title) String strProductTitle;
    @BindString(R.string.worker_list_title) String strWorkerTitle;
    @BindString(R.string.work_list_title) String strWorkTitle;
    @BindString(R.string.report_title) String strReportTitle;
    @BindString(R.string.search_article_no) String strSearchArticleNo;
    @BindString(R.string.search_work) String strSearchWork;
    @BindString(R.string.search_worker) String strSearchWorker;
    @BindString(R.string.click_twice_to_close) String strClickTwiceClose;
    @BindString(R.string.action_mode_item_chosen) String strItemChosen;
    @BindString(R.string.my_role) String strMyRole;
    @BindString(R.string.no_connection) String strNoConnection;

    @Inject MainActivityPresenter presenter;
    @Inject Navigator navigator;
    @Inject SharedPreferences sharedPreferences;

    private static final String TAG_PRODUCT_LIST = "TAG_PRODUCT_LIST";
    private static final String TAG_WORKER_LIST = "TAG_WORKER_LIST";
    private static final String TAG_WORK_LIST = "TAG_WORK_LIST";
    private static final String TAG_REPORT= "TAG_REPORT";

    private MainComponent mainComponent;
    private MenuItem mMenuItemSearch;
    private MenuItem menuItemFilter;
    private boolean isDoubleBackToExitPressed = false;
    private boolean isActionMode = false;
    private ActionMode actionMode = null;
    private Timer timer = null;
    private SearchView mSearchView;
    private boolean isShowEdit = true;
    private Fragment currentFragment;

    @Override
    int setView() {
        return R.layout.activity_main;
    }

    @Override
    void initDagger() {
        if (mainComponent == null) {
            mainComponent = DaggerMainComponent.builder()
                    .applicationComponent(((WorkflowApplication) getApplication()).getApplicationComponent())
                    .mainActivityModule(new MainActivityModule(this))
                    .build();
        }
        mainComponent.inject(this);

    }

    @Override
    void afterViewCreated(Bundle savedInstanceState) {
        Timber.d("MainActivity_AfterViewCreated");
        tvVersion.setText(BuildConfig.VERSION_NAME);
        String prefRole = null;

        if (sharedPreferences.getString(PREF_ACCESS_TOKEN, null) == null) {
            navigator.navigateToAuth(this);
            finish();
            return;
        } else {
            prefRole = sharedPreferences.getString(PREF_ROLE, "");

            NavigationView navigationView = findViewById(R.id.nav_view);

            switch (prefRole) {
                case Roles.ADMIN_PRICE:
                case Roles.SUPER_USER:
                    navigationView.inflateMenu(R.menu.activity_main_drawer);
                    break;
                case Roles.ADMIN_WORK:
                case Roles.ADMIN_QA:
                default:
                    navigationView.inflateMenu(R.menu.activity_main_drawer_secondary);
                    break;
            }

            View headerView = navigationView.getHeaderView(0);
            TextView tvHeaderName = headerView.findViewById(R.id.tv_header_name);
            TextView tvHeaderRole = headerView.findViewById(R.id.tv_header_role);
            String prefName = sharedPreferences.getString(PREF_NAME, null);
            tvHeaderName.setText(prefName);
            tvHeaderRole.setText(WorkflowUtils.renderRole(this, prefRole != null ? prefRole : ""));
        }

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        setNavigationDefaultItem(prefRole);
    }

    @Override
    public void onBackPressed() {

        if (mSearchView != null && !mSearchView.isIconified()) {
            mSearchView.onActionViewCollapsed();
            return;
        }

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (isDoubleBackToExitPressed) {
            super.onBackPressed();
        } else {
            isDoubleBackToExitPressed = true;
            Toast.makeText(this, strClickTwiceClose, Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isDoubleBackToExitPressed = false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Timber.d("MainActivity_OnCreateOptionsMenu");
        // Inflate the menu; this adds items to the action bar if it is present.
        String prefRole = sharedPreferences.getString(PREF_ROLE, "");
        getMenuInflater().inflate(R.menu.options_menu, menu);

        mMenuItemSearch = menu.findItem(R.id.action_search);
        menuItemFilter = menu.findItem(R.id.action_filter);

        mSearchView = (SearchView) mMenuItemSearch.getActionView();

        //set default toolbar status
        if (prefRole.equals(Roles.ADMIN_WORK) || prefRole.equals(Roles.ADMIN_QA)) {
            mSearchView.setInputType(InputType.TYPE_CLASS_TEXT);
            mSearchView.setQueryHint(strSearchWorker);
        } else if (prefRole.equals(Roles.SUPER_USER) || prefRole.equals(Roles.ADMIN_PRICE)) {
            mSearchView.setInputType(InputType.TYPE_CLASS_NUMBER);
            mSearchView.setQueryHint(strSearchWork);
        }

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mSearchView.clearFocus();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Timber.d("memberTimer: %s", timer);
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
                                searchByFilter(s);
                            }
                        });
                    }
                }, 600);

                return false;
            }
        });
        mSearchView.setOnCloseListener(() -> {
            timer = null;
            return false;
        });

        menuItemFilter.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (currentFragment != null && currentFragment.getTag() != null) {
                    switch (currentFragment.getTag()) {
//                        case TAG_PRODUCT_LIST:
//                            navigator.navigateToProductCreate(this);
//                            break;
                        case TAG_WORKER_LIST:
                            FilterSortWorkerDialogFragment filterDialog =
                                    FilterSortWorkerDialogFragment.newInstance(
                                            ((WorkerListFragment) currentFragment).compileFilters());
                            filterDialog.show(getSupportFragmentManager(), "FilterSortWorkerDialog");
                            filterDialog.setListener(filterState -> {
                                Gson gson = new Gson();
                                sharedPreferences.edit().putString(
                                        PREF_WORKER_FILTER_STATUS, gson.toJson(filterState))
                                        .apply();
                                ((WorkerListFragment) currentFragment).invalidateData(filterState);
//                                    setFilterBadgeCount(String.valueOf(filterState.getPositions().size()));
                            });
                            return true;
                        case TAG_WORK_LIST:
                            FilterSortWorkAssignDialogFragment dialog = FilterSortWorkAssignDialogFragment.newInstance(
                                    ((CurrentWorkListFragment) currentFragment).getOptions());
                            dialog.setListener(filterState -> {
                                Gson gson = new Gson();
                                sharedPreferences.edit().putString(
                                        PREF_WORK_FILTER_STATUS, gson.toJson(filterState))
                                        .apply();
                                ((CurrentWorkListFragment) currentFragment).filterHandle(filterState);

                            });
                            dialog.show(getSupportFragmentManager(), "FilterSortWorkDialog");
                            return true;
                        default:
                            return true;
                    }
                } else {
                    return true;
                }
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        String prefRole = sharedPreferences.getString(PREF_ROLE, "");

        switch (id) {
            case R.id.nav_product: {
                toolbar.setTitle(strProductTitle);
                navigateTo(new ProductListFragment(), TAG_PRODUCT_LIST);

                if (mSearchView != null) {
                    mSearchView.setInputType(InputType.TYPE_CLASS_NUMBER);
                    mSearchView.setQueryHint(strSearchArticleNo);
                }
                if (mMenuItemSearch != null) {
                    mMenuItemSearch.setVisible(true);
                }
                if (menuItemFilter != null) {
                    menuItemFilter.setVisible(false);
                }

//                if (prefRole.equals(Roles.SUPER_USER)) {
                    fab.show();
//                } else {
//                    fab.hide();
//                }
                break;
            }
            case R.id.nav_worker: {
                toolbar.setTitle(strWorkerTitle);
                navigateTo(new WorkerListFragment(), TAG_WORKER_LIST);

                if (mSearchView != null) {
                    mSearchView.setInputType(InputType.TYPE_CLASS_TEXT);
                    mSearchView.setQueryHint(strSearchWorker);
                }

                if (mMenuItemSearch != null) {
                    mMenuItemSearch.setVisible(true);
                }
                if (menuItemFilter != null) {
                    menuItemFilter.setVisible(true);
                }

//                if (prefRole.equals(Roles.ADMIN_PRICE)
//                    || prefRole.equals(Roles.ADMIN_QA)) {
//                    fab.hide();
//                } else {
                    fab.show();
//                }
                break;
            }
            case R.id.nav_report: {
                toolbar.setTitle(strReportTitle);
                navigateTo(new SalaryReportListFragment(), TAG_REPORT);

                if (mMenuItemSearch != null) {
                    mMenuItemSearch.setVisible(false);
                }
                if (menuItemFilter != null) {
                    menuItemFilter.setVisible(false);
                }
                fab.hide();
                break;
            }
            case R.id.nav_work:
            default: {
                toolbar.setTitle(strWorkTitle);
                navigateTo(new CurrentWorkListFragment(), TAG_WORK_LIST);

                if (mSearchView != null) {
                    mSearchView.setInputType(InputType.TYPE_CLASS_NUMBER);
                    mSearchView.setQueryHint(strSearchWork);
                }

                if (mMenuItemSearch != null) {
                    mMenuItemSearch.setVisible(true);
                }
                if (menuItemFilter != null) {
                    menuItemFilter.setVisible(true);
                }

//                if (prefRole.equals(Roles.ADMIN_WORK) || prefRole.equals(Roles.ADMIN_QA)) {
//                    fab.hide();
//                } else {
                    fab.show();
//                }
                break;
            }
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showContent() {
        progressBar.setVisibility(View.GONE);
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
    public void navigateToAuthPage() {
        sharedPreferences.edit().remove(PREF_ACCESS_TOKEN).apply();
        sharedPreferences.edit().remove(PREF_REFRESH_TOKEN).apply();
        sharedPreferences.edit().remove(PREF_ROLE).apply();
        sharedPreferences.edit().remove(PREF_NAME).apply();
        sharedPreferences.edit().remove(PREF_USERNAME).apply();
        sharedPreferences.edit().remove(PREF_WORKER_FILTER_STATUS).apply();
        sharedPreferences.edit().remove(PREF_WORK_ASSIGN_FILTER_STATUS).apply();
        sharedPreferences.edit().remove(PREF_WORK_DONE_FILTER_STATUS).apply();
        sharedPreferences.edit().remove(PREF_WORK_ASSIGNED_FILTER_STATUS).apply();

        navigator.navigateToAuth(this);
        finish();
    }

    @OnClick(R.id.fab)
    void onFabClick() {
        if (currentFragment != null && currentFragment.getTag() != null) {
            clearUserActionState();

            switch (currentFragment.getTag()) {
                case TAG_PRODUCT_LIST:
//                    ((ProductListFragment) currentFragment).adapterClear();
                    navigator.navigateToProductCreate(this);
                    break;
                case TAG_WORKER_LIST:
//                    ((WorkerListFragment) currentFragment).adapterClear();
                    navigator.navigateToWorkerCreate(this);
                    break;
                case TAG_WORK_LIST:
//                    ((WorkListFragment) currentFragment).adapterClear();
                    navigator.navigateToWorkCreate(this);
                    break;
            }
        }
    }

    @OnClick(R.id.btn_main_sign_out)
    void signOut() {
        presenter.signOut(sharedPreferences.getString(PREF_USERNAME, ""));
    }

    public CoordinatorLayout getCoordinatorLayout() {
        return coordinatorLayout;
    }

    public boolean isActionMode() {
        return isActionMode;
    }

    public void setActionMode(boolean actionMode) {
        isActionMode = actionMode;
    }

    public void showActionMode() {
        Timber.d("memberActionMode: %s", actionMode);
        actionMode = startSupportActionMode(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                Timber.d("mStatusColorCalledOnCreate %d", Build.VERSION.SDK_INT);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    // This is to highlight the status bar and distinguish it from the action bar,
                    // as the action bar while in the action mode is colored app_green_dark
                    getWindow().setStatusBarColor(getResources().getColor(R.color.secondaryDarkColor));
                }

                getMenuInflater().inflate(R.menu.cab_options_menu, menu);
                isActionMode = true;
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

                MenuItem edit = menu.findItem(R.id.action_edit);
                if (isShowEdit) {
                    edit.setVisible(true);
                    edit.setEnabled(true);
                } else {
                    edit.setVisible(false);
                    edit.setEnabled(false);
                }
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_delete:
                        if (currentFragment != null && currentFragment.getTag() != null) {
                            switch (currentFragment.getTag()) {
                                case TAG_WORKER_LIST:
                                    ((WorkerListFragment) currentFragment).showDeleteConfirmDialog();
                                    break;
                                case TAG_PRODUCT_LIST:
                                    ((ProductListFragment) currentFragment).showDeleteConfirmDialog();
                                    break;
                                case TAG_WORK_LIST:
                                    ((CurrentWorkListFragment) currentFragment).showDeleteConfirmDialog();
                                    break;
                            }
                        }
                        return true;
                    case R.id.action_edit:
                        if (currentFragment != null && currentFragment.getTag() != null) {
                            clearUserActionState();

                            switch (currentFragment.getTag()) {
                                case TAG_WORKER_LIST:
                                    WorkerModel workerModel = ((WorkerListFragment) currentFragment).getSelectedItems().get(0);
//                                    ((WorkerListFragment) currentFragment).adapterClear();
                                    Timber.d("mAdapterCleared");
                                    navigator.navigateToWorkerCreate(MainActivity.this, workerModel);
                                    break;
                                case TAG_PRODUCT_LIST:
                                    ProductModel productModel = ((ProductListFragment) currentFragment).getSelectedItems().get(0);
//                                    ((ProductListFragment) currentFragment).adapterClear();
                                    Timber.d("mAdapterCleared");
                                    navigator.navigateToProductCreate(MainActivity.this, productModel);
                                    break;
                                case TAG_WORK_LIST:
                                    CurrentWorkListModel workModel = ((CurrentWorkListFragment) currentFragment).getSelectedItems().get(0);
//                                    ((WorkListFragment) currentFragment).adapterClear();
                                    navigator.navigateToWorkCreate(MainActivity.this,
                                            new WorkModel(workModel.getId(),
                                                    workModel.getSpkNo(),
                                                    workModel.getArticleNo(),
                                                    workModel.getProductCategoryId().toString(),
                                                    workModel.getQuantity(),
                                                    workModel.getCreatedAt(),
                                                    workModel.getProductId(),
                                                    workModel.getNotes()));
                                    break;
                            }
                        }
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
//                if (currentFragment != null) {
//                    currentFragment.onResume();
//                }

                Timber.d("mStatusColorCalledOnDestroy %d", Build.VERSION.SDK_INT);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    // This is to highlight the status bar and distinguish it from the action bar,
                    // as the action bar while in the action mode is colored app_green_dark
                    getWindow().setStatusBarColor(getResources().getColor(R.color.primaryDarkColor));
                }

                if (currentFragment.getTag() != null) {
                    switch (currentFragment.getTag()) {
                        case TAG_WORKER_LIST:
                            ((WorkerListFragment) currentFragment).resetSelectedItemCount();
                            ((WorkerListFragment) currentFragment).refreshData();
                            break;
                        case TAG_PRODUCT_LIST:
                            ((ProductListFragment) currentFragment).resetSelectedItemCount();
                            ((ProductListFragment) currentFragment).refreshData();
                            break;
                        case TAG_WORK_LIST:
                            ((CurrentWorkListFragment) currentFragment).resetSelectedItemCount();
                            if (isSearching()) {
                                reExecSearchQuery();
                            } else {
                                ((CurrentWorkListFragment) currentFragment).refreshData();
                            }
                            break;
                    }
                }

                isActionMode = false;
                MainActivity.this.actionMode = null;
            }
        });

    }

    public void setActionModeTitle(int itemCount) {
        if (actionMode != null) {
            actionMode.setTitle(String.format(strItemChosen, itemCount));
            Timber.d("%d barang terpilih", itemCount);
        }
    }

    public void stopActionMode() {
        isShowEdit = true;
        if (actionMode != null) {
            Timber.d("stop_action_mode_exe");
            actionMode.finish();
            actionMode = null;
        }
    }

    public void hideActionModeEdit() {
        isShowEdit = false;
        if (actionMode != null) {
            actionMode.invalidate();
        }
    }

    public void showActionModeEdit() {
        isShowEdit = true;
        if (actionMode != null) {
            actionMode.invalidate();
        }
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void clearUserActionState() {
        stopActionMode();
        stopSearchView();
    }

    public void setFilterBadgeCount(String count) {
        if (menuItemFilter != null) {
            LayerDrawable icon = (LayerDrawable) menuItemFilter.getIcon();
            CountDrawable badge;
            // Reuse drawable if possible
            Drawable reuse = icon.findDrawableByLayerId(R.id.ic_action_filter_count);
            if (reuse != null && reuse instanceof CountDrawable) {
                badge = (CountDrawable) reuse;
            } else {
                badge = new CountDrawable(this);
            }

            badge.setCount(count);
            icon.mutate();
            icon.setDrawableByLayerId(R.id.ic_action_filter_count, badge);

        }
    }

    public MainComponent getMainComponent() {
        return mainComponent;
    }

    public boolean isSearching() {
        return mSearchView != null && !mSearchView.isIconified() && !mSearchView.getQuery().toString().isEmpty();
    }

    public void reExecSearchQuery() {
        searchByFilter(mSearchView.getQuery().toString());
    }

    private void navigateTo(Fragment fragment, String TAG) {
        clearUserActionState();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_main_fragment_container, fragment, TAG)
                .commit();
        currentFragment = fragment;
    }

    private void stopSearchView() {
        if (mSearchView != null) {
            mSearchView.onActionViewCollapsed();
        }
    }

    private void setNavigationDefaultItem(String role) {
        MenuItem item = navigationView.getMenu().getItem(0).setChecked(true);

        item.setChecked(true);
        onNavigationItemSelected(item);

        //set default title
        if (role.equals(Roles.ADMIN_PRICE) || role.equals(Roles.SUPER_USER)) {
            setTitle(strWorkTitle);
        } else if (role.equals(Roles.ADMIN_WORK) || role.equals(Roles.ADMIN_QA)) {
            setTitle(strWorkerTitle);
        }
    }

    private void searchByFilter(String s) {
        if (currentFragment != null && currentFragment.getTag() != null) {
            switch (currentFragment.getTag()) {
                case TAG_PRODUCT_LIST:
                    ((ProductListFragment) currentFragment).filterByArticleNo(s);
                    break;
                case TAG_WORKER_LIST:
                    ((WorkerListFragment) currentFragment).filterByName(s);
                    break;
                case TAG_WORK_LIST:
                    ((CurrentWorkListFragment) currentFragment).searchHandle(s);
                    break;
            }
        }
    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }
}

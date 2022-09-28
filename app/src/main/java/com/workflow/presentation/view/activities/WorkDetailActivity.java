package com.workflow.presentation.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.workflow.R;
import com.workflow.WorkflowApplication;
import com.workflow.data.model.WorkModel;
import com.workflow.presentation.di.components.CurrentWorkDetailComponent;
import com.workflow.presentation.di.components.DaggerCurrentWorkDetailComponent;
import com.workflow.presentation.di.modules.CurrentWorkDetailModule;
import com.workflow.presentation.view.WorkDetailView;
import com.workflow.presentation.view.adapters.WorkDetailCollectionAdapter;
import com.workflow.utils.DateUtils;
import com.workflow.utils.WorkflowUtils;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;

import static com.workflow.presentation.navigation.Navigator.PARCELABLE_WORK_MODEL;

public class WorkDetailActivity extends BaseActivity implements WorkDetailView {

    @BindView(R.id.vp_work_detail) ViewPager2 vpWorkDetail;
    @BindView(R.id.tl_work_detail) TabLayout tlWorkDetail;
    @BindView(R.id.tv_work_info_spk_field) AppCompatTextView tvSpkNo;
    @BindView(R.id.tv_work_info_date_field) AppCompatTextView tvCreatedAt;
    @BindView(R.id.tv_work_info_article_field) AppCompatTextView tvArticleNo;
    @BindView(R.id.tv_work_info_category_field) AppCompatTextView tvCategory;
    @BindView(R.id.tv_work_info_quantity_field) AppCompatTextView tvQuantity;
    @BindView(R.id.tv_work_info_notes_field) AppCompatTextView tvNotes;

    @BindString(R.string.worker_detail_header_on_progress) String strHeaderOnProgress;
    @BindString(R.string.worker_detail_header_done) String strHeaderDone;
    @BindString(R.string.salary_report_detail_total_quantity) String strQuantity;

    @Inject WorkDetailCollectionAdapter pagerAdapter;

    private CurrentWorkDetailComponent component;
    private WorkModel workModel;

    @Override
    int setView() {
        return R.layout.activity_work_detail;
    }

    @Override
    void initDagger() {
        workModel = getIntent().getParcelableExtra(PARCELABLE_WORK_MODEL);
        Integer workId = (workModel == null) ? null : workModel.getId();
        if (component == null) {
            component = DaggerCurrentWorkDetailComponent.builder()
                    .applicationComponent(((WorkflowApplication) getApplication()).getApplicationComponent())
                    .currentWorkDetailModule(new CurrentWorkDetailModule(workId, this))
                    .build();
        }
        component.inject(this);
    }

    @Override
    void afterViewCreated(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.outline_arrow_back_ios_white_24);
        renderHeader();
        initViewPager();
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

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showSnackBar(int type, String msg) {

    }

    @Override
    public boolean isConnected() {
        return false;
    }

    private void initViewPager() {
        vpWorkDetail.setAdapter(pagerAdapter);
        String[] tabNames = {strHeaderOnProgress, strHeaderDone};
        new TabLayoutMediator(tlWorkDetail, vpWorkDetail, (tab, position) -> tab.setText(tabNames[position])).attach();
    }

    private void renderHeader() {
        tvArticleNo.setText(workModel.getArticleNo());
        tvCategory.setText(WorkflowUtils.renderProductCategory(this, Integer.valueOf(workModel.getProductCategoryId())));
        tvSpkNo.setText(String.valueOf(workModel.getSpkNo()));
        tvCreatedAt.setText(DateUtils.serverToClient(workModel.getCreatedAt(), DateUtils.TYPE_DATE));
        tvQuantity.setText(String.format(strQuantity, workModel.getQty()));

        if (workModel.getNotes() != null && !workModel.getNotes().isEmpty()) {
            tvNotes.setText(workModel.getNotes());
//            tvNotes.setVisibility(View.VISIBLE);
        } else {
//            tvNotes.setVisibility(View.GONE);
        }
    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, WorkDetailActivity.class);
    }
}

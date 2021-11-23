package com.workflow.presentation.navigation;

import android.content.Context;
import android.content.Intent;

import com.workflow.data.model.DateFilterModel;
import com.workflow.data.model.ProductModel;
import com.workflow.data.model.ReportDetailDateFilterModel;
import com.workflow.data.model.ReportListModel;
import com.workflow.data.model.WorkModel;
import com.workflow.data.model.WorkerModel;
import com.workflow.presentation.view.activities.AuthActivity;
import com.workflow.presentation.view.activities.MainActivity;
import com.workflow.presentation.view.activities.ReportWorkerDetailActivity;
import com.workflow.presentation.view.activities.SalaryReportDetailActivity;
import com.workflow.presentation.view.activities.WorkAssignActivity;
import com.workflow.presentation.view.activities.WorkDetailActivity;
import com.workflow.presentation.view.activities.WorkDoneActivity;
import com.workflow.presentation.view.activities.WorkerWorkAssignActivity;
import com.workflow.presentation.view.activities.ProductCreateActivity;
import com.workflow.presentation.view.activities.WorkCreateActivity;
import com.workflow.presentation.view.activities.WorkerCreateActivity;
import com.workflow.presentation.view.activities.WorkerDetailActivity;

import javax.inject.Inject;

/**
 * Created by Michael on 20/06/19.
 */

public class Navigator {

    public static final String PARCELABLE_WORKER_MODEL = "PARCELABLE_WORKER_MODEL";
    public static final String PARCELABLE_DATE_FILTER_MODEL = "PARCELABLE_DATE_FILTER_MODEL";
    public static final String PARCELABLE_WORK_MODEL = "PARCELABLE_WORK_MODEL";
    public static final String PARCELABLE_PRODUCT_MODEL = "PARCELABLE_PRODUCT_MODEL";
    public static final String PARCELABLE_REPORT_LIST_MODEL = "PARCELABLE_REPORT_LIST_MODEL";
    public static final String PARCELABLE_REPORT_DETAIL_DATE_FILTER = "PARCELABLE_REPORT_DETAIL_DATE_FILTER";
    public static final String PARCELABLE_WORKER_FILTER = "PARCELABLE_WORKER_FILTER";
    public static final String PARCELABLE_UNASSIGNED_WORK_FILTER = "PARCELABLE_UNASSIGNED_WORK_FILTER";
    public static final String SERIALIZABLE_ASSIGNED_WORK = "SERIALIZABLE_ASSIGNED_WORK";
    public static final String SERIALIZABLE_WORK_ASSIGN = "SERIALIZABLE_WORK_ASSIGN";
    public static final String EXTRA_WORKER_ID = "EXTRA_WORKER_ID";
    public static final String EXTRA_WORKER_POSITION = "EXTRA_WORKER_POSITION";
    public static final String EXTRA_WORKER_NAME = "EXTRA_WORKER_NAME";
    public static final String EXTRA_WORKER_WORK_FILTER_TYPE = "EXTRA_WORKER_WORK_FILTER_TYPE";

    @Inject
    public Navigator() {}

    public void navigateToProductCreate(Context context) {
        if (context != null) {
            Intent intentToLaunch = ProductCreateActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToProductCreate(Context context, ProductModel productModel) {
        if (context != null) {
            Intent intentToLaunch = ProductCreateActivity.getCallingIntent(context);
            intentToLaunch.putExtra(PARCELABLE_PRODUCT_MODEL, productModel);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToWorkerCreate(Context context) {
        if (context != null) {
            Intent intentToLaunch = WorkerCreateActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToWorkerCreate(Context context, WorkerModel workerModel) {
        if (context != null) {
            Intent intentToLaunch = WorkerCreateActivity.getCallingIntent(context);
            intentToLaunch.putExtra(PARCELABLE_WORKER_MODEL, workerModel);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToWorkCreate(Context context) {
        if (context != null) {
            Intent intentToLaunch = WorkCreateActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToWorkCreate(Context context, WorkModel workModel) {
        if (context != null) {
            Intent intentToLaunch = WorkCreateActivity.getCallingIntent(context);
            intentToLaunch.putExtra(PARCELABLE_WORK_MODEL, workModel);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToWorkDetail(Context context, WorkModel workModel) {
        if (context != null) {
            Intent intentToLaunch = WorkDetailActivity.getCallingIntent(context);
            intentToLaunch.putExtra(PARCELABLE_WORK_MODEL, workModel);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToWorkerDetail(Context context, WorkerModel param) {
        if (context != null) {
            Intent intentToLaunch = WorkerDetailActivity.getCallingIntent(context);
            intentToLaunch.putExtra(PARCELABLE_WORKER_MODEL, param);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToAssignWork(Context context, WorkerModel workerModel) {
        if (context != null) {
            Intent intentToLaunch = WorkAssignActivity.getCallingIntent(context);
            intentToLaunch.putExtra(PARCELABLE_WORKER_MODEL, workerModel);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToDoneWork(Context context, WorkerModel workerModel) {
        if (context != null) {
            Intent intentToLaunch = WorkDoneActivity.getCallingIntent(context);
            intentToLaunch.putExtra(PARCELABLE_WORKER_MODEL, workerModel);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToReportWorkerDetail(
            Context context, ReportListModel model, ReportDetailDateFilterModel dateFilterModel) {
        if (context != null) {
            Intent intentToLaunch = ReportWorkerDetailActivity.getCallingIntent(context);
            intentToLaunch.putExtra(PARCELABLE_REPORT_LIST_MODEL, model);
            intentToLaunch.putExtra(PARCELABLE_REPORT_DETAIL_DATE_FILTER, dateFilterModel);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToSalaryDetail(Context context, WorkerModel workerModel, DateFilterModel dateFilterModel) {
        if (context != null) {
            Intent intentToLaunch = SalaryReportDetailActivity.getCallingIntent(context);
            intentToLaunch.putExtra(PARCELABLE_WORKER_MODEL, workerModel);
            intentToLaunch.putExtra(PARCELABLE_DATE_FILTER_MODEL, dateFilterModel);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToAuth(Context context) {
        if (context != null) {
            Intent intentToLaunch = AuthActivity.getCallingIntent(context);
            intentToLaunch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToMain(Context context) {
        if (context != null) {
            Intent intentToLaunch = MainActivity.getCallingIntent(context);
            intentToLaunch.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intentToLaunch);
        }
    }
}

package com.workflow.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.workflow.R;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import de.mateware.snacky.Snacky;
import timber.log.Timber;

/**
 * Created by Michael on 05/07/19.
 */

public class WorkflowUtils {

    public static final int SNACK_BAR_SUCCESS = 0;
    public static final int SNACK_BAR_ERROR = 1;
    public static final int SNACK_BAR_INFO = 2;
    public static final int SNACK_BAR_WARNING = 3;
    public static final int LIMIT = 50;

    private static final Locale indonesian = new Locale("in", "ID");
    public static final SimpleDateFormat indoDateFormat = new SimpleDateFormat("dd MMM yyyy", indonesian);
    private static final SimpleDateFormat indoDayMonthFormat = new SimpleDateFormat("dd MMM", indonesian);
    private static final SimpleDateFormat indoDateTimeFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss", indonesian);
    public static final SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
    public static final SimpleDateFormat apiSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    public static int getDisplayWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((AppCompatActivity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int getDisplayHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((AppCompatActivity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static WindowManager.LayoutParams createLayoutParams(AlertDialog dialog, int displayWidth
            , int displayHeight) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        int dialogWindowWidth = (int) (displayWidth * 0.7f);
        int dialogWindowHeight = (int) (displayHeight * 0.7f);

        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;

        return layoutParams;
    }

    public static String convertRupiah(Integer amount) {
        Locale localeId = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeId);
        formatRupiah.setMaximumFractionDigits(0);
        formatRupiah.setMinimumFractionDigits(0);
        if (amount != null) {
            return formatRupiah.format(amount);
        } else {
            return null;
        }
    }

    public static String getRenderedPosition(Context context, String key) {
        switch (key) {
            case Positions.DRAWER:
                return context.getString(R.string.position_drawer);
            case Positions.SEWER:
                return context.getString(R.string.position_sewer);
            case Positions.ASSEMBLER:
                return context.getString(R.string.position_assembler);
            case Positions.SOLE_STITCHER:
                return context.getString(R.string.position_sole_stitcher);
            case Positions.LINING_DRAWER:
                return context.getString(R.string.position_lining_drawer);
            case Positions.INSOLE_STITCHER:
                return context.getString(R.string.position_insole_stitcher);
            default:
                return context.getString(R.string.position_all);
        }

    }

//    public static String getPositionKey(Context context, String renderedPosition) {
//        final String liningDrawer = context.getString(R.string.position_lining_drawer);
//        switch (renderedPosition) {
//            case "Gurat":
//                return Positions.DRAWER;
//            case "Jahit":
//                return Positions.SEWER;
//            case "Tarik":
//                return Positions.ASSEMBLER;
//            case "Jahit Sol":
//                return Positions.SOLE_STITCHER;
//            default:
//                return Positions.DRAWER;
//        }
//    }

//    public static String renderPositionLowerCase(Context context, String key) {
//        switch (key) {
//            case Positions.DRAWER:
//                return context.getString(R.string.position_drawer_lower);
//            case Positions.SEWER:
//                return context.getString(R.string.position_sewer_lower);
//            case Positions.ASSEMBLER:
//                return context.getString(R.string.position_assembler_lower);
//            case Positions.SOLE_STITCHER:
//                return context.getString(R.string.position_sole_stitcher_lower);
//            default:
//                return "none";
//        }
//    }

    public static String renderSort(Context context, String key) {
        if (key != null) {
            switch (key) {
                case Sort.SORT_BY_WORKER_POSITION:
                    return context.getString(R.string.sort_position);
                case Sort.SORT_BY_WORKER_NAME:
                    return context.getString(R.string.sort_name);
                case Sort.SORT_DIRECTION_ASC:
                    return context.getString(R.string.sort_asc);
                case Sort.SORT_DIRECTION_DESC:
                    return context.getString(R.string.sort_desc);
                case Sort.SORT_BY_SPK_NO:
                    return context.getString(R.string.filter_sort_by_spk_no);
                case Sort.SORT_BY_ARTICLE_NO:
                    return context.getString(R.string.filter_sort_by_article_no);
                case Sort.SORT_BY_DATE:
                    return context.getString(R.string.filter_sort_by_date);
                case Sort.SORT_BY_ASSIGNED_AT:
                    return context.getString(R.string.filter_sort_by_assigned_at);
                case Sort.SORT_BY_DONE_AT:
                    return context.getString(R.string.filter_sort_by_done_at);
                default:
                    return "none";
            }
        } else {
            return "none";
        }
    }

    public static String renderWorked(Context context, String key) {
        switch (key) {
            case Positions.DRAWER:
                return context.getString(R.string.position_drawn);
            case Positions.SEWER:
                return context.getString(R.string.position_sewn);
            case Positions.ASSEMBLER:
                return context.getString(R.string.position_assembled);
            case Positions.SOLE_STITCHER:
                return context.getString(R.string.position_sole_stitched);
            case Positions.LINING_DRAWER:
                return context.getString(R.string.position_lining_drawn);
            case Positions.INSOLE_STITCHER:
                return context.getString(R.string.position_insole_stitched);
            default:
                return "none";
        }
    }

    public static String renderWorking(Context context, String key) {
        switch (key) {
            case Positions.DRAWER:
                return context.getString(R.string.position_drawing);
            case Positions.SEWER:
                return context.getString(R.string.position_sewing);
            case Positions.ASSEMBLER:
                return context.getString(R.string.position_assembling);
            case Positions.SOLE_STITCHER:
                return context.getString(R.string.position_sole_stitching);
            case Positions.LINING_DRAWER:
                return context.getString(R.string.position_lining_drawing);
            case Positions.INSOLE_STITCHER:
                return context.getString(R.string.position_insole_stitching);
            default:
                return "none";
        }
    }

    public static String renderRole(Context context, String key) {
        switch (key) {
            case Roles.ADMIN_QA:
                return context.getString(R.string.register_role_quality_assurance);
            case Roles.ADMIN_PRICE:
                return context.getString(R.string.register_role_admin_price);
            case Roles.ADMIN_WORK:
                return context.getString(R.string.register_role_admin_work);
            case Roles.SUPER_USER:
                return "Superuser";
            default:
                return "none";
        }
    }

    public static Snackbar generateSnackBar(View view, int type, String msg) {

        switch (type) {
            case SNACK_BAR_SUCCESS:
                return Snacky.builder()
                        .setView(view)
                        .setText(msg)
                        .setDuration(Snacky.LENGTH_LONG)
                        .setActionText(R.string.confirm_delete_ok)
                        .success();
            case SNACK_BAR_ERROR:
                return Snacky.builder()
                        .setView(view)
                        .setText(msg)
                        .setDuration(Snacky.LENGTH_LONG)
                        .setActionText(R.string.confirm_delete_ok)
                        .error();
            case SNACK_BAR_INFO:
                return Snacky.builder()
                        .setView(view)
                        .setText(msg)
                        .setDuration(Snacky.LENGTH_LONG)
                        .setActionText(R.string.confirm_delete_ok)
                        .info();
            case SNACK_BAR_WARNING:
                return Snacky.builder()
                        .setView(view)
                        .setText(msg)
                        .setDuration(Snacky.LENGTH_LONG)
                        .setActionText(R.string.confirm_delete_ok)
                        .warning();
            default:
                return Snacky.builder()
                        .setView(view)
                        .setText(msg)
                        .setDuration(Snacky.LENGTH_LONG)
                        .setActionText(R.string.confirm_delete_ok)
                        .info();
        }
    }

    public static Snackbar generateSnackBar(Activity activity, int type, String msg) {

        switch (type) {
            case SNACK_BAR_SUCCESS:
                return Snacky.builder()
                        .setActivity(activity)
                        .setText(msg)
                        .setDuration(Snacky.LENGTH_LONG)
                        .setActionText(R.string.confirm_delete_ok)
                        .success();
            case SNACK_BAR_ERROR:
                return Snacky.builder()
                        .setActivity(activity)
                        .setText(msg)
                        .setDuration(Snacky.LENGTH_LONG)
                        .setActionText(R.string.confirm_delete_ok)
                        .error();
            case SNACK_BAR_INFO:
                return Snacky.builder()
                        .setActivity(activity)
                        .setText(msg)
                        .setDuration(Snacky.LENGTH_LONG)
                        .setActionText(R.string.confirm_delete_ok)
                        .info();
            case SNACK_BAR_WARNING:
                return Snacky.builder()
                        .setActivity(activity)
                        .setText(msg)
                        .setDuration(Snacky.LENGTH_LONG)
                        .setActionText(R.string.confirm_delete_ok)
                        .warning();
            default:
                return Snacky.builder()
                        .setActivity(activity)
                        .setText(msg)
                        .setDuration(Snacky.LENGTH_LONG)
                        .setActionText(R.string.confirm_delete_ok)
                        .info();
        }
    }
    public static String toCSV(List<String> array) {
        String result = "";
        if (array.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (String s : array) {
                sb.append(s).append(", ");
            }
            result = sb.deleteCharAt(sb.length() - 2).toString();
        } return result;
    }

    public static String convertApiToIndonesianDate(String apiDate) {
        Date date = new Date();
        try {
            apiDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Timber.d("time_zone_api: %s", apiDateFormat.getTimeZone());
            ;
            Timber.d("time_zone_api_item: %s", apiDate);
            ;
            date = apiDateFormat.parse(apiDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        indoDateTimeFormat.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
        return indoDateFormat.format(date);
    }

    public static String convertApiToIndonesianDateTime(String apiDate) {
        Date date = null;
        try {
            apiDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            date = apiDateFormat.parse(apiDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        indoDateTimeFormat.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
        return indoDateTimeFormat.format(date);
    }

//    public static String convertApiNoTimezoneToIndonesianDateTime(String apiDate) {
//        Date date = null;
//        try {
//            serverDateTime.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
//            date = serverDateTime.parse(apiDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return indoDateTimeFormat.format(date);
//    }

    public static String formatSimpleDateToIndoDate(String simpleDate) {
        Date date = null;
        try {
            date = apiSimpleDateFormat.parse(simpleDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return indoDateFormat.format(date);
    }

    public static String formatSimpleDateToIndoDayMonth(String simpleDate) {
        Date date = null;
        try {
            date = apiSimpleDateFormat.parse(simpleDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return indoDayMonthFormat.format(date);
    }

    public static SimpleDateFormat getIndoDateFormat() {
        return indoDateFormat;
    }

    public static int convertWorkAssignSortToPosition(String sortBy) {
        switch (sortBy) {
            case Sort.SORT_BY_SPK_NO:
                return 1;
            case Sort.SORT_BY_ARTICLE_NO:
                return 2;
            case Sort.SORT_BY_DATE:
                return 3;
            default:
                return 1;
        }
    }

    public static String convertWorkAssignPositionToKey(int position) {
        switch (position) {
            case 1:
                return Sort.SORT_BY_SPK_NO;
            case 2:
                return Sort.SORT_BY_ARTICLE_NO;
            case 3:
                return Sort.SORT_BY_DATE;
            case 4:
                return Sort.SORT_BY_DONE_AT;
            default:
                return Sort.SORT_BY_SPK_NO;
        }
    }

    public static String compileSortDirection(boolean isChecked) {
        if (isChecked) {
            return Sort.SORT_DIRECTION_DESC;
        } else {
            return Sort.SORT_DIRECTION_ASC;
        }
    }

    public static boolean renderSortDirection(String sortDirection) {
        return !sortDirection.equals(Sort.SORT_DIRECTION_ASC);
    }

    public static String renderProductCategory(Context context, Integer productCategory) {
        String menShoesRes = context.getResources().getString(R.string.product_category_men_shoes);
        String womenShoesRes = context.getResources().getString(R.string.product_category_women_shoes);
        String menSandalsRes = context.getResources().getString(R.string.product_category_men_sandals);
        String returnProductCategory;

        switch (productCategory) {
            case 1:
                returnProductCategory =  menShoesRes;
                break;
            case 3:
                returnProductCategory =  womenShoesRes;
                break;
            case 2:
                returnProductCategory =  menSandalsRes;
                break;
            default:
                returnProductCategory = "N/A";
                break;
        }

        return returnProductCategory;
    }

    public static String transformProductCategory(Context context, String productCategory) {
        String menShoesRes = context.getResources().getString(R.string.product_category_men_shoes);
        String womenShoesRes = context.getResources().getString(R.string.product_category_women_shoes);
        String menSandalsRes = context.getResources().getString(R.string.product_category_men_sandals);
        String returnProductCategory;

        switch (productCategory) {
            case "Sepatu Pria":
                returnProductCategory =  menShoesRes;
                break;
            case "Sepatu Wanita":
                returnProductCategory =  womenShoesRes;
                break;
            case "Sandal Pria":
                returnProductCategory =  menSandalsRes;
                break;
            default:
                returnProductCategory = "N/A";
                break;
        }

        return returnProductCategory;
    }
}

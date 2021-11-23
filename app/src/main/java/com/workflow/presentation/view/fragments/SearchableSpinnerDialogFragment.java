package com.workflow.presentation.view.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.workflow.R;
import com.workflow.data.model.GenericItemPaginationModel;
import com.workflow.data.model.ListModel;
import com.workflow.data.model.SearchPaginatedQueryModel;
import com.workflow.presentation.view.adapters.ListAdapter;
import com.workflow.presentation.view.adapters.OnRecyclerObjectClickListener;
import com.workflow.presentation.view.adapters.WorkflowRvOnScrollListener;
import com.workflow.presentation.view.itemdecoration.VerticalSingleColumnDecoration;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * Created by Michael on 03/07/19.
 */

public class SearchableSpinnerDialogFragment extends BaseDialogFragment {

//    public static final String PARCELABLE_SEARCHABLE_LIST_MODEL = "PARCELABLE_SEARCHABLE_LIST_MODEL";

    @BindView(R.id.rv_searchable_spinner) RecyclerView recyclerView;
    @BindView(R.id.et_searchable_spinner_search) EditText etSearch;
    @BindView(R.id.vf_dialog_searchable_spinner) ViewFlipper viewFlipper;
    @BindView(R.id.tv_error_desc) TextView tvError;
    @BindView(R.id.layout_alpha_progress_bar) ConstraintLayout progressBar;

    private ListAdapter adapter;
    private DialogListener dialogListener;
    private OnRecyclerObjectClickListener adapterListener;
    private Timer timer;
    private LinearLayoutManager linearLayoutManager;
    private boolean mIsLoading;
    private int mCurrentPage = 1;
    private int mLimit = 10;
    private boolean mIsLastPage;

    private int height;
    private int width;

    public SearchableSpinnerDialogFragment(int height, int width) {
        this.height = height;
        this.width = width;
    }

    @Override
    int setView() {
        return R.layout.fragment_dialog_searchable_spinner_linear;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog superDialog = super.onCreateDialog(savedInstanceState);
//        superDialog.setCanceledOnTouchOutside(false);

        return superDialog;
    }

    @Override
    void initDagger() {
    }

    @Override
    void afterViewCreated(Bundle savedInstanceState) {
        initTextWatcher();
        initRvAdapter();

        //show keyboard upon view created
        etSearch.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                etSearch.requestFocus();
                imm.showSoftInput(etSearch, 0);
            }
        }, 50);

        etSearch.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                        && event.getAction() == KeyEvent.ACTION_DOWN)) {
                    if (progressBar.getVisibility() == View.GONE) {
                        dialogListener.onItemClick(0, adapter.getItem(0));
                        ;
                        dismiss();
                        // return true indicates handled
                        return true;
                    }
                }

                return false;
            }
        });
        showLoading();
        SearchPaginatedQueryModel queryModel = new SearchPaginatedQueryModel(
                null
                , mLimit
                , mCurrentPage
        );
        dialogListener.getFilteredList(queryModel);
    }

    @Override
    public void onResume() {
        super.onResume();

        getDialog().getWindow().setLayout(width, height);
//        Window window = getDialog().getWindow();
//        if(window == null) return;
//        WindowManager.LayoutParams params = window.getAttributes();
//        params.width = 800;
//        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        window.setAttributes(params);
    }

    //    private List<ListModel> readBundle(Bundle bundle) {
//        List<ListModel> models = null;
//        if (bundle != null) {
//            models = bundle.getParcelableArrayList(PARCELABLE_SEARCHABLE_LIST_MODEL);
//        }
//        return models;
//    }
//
//    public static SearchableSpinnerDialogFragment newInstance(List<ListModel> listModels) {
//        Bundle bundle = new Bundle();
//        ArrayList<ListModel> listModelArrayList = new ArrayList<>();
//        listModelArrayList.addAll(listModels);
//        bundle.putParcelableArrayList(PARCELABLE_SEARCHABLE_LIST_MODEL, listModelArrayList);
//
//        SearchableSpinnerDialogFragment fragment = new SearchableSpinnerDialogFragment();
//        fragment.setArguments(bundle);
//
//        return fragment;
//    }


    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        dialogListener.onDismiss();
        super.onDismiss(dialog);
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        dialogListener.onCancel();
        super.onCancel(dialog);
    }

    public void setListener(DialogListener listener) {
        this.dialogListener = listener;
    }

    public void renderItems(GenericItemPaginationModel<List<ListModel>> items) {
        progressBar.setVisibility(View.GONE);
        viewFlipper.setDisplayedChild(0);
        adapter.removeLoadingFooter();
        mIsLoading = false;
        if (mCurrentPage == 1) {
            items.getItems().get(0).setHighlighted(true);
            adapter.setItems(items.getItems());
        } else {
            adapter.addAll(items.getItems());
        }

        if (mCurrentPage >= items.getPaginationModel().getTotalPage()) {
            mIsLastPage = true;
        } else {
            adapter.addLoadingFooter();
            mIsLastPage = false;
        }
    }

    public void showError(String err) {
        viewFlipper.setDisplayedChild(1);
        progressBar.setVisibility(View.GONE);
        tvError.setText(err);
    }

    public void showContent() {
        viewFlipper.setDisplayedChild(0);
        progressBar.setVisibility(View.GONE);
    }

    private void showLoading() {
        viewFlipper.setDisplayedChild(0);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void showDataEmpty() {
        viewFlipper.setDisplayedChild(2);
        progressBar.setVisibility(View.GONE);
    }

    private void initRvAdapter() {
        adapter = new ListAdapter(context);
        adapter.setListener(new OnRecyclerObjectClickListener<ListModel>() {
                    @Override
                    public void onItemClicked(int position, ListModel item) {
                        dismiss();
                        dialogListener.onItemClick(position, item);
                    }

                    @Override
                    public void onItemLongClick(int position, ListModel item) {

                    }

            @Override
            public void onItemSelected(int position, ListModel item) {

            }
        });

        recyclerView.setAdapter(adapter);
        linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL
                ,false);
        recyclerView.addItemDecoration(new VerticalSingleColumnDecoration(context , R.dimen.activity_horizontal_margin));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new WorkflowRvOnScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                mIsLoading = true;
                mCurrentPage ++;
                String filter = etSearch.getText().toString().isEmpty()
                        ? null : etSearch.getText().toString();
                SearchPaginatedQueryModel queryModel = new SearchPaginatedQueryModel(
                        filter
                        , mLimit
                        , mCurrentPage
                );
                dialogListener.getFilteredList(queryModel);
            }

            @Override
            public boolean isLastPage() {
                return mIsLastPage;
            }

            @Override
            public boolean isLoading() {
                return mIsLoading;
            }
        });
    }

    private void initTextWatcher() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (timer != null) {
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                showLoading();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mCurrentPage = 1;
                        String filter = etSearch.getText().toString().isEmpty()
                                ? null : etSearch.getText().toString();
                        SearchPaginatedQueryModel queryModel = new SearchPaginatedQueryModel(
                                filter
                                , mLimit
                                , mCurrentPage
                        );
                        dialogListener.getFilteredList(queryModel);
                    }
                }, 600);
            }
        });
    }

    public interface DialogListener {
        void onItemClick(int position, ListModel listModel);
        void getFilteredList(SearchPaginatedQueryModel queryModel);
        void onDismiss();
        void onCancel();
    }
}

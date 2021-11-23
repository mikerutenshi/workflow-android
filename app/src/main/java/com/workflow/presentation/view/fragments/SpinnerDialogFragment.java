package com.workflow.presentation.view.fragments;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.workflow.R;
import com.workflow.data.model.ListModel;
import com.workflow.presentation.di.components.DaggerSpinnerDialogComponent;
import com.workflow.presentation.di.components.SpinnerDialogComponent;
import com.workflow.presentation.di.modules.AdapterModule;
import com.workflow.presentation.di.modules.ContextModule;
import com.workflow.presentation.view.adapters.OnRecyclerObjectClickListener;
import com.workflow.presentation.view.adapters.SpinnerAdapter;
import com.workflow.presentation.view.itemdecoration.VerticalSingleColumnDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class SpinnerDialogFragment extends BaseDialogFragment {

    @BindView(R.id.rv_spinner_content) RecyclerView recyclerView;

    @Inject SpinnerAdapter adapter;
    @Inject LinearLayoutManager linearLayoutManager;
    @Inject VerticalSingleColumnDecoration verticalSingleColumnDecoration;

    public static final String EXTRA_DIALOG_ITEM_LIST = "EXTRA_DIALOG_ITEM_LIST";

    private SpinnerDialogComponent component;
    private DialogListener dialogListener;

    @Override
    int setView() {
        return R.layout.fragment_dialog_spinner;
    }

    @Override
    void initDagger() {
        if (component == null) {
            component = DaggerSpinnerDialogComponent.builder()
                    .contextModule(new ContextModule(context))
                    .build();
        }
        component.inject(this);
    }

    @Override
    void afterViewCreated(Bundle savedInstanceState) {

        List<ListModel> items = readBundle(getArguments());
        adapter.setItems(items);
        adapter.setListener(new OnRecyclerObjectClickListener<ListModel>() {
            @Override
            public void onItemClicked(int position, ListModel item) {
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
        recyclerView.addItemDecoration(verticalSingleColumnDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void setDialogListener(DialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    public static SpinnerDialogFragment newInstance(ArrayList<ListModel> models) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(EXTRA_DIALOG_ITEM_LIST, models);

        SpinnerDialogFragment fragment = new SpinnerDialogFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    private List<ListModel> readBundle(Bundle bundle) {
        List<ListModel> list = new ArrayList<>();
        if (bundle != null) {
            list = bundle.getParcelableArrayList(EXTRA_DIALOG_ITEM_LIST);
        }
        return list;
    }


    public interface DialogListener {
        void onItemClick(int position, ListModel listModel);
    }
}

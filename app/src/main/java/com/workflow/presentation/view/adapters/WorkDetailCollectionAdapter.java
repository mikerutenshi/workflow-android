package com.workflow.presentation.view.adapters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.workflow.presentation.view.fragments.CurrentWorkDetailAssignedFragment;
import com.workflow.presentation.view.fragments.CurrentWorkDetailDoneFragment;

import java.util.ArrayList;
import java.util.List;

public class WorkDetailCollectionAdapter extends FragmentStateAdapter {

    private List<Fragment> fragments = new ArrayList<>();

    public WorkDetailCollectionAdapter(@NonNull AppCompatActivity activity,
                                       CurrentWorkDetailAssignedFragment workAssignedFragment,
                                       CurrentWorkDetailDoneFragment workDoneFragment) {
        super(activity);
        this.fragments.add(workAssignedFragment);
        this.fragments.add(workDoneFragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}

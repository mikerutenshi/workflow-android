package com.workflow.presentation.view.adapters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.workflow.presentation.view.fragments.WorkAssignedFragment;
import com.workflow.presentation.view.fragments.WorkDoneFragment;
import com.workflow.presentation.view.fragments.WorkerWorkFragment;

import java.util.ArrayList;
import java.util.List;

public class WorkerWorkCollectionAdapter extends FragmentStateAdapter {

    private List<Fragment> fragments = new ArrayList<>();

    public WorkerWorkCollectionAdapter(@NonNull AppCompatActivity activity,
                                       WorkAssignedFragment workAssignedFragment,
                                       WorkDoneFragment workDoneFragment) {
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

    public WorkerWorkFragment getCurrentFragment(int position) {
        return (WorkerWorkFragment) fragments.get(position);
    }
}

package com.myclass.app;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.jetbrains.annotations.NotNull;

public class studentPageAdapter extends FragmentPagerAdapter {
    public int numOfTabes;

    public studentPageAdapter(FragmentManager fragmentManager, int numOfTabes){
        super(fragmentManager);
        this.numOfTabes = numOfTabes;
    }
    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new studentList1();
            case 1:
                return new addStudent1();
            case 2:
                return new requestedStudent();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return numOfTabes;
    }
}

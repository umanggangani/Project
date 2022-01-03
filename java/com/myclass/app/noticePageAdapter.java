package com.myclass.app;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.jetbrains.annotations.NotNull;

public class noticePageAdapter extends FragmentPagerAdapter {
    public int numOfTabes;

    public noticePageAdapter(FragmentManager fragmentManager, int numOfTabes){
        super(fragmentManager);
        this.numOfTabes = numOfTabes;
    }
    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new noticeList1();
            case 1:
                return new addNotice1();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabes;
    }
}


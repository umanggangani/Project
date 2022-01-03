package com.myclass.app;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class testPageAdapter extends FragmentPagerAdapter
    {   public int numOfTabes;

    public testPageAdapter(FragmentManager fragmentManager, int numOfTabes){
        super(fragmentManager);
        this.numOfTabes = numOfTabes;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new testList1();
            case 1:
                return new addTest1();
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return numOfTabes;
    }
}

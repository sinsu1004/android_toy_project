package com.example.project.fragmentAdapter;

;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.project.fragment.Fragment_home;
import com.example.project.fragment.Fragment_problem_make;
import com.example.project.fragment.Fragment_setting;
import com.example.project.fragment.Fragment_problem_list;


public class FragmentAdapter extends FragmentPagerAdapter {

    public static int PAGE_POSITION = 4;

    public FragmentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {

            //1번째 탭
            case 0:
                return Fragment_home.newInstance();
            case 1:
                return Fragment_problem_list.newInstance();
            case 2:
                return Fragment_setting.newInstance();
            case 3:
                return Fragment_problem_make.newInstance();

            default:
                return null;

        }
    }
    @Override
    public int getItemPosition(Object object){
        return POSITION_NONE;
    }


    @Override
    public int getCount() {
        return 4;
    }
}

package com.example.project.activity;

import static com.example.project.fragmentAdapter.FragmentAdapter.PAGE_POSITION;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.project.R;
import com.example.project.fragmentAdapter.FragmentAdapter;
import com.example.project.settings;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class Mainscreen extends AppCompatActivity {



    private TabLayout tabLayout;

    public TabLayout getTabLayout() {
        return tabLayout;
    }

    private ViewPager viewPager;

    public ViewPager getViewPager() {
        return viewPager;
    }

    private ArrayList<String> tabNames=new ArrayList<>();

    private FragmentAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainscreen);
        //ArrayList에 Tab 이름 저장
        loadTabName();
        //TabLayout 설정
        setTabLayout();
        //ViewPager 연결
        setViewPager();






    }
    private void loadTabName(){
        tabNames.add("홈");
        tabNames.add("제시어 목록");
        tabNames.add("설정");
        tabNames.add("제시어 만들기");

    }

    @TargetApi(Build.VERSION_CODES.N)
    private void setTabLayout() {



        tabLayout =findViewById(R.id.tab);
        //ArrayList에 저장된 순서대로 Tab 이름을 지정해줌
        for (String name : tabNames) {
            tabLayout.addTab(tabLayout.newTab().setText(name));
        }
        //Tab 아이콘 설절
        tabLayout.getTabAt(0).setIcon(R.drawable.home);
        tabLayout.getTabAt(1).setIcon(R.drawable.list);
        tabLayout.getTabAt(2).setIcon(R.drawable.settings);
        tabLayout.getTabAt(3).setIcon(R.drawable.user);

    }
    private void setViewPager() {
        //어댑터 초기화
        adapter = new FragmentAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager =  findViewById(R.id.viewPager);
        //어댑터 연결
        viewPager.setAdapter(adapter);
        //페이지 리스너 (viewPager와 TabLayout의 페이지를 맞춰줌)
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //탭 선택 리스너 (탭 행동 설정)
        tabLayout.setTabTextColors(Color.BLACK,Color.parseColor("#0070C0"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            //선택된 탭일 때
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //선택된 탭과 연결된 fragment를 가져옴
                viewPager.setCurrentItem(tab.getPosition());




                //아이콘 색상을 흰색으로 설정
                tab.getIcon().setColorFilter(Color.parseColor("#0070C0"), PorterDuff.Mode.SRC_IN);
            }
            //선택되지 않은 탭일 때
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //아이콘 색상을 #0070C0 으로 설정
                tab.getIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}

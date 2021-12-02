package com.example.project.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project.R;
import com.example.project.activity.MainActivity;
import com.example.project.activity.koreagame;
import com.example.project.activity.startproblem;

public class Fragment_home extends Fragment {
    public Fragment_home() {}
    private ImageButton bodyspeak_button;
    private ImageButton speedquiz;
    private ImageButton koreagame;
    private ImageButton humanquiz;
    public static Fragment_home newInstance() {
        Fragment_home calendarFragment= new Fragment_home();
        Bundle bundle = new Bundle();
        return calendarFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("home생성");
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        bodyspeak_button=view.findViewById(R.id.bodyspeak_button);
        speedquiz=view.findViewById(R.id.speedquiz);
        koreagame=view.findViewById(R.id.koreagame);
        humanquiz=view.findViewById(R.id.humanquiz);
        bodyspeak_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), MainActivity.class);
                intent.putExtra("name","몸으로 말해요");
                startActivity(intent);
            }
        });

        speedquiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), MainActivity.class);
                intent.putExtra("name","스피드 퀴즈");
                startActivity(intent);
            }
        });
        koreagame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), koreagame.class);
                intent.putExtra("name","초성 게임");
                startActivity(intent);
            }
        });

        humanquiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), MainActivity.class);
                intent.putExtra("name","인물 퀴즈");
                startActivity(intent);
            }
        });

















        return view;
    }

}


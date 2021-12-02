package com.example.project.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project.R;
import com.example.project.activity.MainActivity;
import com.example.project.activity.startproblem;

public class Fragment_home extends Fragment {
    public Fragment_home() {}
    private Button bodyspeak_button;

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

        bodyspeak_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });




















        return view;
    }

}


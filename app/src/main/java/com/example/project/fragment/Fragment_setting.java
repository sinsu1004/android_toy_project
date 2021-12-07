package com.example.project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project.R;
import com.example.project.settings;

public class Fragment_setting extends Fragment {
    public Fragment_setting() {}
    private Button settingbutton;
    private Button count_bt;
    private Button passcount_bt;
    private EditText count;
    private EditText passcount;
    private TextView nowcount;
    private TextView nowpass;
    private TextView textView5;

    public static Fragment_setting newInstance() {
        Fragment_setting calendarFragment= new Fragment_setting();
        Bundle bundle = new Bundle();
        return calendarFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("설정");
        View view = inflater.inflate(R.layout.fragment_setting, container, false);



        count=view.findViewById(R.id.countdowntime);
        passcount=view.findViewById(R.id.passnumber);
        nowcount=view.findViewById(R.id.nowcount);
        nowpass=view.findViewById(R.id.nowpass);
        count_bt=view.findViewById(R.id.countdowntime_bt);
        passcount_bt=view.findViewById(R.id.passnumber_bt);
        textView5=view.findViewById(R.id.textView5);


        textView5.setText(((settings)getActivity().getApplication()).getNickname()+"님");
        nowcount.setText("카운트 시간 : " +Integer.toString(((settings)getActivity().getApplication()).getCount())+"초");
        nowpass.setText("PASS 개수 : " +Integer.toString(((settings)getActivity().getApplication()).getPasscount())+"개");

        count_bt.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(count_bt.getText().equals("수정")){
                    count.setEnabled(true);
                    count.setText(null);
                    count_bt.setText("저장");
                }
                else if(count_bt.getText().equals("저장")) {
                    ((settings) getActivity().getApplication()).setCount(Integer.parseInt(count.getText().toString()));
                    nowcount.setText("카운트 시간 : " + ((settings) getActivity().getApplication()).getCount()+"초");
                    count.setEnabled(false);
                    count_bt.setText("수정");
                }
            }
        });

        passcount_bt.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if(passcount_bt.getText().equals("수정")){
                    passcount.setEnabled(true);
                    passcount.setText(null);
                    passcount_bt.setText("저장");
                }
                else if(passcount_bt.getText().equals("저장")) {
                    ((settings) getActivity().getApplication()).setPasscount(Integer.parseInt(passcount.getText().toString()));
                    nowpass.setText("PASS 개수 : " + passcount.getText().toString()+"개");
                    passcount.setEnabled(false);
                    passcount_bt.setText("수정");
                }
            }
        });






















        return view;
    }

}


package com.example.project.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.example.project.settings;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class koreagame extends AppCompatActivity {
    private ArrayList<String> problem=new ArrayList<>(Arrays.asList("ㄱㄴ","ㅇㅇ","ㅅㅅ","ㄱㅂ","ㅇㅅ","ㄱㅂ","ㅎㅇ","ㄱㅈ","ㅂㄱ","ㅂㅂ","ㅁㅊ"));
    private ArrayList<String> data=new ArrayList<>();
    private TextView textView21;
    private EditText koreaproblems;
    private Button koreasave;
    private TextView problemview;
    private String hoya="";
    private String xs;
    private Boolean check;
    private static int MILLISINFUTURE ;
    private static int COUNT_DOWN_INTERVAL = 1000;
    private static int count;

    private CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.koreagame);

        count=((settings) getApplication()).getCount();
        MILLISINFUTURE = 10 * 100 * count;
        int random=(int) Math.floor(Math.random()*10);
        textView21=findViewById(R.id.textView21);
        textView21.setText(problem.get(random));

        koreaproblems=findViewById(R.id.koreaproblems);

        koreasave=findViewById(R.id.koreasave);

        problemview=findViewById(R.id.problemview);
        countDownTimer();
        countDownTimer.start();

        koreasave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check=true;
                xs =koreaproblems.getText().toString();
                for(String datas :data){
                   if(datas.equals(xs)){
                       check=false;
                   }
                }
                if(check==true) {
                    hoya += "\n";
                    hoya += xs;
                    data.add(xs);
                    problemview.setText(hoya);
                    koreaproblems.setText("");
                }
                else{
                    koreaproblems.setText("");
                    Toast.makeText(getApplicationContext(), "이미 사용한 단어입니다. ", Toast.LENGTH_SHORT).show();
                }

            }
        });








    }
    public void countDownTimer() {


        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            public void onTick(long millisUntilFinished) {
                count--;
            }

            public void onFinish() {
                textView21.setText("Finish");
                koreaproblems.setEnabled(false);
                koreasave.setEnabled(false);
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(1000); // 0.5초간 진동




            }


        };


    }
}

package com.example.project.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.example.project.settings;

import java.util.ArrayList;
import java.util.Collections;

public class startproblem extends AppCompatActivity {

    private TextView main;
    private TextView usertime;
    private TextView usertruetext;
    private Button usernext;
    private Button userpass;
    private int count;
    private static int MILLISINFUTURE = 10 * 6000;
    private static int COUNT_DOWN_INTERVAL = 1000;
    private int passcount;
    private int truecount=0;

    private CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startproblem);

        usertime=(TextView) findViewById(R.id.usertime);
        main=(TextView) findViewById(R.id.usermaintext);
        usernext= (Button) findViewById(R.id.usernext);
        userpass=(Button) findViewById(R.id.userpass);
        usertruetext=(TextView) findViewById(R.id.usertruetext);
        count=((settings) getApplication()).getCount();
        MILLISINFUTURE = 10 * 100 * count;
        passcount=((settings) getApplication()).getPasscount();

        Intent intent = getIntent();

        ArrayList<String> list = (ArrayList<String>) intent.getSerializableExtra("problemlist");
        Collections.shuffle(list);
        main.setText(list.get(0));
        list.remove(0);
        countDownTimer();
        countDownTimer.start();
        Toast.makeText(getApplication(), "정답개수: " + 0 + "  남은 PASS 횟수: " + passcount, Toast.LENGTH_LONG).show();

        usernext.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                    if (list.size() != 0) {
                        truecount++;
                        main.setText(list.get(0));
                        list.remove(0);
                        usertruetext.setText("정답개수: " + truecount + "  남은 PASS 횟수: " + passcount);
                    } else {
                        Toast.makeText(getApplication(), "남은 제시어가 없습니다", Toast.LENGTH_LONG).show();
                    }

                }
            });
        userpass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                    if (list.size() != 0) {
                        if (passcount != 0) {
                            passcount--;
                            main.setText(list.get(0));
                            list.remove(0);
                            usertruetext.setText("정답개수: " + truecount + "  남은 PASS 횟수: " + passcount);
                        } else {
                            Toast.makeText(getApplication(), "남은 pass가 없습니다.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplication(), "남은 제시어가 없습니다", Toast.LENGTH_LONG).show();
                    }

                }
            });


    }

    public void countDownTimer() {


        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            public void onTick(long millisUntilFinished) {

                usertime.setText(String.valueOf(count));
                count--;
            }

            public void onFinish() {
                usertime.setText("Finish");
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(1000); // 0.5초간 진동

                Intent intent=new Intent(getApplicationContext(), true_count_view.class);
                intent.putExtra("truecount",truecount);
                startActivity(intent);
                finish();



            }


        };


    }

}




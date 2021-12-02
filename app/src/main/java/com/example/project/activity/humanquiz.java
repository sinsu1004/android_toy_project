package com.example.project.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.example.project.settings;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class humanquiz extends AppCompatActivity {

    private TextView textView20;
    private ImageView imageView5;
    private Button next2;
    private Button pass2;
    private CountDownTimer countDownTimer;
    private static int MILLISINFUTURE;
    private static int count;
    private static int truenum = 0;
    private static int COUNT_DOWN_INTERVAL = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.humanquiz);

        textView20=findViewById(R.id.textView20);
        imageView5=findViewById(R.id.imageView5);
        next2=findViewById(R.id.next2);
        pass2=findViewById(R.id.pass2);
        loadImageTask imageTask = new loadImageTask("https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/201608/04/htm_2016080484837486184.jpg");
        imageTask.execute();
        count=((settings) getApplication()).getCount();
        MILLISINFUTURE = 10 * 100 * count;
        countDownTimer();
        countDownTimer.start();
        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        pass2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });






    }


    public class loadImageTask extends AsyncTask<Bitmap, Void, Bitmap> {     //이미지 업로드

        private String url;

        public loadImageTask(String url) {

            this.url = url;
        }

        @Override
        protected Bitmap doInBackground(Bitmap... params) {

            Bitmap imgBitmap = null;

            try {
                URL url1 = new URL(url);
                URLConnection conn = url1.openConnection();
                conn.connect();
                int nSize = conn.getContentLength();
                BufferedInputStream bis = new BufferedInputStream(conn.getInputStream(), nSize);
                imgBitmap = BitmapFactory.decodeStream(bis);
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return imgBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bit) {
            super.onPostExecute(bit);
            imageView5.setImageBitmap(bit);
        }
    }

    public void countDownTimer() {     //카운트 다운


        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            public void onTick(long millisUntilFinished) {

                textView20.setText(String.valueOf(count));
                count--;
            }

            public void onFinish() {
                textView20.setText("Finish");
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(1000); // 0.5초간 진동

//                Intent intent=new Intent(getApplicationContext(),test3Activity.class);
//                intent.putExtra("truecount",truenum);
//                startActivity(intent);
//                finish();



            }


        };


    }
}

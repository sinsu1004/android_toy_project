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
import androidx.core.app.ActivityCompat;

import com.example.project.R;
import com.example.project.settings;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class test2Activity extends AppCompatActivity {
    private static int count = 60;
    private static int MILLISINFUTURE = 10 * 6000;
    private static int COUNT_DOWN_INTERVAL = 1000;
    private static int truenum = 0;
    private static int passnum=3;


    private static ArrayList namelist = new ArrayList<>();
    private static Map<String,String> imagemap=new HashMap<>();


    private CountDownTimer countDownTimer;
    private TextView countTxt;
    private TextView problem;
    private TextView truetext;
    private ImageView image;
    private Button next;
    private Button pass;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test2);

        Intent intent = getIntent();

        namelist=(ArrayList)intent.getSerializableExtra("namelist");
        imagemap=(HashMap)intent.getSerializableExtra("imagemap");



        count=((settings) getApplication()).getCount();
        MILLISINFUTURE = 10 * 100 * count;
        passnum=((settings) getApplication()).getPasscount();

        next = (Button) findViewById(R.id.next);
        pass = (Button) findViewById(R.id.pass);
        countTxt = (TextView) findViewById(R.id.time);
        truetext = (TextView) findViewById(R.id.truetext);
        problem = (TextView) findViewById(R.id.problem);
        image =(ImageView) findViewById(R.id.imageView);




        problem.setText((String) namelist.get(0));
        loadImageTask imageTask = new loadImageTask(imagemap.get(namelist.get(0)));
        imageTask.execute();
        namelist.remove(0);
        truetext.setText("정답개수: " + truenum+"  남은 PASS 횟수: "+String.valueOf(passnum));
        countDownTimer();
        countDownTimer.start();





        pass.setOnClickListener(new View.OnClickListener() {  //pass 버튼을 누르면
            @Override
            public void onClick(View view) {
                if(passnum!=0){
                    //http 통신 수행
                    if(namelist.size()!=0) {
                        problem.setText((String) namelist.get(0));
                        loadImageTask imageTask = new loadImageTask(imagemap.get(namelist.get(0)));
                        imageTask.execute();
                        namelist.remove(0);
                        passnum--;
                        truetext.setText("정답개수: " + truenum + "  남은 PASS 횟수: " + String.valueOf(passnum));
                    }
                    else{
                        problem.setText("남은 문제가 없습니다 ㅎㅎ");
                    }
                }

            }
        });

        next.setOnClickListener(new View.OnClickListener() {  //정답 버튼을 누르면
           @Override
            public void onClick(View view) {
                //http 통신 수행
               if(namelist.size()!=0) {
                   loadImageTask imageTask = new loadImageTask(imagemap.get(namelist.get(0)));
                   imageTask.execute();
                   problem.setText((String) namelist.get(0));
                   namelist.remove(0);
                   truenum++;
                   truetext.setText("정답개수: " + truenum + "  남은 PASS 횟수: " + String.valueOf(passnum));
               }
               else{
                   problem.setText("남은 문제가 없습니다 ㅎㅎ");
               }

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
            image.setImageBitmap(bit);
        }
    }


    public void countDownTimer() {


        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            public void onTick(long millisUntilFinished) {

                countTxt.setText(String.valueOf(count));
                count--;
            }

            public void onFinish() {
                countTxt.setText("Finish");
                next.setEnabled(false);
                pass.setEnabled(false);
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(1000); // 0.5초간 진동

                Intent intent=new Intent(getApplicationContext(),test3Activity.class);
                intent.putExtra("truecount",truenum);
                startActivity(intent);
                finish();



            }


        };


    }

    @Override public void onBackPressed() {
        //super.onBackPressed();
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        ActivityCompat.finishAffinity(this);
        System.exit(0);
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        try{
            countDownTimer.cancel();

        }catch(Exception e) {}
        countDownTimer=null;

    }
}
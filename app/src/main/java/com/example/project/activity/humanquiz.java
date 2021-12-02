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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.HttpConnection;
import com.example.project.R;
import com.example.project.jsontype.datatype;
import com.example.project.settings;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;

public class humanquiz extends AppCompatActivity {

    private TextView textView20;
    private ImageView imageView5;
    private TextView textView22;
    private ArrayList<String> datalist=new ArrayList<>();
    private Button next2;
    private Button pass2;
    private CountDownTimer countDownTimer;
    private static int MILLISINFUTURE;
    private static int count;
    private static int truenum = 0;
    private int passnum;
    private static int COUNT_DOWN_INTERVAL = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.humanquiz);

        datatype a=new datatype();
        a.setName("dd");
        NetworkTask networkTask = new NetworkTask("http://192.168.0.14:5000/sinsu/humanquiz",a);
        networkTask.execute();
        textView20=findViewById(R.id.textView20);
        textView22=findViewById(R.id.textView22);
        imageView5=findViewById(R.id.imageView5);
        next2=findViewById(R.id.next2);
        pass2=findViewById(R.id.pass2);

        loadImageTask imageTask = new loadImageTask("https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/201608/04/htm_2016080484837486184.jpg");
        imageTask.execute();
        passnum=((settings) getApplication()).getPasscount();
        count=((settings) getApplication()).getCount();
        MILLISINFUTURE = 10 * 100 * count;
        countDownTimer();
        countDownTimer.start();
        textView22.setText("정답개수: " + truenum + "  남은 PASS 횟수: " + String.valueOf(passnum));
        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImageTask imageTask = new loadImageTask(datalist.get(0));
                imageTask.execute();
                datalist.remove(0);
                truenum++;
                textView22.setText("정답개수: " + truenum + "  남은 PASS 횟수: " + String.valueOf(passnum));
            }
        });

        pass2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passnum!=0){
                loadImageTask imageTask = new loadImageTask(datalist.get(0));
                imageTask.execute();
                datalist.remove(0);
                passnum--;
                textView22.setText("정답개수: " + truenum + "  남은 PASS 횟수: " + String.valueOf(passnum));
                }
                else {
                    Toast.makeText(getApplicationContext(), "남은 paas 가 없습니다.", Toast.LENGTH_SHORT).show();
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
            imageView5.setImageBitmap(bit);
        }
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {
        private String url;
        private datatype values;

        public NetworkTask(String url, datatype values) {
            this.url = url;
            this.values = values;
        }


        @Override
        protected String doInBackground(Void... voids) {
            JSONArray jsonArray =new JSONArray();
            //String result;  //요청 결괄르 저장할 변수
            HttpConnection recon=new HttpConnection();
            jsonArray=recon.request(url,values); //해당 URL로 부터 결과물을 얻어온다.
            for(int i=0;i<jsonArray.length();i++){
                try {
                    datalist.add(jsonArray.getJSONObject(i).getString("image"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {   //doInbackground()로 부터 리턴되 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            super.onPostExecute(s);

            Collections.shuffle(datalist);

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

                Intent intent=new Intent(getApplicationContext(),test3Activity.class);
                intent.putExtra("truecount",truenum);
                startActivity(intent);
                finish();



            }


        };


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

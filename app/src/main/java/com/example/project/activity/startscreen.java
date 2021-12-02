package com.example.project.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.project.HttpConnection;
import com.example.project.R;
import com.example.project.jsontype.datatype;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class startscreen extends AppCompatActivity {

    private static ArrayList namelist = new ArrayList<>();
    private static HashMap<String,String> imagemap=new HashMap<>();
    private String serverurl="http://192.168.0.14:5000/sinsu/movie";
    private TextView start;
    private CountDownTimer countDownTimer;
    private static int count = 3;
    private static int MILLISINFUTURE = 10 * 300;
    private static int COUNT_DOWN_INTERVAL = 1000;
    private boolean stop=true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startscreen);

        start=(TextView) findViewById(R.id.startView);
        datatype a=new datatype();
        a.setName("dd");
        NetworkTask networkTask = new NetworkTask(serverurl,a);
        networkTask.execute();
        Intent intent = getIntent();


        start.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(stop) {
                    stop=false;
                    countDownTimer();
                    countDownTimer.start();
                }
                return true;
            }
        });

    }

    public class NetworkTask extends AsyncTask<Void,Void,String> {  //서버 통신
        private  String url;
        private datatype values;

        public NetworkTask(String url,datatype values) {
            this.url =url;
            this.values=values;

        }
        @Override
        protected String doInBackground(Void... params){
            JSONArray jsonArray =new JSONArray();
            //String result;  //요청 결괄르 저장할 변수
            HttpConnection recon=new HttpConnection();
            jsonArray=recon.request(url,values); //해당 URL로 부터 결과물을 얻어온다.
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject1 = null;
                try {
                    jsonObject1 = jsonArray.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String name= null;
                try {
                    name = jsonObject1.getString("name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String image= null;
                try {
                    image = jsonObject1.getString("image");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                namelist.add(name);
                imagemap.put(name,image);

            }

            return "다운로드완료";
        }
        @Override
        protected void onPostExecute(String s) {   //doInbackground()로 부터 리턴되 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            super.onPostExecute(s);
            Collections.shuffle(namelist);


        }
    }

    public void countDownTimer() {


        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            public void onTick(long millisUntilFinished) {

                start.setText(String.valueOf(count));
                count--;
            }

            public void onFinish() {
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(1000); // 0.5초간 진동

                Intent intent=new Intent(getApplicationContext(),test2Activity.class);
                intent.putExtra("namelist",namelist);
                intent.putExtra("imagemap",imagemap);
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

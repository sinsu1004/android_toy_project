package com.example.project.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project.HttpConnection;
import com.example.project.R;
import com.example.project.jsontype.datatype;
import com.example.project.settings;

import org.json.JSONArray;

import java.util.ArrayList;

public class savelist extends Activity {
    EditText readhead;
    EditText readinfo;
    Button databasesave;
    Button bt_cancel;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.savelist);


        readhead=findViewById(R.id.readhead);
        readinfo=findViewById(R.id.readinfo);
        databasesave=findViewById(R.id.databasesave);
        bt_cancel=findViewById(R.id.bt_cancel);

        Intent intent = getIntent();
        data =(String) intent.getSerializableExtra("data");
        data=data.replace("\n","1");

        databasesave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a=readhead.getText().toString();
                String b=readinfo.getText().toString();
                if(a.equals("")){
                     Toast.makeText(getApplicationContext(), "제목을 입력하세요", Toast.LENGTH_LONG).show();
                }
                else if(b.equals("")){
                     Toast.makeText(getApplicationContext(), "설명을 입력하세요", Toast.LENGTH_LONG).show();
                }
                else{
                    settings settings=((settings) getApplication());
                    datatype c=new datatype();
                    c.setName(settings.getNickname());
                    c.setHead(a);
                    c.setIntroduce(b);
                    c.setData(data);
                    NetworkTask networkTask = new NetworkTask("http://192.168.0.14:5000/sinsu/save_userproblem",c);
                    networkTask.execute();


                }
                
            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



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


            return null;



        }

        @Override
        protected void onPostExecute(String s) {   //doInbackground()로 부터 리턴되 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            super.onPostExecute(s);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event){

        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }


}

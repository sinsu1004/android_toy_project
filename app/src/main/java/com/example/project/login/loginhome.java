package com.example.project.login;

import android.content.Intent;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.HttpConnection;
import com.example.project.R;
import com.example.project.activity.Mainscreen;
import com.example.project.jsontype.datatype;
import com.example.project.settings;

import org.json.JSONArray;
import org.json.JSONException;

public class loginhome extends AppCompatActivity {

    private JSONArray datalist;
    EditText new_nickname;
    Button check_nickname;
    ImageButton image_start;
    TextView check_text;
    TextView textView;
    String android;
    String nickname;
    boolean login=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginhome);

        new_nickname = findViewById(R.id.new_nickname);
        check_nickname = findViewById(R.id.check_nickname);
        image_start = findViewById(R.id.image_start);
        check_text = findViewById(R.id.check_text);
        textView=findViewById(R.id.textView);
        android = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        datatype c=new datatype();
        c.setName(android);
        NetworkTask networkTask=new NetworkTask("http://192.168.0.14:5000/sinsu/check_device",c);
        networkTask.execute();




        check_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datatype b=new datatype();
                b.setName(new_nickname.getText().toString());
                NetworkTask networkTask=new NetworkTask("http://192.168.0.14:5000/sinsu/check_nickname",b);
                networkTask.execute();


            }
        });
        image_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(login==false) {
                    datatype b = new datatype();
                    b.setName(android);
                    b.setImage(nickname);
                    NetworkTask networkTask = new NetworkTask("http://192.168.0.14:5000/sinsu/insert_nickname", b);
                    networkTask.execute();
                    settings settings=((settings) getApplication());
                    settings.setNickname(nickname);
                    Intent intent=new Intent(getApplicationContext(), Mainscreen.class);
                    startActivity(intent);
                    finish();
                }
                else if(login==true){
                    Intent intent=new Intent(getApplicationContext(), Mainscreen.class);
                    startActivity(intent);
                    finish();
                }
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
            JSONArray datas;
            HttpConnection recon=new HttpConnection();
            datas=recon.request(url,values);

            datalist=datas;
            return null;
        }

        @Override
        protected void onPostExecute(String s) {   //doInbackground()로 부터 리턴되 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            super.onPostExecute(s);

            try {
                if(datalist.getJSONObject(0).getString("data").equals("ok_nickname")){
                    check_text.setText("사용가능한 닉네임입니다. \n"+new_nickname.getText()+"으로 시작하시려면 ↓↓↓↓↓ 이미지를 눌러주세요");
                    check_text.setVisibility(View.VISIBLE);
                    image_start.setVisibility(View.VISIBLE);
                    new_nickname.clearFocus();
                    nickname=new_nickname.getText().toString();
                    new_nickname.setText("");


                } // 닉네임 중복 체크  사용가능 할때
                else if(datalist.getJSONObject(0).getString("data").equals("no_nickname")){
                    check_text.setText("이미 존재하는 닉네임입니다.");
                    check_text.setVisibility(View.VISIBLE);
                    image_start.setVisibility(View.INVISIBLE);
                    new_nickname.setText("");

                } // 닉네임 중복 체크 사용 불가능 할때
                else if(datalist.getJSONObject(0).getString("data").equals("no_device")){



                }
                else if(datalist.getJSONObject(0).getString("data").equals("ok_device")){
                    settings settings=((settings) getApplication());
                    settings.setNickname(datalist.getJSONObject(1).getString("nickname"));
                    image_start.setVisibility(View.VISIBLE);
                    new_nickname.setVisibility(View.GONE);
                    check_nickname.setVisibility(View.GONE);
                    check_text.setVisibility(View.GONE);

                    textView.setTextSize(50);
                    textView.setText("환영합니다\n"+settings.getNickname()+"님");
                    login=true;
                }
                else if(datalist.getJSONObject(0).getString("data").equals("save")){
                    Toast.makeText(getApplication(), "닉네임 저장 성공", Toast.LENGTH_LONG).show();

                }// 닉네임을 저장하고 게임창으로 넘어간다
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View focusView = getCurrentFocus();
        if (focusView != null) {
            Rect rect = new Rect();
            focusView.getGlobalVisibleRect(rect);
            int x = (int) ev.getX(), y = (int) ev.getY();
            if (!rect.contains(x, y)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
                focusView.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }  // editText가 아닌 다른 곳 클릭시 키보드 내리기





}

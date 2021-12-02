package com.example.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.project.R;

public class test3Activity extends AppCompatActivity {
    private TextView imfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test3);

        Intent intent = getIntent();
        int truecount = (int)intent.getSerializableExtra("truecount");
        imfo=(TextView) findViewById(R.id.trueView);
        imfo.setText("정답 개수: "+String.valueOf(truecount));







    }
    @Override public void onBackPressed() {
        //super.onBackPressed();
        Intent intent=new Intent(getApplicationContext(),Mainscreen.class);
        startActivity(intent);


        ActivityCompat.finishAffinity(this);
        System.exit(0);
    }
}

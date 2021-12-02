package com.example.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.project.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Intent intent = getIntent();
        String name= (String) intent.getSerializableExtra("name");

        ImageButton a1=findViewById(R.id.movie);
        TextView a3=(TextView) findViewById(R.id.textView2);
        a3.setText(name);


        a1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), startscreen.class);
                startActivity(intent);
                finish();
            }

        });



    }
    @Override public void onBackPressed() {
        //super.onBackPressed();
        Intent intent=new Intent(getApplicationContext(),Mainscreen.class);
        startActivity(intent);
        ActivityCompat.finishAffinity(this);
        System.exit(0);
    }

}
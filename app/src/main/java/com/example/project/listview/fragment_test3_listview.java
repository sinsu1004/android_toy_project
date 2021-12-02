package com.example.project.listview;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.project.R;

public class fragment_test3_listview extends LinearLayout {

    TextView testlist1,testlist2,testlist3,testlist4,
             testview;

    public fragment_test3_listview(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.fragment_test3_listview,this,true);

        testlist1=findViewById(R.id.textlist1);
        testlist2=findViewById(R.id.textlist2);
        testlist3=findViewById(R.id.textlist3);
        testlist4=findViewById(R.id.textlist4);

    }

    public fragment_test3_listview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public void setNickname(String name) {
        testlist3.setText(name);
    }
    public void setHead(String s){
        testlist1.setText(s);
    }
    public void setIntroduce(String s){
        testlist2.setText(s);
    }
    public void setViewCount(Integer a){
        testlist4.setText(a.toString());
    }


}

package com.example.project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project.R;
import com.example.project.activity.savelist;
import com.example.project.activity.startproblem;
import com.example.project.jsontype.up_datatype;
import com.example.project.settings;


import java.util.ArrayList;

public class Fragment_test1 extends Fragment {
    public Fragment_test1() {}
    private TextView textView;
    private Button save;
    private Button start2;
    private Button save2;
    private ImageButton back_bt;
    private EditText problems;
    private String problemview="";
    private ArrayList<String> problemlist=new ArrayList<>();
    private Bundle bundle;

    public static Fragment_test1 newInstance() {
        Fragment_test1 calendarFragment= new Fragment_test1();
        Bundle bundle = new Bundle();
        return calendarFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("제시어 내가만들기");
        View view = inflater.inflate(R.layout.fragment_test1, container, false);
        textView=view.findViewById(R.id.problemview);
        save=view.findViewById(R.id.problemsave);
        save2=view.findViewById(R.id.save2);
        start2=view.findViewById(R.id.start2);
        problems=view.findViewById(R.id.problems);
        back_bt=view.findViewById(R.id.back_bt);
        textView.setMovementMethod(new ScrollingMovementMethod());


        if (((settings)getActivity().getApplication()).getName()!=null) {
            problems.setEnabled(false);
            save.setEnabled(false);
            save2.setEnabled(false);
            start2.setEnabled(false);
            back_bt.setEnabled(false);
            String data=((settings)getActivity().getApplication()).getData();
            data=data.replace("1","\n");
            textView.setText(data);
            String[] problemlist2=data.split("\n");
            for(String i:problemlist2){
                problemlist.add(i);
            }
            start2.setEnabled(true);

        }

        back_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int a=problemview.lastIndexOf("\n");
                problemview=problemview.substring(0,a);
                problemlist.remove(problemlist.size()-1);
                textView.setText(problemview);
            }
        });


        save.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                problemview+="\n";
                problemview+=problems.getText();
                problemlist.add(problems.getText().toString());
                problems.setText("");
                textView.setText(problemview);
            }
        });
        start2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),startproblem.class);
                intent.putExtra("problemlist",problemlist);
                startActivity(intent);


            }
        });

        save2.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), savelist.class);
                intent.putExtra("data",problemview);
                startActivityForResult(intent,1);
            }
        });




        return view;
    }





}

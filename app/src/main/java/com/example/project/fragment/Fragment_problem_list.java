package com.example.project.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.project.HttpConnection;
import com.example.project.R;
import com.example.project.activity.Mainscreen;
import com.example.project.jsontype.datatype;
import com.example.project.listViewitem;
import com.example.project.listview.fragment_test3_listview;
import com.example.project.settings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_problem_list extends Fragment {
    public Fragment_problem_list() {}
    private JSONArray datalist;
    private Button bodyspeak_button;
    private String serverurl="http://192.168.0.14:5000/sinsu/userproblem";
    private ListView lv;
    private SingleAdapter adapter=new SingleAdapter();
    private Mainscreen activity;

    public static Fragment_problem_list newInstance() {
        Fragment_problem_list calendarFragment= new Fragment_problem_list();
        Bundle bundle = new Bundle();
        return calendarFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("사용자가 만든 제시어");
        View view = inflater.inflate(R.layout.fragment_problem_list, container, false);
        activity= (Mainscreen) getActivity();
        SearchView searchView =view.findViewById(R.id.search_view);
        lv=view.findViewById(R.id.listView);

        datatype a=new datatype();
        a.setId(0);
        NetworkTask networkTask = new NetworkTask(serverurl,a);
        networkTask.execute();


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listViewitem data=(listViewitem) adapterView.getItemAtPosition(i);
                datatype c=new datatype();
                c.setName(data.getHead());
                NetworkTask networkTask = new NetworkTask("http://192.168.0.14:5000/sinsu/userselect",c);
                networkTask.execute();



                //TabLayout.Tab tabView = activity.getTabLayout().getTabAt(1);//tab이동
                //tabView.select();

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String s) {
               // Toast.makeText(getActivity(),"[검색버튼클릭] 검색어 = "+s, Toast.LENGTH_LONG).show();
                datatype b=new datatype();
                b.setName(s);
                NetworkTask networkTask = new NetworkTask("http://192.168.0.14:5000/sinsu/usersearch",b);
                networkTask.execute();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
               // Toast.makeText(getActivity(), "입력하고있는 단어 = "+s, Toast.LENGTH_LONG).show();
                datatype b=new datatype();
                b.setName(s);
                NetworkTask networkTask = new NetworkTask("http://192.168.0.14:5000/sinsu/usersearch",b);
                networkTask.execute();
                return true;



            }
        });


        return view;
    }

    public class NetworkTask extends AsyncTask<Void,Void,String> {
        private String url;
        private datatype values;

        public NetworkTask(String url, datatype values) {
            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... voids) {
            JSONArray jsonArray;
            HttpConnection recon=new HttpConnection();
            jsonArray=recon.request(url,values); //해당 URL로 부터 결과물을 얻어온다.
            datalist=jsonArray;

            return "성공입니당";
        }

        @Override
        protected void onPostExecute(String s) {   //doInbackground()로 부터 리턴되 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            super.onPostExecute(s);
            adapter.removeAll();

            try {
                JSONObject star=datalist.getJSONObject(0);

                try {
                    String test = star.getString("data");
                    ((settings)getActivity().getApplication()).setName(datalist.getJSONObject(0).getString("name"));
                    ((settings)getActivity().getApplication()).setHead(datalist.getJSONObject(0).getString("head"));
                    ((settings)getActivity().getApplication()).setIntroduce(datalist.getJSONObject(0).getString("introduce"));
                    ((settings)getActivity().getApplication()).setViewcount(datalist.getJSONObject(0).getInt("viewcount"));
                    ((settings)getActivity().getApplication()).setData(datalist.getJSONObject(0).getString("data"));

                    ViewPager viewPager = activity.getViewPager();
                    viewPager.setCurrentItem(3,false);
                    // Toast.makeText(getActivity(), test, Toast.LENGTH_LONG).show();

                }catch (Exception e){
                    for(int i=0;i<datalist.length();i++){
                        try {
                            JSONObject jo=datalist.getJSONObject(i);

                            String name = jo.getString("name");
                            String head = jo.getString("head");
                            String introduce = jo.getString("introduce");
                            String viewcount = jo.getString("viewcount");
                            adapter.addItem(new listViewitem(name, head, introduce, Integer.parseInt(viewcount)));
                            lv.setAdapter(adapter);

                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }

                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    class SingleAdapter extends BaseAdapter{
        ArrayList<listViewitem> items =new ArrayList<listViewitem>();

        @Override
        public int getCount() {
            return items.size();
        }
        public void addItem(listViewitem a){
            items.add(a);
        }

        public void removeItem(listViewitem a){
            items.remove(a);
        }

        public void removeAll(){
            items.clear();
        }

        @Override
        public Object getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            fragment_test3_listview listview=null;
            if(listview==null){
                listview=new fragment_test3_listview(getActivity().getApplicationContext());

            }else{
                listview=(fragment_test3_listview)view;
            }
            listViewitem item=items.get(i);
            listview.setNickname(item.getName());
            listview.setHead(item.getHead());
            listview.setIntroduce(item.getIntroduce());
            listview.setViewCount(item.getCount());



            return listview;
        }
    }



    @Override
    public void onStart() {

        super.onStart();
    }
}


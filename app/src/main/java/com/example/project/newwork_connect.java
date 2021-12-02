package com.example.project;

import android.os.AsyncTask;

import com.example.project.jsontype.datatype;

public class newwork_connect {

    public class NetworkTask extends AsyncTask<Void, Void, String> {
        private String url;
        private datatype values;

        public NetworkTask(String url, datatype values) {
            this.url = url;
            this.values = values;
        }


        @Override
        protected String doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {   //doInbackground()로 부터 리턴되 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            super.onPostExecute(s);
        }
    }
}

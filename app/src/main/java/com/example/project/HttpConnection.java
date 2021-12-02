package com.example.project;

import com.example.project.jsontype.datatype;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpConnection {
    public JSONArray request(String url, datatype _params){


        //HttpURLConnection 참조 변수.
        HttpURLConnection urlConn = null;
        //URL 뒤세 붙여서 보낼 파라미터.
        StringBuffer data=new StringBuffer();

        try{
            String json="";

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("id",_params.getId());
            jsonObject.accumulate("name",_params.getName());
            jsonObject.accumulate("image",_params.getImage());
            jsonObject.accumulate("head",_params.getHead());
            jsonObject.accumulate("introduce",_params.getIntroduce());
            jsonObject.accumulate("data",_params.getData());
            json=jsonObject.toString();
            URL urls=new URL(url);
            urlConn =(HttpURLConnection) urls.openConnection();


            //[2-1] urlConn 설정
            urlConn.setRequestMethod("POST"); //URL 요청에 대한 메소드 설정 : POST
            urlConn.setRequestProperty("Accept", "application/json");
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Content-type", "application/json");

            // [2-2]. parameter 전달 및 데이터 읽어오기.
            //String strParams = sbParams.toString(); //sbParams에 정리한 파라미터들을 스트링으로 저장. 예)id=id1&pw=123;
            OutputStream os = urlConn.getOutputStream();
            os.write(json.getBytes("UTF-8")); // 출력 스트림에 출력.
            os.flush(); // 출력 스트림을 플러시(비운다)하고 버퍼링 된 모든 출력 바이트를 강제 실행.
            os.close(); // 출력 스트림을 닫고 모든 시스템 자원을 해제.

            //[2-3] 연결 요청 확인
            //실패 시 null을 리턴하고 메서드를 종료.
            if(urlConn.getResponseCode() != HttpURLConnection.HTTP_OK)
                return null;
            //[2-4] 읽어온 결과물 리턴.
            //요청한 URL의 출력물을 BufferedReader로 받는ㄷ.
            BufferedReader reader=new BufferedReader(new InputStreamReader(urlConn.getInputStream(),"UTF-8"));

            // 출력물의 라인과 그 합에 대한 변수.
            String line;
            String page= "";
            while((line = reader.readLine()) !=null){
                page+=line;
            }

            JSONArray jsonArray =new JSONArray(page);



            return jsonArray;


        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if(urlConn !=null)
                urlConn.disconnect();
        }

        return null;

    }
}

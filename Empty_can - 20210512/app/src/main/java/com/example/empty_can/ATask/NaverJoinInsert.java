package com.example.empty_can.ATask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import static com.example.empty_can.Common.CommonMethod.ipConfig;

public class NaverJoinInsert extends AsyncTask<Void, Void, String> {

    private static final String TAG = "main:naverJoinInsert";

    String m_name, m_mobile, m_nikname, m_email, m_gender;

    public NaverJoinInsert(String m_name, String m_mobile, String m_nikname, String m_email, String m_gender) {

        this.m_name = m_name;
        this.m_mobile = m_mobile;
        this.m_nikname = m_nikname;
        this.m_email = m_email;
        this.m_gender = m_gender;
    }

    String state = "";
        HttpClient httpClient;
        HttpPost httpPost;
        HttpResponse httpResponse;
        HttpEntity httpEntity;


    @Override
        protected String doInBackground(Void... voids) {
            try {
                // MultipartEntityBuild 생성
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                builder.setCharset(Charset.forName("UTF-8"));

                // 문자열 및 데이터 추가
                builder.addTextBody("m_name", m_name, ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("m_mobile", m_mobile, ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("m_nikname", m_nikname, ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("m_email", m_email, ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("m_gender", m_gender, ContentType.create("Multipart/related", "UTF-8"));

                String postURL = ipConfig + "/justdo_eat/memberJoin";
                Log.d(TAG, "naverJoinInsert: " + "name : "+ m_name + "mobile : " + m_mobile + "email : "  + m_email  + "gender : " + m_gender + "nikname :" + m_nikname);

                // 전송
                InputStream inputStream = null;
                httpClient = AndroidHttpClient.newInstance("Android");
                httpPost = new HttpPost(postURL);
                httpPost.setEntity(builder.build());
                httpResponse = httpClient.execute(httpPost);
                httpEntity = httpResponse.getEntity();
                inputStream = httpEntity.getContent();

                // 응답
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuilder stringBuilder = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line + "\n");
                }
                state = stringBuilder.toString();

                inputStream.close();

            }  catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(httpEntity != null){
                    httpEntity = null;
                }
                if(httpResponse != null){
                    httpResponse = null;
                }
                if(httpPost != null){
                    httpPost = null;
                }
                if(httpClient != null){
                    httpClient = null;
                }

            }

            return state;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Log.d(TAG, "onPostExecute: " + result);
        }
    }






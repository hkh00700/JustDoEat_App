package com.example.empty_can.ATask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;

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

public class Email_check extends AsyncTask<Void, Void, String> {
    String m_email;

    public Email_check(String m_email) {
        this.m_email = m_email;
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
            builder.addTextBody("m_email", m_email, ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + "/justdo_eat/email_check";

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
}

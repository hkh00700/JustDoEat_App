package com.example.empty_can.ATask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.empty_can.DTO.MemberDTO;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.example.empty_can.Common.CommonMethod.ipConfig;
import static com.example.empty_can.Common.CommonMethod.loginDTO;

public class KakaoSelect extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "main:KakaoSelect";
    String m_email;

    public KakaoSelect(String m_email) {
        this.m_email = m_email;

        Log.d(TAG, "LoginSelect: " + m_email);
    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected Void doInBackground(Void... voids) {
        
                
        try {
        // MultipartEntityBuild 생성
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        // 문자열 및 데이터 추가
        builder.addTextBody("m_email", m_email, ContentType.create("Multipart/related", "UTF-8"));

        String postURL = ipConfig + "/justdo_eat/kakaoMemberLogin";

        // 전송
        InputStream inputStream = null;
        httpClient = AndroidHttpClient.newInstance("Android");
        httpPost = new HttpPost(postURL);
        httpPost.setEntity(builder.build());
        httpResponse = httpClient.execute(httpPost);
        httpEntity = httpResponse.getEntity();
        inputStream = httpEntity.getContent();

        loginDTO = readMessage(inputStream);

        inputStream.close();

    } catch (Exception e) {
        Log.d("main:loginselect", e.getMessage());
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
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

    }

    public MemberDTO readMessage(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

        String id = "", name = "", phone = "", email = "", gender = "", nikname="";

        reader.beginObject();
        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("m_id")) {
                id = reader.nextString();
            } else if (readStr.equals("m_name")) {
                name = reader.nextString();
            } else if (readStr.equals("m_phone")) {
                phone = reader.nextString();
            } else if (readStr.equals("m_email")) {
                email = reader.nextString();
            } else if (readStr.equals("m_gender")) {
                gender = reader.nextString();
            } else if (readStr.equals("m_nikname")) {
                nikname = reader.nextString();
            }else {
                reader.skipValue();
            }
        }
        reader.endObject();
        Log.d("main:loginselect : ", id + "," + name + "," + phone + "," + email + "," + gender + "," + nikname);
        return new MemberDTO(id, name, phone, email, gender, nikname);


    }

}

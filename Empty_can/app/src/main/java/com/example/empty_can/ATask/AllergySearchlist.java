package com.example.empty_can.ATask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static com.example.empty_can.Common.CommonMethod.ipConfig;

public class AllergySearchlist extends AsyncTask {

    private static final String TAG = "AllergySearchlist";

    //ArrayList<String> list;
    String list;
    String state;
    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;
    ArrayList<String>  searchlist;
    @Override
    protected String doInBackground(Object[] objects) {

        try {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));
            String postURL = ipConfig + "/justdo_eat/allergySearchlist";

            //전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();


            //받는 것

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuffer stringBuffer = new StringBuffer();
            String line = null;

            while((line = bufferedReader.readLine()) != null){
                stringBuffer.append(line + "\n");
            }
            state = stringBuffer.toString().trim();

                //: list를 줄 순서로 list에 담기
            //new FoodRandom(food);

            inputStream.close();


        }catch (Exception e){
            e.getMessage();
        }finally {
            if(httpEntity != null) httpEntity = null;
            if(httpResponse != null) httpResponse = null;
            if(httpPost != null) httpPost = null;
            if(httpClient != null) httpClient = null;
        }

        return state;

    }
}

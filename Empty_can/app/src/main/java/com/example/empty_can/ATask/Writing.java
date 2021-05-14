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
import org.apache.http.entity.mime.content.FileBody;

import java.io.File;
import java.nio.charset.Charset;

import static com.example.empty_can.Common.CommonMethod.ipConfig;

public class Writing extends AsyncTask<Void, Void, String> { 
    private static final String TAG = "main:UserReviewInsert";

    String s_title, s_content, s_nikname, s_photo_path, imageRealPath;

    public Writing(String s_title, String s_content, String s_nikname, String s_photo_path, String imageRealPath) {
        this.s_title = s_title;
        this.s_content = s_content;
        this.s_nikname = s_nikname;
        this.s_photo_path = s_photo_path;
        this.imageRealPath = imageRealPath;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    //성공여부 확인 하는 값
    String state ="";

    @Override
    protected String doInBackground(Void... voids) {
        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));
            String nikname = "niks1";//유저 닉네임 값
            // 문자열 및 데이터 추가
            builder.addTextBody("s_title", s_title, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("s_content", s_content, ContentType.create("Multipart/related", "UTF-8"));
            if(s_photo_path != null)
                builder.addTextBody("s_photo_path", s_photo_path, ContentType.create("Multipart/related", "UTF-8"));
            if(s_photo_path != null)
                builder.addPart("image", new FileBody(new File(s_photo_path)));

            String postURL = ipConfig + "/justdo_eat/ReviewInsert";
            // 전송
            //InputStream inputStream = null;
            HttpClient httpClient = AndroidHttpClient.newInstance("Android");
            HttpPost httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            //inputStream = httpEntity.getContent();

            // 응답
            /*BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }
            inputStream.close();*/

            // 응답결과
            /*String result = stringBuilder.toString();
            Log.d("response", result);*/

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(String s) {
    }
}

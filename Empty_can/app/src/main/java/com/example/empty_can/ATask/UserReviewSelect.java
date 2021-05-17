package com.example.empty_can.ATask;

import android.app.ProgressDialog;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.empty_can.Adapter.ReviewAdapter;
import com.example.empty_can.DTO.MemberReviewDTO;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.example.empty_can.Common.CommonMethod.ipConfig;


public class UserReviewSelect extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "main:UserReviewSelect";

    ArrayList<MemberReviewDTO> userReviewList;
    ReviewAdapter adapter;
    ProgressDialog progressDialog;


    public UserReviewSelect(ArrayList<MemberReviewDTO> userReviewList, ReviewAdapter adapter, ProgressDialog progressDialog) {
        this.userReviewList = userReviewList;
        this.adapter = adapter;
        this.progressDialog = progressDialog;
    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        userReviewList.clear();
        String result = "";
        String postURL = ipConfig + "/justdo_eat/ReviewSelect";

        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            //builder.setCharset(Charset.forName("UTF-8"));
            //String nikname = "niks";//현재로그인중인 회원 닉네임정보
            //builder.addTextBody("s_nikname", nikname, ContentType.create("Multipart/related", "UTF-8"));//현재로그인중인 회원 닉네임정보


            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            readJsonStream(inputStream);

            /*BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }
            String jsonStr = stringBuilder.toString();

            inputStream.close();*/

        } catch (Exception e) {
            Log.d("", e.getMessage());
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
        super.onPostExecute(aVoid);

        if(progressDialog != null){
            progressDialog.dismiss();
        }

        Log.d("ShowReviewActivity", "UserReviewSelect Complete!!!");

        adapter.notifyDataSetChanged();
    }

    public void readJsonStream(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                userReviewList.add(readMessage(reader));
            }
            reader.endArray();
        } finally {
            reader.close();
        }
    }

    public MemberReviewDTO readMessage(JsonReader reader) throws IOException {
        String content = "", photo_path = "", title = "", id = "";
        int no = 0;
        reader.beginObject();
        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("s_title")) {
                title = reader.nextString();
           } else if (readStr.equals("s_content")) {
                content = reader.nextString();
            } else if (readStr.equals("s_photo_path")) {
                photo_path = reader.nextString();
            } else if (readStr.equals("no")) {
                no = reader.nextInt();
            } else if (readStr.equals("s_id")) {
                id = reader.nextString();
            }   else {
                reader.skipValue();
            }
        }
        reader.endObject();
        Log.d("listselect:myitem", content + "," + title + ","  + photo_path);
        return new MemberReviewDTO(title, content, photo_path, no, id);

    }

    /*public List<Double> readDoublesArray(JsonReader reader) throws IOException {
        List<Double> doubles = new ArrayList<Double>();

        reader.beginArray();
        while (reader.hasNext()) {
            doubles.add(reader.nextDouble());
        }
        reader.endArray();
        return doubles;
    }

    public User readUser(JsonReader reader) throws IOException {
        String username = null;
        int followersCount = -1;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("name")) {
                username = reader.nextString();
            } else if (name.equals("followers_count")) {
                followersCount = reader.nextInt();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new User(username, followersCount);
    }*/

}



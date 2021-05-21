package com.example.empty_can;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.empty_can.ATask.Writing;
import com.example.empty_can.Adapter.ReviewAdapter;
import com.example.empty_can.Common.CommonMethod;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static com.example.empty_can.Common.CommonMethod.ipConfig;
import static com.example.empty_can.Common.CommonMethod.isNetworkConnected;

public class WritingActivity extends AppCompatActivity {
    private static final String TAG = "main:ReviewWriting";

    MediaController m;

    public String s_phto_path, imageRealPath;

    EditText title, content;
    String s_content = "", s_id = "";
    String s_title = "";

    final int CAMERA_REQUEST = 1000;
    final int LOAD_IMAGE = 1001;
    Button btnLoad;
    ReviewAdapter adapter;

    File file = null;
    long fileSize = 0;
    ImageView imageView;
    String imgFilePath = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);

        imageView = findViewById(R.id.imageView2);
        content = (EditText)findViewById(R.id.ucontent);
        title = (EditText)findViewById(R.id.utitle);
        btnLoad = (Button) findViewById(R.id.btnLoad);




        //사진로드
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setVisibility(View.VISIBLE);

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), LOAD_IMAGE);
            }
        });

    }
    private File createFile() throws IOException {

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "My" + timestamp +".jpg";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File curFile = null;
        try {
            curFile = File.createTempFile(imageFileName,".jpg", storageDir);
        } catch (IOException e) {
            //  Log.d(TAG, "createFile: " + e.getMessage());;
        }

        imgFilePath = curFile.getAbsolutePath();
        Log.d("main:paths", imgFilePath);

        return  curFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            galleryAddPic();
            setPic();
        }else if (requestCode == LOAD_IMAGE && resultCode == RESULT_OK) {
            try {
                String path = "";
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    // Get the path from the Uri
                    path = getPathFromURI(selectedImageUri);
                }
                // 이미지 돌리기 및 리사이즈
                Bitmap newBitmap = CommonMethod.imageRotateAndResize(path);
                if(newBitmap != null){
                    imageView.setImageBitmap(newBitmap);
                }else{
                    Toast.makeText(this, "이미지가 null 입니다...", Toast.LENGTH_SHORT).show();
                }

                imageRealPath = path;
                String uploadFileName = imageRealPath.split("/")[imageRealPath.split("/").length - 1];
                s_phto_path = ipConfig + "/justdo_eat/resources/" + uploadFileName;
            } catch (Exception e){
                e.printStackTrace();
            }
        }

    }




    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imgFilePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    // 크기가 조정된 이미지 디코딩
    private void setPic() {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(imgFilePath, bmOptions);
        imageView.setImageBitmap(bitmap);
        imageRealPath = file.getAbsolutePath();
        String uploadFileName = imageRealPath.split("/")[imageRealPath.split("/").length - 1];
        s_phto_path = ipConfig + "/justdo_eat/resources/" + uploadFileName;
    }
    // Get the real path from the URI
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    public void btnAddClicked(View view){

        if(isNetworkConnected(this) == true){
            Log.d(TAG, "btnAddClicked: fileSize : " + fileSize);
            if(fileSize <= 30000000){  // 파일크기가 30메가 보다 작아야 업로드 할수 있음
                    s_content = content.getText().toString();
                    s_title = title.getText().toString();
                    Log.d(TAG, "btnAddClicked: s_phto_path" + s_phto_path);
                    Writing userReviewInsert = new Writing(s_title, s_content, s_id, s_phto_path, imageRealPath);
                try {
                    userReviewInsert.execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //activity.finish();

                Intent showIntent = new Intent(getApplicationContext(), MainActivity.class);
                showIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TOP);
                showIntent.putExtra("num", 5);
                startActivity(showIntent);

            // showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |   // 이 엑티비티 플래그를 사용하여 엑티비티를 호출하게 되면 새로운 태스크를 생성하여 그 태스크안에 엑티비티를 추가하게 됩니다. 단, 기존에 존재하는 태스크들중에 생성하려는 엑티비티와 동일한 affinity(관계, 유사)를 가지고 있는 태스크가 있다면 그곳으로 새 엑티비티가 들어가게됩니다.
            //           Intent.FLAG_ACTIVITY_SINGLE_TOP | // 엑티비티를 호출할 경우 호출된 엑티비티가 현재 태스크의 최상단에 존재하고 있었다면 새로운 인스턴스를 생성하지 않습니다. 예를 들어 ABC가 엑티비티 스택에 존재하는 상태에서 C를 호출하였다면 여전히 ABC가 존재하게 됩니다.
            //           Intent.FLAG_ACTIVITY_CLEAR_TOP); // 만약에 엑티비티스택에 호출하려는 엑티비티의 인스턴스가 이미 존재하고 있을 경우에 새로운 인스턴스를 생성하는 것 대신에 존재하고 있는 엑티비티를 포그라운드로 가져옵니다. 그리고 엑티비티스택의 최상단 엑티비티부터 포그라운드로 가져올 엑티비티까지의 모든 엑티비티를 삭제합니다.*/
            //MainActivity activity = new MainActivity();
            //activity.MainActivity(2);
               //showIntent.putExtra("num", 2);
                //startActivity(showIntent);

                finish();

                Toast.makeText(this, "리뷰가 등록되었습니다.", Toast.LENGTH_SHORT).show();


            }else{
                // 알림창 띄움
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("알림");
                builder.setMessage("파일 크기가 30MB초과하는 파일은 업로드가 제한되어 있습니다.\n30MB이하 파일로 선택해 주십시요!!!");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }

        }else {
            Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.",
                    Toast.LENGTH_SHORT).show();
        }

    }
    public void btnCancelClicked(View view){
        finish();
    }
}
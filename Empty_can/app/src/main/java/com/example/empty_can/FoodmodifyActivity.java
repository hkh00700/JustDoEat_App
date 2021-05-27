package com.example.empty_can;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.empty_can.ATask.AllergyGet;
import com.example.empty_can.ATask.AllergyInsert;
import com.example.empty_can.ATask.AllergySearchlist;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.empty_can.Common.CommonMethod.loginDTO;


public class FoodmodifyActivity extends AppCompatActivity {
    private static final String TAG = "main:FoodmodifyActivity";

    ListView mlistView;
    CheckBox mcheckBox;
    Button mButton, btnModi;
    ArrayList<String> resultlist = new ArrayList<String>(); //선택결과를 담을 리스트
    String allergyStr = "";
    String[] allergys = null;





    private AllergyAdapter adapter = null;
    //    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayList<ListViewItem> arrayList = new ArrayList<ListViewItem>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodmodify);

        checkDangerousPermissions();

        setLayout();


        AllergySearchlist allergySearchlist = new AllergySearchlist();
        String search = "";
        AllergyGet allergyGet = new AllergyGet(loginDTO.getNikname());
        try {
            String allergy = allergyGet.execute().get();
            Log.d(TAG, "onCreate: 알러지" + allergy);
            allergys = allergy.split(",");
            Log.d(TAG, "onCreate: 알러지 리스트" + allergys);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        try {
            search = (String) allergySearchlist.execute().get();
            Log.d(TAG, "settingList: " + search);
            String[] searchs = search.split(",");

            for (int i = 0; i < searchs.length; i++) {
                String s = searchs[i].trim();
                arrayList.add(new ListViewItem(s,false));
                for(int j = 0; j < allergys.length; j++){
                    if(allergys[j].trim().equals(s) ) {
                        arrayList.get(i).setIsCheckable(true);
                        //arrayList.add(new ListViewItem(s, true));
                        Log.d(TAG, "onCreate: s 모든 리스트 for" + s);
                        //arrayList.remove(allergys[j]);

                    }

                }

            }

           /* for (int i = 0; i < searchs.length; i++) {
                String s = searchs[i].trim();
                arrayList.add(new ListViewItem(s, false));
                Log.d(TAG, "onCreate: s 모든 리스트" + s);

                for(int j = 0; j < allergys.length; j++){
                    if(allergys[j].toString().equals(s) ) {
                        Log.d(TAG, "onCreate: allergys" + allergys[j] + j);
                        Log.d(TAG, "onCreate: s 모든 리스트 for" + s);

                        arrayList.get(i).setIsCheckable(true);
                    }
                    Log.d(TAG, "onCreate: s 모든 리스트 for2" + s);

                }
            }*/










        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        adapter = new AllergyAdapter(FoodmodifyActivity.this, arrayList);
        mlistView.setAdapter(adapter);
        mlistView.setOnItemClickListener(mItemClickListener);


        btnModi = findViewById(R.id.btnModi);

        btnModi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String allergyStr = "";

              for (int i = 0; i < resultlist.size(); i++) {
                    if (i < resultlist.size() - 1) {
                        allergyStr += resultlist.get(i) + ",";
                    } else {
                        allergyStr += resultlist.get(i);
                    }
                }
                Toast.makeText(FoodmodifyActivity.this, "선택알러지 : " + allergyStr, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick: " + allergyStr);


                // 데이터베이스에 삽입
                AllergyInsert allergyInsert = new AllergyInsert(loginDTO.getNikname(), allergyStr);
                try {
                    String allergy = allergyInsert.execute().get();

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                finish();
            }




        });




    }

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            adapter.setChecked(position);

            if(adapter.isCheckedConfirm[position]){
                Toast.makeText(FoodmodifyActivity.this, adapter.sArrayList.get(position).getName() + "을 선택하셨습니다.", Toast.LENGTH_SHORT).show();
                resultlist.add(adapter.sArrayList.get(position).name);
                adapter.notifyDataSetChanged();

            } else {
                Toast.makeText(FoodmodifyActivity.this, adapter.sArrayList.get(position).name + "을 선택해제 하셨습니다.", Toast.LENGTH_SHORT).show();
                for(int i = 0; i < resultlist.size(); i++){
                    if(resultlist.get(i).equals(adapter.sArrayList.get(position)))
                        resultlist.remove(i);
                }

                adapter.notifyDataSetChanged();

            }
            adapter.notifyDataSetChanged();
        }
    };




    private void setLayout() {
        mlistView = findViewById(R.id.listView_main);
        mcheckBox = findViewById(R.id.checkBox_main);
/*
    //    mButton = findViewById(R.id.button_main);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i = 0; i < adapter.geChecked().size(); i++){
                    int a = adapter.geChecked().get(i);
                    mButton.setText("현재 선택하신 알레르기는 : " + adapter.getItem(a).toString());
                }

            }
        });
*/



        //전체선택 했을 때 좀 더 고민해보기.
        mcheckBox = findViewById(R.id.checkBox_main);
        mcheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.allnoCheck(mcheckBox.isChecked());
                adapter.notifyDataSetChanged();
            }
        });
    }






    //인터넷 연결
    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            //        Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            //        Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                //           Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                //            ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    //         Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    //          Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}



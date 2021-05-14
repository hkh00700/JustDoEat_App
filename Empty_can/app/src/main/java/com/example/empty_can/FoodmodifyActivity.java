
package com.example.empty_can;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.example.empty_can.ATask.AllergyInsert;
import com.example.empty_can.ATask.AllergySearchlist;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.empty_can.Common.CommonMethod.loginDTO;

public class FoodmodifyActivity extends AppCompatActivity {
    private static final String TAG = "main:FoodmodifyActivity";
   /* ListView listView;
    AllergyAdapter adapter;
    ArrayList<ListViewItem> items;
    ArrayList<String> list;*/

    TextView textView;

    String test;

    /*SearchFragment searchFragment;
    AllergyListFragment allergyListFragment;*/

    Button btnModi,btnReset;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodmodify);

        checkDangerousPermissions();
       /*FragmentManager manager = getSupportFragmentManager();
       allergyListFragment = (AllergyListFragment) manager.findFragmentById(R.id.fragment);
       searchFragment = new SearchFragment();

       btnModi=findViewById(R.id.btnModi);
       btnReset=findViewById(R.id.btnReset);




       btnReset.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Toast.makeText(FoodmodifyActivity.this, "음식취향수정을 취소했습니다.", Toast.LENGTH_SHORT).show();
               finish();
           }
       });


       btnModi.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(android.view.View v) {
               ArrayList<String> allergyList =  allergyListFragment.adapter.getList();
               Log.d(TAG, "onClick: " + allergyList);

               String allergyStr = "";
               // 데이터베이스에 삽입하기 위해 쉼표 작업하기
               for(int i=0; i<allergyList.size(); i++){
                   if(i < allergyList.size()-1){
                       allergyStr += allergyList.get(i) + ",";
                   }else {
                       allergyStr += allergyList.get(i);
                   }

               }
               Log.d(TAG, "onClick: " + allergyStr);
               if(allergyStr != "") {
                   Toast.makeText(FoodmodifyActivity.this, allergyList + "을(를) 알레르기정보에 저장했습니다.", Toast.LENGTH_SHORT).show();
               }else{
                   Toast.makeText(FoodmodifyActivity.this, "알레르기 정보를 입력하지 않았습니다.", Toast.LENGTH_SHORT).show();

               }
               // 데이터베이스에 삽입
               AllergyInsert allergyInsert = new AllergyInsert(loginDTO.getId(), allergyStr);
               try {
                   allergyInsert.execute().get();
               } catch (ExecutionException e) {
                   e.printStackTrace();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }

               finish();
           }
       });


*/



     /*   items = new ArrayList<>();
        list = new ArrayList<>();

        adapter = new AllergyAdapter(FoodmodifyActivity.this, items, list);

        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        adapter.addItem(new ListViewItem("없음", false));
        adapter.addItem(new ListViewItem("갑각류", false));
        adapter.addItem(new ListViewItem("견과류", false));
        adapter.addItem(new ListViewItem("계란", false));
        adapter.addItem(new ListViewItem("밀류(글루텐)", false));
        adapter.addItem(new ListViewItem("우유", false));
        adapter.addItem(new ListViewItem("조개류", false));
        adapter.addItem(new ListViewItem("콩류", false));
        adapter.addItem(new ListViewItem("육류", false));
*/



    /*    test = "";
        AllergySearchlist allergySearchlist = new AllergySearchlist();
        try {
            test = (String) allergySearchlist.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        textView = findViewById(R.id.testText);
        textView.setText(test);
*/



    }

    public void ChangeFragment(int state){
       /* if(state == 0){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, searchFragment).addToBackStack(null).commit();
        } else if (state == 1){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, allergyListFragment).addToBackStack(null).commit();
        }
*/
        //두번째 프래그먼트로 가는거, list 체크했을 때 없어지는거 그대로 있게 하기





    }

    public ArrayList<String> Allergylist(ArrayList<String> searchlist){
        AllergySearchlist allergySearchlist = new AllergySearchlist();
        try {
            searchlist = (ArrayList<String>) allergySearchlist.execute().get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return searchlist;
    }



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
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}



package com.example.empty_can;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.empty_can.ATask.LoginSelect;
import com.kakao.sdk.auth.LoginClient;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.common.util.Utility;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import java.util.concurrent.ExecutionException;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

import static com.example.empty_can.Common.CommonMethod.loginDTO;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "main:LoginActivity";

    EditText u_id, u_pw;
    Button btnLogin, btnJoin;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        u_id = findViewById(R.id.u_id);
        u_pw = findViewById(R.id.u_pw);

        btnJoin = findViewById(R.id.btnJoin);
        btnLogin = findViewById(R.id.btnLogin);

        checkDangerousPermissions();

        Log.e("Debug", Utility.INSTANCE.getKeyHash(this));

        // SDK 초기화
        KakaoSdk.init(this, "4007c2646272cd2d41f9fb1099abeda5");

        textView = findViewById(R.id.textView);


        // 로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(u_id.getText().toString().length() != 0 && u_pw.getText().toString().length() != 0){
                    String id = u_id.getText().toString();
                    String pw = u_pw.getText().toString();

                    LoginSelect loginSelect = new LoginSelect(id, pw);

                    try {
                        loginSelect.execute().get();
                    } catch (ExecutionException e) {
                        e.getMessage();
                    } catch (InterruptedException e) {
                        e.getMessage();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "아이디와 암호를 모두 입력하세요", Toast.LENGTH_SHORT).show();
                    Log.d("main:login", "아이디와 암호를 모두 입력하세요 !!!");
                    return;
                }

                if(loginDTO != null){
                    Toast.makeText(LoginActivity.this, "로그인 되었습니다 !!!", Toast.LENGTH_SHORT).show();
                    Log.d("main:login", loginDTO.getId() + "님 로그인 되었습니다 !!!");

                }else {
                    Toast.makeText(LoginActivity.this, "아이디나 비밀번호가 일치안함 !!!", Toast.LENGTH_SHORT).show();
                    Log.d("main:login", "아이디나 비밀번호가 일치안함 !!!");
                    u_id.setText(""); u_pw.setText("");
                    u_id.requestFocus();
                }

            }
        });

        //회원가입 버튼
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
            }
        });

        // 카카오로그인 버튼 클릭 이벤트
        ImageView btnkakao = findViewById(R.id.btnkakao);
        btnkakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginClient.getInstance().loginWithKakaoAccount(getApplicationContext(), new Function2<OAuthToken, Throwable, Unit>() {
                    @Override
                    public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {

                        if (throwable != null) {
                            Log.e("Debug", "로그인 실패!");
                        } else if (oAuthToken != null) {
                            Log.e("Debug", "로그인 성공!");
                            // 로그인 성공 시 사용자 정보 받기
                            UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
                                @Override
                                public Unit invoke(User user, Throwable throwable) {
                                    if (throwable != null) {
                                        Log.e("Deubg", "사용자 정보 받기 실패!");
                                    } else if (user != null) {
                                        Log.e("Debug", "사용자 정보 받기 성공!");
                                        String nickName = user.getKakaoAccount().getProfile().getNickname();
                                        String email = user.getKakaoAccount().getEmail();
                                        String gender = user.getKakaoAccount().getGender().toString();
                                        textView.setText(nickName + " 님 로그인되었습니다." + "\n email :" + email + "\n 성별 :" + gender);

                                    }
                                    return null;
                                }
                            });
                        }
                        return null;
                    }
                });
            }
        });





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

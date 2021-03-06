package com.example.empty_can;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.empty_can.ATask.KakaoSelect;
import com.example.empty_can.ATask.LoginSelect;
import com.example.empty_can.ATask.NaverJoinInsert;
import com.example.empty_can.ATask.kakaoJoinInsert;
import com.kakao.sdk.auth.LoginClient;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.common.util.Utility;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

import static com.example.empty_can.Common.CommonMethod.loginDTO;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "main:LoginActivity";

    String state;
    EditText u_id, u_pw;
    Button btnLogin, btnJoin, buttonOAuthLogout;

    /*OAuthLogin naverLogin;
    Context nContext;*/
    private static String OAUTH_CLIENT_ID = "114sQ5mbj3x9mYKxZzTG";
    private static String OAUTH_CLIENT_SECRET = "Qr9JX2hDbR";
    private static String OAUTH_CLIENT_NAME = "Empty_can";

    private static OAuthLogin naverLogin;
    private static Context nContext;

    private OAuthLoginButton btnnaver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        checkDangerousPermissions();

        nContext = this;
        initData();
        initView();
        this.setTitle("OAuthLoginSample Ver." + OAuthLogin.getVersion());

        /* nContext = getApplicationContext();*/

        u_id = findViewById(R.id.u_id);
        u_pw = findViewById(R.id.u_pw);

        btnJoin = findViewById(R.id.btnJoin);
        btnLogin = findViewById(R.id.btnLogin);

     /*   buttonOAuthLogout = findViewById(R.id.buttonOAuthLogout);

        buttonOAuthLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteTokenTask().execute();
            }
        });
*/


        Log.e("Debug", Utility.INSTANCE.getKeyHash(this));

        // SDK ?????????
        KakaoSdk.init(this, "4007c2646272cd2d41f9fb1099abeda5");

        // ????????? ??????
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginDTO = null;


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
                    Toast.makeText(LoginActivity.this, "???????????? ????????? ?????? ???????????????", Toast.LENGTH_SHORT).show();
                    Log.d("main:login", "???????????? ????????? ?????? ??????????????? !!!");
                    return;
                }

                if(loginDTO != null){
                    Toast.makeText(LoginActivity.this, "????????? ??????????????? !!!", Toast.LENGTH_SHORT).show();
                    Log.d("main:login", loginDTO.getId() + "??? ????????? ??????????????? !!!" + loginDTO.getName() + loginDTO.getNikname());

                    u_id.setText("");  u_pw.setText("");

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                }else {
                    Toast.makeText(LoginActivity.this, "???????????? ??????????????? ???????????? !!!", Toast.LENGTH_SHORT).show();
                    Log.d("main:login", "???????????? ??????????????? ???????????? !!!");
                    u_id.setText(""); u_pw.setText("");
                    u_id.requestFocus();
                }

            }
        });

        //???????????? ??????
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);*/
                Intent intent1 = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent1);
            }
        });


        // ?????????????????? ?????? ?????? ?????????
        ImageView btnkakao = findViewById(R.id.btnkakao);
        btnkakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginDTO = null;

                LoginClient.getInstance().loginWithKakaoAccount(getApplicationContext(), new Function2<OAuthToken, Throwable, Unit>() {
                    @Override
                    public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {

                        if (throwable != null) {
                            Log.e("Debug", "????????? ??????!");
                        } else if (oAuthToken != null) {
                            Log.e("Debug", "????????? ??????!");

                            // ????????? ?????? ??? ????????? ?????? ??????
                            UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
                                @Override
                                public Unit invoke(User user, Throwable throwable) {
                                    if (throwable != null) {
                                        Log.e("Deubg", "????????? ?????? ?????? ??????!");
                                    } else if (user != null) {
                                        Log.e("Debug", "????????? ?????? ?????? ??????!");
                                        String m_nikname = user.getKakaoAccount().getProfile().getNickname();
                                        String m_email = user.getKakaoAccount().getEmail();
                                        String m_gender = user.getKakaoAccount().getGender().toString();

                                        KakaoSelect kakaoSelect = new KakaoSelect(m_email);

                                        try {
                                            kakaoSelect.execute().get();
                                        } catch (ExecutionException e) {
                                            e.printStackTrace();
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }

                                        if(loginDTO == null){
                                            kakaoJoinInsert kakaojoinInsert = new kakaoJoinInsert(m_nikname, m_email, m_gender);

                                            try {
                                                state = kakaojoinInsert.execute().get().trim();
                                                Log.d("main:joinact0 : ", state);

                                                KakaoSelect kakaoSelect1 = new KakaoSelect(m_email);
                                                kakaoSelect1.execute().get();

                                                Log.d("main:joinact0 : ", loginDTO.getEmail());
                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(intent);
                                            } catch (ExecutionException e) {
                                                e.printStackTrace();
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        }else {
                                            Log.d("main:joinact1 : ", loginDTO.getEmail());
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                        }


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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        u_id.setText("");  u_pw.setText("");
    }

    //??????????????????
        private void initData() {
            naverLogin = OAuthLogin.getInstance();

            naverLogin.showDevelopersLog(true);
            naverLogin.init(nContext, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME);
        }
        private void initView() {
            btnnaver = (OAuthLoginButton) findViewById(R.id.btnnaver);
            btnnaver.setOAuthLoginHandler(mOAuthLoginHandler);
        }

        @Override
        protected void onResume() {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            super.onResume();

        }

        static private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
            @Override
            public void run(boolean success) {
                if (success) {
                    Log.d(TAG, "run: ???????????????!");

                    new RequestApiTask(nContext, naverLogin).execute();

                } else {
                    String errorCode = naverLogin.getLastErrorCode(nContext).getCode();
                    String errorDesc = naverLogin.getLastErrorDesc(nContext);
                    Toast.makeText(nContext, "errorCode:" + errorCode + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
                }
            }
        };

        private static class RequestApiTask extends AsyncTask<Void, Void, String> {
            String state;
            private final Context nContext;
            private final OAuthLogin naverLogin;

            public RequestApiTask(Context nContext, OAuthLogin naverLogin) {
                this.nContext = nContext;
                this.naverLogin = naverLogin;
            }

            @Override
            protected void onPreExecute() {
                loginDTO = null;
            }

            @Override
            protected String doInBackground(Void... params) {
                String url = "https://openapi.naver.com/v1/nid/me";
                String at = naverLogin.getAccessToken(nContext);
                return naverLogin.requestApi(nContext, at, url);
            }

            protected void onPostExecute(String content) {
                try {
                    JSONObject loginResult = new JSONObject(content);
                    if (loginResult.getString("resultcode").equals("00")){
                        JSONObject response = loginResult.getJSONObject("response");
                        String m_email = response.getString("email");
                        String m_nickname = response.getString("nickname");
                        String m_mobile = response.getString("mobile");
                        String m_name = response.getString("name");
                        String m_gender = response.getString("gender");


                        KakaoSelect kakaoSelect = new KakaoSelect(m_email);

                        try {
                            kakaoSelect.execute().get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }



                        if(loginDTO == null){
                            NaverJoinInsert naverJoinInsert = new NaverJoinInsert(m_name, m_mobile, m_nickname, m_email, m_gender);
                            try {
                                state = naverJoinInsert.execute().get().trim();
                                Log.d("main:joinact0 : ", state);

                                KakaoSelect kakaoSelect1 = new KakaoSelect(m_email);
                                kakaoSelect1.execute().get();

                                Log.d("main:joinact1 : ", loginDTO.getEmail());
                                Intent intent = new Intent(nContext, MainActivity.class);
                                nContext.startActivity(intent);

                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            Toast.makeText(nContext, " email : "+ m_email + "nickname" + m_nickname + "mobile" + m_mobile + "name" + m_name + "gender" + m_gender , Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onPostExecute: ?????????????????? " + m_email);
                        }else {
                            Log.d("main:joinact1 : ", loginDTO.getEmail());
                            Intent intent = new Intent(nContext, MainActivity.class);
                            nContext.startActivity(intent);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }


        private class DeleteTokenTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... params) {
                boolean isSuccessDeleteToken = naverLogin.logoutAndDeleteToken(nContext);

                if (!isSuccessDeleteToken) {
                    Log.d(TAG, "errorCode:" + naverLogin.getLastErrorCode(nContext));
                    Log.d(TAG, "errorDesc:" + naverLogin.getLastErrorDesc(nContext));
                }

                return null;
            }

        }




    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "?????? ??????", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "?????? ??????", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "?????? ?????? ?????????.", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(this, permissions[i] + " ????????? ?????????.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " ????????? ???????????? ??????.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}

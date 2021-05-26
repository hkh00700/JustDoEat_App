package com.example.empty_can;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.empty_can.ATask.Email_check;
import com.example.empty_can.ATask.Id_check;
import com.example.empty_can.ATask.JoinInsert;

import java.util.concurrent.ExecutionException;

public class JoinActivity extends AppCompatActivity { 

    private static final String TAG = "main:JoinActivity";

    String state;

    EditText m_id, m_pw, m_name, m_phone, m_gender, m_email, m_nikname;
    Button btnJoin, btnCancel, btnjun, btnejun;
    String chk = "chk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);

        m_id = findViewById(R.id.m_id);
        m_pw = findViewById(R.id.m_pw);
        m_name = findViewById(R.id.m_name);
        m_phone = findViewById(R.id.m_phone);
        m_gender = findViewById(R.id.m_gender);
        m_email = findViewById(R.id.m_email);
        m_nikname = findViewById(R.id.m_nikname);

        btnJoin = findViewById(R.id.btnJoin);
        btnCancel = findViewById(R.id.btnCancel);
        btnjun = findViewById(R.id.btnjun);
        btnejun = findViewById(R.id.btnejun);

        btnjun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = m_id.getText().toString();
                Id_check id_check = new Id_check(id);

                try {
                    state = id_check.execute().get().trim();
                    Log.d("main:joinact0 : ", state);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(state.equals("1")){
                    Toast.makeText(JoinActivity.this, "사용중인 아이디입니다.", Toast.LENGTH_SHORT).show();
                    m_id.setText("");
                    chk = "chk";
                }else{
                    Toast.makeText(JoinActivity.this, "사용가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                    chk = "chked";
                }

            }
        });

        btnejun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = m_email.getText().toString();
                Email_check email_check = new Email_check(email);

                try {
                    state = email_check.execute().get().trim();
                    Log.d("main:joinact0 : ", state);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(state.equals("1")){
                    Toast.makeText(JoinActivity.this, "사용중인 이메일입니다.", Toast.LENGTH_SHORT).show();
                    m_email.setText("");
                    chk = "chk";
                }else{
                    Toast.makeText(JoinActivity.this, "사용가능한 이메일입니다.", Toast.LENGTH_SHORT).show();
                    chk = "chked";
                }

            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = m_id.getText().toString();
                String pw = m_pw.getText().toString();
                String name = m_name.getText().toString();
                String phone = m_phone.getText().toString();
                String gender = m_gender.getText().toString();
                String email = m_email.getText().toString();
                String nikname = m_nikname.getText().toString();

                JoinInsert joinInsert = new JoinInsert(id, pw, name, phone, gender, email, nikname);

                try {
                    state = joinInsert.execute().get().trim();
                    Log.d("main:joinact0 : ", state);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(state.equals("1")){
                    Toast.makeText(JoinActivity.this, "삽입성공 !!!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "회원가입을 축하합니다!!!");
                    finish();
                }else{
                    Toast.makeText(JoinActivity.this, "삽입실패 !!!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "다시 회원가입을 해주세요!!!" + state);
                    finish();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

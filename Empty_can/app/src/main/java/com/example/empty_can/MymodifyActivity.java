package com.example.empty_can;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.empty_can.ATask.ModifyUpdate;

import java.util.concurrent.ExecutionException;

import static com.example.empty_can.Common.CommonMethod.loginDTO;

public class MymodifyActivity extends AppCompatActivity { 
    private static final String TAG = "main:MymodifyActivity";

    String state;
    String m_email;
    EditText m_pw, m_name, m_phone, m_gender, m_nikname, m_address1, m_address2;

    Button btnModify, btnCancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mymodify);

        m_pw = findViewById(R.id.m_pw);
        m_name = findViewById(R.id.m_name);
        m_phone = findViewById(R.id.m_phone);
        m_gender = findViewById(R.id.m_gender);
        m_nikname = findViewById(R.id.m_nikname);
        m_address1 = findViewById(R.id.m_address1);
        m_address2 = findViewById(R.id.m_address2);
        m_email = loginDTO.getEmail();

        m_name.setText(loginDTO.getName());
        m_phone.setText(loginDTO.getPhone());
        m_gender.setText(loginDTO.getGender());
        m_nikname.setText(loginDTO.getNikname());


        btnModify = findViewById(R.id.btnModify);
        btnCancel = findViewById(R.id.btnCancel);

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pw = m_pw.getText().toString();
                String name = m_name.getText().toString();
                String phone = m_phone.getText().toString();
                String gender = m_gender.getText().toString();
                String nikname = m_nikname.getText().toString();
                String address1 = m_address1.getText().toString();
                String address2 = m_address2.getText().toString();



                ModifyUpdate modifyUpdate = new ModifyUpdate(pw, name, phone, gender, nikname, address1, address2, m_email);


                try {
                    state = modifyUpdate.execute().get().trim();
                    Log.d("main:joinact0 : ", state);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(state.equals("1")){
                    Toast.makeText(MymodifyActivity.this, "수정성공 !!!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "회원정보 수정이 완료되었습니다.");
                    finish();
                }else{
                    Toast.makeText(MymodifyActivity.this, "수정실패 !!!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "회원정보 수정이 실패하였습니다." + state);
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

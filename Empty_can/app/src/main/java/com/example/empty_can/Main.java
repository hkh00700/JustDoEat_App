package com.example.empty_can;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.example.empty_can.Common.CommonMethod.loginDTO;

public class Main extends Fragment {
    private static final String TAG = "Main";
    TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.main, container,false);

        textView = view.findViewById(R.id.textView);

        if(loginDTO != null){
            textView.setText(loginDTO.getNikname() + "님 로그인되었습니다.");

        }



        return view;
    }
}


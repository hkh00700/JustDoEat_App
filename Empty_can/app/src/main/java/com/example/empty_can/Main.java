package com.example.empty_can;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
            textView.setText(loginDTO.getNikname());
        }



        return view;
    }
}


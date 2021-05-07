package com.example.empty_can;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class FoodmodifyActivity extends AppCompatActivity {
    private static final String TAG = "main:FoodmodifyActivity";
    ListView listView;
    AllergyAdapter adapter;
    ArrayList<ListViewItem> items;
    ArrayList<String> list;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodmodify);

        items = new ArrayList<>();
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

        // 확인버튼 누르면 체크박스에 체크되어 있는 항목 찾아서 보내주기

    }
}


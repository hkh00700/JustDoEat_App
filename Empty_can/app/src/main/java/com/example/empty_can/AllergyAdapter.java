package com.example.empty_can;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class AllergyAdapter extends BaseAdapter {
    private static final String TAG = "main:AllergyAdapter";
    
    Context context;
    ArrayList<ListViewItem> items;
    ArrayList<String> list;

    LayoutInflater inflater;

    public AllergyAdapter(Context context, ArrayList<ListViewItem> items, ArrayList<String> list) {
        this.context = context;
        this.items = items;
        this.list = list;

        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // 아이템 추가
    public void addItem(ListViewItem item){
        items.add(item);
    }

    // 선택한 아이템 리스트 가져오기
    public ArrayList<String> getList(){
        return list;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.row, parent, false);
            holder = new ViewHolder();

            holder.textView = convertView.findViewById(R.id.textView);
            holder.checkBox = convertView.findViewById(R.id.checkBox);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ListViewItem item = items.get(position);
        String food = item.getText();
        Boolean check = item.isCheck();

        holder.textView.setText(food);
        holder.checkBox.setChecked(check);

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "onCheckedChanged: " + isChecked);

                if(holder.checkBox.isChecked() == true){
                    list.add(holder.textView.getText().toString());
                }else if(holder.checkBox.isChecked() == false){
                    for(int i=0; i<list.size(); i++){
                        if(holder.textView.getText().equals(list.get(i))){
                            list.remove(i);
                            break;
                        }
                    }
                }

                for(int i=0; i<list.size(); i++){
                    Log.d(TAG, "onClick: " + list.get(i));
                }

            }
        });


       /*holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.checkBox.isChecked() == true){
                    holder.checkBox.setChecked(false);

                    *//*if(items.size() == 0 || list.size() == 0){
                        return;
                    }

                    for(int i=0; i<items.size(); i++){
                        if(items.get(position).getText().equals(list.get(i))){
                            list.remove(i);
                            break;
                        }
                    }*//*
                }else {
                    holder.checkBox.setChecked(true);
                    list.add(items.get(position).getText());
                }

                for(int i=0; i<list.size(); i++){
                    Log.d(TAG, "onClick: " + list.get(i));
                }

                notifyDataSetChanged();
            }
        });*/






        return convertView;
    }

    /*public class ViewHolder {
        public TextView textView;
        public CheckBox checkBox;
    }*/

    public class ViewHolder implements Checkable{
        public TextView textView;
        public CheckBox checkBox;
        public int Position;


        @Override
        public void setChecked(boolean checked) {
            if(checkBox.isChecked() != checked){
                checkBox.setChecked(checked) ;
                Log.d(TAG, "setChecked: " + checkBox.isChecked());
            }
        }

        @Override
        public boolean isChecked() {
            Log.d(TAG, "isChecked: " + checkBox.isChecked());
            return checkBox.isChecked() ;
        }

        @Override
        public void toggle() {
            setChecked(checkBox.isChecked() ? false : true) ;
            Log.d(TAG, "toggle: " + checkBox.isChecked());
        }
    }
}



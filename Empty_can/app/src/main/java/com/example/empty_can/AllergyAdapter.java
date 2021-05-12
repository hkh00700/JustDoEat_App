package com.example.empty_can;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

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

    public boolean itChk(int position) {
        return items.get(position).isCheck();
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

                if (holder.checkBox.isChecked() == true) {
                    list.add(holder.textView.getText().toString());
                    boolean newState = !items.get(position).isCheck();
                    items.get(position).check = newState;
                    Toast.makeText(context, items.get(position).getText() + "을(를) 선택하셨습니다.", Toast.LENGTH_SHORT).show();
                }else if(holder.checkBox.isChecked() == false){

                    for(int i=0; i<list.size(); i++){
                        if(holder.textView.getText().equals(list.get(i))){
                            items.get(position).setCheck(false);
                            Toast.makeText(context, items.get(position).getText() + "을(를) 선택 해제 하셨습니다.", Toast.LENGTH_SHORT).show();
                            list.remove(i);
                            break;
                        }
                    }
                }

                for (int i = 0; i < list.size(); i++) {
                    Log.d(TAG, "onClick: " + list.get(i));

                }



            }
        });

        holder.checkBox.setChecked(itChk(position));
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



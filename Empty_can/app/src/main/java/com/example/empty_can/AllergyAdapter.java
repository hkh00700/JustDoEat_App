package com.example.empty_can;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.empty_can.R.layout.row;

public class AllergyAdapter extends BaseAdapter {
    private static final String TAG = "main:AllergyAdapter";
    ViewHolder viewHolder = null;
    ArrayList<ListViewItem> sArrayList = new ArrayList<ListViewItem>();

    LayoutInflater inflater = null;
    //  ArrayList<String> sArrayList = new ArrayList<>();
    boolean[] isCheckedConfirm;

    Context context;
    public AllergyAdapter (Context context, ArrayList<ListViewItem> mlist){
        inflater = LayoutInflater.from(context);
        this.sArrayList = mlist;
        this.isCheckedConfirm = new boolean[sArrayList.size()];

        for(int i = 0; i <sArrayList.size(); i++){
            this.isCheckedConfirm[i] = sArrayList.get(i).isCheckable;
        }
    }

    //CheckBox를 모두 해제하는 메소드
    public void allnoCheck(boolean ischked){
        int tempSize = isCheckedConfirm.length;
        for(int a = 0; a<tempSize; a++){
            isCheckedConfirm[a] = ischked;
        }

    }
    public void setChecked(int position) {
        isCheckedConfirm[position] = !isCheckedConfirm[position];
    }

    public ArrayList<Integer> geChecked() {
        int tempSize = isCheckedConfirm.length;
        ArrayList<Integer> mArrayList = new ArrayList<Integer>();
        for(int b=0 ; b<tempSize ; b++){
            if(isCheckedConfirm[b]){
                mArrayList.add(b);
            }
        }
        return mArrayList;
    }


    @Override
    public int getCount() {
        return sArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.row, null);
            viewHolder.cBox = convertView.findViewById(R.id.rcheckBox);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.cBox.setClickable(false);
        viewHolder.cBox.setFocusable(false);

        viewHolder.cBox.setText(sArrayList.get(position).getName());
        viewHolder.cBox.setChecked(isCheckedConfirm[position]);



        return convertView;
    }

}

class ViewHolder {
    // 새로운 Row에 들어갈 CheckBox
    CheckBox cBox = null;
}




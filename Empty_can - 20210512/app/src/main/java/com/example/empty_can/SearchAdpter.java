package com.example.empty_can;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SearchAdpter extends BaseAdapter {
    private Context context;
    private List<String> list;
    private LayoutInflater inflater;
    private ViewHolder viewHolder;
    FoodmodifyActivity activity;

    public SearchAdpter(List<String> list, Context context) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item,null);

            viewHolder = new ViewHolder();
            viewHolder.label = convertView.findViewById(R.id.label);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 리스트에 있는 데이터를 리스트뷰 셀에 뿌린다.
        viewHolder.label.setText(list.get(position));

        viewHolder.label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,  list.get(position)+ "선택되었습니다.", Toast.LENGTH_SHORT).show();
                AllergyListFragment allergyListFragment = new AllergyListFragment();
                allergyListFragment.searchresult(list.get(position));

            }
        });

        return convertView;
    }

    class ViewHolder {
        public TextView label;

    }

}

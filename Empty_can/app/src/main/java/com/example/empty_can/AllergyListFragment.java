package com.example.empty_can;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class AllergyListFragment extends Fragment {
    ListView listView;
    AllergyAdapter adapter;
    ArrayList<ListViewItem> items;
    ArrayList<String> list;

    FoodmodifyActivity activity;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.fragment_allergy_list, container, false);
        activity = (FoodmodifyActivity) getActivity();
        items = new ArrayList<>();
        list = new ArrayList<>();

        adapter = new AllergyAdapter(getActivity(),items, list);

        listView = rootview.findViewById(R.id.listView);
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


        rootview.findViewById(R.id.edit_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.ChangeFragment(0);
            }
        });
        return rootview;
    }
}



//어댑터에 체크한 값 담겨있음,, 갑각류 등을 DB멤버행에 넣기
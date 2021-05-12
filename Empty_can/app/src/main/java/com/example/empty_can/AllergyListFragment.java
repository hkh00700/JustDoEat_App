package com.example.empty_can;

import android.os.Bundle;
<<<<<<< HEAD

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.empty_can.ATask.AllergySearchlist;
=======
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
>>>>>>> aa5be549d2587ec63896e1416ad35b19d8cdd32e

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class AllergyListFragment extends Fragment {
    private static final String TAG = "main:AllergyListFrag";

    ListView listView;
    AllergyAdapter adapter;
    ArrayList<ListViewItem> items;
    ArrayList<String> list;
    String searchresult;
    String allergyString;

    EditText editSearch;


    FoodmodifyActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.fragment_allergy_list, container, false);
        activity = (FoodmodifyActivity) getActivity();
        items = new ArrayList<>();
        list = new ArrayList<>();

        listView = rootview.findViewById(R.id.listView);

        adapter = new AllergyAdapter(getActivity(),items, list);
        listView.setAdapter(adapter);

        /*adapter.addItem(new ListViewItem("없음", false));
        adapter.addItem(new ListViewItem("갑각류", false));
        adapter.addItem(new ListViewItem("견과류", false));
        adapter.addItem(new ListViewItem("계란", false));
        adapter.addItem(new ListViewItem("밀류(글루텐)", false));
        adapter.addItem(new ListViewItem("우유", false));
        adapter.addItem(new ListViewItem("조개류", false));
        adapter.addItem(new ListViewItem("콩류", false));
        adapter.addItem(new ListViewItem("육류", false));*/

        /*if (searchresult != null){
            adapter.addItem(new ListViewItem(searchresult, true));
        }*/

        /*rootview.findViewById(R.id.edit_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.ChangeFragment(0);
            }
        });*/
        settingList();

      /*
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text =editSearch.getText().toString();
                search(text);
            }
        });*/

        return rootview;
    }
<<<<<<< HEAD

   /* private void search(String chartext) {
        //문자 입력시마다 리스트를 지우고 새로 뿌려주기
        list.clear();

        //문자 입력이 없을 떄는 모든 데이터를 보여준다.
        if(chartext.length() == 0){
            list.addAll(arraylist);
        }else { //문자를 입력할 때는 리스트의 모든 데이터를 검색한다.
            for(int i = 0; i<arraylist.size(); i++){
                if(arraylist.get(i).toLowerCase().contains(chartext))
                    list.add(arraylist.get(i));

            }

        }




        adapter.notifyDataSetChanged();
    }*/




    public void searchresult(String searchresult){
        this.searchresult = searchresult;
    }

    public void settingList() {
        AllergySearchlist allergySearchlist = new AllergySearchlist();
        String search = "";

        try {
            search = (String) allergySearchlist.execute().get();
            Log.d(TAG, "settingList: " + search);
            String[] searchs = search.split(",");

            for(int i = 0; i < searchs.length; i++){
                String k = searchs[i].trim();
                adapter.addItem(new ListViewItem(k, false));
            }


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


}
=======
}



//어댑터에 체크한 값 담겨있음,, 갑각류 등을 DB멤버행에 넣기
>>>>>>> aa5be549d2587ec63896e1416ad35b19d8cdd32e
package com.example.empty_can;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.empty_can.ATask.AllergySearchlist;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class SearchFragment extends Fragment {
    private List<String> list;    // 데이터를 넣은 리스트변수
    private ListView listView;   // 검색을 보여줄 리스트변수
    private EditText editSearch; // 검색어를 입력할 Input 창
    private SearchAdpter adapter;
    private ArrayList<String> arraylist;
    ArrayList<String> searchlist;


    private static final String TAG = "SearchFragment";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.fragment_search, container, false);


        editSearch = rootview.findViewById(R.id.editSearch);
        listView = rootview.findViewById(R.id.listView);

        //리스트 생성
        list = new ArrayList<String>();

        //검색에 사용할 데이터를 미리 저장하는 곳
        settingList();

        //리스트의 모든 데이터를 arraylist에 복사한다.
        arraylist = new ArrayList<String>();
        arraylist.addAll(list);

        //리스트에 연동될 아답터 생성
        adapter = new SearchAdpter(list, getActivity());
        listView.setAdapter(adapter);



        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = editSearch.getText().toString();
                search(text);
            }
        });

   /*     listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: searchFragment 를 눌렀음");
            }
        });*/


        return rootview;
    }

    private void search(String chartext) {
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
    }

    //list 가져오기
    public void settingList() {
        AllergySearchlist allergySearchlist = new AllergySearchlist();
        String search = "";

        try {
            search = (String) allergySearchlist.execute().get();
            Log.d(TAG, "settingList: " + search);
            String[] searchs = search.split("<br/>");

            for(int i = 0; i < searchs.length; i++){
                list.add(searchs[i].trim());
            }


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }



    /*    list.add("딸기");
        list.add("바나나");
        list.add("사과");
        list.add("돼지고기");
        list.add("피자");
        list.add("자두");
        list.add("복숭아");
        list.add("사과");
        list.add("땅콩");
        list.add("조개");
        list.add("반지락");*/
}


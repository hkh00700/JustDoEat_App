package com.example.empty_can.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.empty_can.DTO.MemberReviewDTO;
import com.example.empty_can.R;

import java.util.ArrayList;
import static com.example.empty_can.ShowReviewActivity.selItem;



public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ItemViewHolder> {
    private static final String TAG = "main:ReviewListAdapter";
    ArrayList<MemberReviewDTO> arrayList;
    Context context;



    public ReviewAdapter(ArrayList<MemberReviewDTO> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.cardviewlist, parent, false);


        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Log.d("main:adater", "" + position);

        MemberReviewDTO dto = arrayList.get(position);
        holder.setItem(dto);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: " + position);

                selItem = arrayList.get(position);

                Toast.makeText(context, "Onclick " + arrayList.get(position).getNikname(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    // 리사이클러뷰 내용 모두 지우기
    public void removeAllItem(){
        arrayList.clear();
    }

    // 특정 인덱스 항목 가져오기
    public MemberReviewDTO getItem(int position) {
        return arrayList.get(position);
    }

    // 특정 인덱스 항목 셋팅하기
    public void setItem(int position, MemberReviewDTO item){
        arrayList.set(position, item);
    }

    // arrayList 통째로 셋팅하기
    public void setItems(ArrayList<MemberReviewDTO> arrayList){
        this.arrayList = arrayList;
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout parentLayout;
        public TextView title;
        public TextView content;
        public TextView nikname;
        public ImageView iv_img;



        public ItemViewHolder(@NonNull final View itemView) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.parentLayout);
            content = itemView.findViewById(R.id.content);
            title = itemView.findViewById(R.id.title);
            nikname = itemView.findViewById(R.id.nikname);
            iv_img = itemView.findViewById(R.id.iv_img);


        }

        public void setItem(MemberReviewDTO item){
            nikname.setText(item.getNikname());
            content.setText(item.getContent());
            title.setText(item.getTitle());
            Glide.with(itemView).load(item.getPhoto_path()).into(iv_img);

        }
    }



}

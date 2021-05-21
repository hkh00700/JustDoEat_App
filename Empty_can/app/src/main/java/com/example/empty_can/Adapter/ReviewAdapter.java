package com.example.empty_can.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.empty_can.ATask.ReviewDelete;
import com.example.empty_can.ATask.ReviewUpdate;
import com.example.empty_can.DTO.MemberReviewDTO;
import com.example.empty_can.MainActivity;
import com.example.empty_can.R;
import com.example.empty_can.ReviewRead;
import com.example.empty_can.ReviewUpdateActivity;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.empty_can.Common.CommonMethod.loginDTO;
import static com.example.empty_can.List.selItem;


public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ItemViewHolder> {
    private static final String TAG = "main:ReviewListAdapter";
    ArrayList<MemberReviewDTO> arrayList;
    Context context;
    ReviewDelete reviewDelete;
    ReviewUpdate reviewUpdate;
    String imagePath, pImgDbPathU, imageDbPathU, imageRealPathU;
    int kk;





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

                Intent intent = new Intent(context, ReviewRead.class);
                intent.putExtra("selItem", dto);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |   // 이 엑티비티 플래그를 사용하여 엑티비티를 호출하게 되면 새로운 태스크를 생성하여 그 태스크안에 엑티비티를 추가하게 됩니다. 단, 기존에 존재하는 태스크들중에 생성하려는 엑티비티와 동일한 affinity(관계, 유사)를 가지고 있는 태스크가 있다면 그곳으로 새 엑티비티가 들어가게됩니다.
                        Intent.FLAG_ACTIVITY_SINGLE_TOP | // 엑티비티를 호출할 경우 호출된 엑티비티가 현재 태스크의 최상단에 존재하고 있었다면 새로운 인스턴스를 생성하지 않습니다. 예를 들어 ABC가 엑티비티 스택에 존재하는 상태에서 C를 호출하였다면 여전히 ABC가 존재하게 됩니다.
                        Intent.FLAG_ACTIVITY_CLEAR_TOP); // 만약에 엑티비티스택에 호출하려는 엑티비티의 인스턴스가 이미 존재하고 있을 경우에 새로운 인스턴스를 생성하는 것 대신에 존재하고 있는 엑티비티를 포그라운드로 가져옵니다. 그리고 엑티비티스택의 최상단 엑티비티부터 포그라운드로 가져올 엑티비티까지의 모든 엑티비티를 삭제합니다.
                context.startActivity(intent);
                selItem = arrayList.get(position);

            //    Toast.makeText(context, "Onclick " + arrayList.get(position).getId(), Toast.LENGTH_SHORT).show();

            }
        });


        holder.review_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(context);
                ad.setIcon(R.mipmap.ic_launcher);
                ad.setMessage("삭제하시겠습니까?");

                ad.setPositiveButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


                ad.setNegativeButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reviewDelete = new ReviewDelete(dto.getNo(), dto.getId());
                        try {
                            reviewDelete.execute().get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                      /*  Intent intent = new Intent(context, ShowReviewActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |   // 이 엑티비티 플래그를 사용하여 엑티비티를 호출하게 되면 새로운 태스크를 생성하여 그 태스크안에 엑티비티를 추가하게 됩니다. 단, 기존에 존재하는 태스크들중에 생성하려는 엑티비티와 동일한 affinity(관계, 유사)를 가지고 있는 태스크가 있다면 그곳으로 새 엑티비티가 들어가게됩니다.
                                Intent.FLAG_ACTIVITY_SINGLE_TOP | // 엑티비티를 호출할 경우 호출된 엑티비티가 현재 태스크의 최상단에 존재하고 있었다면 새로운 인스턴스를 생성하지 않습니다. 예를 들어 ABC가 엑티비티 스택에 존재하는 상태에서 C를 호출하였다면 여전히 ABC가 존재하게 됩니다.
                                Intent.FLAG_ACTIVITY_CLEAR_TOP); // 만약에 엑티비티스택에 호출하려는 엑티비티의 인스턴스가 이미 존재하고 있을 경우에 새로운 인스턴스를 생성하는 것 대신에 존재하고 있는 엑티비티를 포그라운드로 가져옵니다. 그리고 엑티비티스택의 최상단 엑티비티부터 포그라운드로 가져올 엑티비티까지의 모든 엑티비티를 삭제합니다.
                        context.startActivity(intent);*/

                        Intent showIntent = new Intent(context, MainActivity.class);
                        showIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        showIntent.putExtra("num", 5);
                        context.startActivity(showIntent);

                        dialog.dismiss();


                    }
                });
                ad.show();
                notifyDataSetChanged();
            }
        });

        holder.review_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(context);
                ad.setIcon(R.mipmap.ic_launcher);
                ad.setMessage("수정하시겠습니까?");

                ad.setPositiveButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


                ad.setNegativeButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        imagePath = dto.getPhoto_path();
                        pImgDbPathU = imagePath;
                        imageDbPathU = imagePath;

                        Intent intent = new Intent(context, ReviewUpdateActivity.class);
                        intent.putExtra("selItem", dto);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |   // 이 엑티비티 플래그를 사용하여 엑티비티를 호출하게 되면 새로운 태스크를 생성하여 그 태스크안에 엑티비티를 추가하게 됩니다. 단, 기존에 존재하는 태스크들중에 생성하려는 엑티비티와 동일한 affinity(관계, 유사)를 가지고 있는 태스크가 있다면 그곳으로 새 엑티비티가 들어가게됩니다.
                                Intent.FLAG_ACTIVITY_SINGLE_TOP | // 엑티비티를 호출할 경우 호출된 엑티비티가 현재 태스크의 최상단에 존재하고 있었다면 새로운 인스턴스를 생성하지 않습니다. 예를 들어 ABC가 엑티비티 스택에 존재하는 상태에서 C를 호출하였다면 여전히 ABC가 존재하게 됩니다.
                                Intent.FLAG_ACTIVITY_CLEAR_TOP); // 만약에 엑티비티스택에 호출하려는 엑티비티의 인스턴스가 이미 존재하고 있을 경우에 새로운 인스턴스를 생성하는 것 대신에 존재하고 있는 엑티비티를 포그라운드로 가져옵니다. 그리고 엑티비티스택의 최상단 엑티비티부터 포그라운드로 가져올 엑티비티까지의 모든 엑티비티를 삭제합니다.
                        context.startActivity(intent);

                        dialog.dismiss();
                    }
                });
                ad.show();

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
        public TextView content, review_update;
        public TextView id;
        public ImageView iv_img, review_delete;
     //   public TextView review_delete;
    //    public ImageView review_update;
        Button btnadd;


        public ItemViewHolder(@NonNull final View itemView) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.parentLayout);
            content = itemView.findViewById(R.id.content);
            title = itemView.findViewById(R.id.title);
            id = itemView.findViewById(R.id.id);
           iv_img = itemView.findViewById(R.id.iv_img);
            review_delete = itemView.findViewById(R.id.review_delete);
            review_update = itemView.findViewById(R.id.review_update);
            btnadd = itemView.findViewById(R.id.btnadd);



        }

        public void setItem(MemberReviewDTO item){
            id.setText(item.getId());
            content.setText(item.getContent());
            title.setText(item.getTitle());
            Glide.with(itemView).load(item.getPhoto_path()).into(iv_img);
          //  Log.d(TAG, "setItem: " + item.getId() +" "+ loginDTO.getId());

            if(!item.getId().equals(loginDTO.getId())){
                review_delete.setVisibility(View.INVISIBLE);
                review_update.setVisibility(View.INVISIBLE);
            }else {
                review_delete.setVisibility(View.VISIBLE);
                review_update.setVisibility(View.VISIBLE);
            }

        }
    }



}

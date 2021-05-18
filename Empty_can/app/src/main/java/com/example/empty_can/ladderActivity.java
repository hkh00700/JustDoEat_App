package com.example.empty_can;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.empty_can.DTO.tDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ladderActivity extends AppCompatActivity {
    private static final String TAG = "main:ladderActivity";
    Random rnd;
    tDTO dto;
    TextView tView1, tView2, tView3, tView4, tView5;
    Button button2;
    ImageView imageView2;
    ImageButton imgButton1, imgButton2, imgButton3, imgButton4, imgButton5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ladder);

        TextView tView1 = findViewById(R.id.tView1);
        TextView tView2 = findViewById(R.id.tView2);
        TextView tView3 = findViewById(R.id.tView3);
        TextView tView4 = findViewById(R.id.tView4);
        TextView tView5 = findViewById(R.id.tView5);

      /*  String food5 = "";
        RandomFive randomFive = new RandomFive();
        try {
            food5 = randomFive.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String[] foods = food5.split(",");
        tView1.setText(foods[0]);
        tView2.setText(foods[1]);
        tView3.setText(foods[2]);
        tView4.setText(foods[3]);
        tView5.setText(foods[4]);*/

        // 받기
        Intent intent = getIntent();
        int count = intent.getIntExtra("count", 0);
        String t1 = intent.getStringExtra("t1");
        String t2 = intent.getStringExtra("t2");
        String t3 = intent.getStringExtra("t3");
        String t4 = intent.getStringExtra("t4");
        String t5 = intent.getStringExtra("t5");

        dto = new tDTO(t1, t2, t3, t4, t5);
        this.dto = dto;

        tView1.setText(t1);
        tView2.setText(t2);
        tView3.setText(t3);
        tView4.setText(t4);
        tView5.setText(t5);

        imgButton1 = findViewById(R.id.imgButton1);
        imgButton2 = findViewById(R.id.imgButton2);
        imgButton3 = findViewById(R.id.imgButton3);
        imgButton4 = findViewById(R.id.imgButton4);
        imgButton5 = findViewById(R.id.imgButton5);
        imageView2 = findViewById(R.id.imageView2);
        button2 = findViewById(R.id.button2);
        Button btnSwitch3 = findViewById(R.id.btnSwitch3);
        btnSwitch3.setVisibility(View.GONE);

        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), dto.getT1()+" 당첨~!!*^^*", Toast.LENGTH_LONG).show();

                Random rnd = new Random();
                int value = rnd.nextInt(5);
                Log.d("random", "onClick: random" + value);

                if (value == 0) {
                    Toast.makeText(getApplicationContext(), tView1.getText() + " 당첨~!!*^^*", Toast.LENGTH_LONG).show();
                    btnSwitch3.setText("내주변 " + tView1.getText() + " 잘하는 집 검색하기");
                    imageView2.setImageResource(R.drawable.ladder1);
                    btnSwitch3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://map.naver.com/v5/search/" + tView1.getText()));
                            startActivity(intent);
                        }
                    });
                } else if (value == 1) {
                    Toast.makeText(getApplicationContext(), tView2.getText() + " 당첨~!!*^^*", Toast.LENGTH_LONG).show();
                    btnSwitch3.setText("내주변 " + tView2.getText() + " 잘하는 집 검색하기");
                    btnSwitch3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://map.naver.com/v5/search/" + tView2.getText()));
                            startActivity(intent);
                        }
                    });
                    imageView2.setImageResource(R.drawable.ladder2);
                } else if (value == 2) {
                    Toast.makeText(getApplicationContext(), tView3.getText() + " 당첨~!!*^^*", Toast.LENGTH_LONG).show();
                    btnSwitch3.setText("내주변 " + tView3.getText() + " 잘하는 집 검색하기");
                    btnSwitch3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://map.naver.com/v5/search/" + tView3.getText()));
                            startActivity(intent);
                        }
                    });
                    imageView2.setImageResource(R.drawable.ladder3);
                } else if (value == 3) {
                    Toast.makeText(getApplicationContext(), tView4.getText() + " 당첨~!!*^^*", Toast.LENGTH_LONG).show();
                    btnSwitch3.setText("내주변 " + tView4.getText() + " 잘하는 집 검색하기");
                    btnSwitch3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://map.naver.com/v5/search/" + tView4.getText()));
                            startActivity(intent);
                        }
                    });
                    imageView2.setImageResource(R.drawable.ladder4);
                } else if (value == 4) {
                    Toast.makeText(getApplicationContext(), tView5.getText() + " 당첨~!!*^^*", Toast.LENGTH_LONG).show();
                    btnSwitch3.setText("내주변 " + tView5.getText() + " 잘하는 집 검색하기");
                    btnSwitch3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://map.naver.com/v5/search/" + tView5.getText()));
                            startActivity(intent);
                        }
                    });
                    imageView2.setImageResource(R.drawable.ladder5);
                }
                btnSwitch3.setVisibility(View.VISIBLE);
            }

        });

        ArrayList<String> ranfood = new ArrayList<>();
        ranfood.add(t1);
        ranfood.add(t2);
        ranfood.add(t3);
        ranfood.add(t4);
        ranfood.add(t5);

        imgButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Collections.shuffle(ranfood);
                Log.d(TAG, "onClick: " + Arrays.toString(ranfood.toArray(new String[ranfood.size()])));
                tView1.setText(ranfood.get(0));
                tView2.setText(ranfood.get(1));
                tView3.setText(ranfood.get(2));
                tView4.setText(ranfood.get(3));
                tView5.setText(ranfood.get(4));

                imageView2.setImageResource(R.drawable.ladder3);
                Toast.makeText(getApplicationContext(), ranfood.get(2) + " 당첨~!!*^^*", Toast.LENGTH_SHORT).show();
                btnSwitch3.setVisibility(View.VISIBLE);
                btnSwitch3.setText("내주변 " + ranfood.get(2) + " 잘하는 집 검색하기");
                btnSwitch3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://map.naver.com/v5/search/" + ranfood.get(2)));
                        startActivity(intent);
                    }
                });


            }
        });
        imgButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.shuffle(ranfood);
                Log.d(TAG, "onClick: " + Arrays.toString(ranfood.toArray(new String[ranfood.size()])));
                tView1.setText(ranfood.get(0));
                tView2.setText(ranfood.get(1));
                tView3.setText(ranfood.get(2));
                tView4.setText(ranfood.get(3));
                tView5.setText(ranfood.get(4));
                imageView2.setImageResource(R.drawable.ladder1);
                Toast.makeText(getApplicationContext(), ranfood.get(0) + " 당첨~!!*^^*", Toast.LENGTH_LONG).show();
                btnSwitch3.setVisibility(View.VISIBLE);
                btnSwitch3.setText("내주변 " + ranfood.get(0) + " 잘하는 집 검색하기");
                btnSwitch3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://map.naver.com/v5/search/" + ranfood.get(0)));
                        startActivity(intent);
                    }
                });
            }
        });
        imgButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.shuffle(ranfood);
                Log.d(TAG, "onClick: " + Arrays.toString(ranfood.toArray(new String[ranfood.size()])));
                tView1.setText(ranfood.get(0));
                tView2.setText(ranfood.get(1));
                tView3.setText(ranfood.get(2));
                tView4.setText(ranfood.get(3));
                tView5.setText(ranfood.get(4));
                imageView2.setImageResource(R.drawable.ladder2);
                Toast.makeText(getApplicationContext(), ranfood.get(1) + " 당첨~!!*^^*", Toast.LENGTH_LONG).show();
                btnSwitch3.setVisibility(View.VISIBLE);
                btnSwitch3.setText("내주변 " + ranfood.get(1) + " 잘하는 집 검색하기");
                btnSwitch3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://map.naver.com/v5/search/" + ranfood.get(1)));
                        startActivity(intent);
                    }
                });
            }
        });
        imgButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.shuffle(ranfood);
                Log.d(TAG, "onClick: " + Arrays.toString(ranfood.toArray(new String[ranfood.size()])));
                tView1.setText(ranfood.get(0));
                tView2.setText(ranfood.get(1));
                tView3.setText(ranfood.get(2));
                tView4.setText(ranfood.get(3));
                tView5.setText(ranfood.get(4));
                imageView2.setImageResource(R.drawable.ladder5);
                Toast.makeText(getApplicationContext(), ranfood.get(4) + " 당첨~!!*^^*", Toast.LENGTH_LONG).show();
                btnSwitch3.setVisibility(View.VISIBLE);
                btnSwitch3.setText("내주변 " + ranfood.get(4) + " 잘하는 집 검색하기");
                btnSwitch3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://map.naver.com/v5/search/" + ranfood.get(4)));
                        startActivity(intent);
                    }
                });
            }
        });
        imgButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.shuffle(ranfood);
                Log.d(TAG, "onClick: " + Arrays.toString(ranfood.toArray(new String[ranfood.size()])));
                tView1.setText(ranfood.get(0));
                tView2.setText(ranfood.get(1));
                tView3.setText(ranfood.get(2));
                tView4.setText(ranfood.get(3));
                tView5.setText(ranfood.get(4));
                imageView2.setImageResource(R.drawable.ladder4);
                Toast.makeText(getApplicationContext(), ranfood.get(3) + " 당첨~!!*^^*", Toast.LENGTH_LONG).show();
                btnSwitch3.setVisibility(View.VISIBLE);
                btnSwitch3.setText("내주변 " + ranfood.get(3) + " 잘하는 집 검색하기");
                btnSwitch3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://map.naver.com/v5/search/" + ranfood.get(3)));
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
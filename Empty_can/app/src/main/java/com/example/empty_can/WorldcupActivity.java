package com.example.empty_can;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class WorldcupActivity extends AppCompatActivity { 
    private static final String TAG = "main:WorldcupActivity";
    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> list2 = new ArrayList<>();
    ArrayList<String> list3 = new ArrayList<>();
    ArrayList<String> list4 = new ArrayList<>();
    ArrayList<String> list5 = new ArrayList<>();
    int num = 0;
    String[] foods = null;
    String xyz = "";
    int random = 0;

    String[] number = new String[]{
            "4강",
            "8강",
            "16강",
            "32강"
    };
    int j = 1;
    int k = 1;
    int l = 1;
    int m = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.worldcup);

        Intent intent = getIntent();
        foods = intent.getStringArrayExtra("foods");
        for (int i = 0; i < foods.length; i++) {
            Log.d(TAG, "onCreate: foods : " + foods[i]);
        }
        for (int i = 0; i < foods.length; i++){
            random = (int) (Math.random()*32);
            xyz = foods[i];
            foods[i] = foods[random];
            foods[random] = xyz;
        }
        for (int i = 0; i < foods.length; i++) {
            Log.d(TAG, "onCreate: foods random : " + foods[i]);
        }





        Spinner worldSpinner = (Spinner)findViewById(R.id.spinner);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.worldarrays,number );

        spinnerArrayAdapter.setDropDownViewResource(R.layout.worldarrays);

        worldSpinner.setAdapter(spinnerArrayAdapter);


        worldSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //((TextView)parent.getChildAt(0)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
               num = Integer.parseInt(parent.getItemAtPosition(position).toString().replace("강", ""));
                Log.d(TAG, "onCreate: num : " + num);
                j = 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.worldcup_num, android.R.layout.simple_spinner_item);

        worldSpinner.setAdapter(arrayAdapter);*/

        //String[] str = getResources().getStringArray(R

        TextView TextView1 = (TextView) findViewById(R.id.TextView1);
        TextView TextView2 = (TextView) findViewById(R.id.TextView2);
        TextView vs =  (TextView) findViewById(R.id.vs);
        TextView result = (TextView) findViewById(R.id.result);
        result.setVisibility(View.GONE);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView1.setText(foods[j-1]);//
                TextView2.setText(foods[j]);//
                Toast.makeText(WorldcupActivity.this,
                        num + "선택!",Toast.LENGTH_SHORT).show();
            }
        });


        TextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //4강
                if(num == 4) {
                    if (j == 1) {
                        list.add(TextView1.getText().toString());
                        TextView1.setText(foods[2 * j]);//2,4,6,8
                        TextView2.setText(foods[2 * j + 1]);//3,5,7,9
                    } else if (j == 2) {
                        list.add(TextView1.getText().toString());
                        TextView1.setText(list.get(0));//2,4,6,8
                        TextView2.setText(list.get(1));//3,5,7,9
                    } else if (j == 3) {
                        result.setText(list.get(0));
                        TextView1.setVisibility(View.GONE);
                        TextView2.setVisibility(View.GONE);
                        vs.setVisibility(View.GONE);
                        result.setVisibility(View.VISIBLE);
                    }
                //8강
                }else if(num == 8){
                    if (j <= 3) {
                        list.add(TextView1.getText().toString());
                        TextView1.setText(foods[2 * j]);//2,4,6,8
                        TextView2.setText(foods[2 * j + 1]);//3,5,7,9
                    } else if (j == 4) {
                        list.add(TextView1.getText().toString());
                        TextView1.setText(list.get(2*k-2));//0,2,4
                        TextView2.setText(list.get(2*k-1));//1,3,5
                        k++;
                    } else if (j == 5) {
                        list2.add(TextView1.getText().toString());
                        TextView1.setText(list.get(2*k-2));//0,2,4
                        TextView2.setText(list.get(2*k-1));//1,3,5
                        k++;
                    }else if(j==6){
                        list2.add(TextView1.getText().toString());
                        TextView1.setText(list2.get(0));//2,4,6,8
                        TextView2.setText(list2.get(1));
                        /*result.setText(TextView1.getText());
                        TextView1.setVisibility(View.GONE);
                        TextView2.setVisibility(View.GONE);
                        vs.setVisibility(View.GONE);
                        result.setVisibility(View.VISIBLE);*/
                    }else {
                        result.setText(TextView1.getText());
                        TextView1.setVisibility(View.GONE);
                        TextView2.setVisibility(View.GONE);
                        vs.setVisibility(View.GONE);
                        result.setVisibility(View.VISIBLE);
                    }
                }else if (num == 16) {//16강
                    if(j<=7){
                        list.add(TextView1.getText().toString());
                        TextView1.setText(foods[2*j]);
                        TextView2.setText(foods[2*j+1]);
                    }else if(j == 8 ){
                        list.add(TextView1.getText().toString());
                        TextView1.setText(list.get(2*k-2));//0
                        TextView2.setText(list.get(2*k-1));//1
                        k++;
                    }else if (j >= 9 && j <= 11){
                        list2.add(TextView1.getText().toString());
                        TextView1.setText(list.get(2*k-2));//2,4
                        TextView2.setText(list.get(2*k-1));//3,5
                        k++;
                    }else if (j == 12){
                        list2.add(TextView1.getText().toString());
                        TextView1.setText(list2.get(2*l-2));//0,2,4
                        TextView2.setText(list2.get(2*l-1));//1,3,5
                        l++;
                    } else if (j == 13) {
                        list3.add(TextView1.getText().toString());
                        TextView1.setText(list2.get(2*l-2));//0,2,4
                        TextView2.setText(list2.get(2*l-1));//1,3,5
                        l++;
                    }else if(j==14){
                        list3.add(TextView1.getText().toString());
                        TextView1.setText(list3.get(0));//2,4,6,8
                        TextView2.setText(list3.get(1));
                        /*result.setText(TextView1.getText());
                        TextView1.setVisibility(View.GONE);
                        TextView2.setVisibility(View.GONE);
                        vs.setVisibility(View.GONE);
                        result.setVisibility(View.VISIBLE);*/
                    }else{
                        result.setText(TextView1.getText());
                        TextView1.setVisibility(View.GONE);
                        TextView2.setVisibility(View.GONE);
                        vs.setVisibility(View.GONE);
                        result.setVisibility(View.VISIBLE);

                    }

                }else if (num == 32) {   //32강
                    if (j <= 15) {
                        list.add(TextView1.getText().toString());
                        TextView1.setText(foods[2 * j]);
                        TextView2.setText(foods[2 * j + 1]);
                    } else if (j == 16) {
                        list.add(TextView1.getText().toString());
                        TextView1.setText(list.get(2 * k - 2));//0
                        TextView2.setText(list.get(2 * k - 1));//1
                        k++;
                    } else if (j >= 17 && j <= 23) {
                        list2.add(TextView1.getText().toString());
                        TextView1.setText(list.get(2 * k - 2));//0,2,4
                        TextView2.setText(list.get(2 * k - 1));//1,3,5
                        k++;
                    } else if (j == 24) {
                        list2.add(TextView1.getText().toString());
                        TextView1.setText(list2.get(2 * l - 2));//0,2,4
                        TextView2.setText(list2.get(2 * l - 1));//1,3,5
                        l++;

                    } else if (j >= 25 && j <= 27) {
                        list3.add(TextView1.getText().toString());
                        TextView1.setText(list2.get(2 * l - 2));//0,2,4
                        TextView2.setText(list2.get(2 * l - 1));//1,3,5
                        l++;
                    } else if (j == 28) {
                        list3.add(TextView1.getText().toString());
                        TextView1.setText(list3.get(2 * m - 2));//0,2,4
                        TextView2.setText(list3.get(2 * m - 1));//1,3,5
                        m++;

                    } else if (j == 29) {
                        list4.add(TextView1.getText().toString());
                        TextView1.setText(list3.get(2 * m - 2));//0,2,4
                        TextView2.setText(list3.get(2 * m - 1));//1,3,5
                        m++;

                    } else if (j == 30) {
                        list4.add(TextView1.getText().toString());
                        TextView1.setText(list4.get(0));//2,4,6,8
                        TextView2.setText(list4.get(1));

                    } else {
                        result.setText(TextView1.getText());
                        TextView1.setVisibility(View.GONE);
                        TextView2.setVisibility(View.GONE);
                        vs.setVisibility(View.GONE);
                        result.setVisibility(View.VISIBLE);


                    }

                }
                j++;
            }
        });
        TextView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //4강
                    if(num == 4) {
                        if (j == 1) {
                            list.add(TextView2.getText().toString());
                            TextView1.setText(foods[2 * j]);//2,4,6,8
                            TextView2.setText(foods[2 * j + 1]);//3,5,7,9
                        } else if (j == 2) {
                            list.add(TextView2.getText().toString());
                            TextView1.setText(list.get(0));//2,4,6,8
                            TextView2.setText(list.get(1));//3,5,7,9
                        } else if (j == 3) {
                            result.setText(list.get(1));
                            TextView1.setVisibility(View.GONE);
                            TextView2.setVisibility(View.GONE);
                            vs.setVisibility(View.GONE);
                            result.setVisibility(View.VISIBLE);
                        }
                    //8강
                    }else if (num == 8) {
                        if (j <= 3) {
                            list.add(TextView2.getText().toString());
                            TextView1.setText(foods[2 * j]);//2,4,6,8
                            TextView2.setText(foods[2 * j + 1]);//3,5,7,9
                        } else if (j == 4) {
                            list.add(TextView2.getText().toString());
                            TextView1.setText(list.get(2*k-2));//0,2,4
                            TextView2.setText(list.get(2*k-1));//1,3,5
                            k++;
                        } else if (j == 5 ) {
                            list2.add(TextView2.getText().toString());
                            TextView1.setText(list.get(2*k-2));//0,2,4
                            TextView2.setText(list.get(2*k-1));//1,3,5
                            k++;
                        }else if(j==6){
                            list2.add(TextView2.getText().toString());
                            TextView1.setText(list2.get(0));//2,4,6,8
                            TextView2.setText(list2.get(1));
                        /*result.setText(TextView1.getText());
                        TextView1.setVisibility(View.GONE);
                        TextView2.setVisibility(View.GONE);
                        vs.setVisibility(View.GONE);
                        result.setVisibility(View.VISIBLE);*/
                        }else {

                            result.setText(TextView2.getText());
                            TextView1.setVisibility(View.GONE);
                            TextView2.setVisibility(View.GONE);
                            vs.setVisibility(View.GONE);
                            result.setVisibility(View.VISIBLE);
                        }
                    }else if (num == 16) {//16강
                        if (j <= 7) {
                            list.add(TextView2.getText().toString());
                            TextView1.setText(foods[2 * j]);
                            TextView2.setText(foods[2 * j + 1]);
                        } else if (j == 8) {
                            list.add(TextView2.getText().toString());
                            TextView1.setText(list.get(2 * k - 2));
                            TextView2.setText(list.get(2 * k - 1));
                            k++;
                        } else if (j >= 9 && j <= 11) {
                            list2.add(TextView2.getText().toString());
                            TextView1.setText(list.get(2 * k - 2));//0,2,4
                            TextView2.setText(list.get(2 * k - 1));//1,3,5
                            k++;
                        } else if (j == 12) {
                            list2.add(TextView2.getText().toString());
                            TextView1.setText(list2.get(2 * l - 2));//0,2,4
                            TextView2.setText(list2.get(2 * l - 1));//1,3,5
                            l++;
                        } else if (j == 13) {
                            list3.add(TextView2.getText().toString());
                            TextView1.setText(list2.get(2 * l - 2));//0,2,4
                            TextView2.setText(list2.get(2 * l - 1));//1,3,5
                            l++;
                        } else if (j == 14) {
                            list3.add(TextView2.getText().toString());
                            TextView1.setText(list3.get(0));//2,4,6,8
                            TextView2.setText(list3.get(1));
                        /*result.setText(TextView1.getText());
                        TextView1.setVisibility(View.GONE);
                        TextView2.setVisibility(View.GONE);
                        vs.setVisibility(View.GONE);
                        result.setVisibility(View.VISIBLE);*/
                        } else {
                            result.setText(TextView2.getText());
                            TextView1.setVisibility(View.GONE);
                            TextView2.setVisibility(View.GONE);
                            vs.setVisibility(View.GONE);
                            result.setVisibility(View.VISIBLE);

                        }


                    }else if (num == 32) {   //32강
                        if (j <= 15) {
                            list.add(TextView2.getText().toString());
                            TextView1.setText(foods[2 * j]);
                            TextView2.setText(foods[2 * j + 1]);
                        } else if (j == 16) {
                            list.add(TextView2.getText().toString());
                            TextView1.setText(list.get(2 * k - 2));//0
                            TextView2.setText(list.get(2 * k - 1));//1
                            k++;
                        } else if (j >= 17 && j <= 23) {
                            list2.add(TextView2.getText().toString());
                            TextView1.setText(list.get(2 * k - 2));//0,2,4
                            TextView2.setText(list.get(2 * k - 1));//1,3,5
                            k++;
                        } else if (j == 24) {
                            list2.add(TextView2.getText().toString());
                            TextView1.setText(list2.get(2 * l - 2));//0,2,4
                            TextView2.setText(list2.get(2 * l - 1));//1,3,5
                            l++;

                        } else if (j >= 25 && j <= 27) {
                            list3.add(TextView2.getText().toString());
                            TextView1.setText(list2.get(2 * l - 2));//0,2,4
                            TextView2.setText(list2.get(2 * l - 1));//1,3,5
                            l++;
                        } else if (j == 28) {
                            list3.add(TextView2.getText().toString());
                            TextView1.setText(list3.get(2 * m - 2));//0,2,4
                            TextView2.setText(list3.get(2 * m - 1));//1,3,5
                            m++;

                        } else if (j == 29) {
                            list4.add(TextView2.getText().toString());
                            TextView1.setText(list3.get(2 * m - 2));//0,2,4
                            TextView2.setText(list3.get(2 * m - 1));//1,3,5
                            m++;

                        } else if (j == 30) {
                            list4.add(TextView2.getText().toString());
                            TextView1.setText(list4.get(0));//2,4,6,8
                            TextView2.setText(list4.get(1));

                        } else {
                            result.setText(TextView2.getText());
                            TextView1.setVisibility(View.GONE);
                            TextView2.setVisibility(View.GONE);
                            vs.setVisibility(View.GONE);
                            result.setVisibility(View.VISIBLE);

                        }
                    }
                    j++;
                }
        });
    }
}

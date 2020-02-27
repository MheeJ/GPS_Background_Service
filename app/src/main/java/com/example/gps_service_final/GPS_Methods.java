package com.example.gps_service_final;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GPS_Methods extends AppCompatActivity implements View.OnClickListener  {
    Button My_Location;
    TextView My_Latitude_Text, My_Longitude_Text;
    String log;
    String lat;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps_methods);

        initObject();
        intent = getIntent();
        show_my_location();


    }





    public void initObject(){

        My_Location = (Button) findViewById(R.id.my_location_method);
        My_Location.setOnClickListener(this);
        My_Longitude_Text = (TextView) findViewById(R.id.my_longitude);
        My_Latitude_Text = (TextView)findViewById(R.id.my_latitude);
    }

    /*
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode,resultCode,data);
            switch(requestCode){
                case 1:
                    if(resultCode==RESULT_OK){//서브Activity에서 보내온 resultCode와 비교
                        //서브액티비티에서 인텐트에 담아온 정보 꺼내기
                        log = data.getStringExtra("Now_Longitude");
                        lat = data.getStringExtra("Now_Latitude");
                        //텍스트뷰에 수정된 문자열 넣기
                        My_Longtitude_Text.setText(log);
                        My_Latitude_Text.setText(lat);
                    }else{

                    }
                    break;
            }
        }*/
    public void show_my_location(){
        log = intent.getStringExtra( "Now_Longitude");
        lat = intent.getStringExtra("Now_Latitude");
        My_Longitude_Text.setText(log);
        My_Latitude_Text.setText(lat);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_location_method:
                //버튼 클릭시 동작
                /*    log = intent.getStringExtra( "Now_Longitude");
               lat = intent.getStringExtra("Now_Latitude");
               My_Longtitude_Text.setText(log);
               My_Latitude_Text.setText(lat);*/
                break;

        }
    }
}

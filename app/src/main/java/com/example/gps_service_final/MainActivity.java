package com.example.gps_service_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnStart, btnEnd, Go_GPSmethods, Go_comfirm;
    private TextView GPS_Address;
    public String longitude_list = null;
    public String latitude_list = null;
    public String getLongitude_list;
    public String getLatitude_list;
    public TextView my , kk;
    public ArrayList<String> longitude_Array;
    public ArrayList<String> latitude_Array;
    private String DataFromService1,DataFromService2;
    MyReceiver myReceiver;



    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent=getIntent();
        initGUI();
    }

    //텍스트뷰
    public void setTextView(String text){GPS_Address.setText(text);}
    //토스트
    public void  setToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    private void initGUI(){
        btnStart = (Button)findViewById(R.id.btn_start);
        btnStart.setOnClickListener(this);
        btnEnd = (Button)findViewById(R.id.btn_end);
        btnEnd.setOnClickListener(this);
        Go_GPSmethods = (Button)findViewById(R.id.go_gpsmethods);
        Go_comfirm = (Button)findViewById(R.id.gps_enable);
        GPS_Address = (TextView)findViewById(R.id.gps_address) ;

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                Toast.makeText(getApplicationContext(),"Service 시작",Toast.LENGTH_SHORT).show();
                prepareService();
                Intent intent = new Intent(MainActivity.this, GPSBackgroundService.class);
                startService(intent);
                break;
            case R.id.btn_end:
                Toast.makeText(getApplicationContext(),"Service 끝",Toast.LENGTH_SHORT).show();
                DataFromService1 = "";
                DataFromService2 = "";
                Intent intent1 = new Intent(MainActivity.this, GPSBackgroundService.class);
                stopService(intent1);
                set_UI();
        }
    }

    //서비스 시작하기전 준비
    private void prepareService(){
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(GPSBackgroundService.MY_ACTION);
        registerReceiver(myReceiver, intentFilter);
    }

    //서비스에서 데이터 가져오는 부분
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub
            DataFromService1 = arg1.getStringExtra("ServiceData_longitude");
            DataFromService2 = arg1.getStringExtra("ServiceData_latitude");
            set_UI();
        }
    }

    //서비스에서 가져온 데이터 UI에 표시
    public void set_UI(){
        setToast("위도"+DataFromService1+"\n경도"+DataFromService2);
        setTextView("위도"+DataFromService1+"\n경도"+DataFromService2);
    }

}

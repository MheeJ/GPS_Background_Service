package com.example.gps_service_final;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class GPS_Background_Service extends Service {
    Intent intent1;

    @Override
    public IBinder onBind(Intent intent) {
        // Service 객체와 (화면단 Activity 사이에서)
        // 통신(데이터를 주고받을) 때 사용하는 메서드
        // 데이터를 전달할 필요가 없으면 return null;
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();

        //서비스에서 가장 먼저 호출됨(최초에 한번만)
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 서비스가 호출될 때마다 실행
        //  startActivity(intent1);
        intent1 = new Intent(GPS_Background_Service.this, GPS_Movement_Service.class);
        //intent2 = new Intent(MyService.this,GPS_Example.class);
        startActivity(intent1.addFlags(FLAG_ACTIVITY_NEW_TASK));
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
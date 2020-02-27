package com.example.gps_service_final;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import java.util.ArrayList;

public class GPS_Movement_Service extends AppCompatActivity implements View.OnClickListener {
    TextView tv;
    TextView show_arry;
    Button GPS_Enable, Go_GPSMethods;

    public ArrayList<String> longitude_Array;
    public ArrayList<String> latitude_Array;

    public double longitude; //경도
    public double latitude; //위도
    public double altitude; //고도
    public float accuracy; //정확도
    public String provider; //위치제공자

    public String longitude_list = null;
    public String latitude_list = null;
    public String longitude_str = null;
    public String latitude_str = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps_movement_service);


        // Location 제공자에서 정보를 얻어오기(GPS)
        // 1. Location을 사용하기 위한 권한을 얻어와야한다 AndroidManifest.xml
        //     ACCESS_FINE_LOCATION : NETWORK_PROVIDER, GPS_PROVIDER
        //     ACCESS_COARSE_LOCATION : NETWORK_PROVIDER
        // 2. LocationManager 를 통해서 원하는 제공자의 리스너 등록
        // 3. GPS 는 에뮬레이터에서는 기본적으로 동작하지 않는다
        // 4. 실내에서는 GPS_PROVIDER 를 요청해도 응답이 없다.  특별한 처리를 안하면 아무리 시간이 지나도
        //    응답이 없다.
        //    해결방법은
        //     ① 타이머를 설정하여 GPS_PROVIDER 에서 일정시간 응답이 없는 경우 NETWORK_PROVIDER로 전환
        //     ② 혹은, 둘다 한꺼번헤 호출하여 들어오는 값을 사용하는 방식.

        initObject();
        CurrentPosition();
    } // end of onCreate


    public void initObject() {
        tv = (TextView) findViewById(R.id.my_location);
        show_arry = (TextView) findViewById(R.id.my_location_list);
        GPS_Enable = (Button) findViewById(R.id.gps_enable);
        GPS_Enable.setOnClickListener(this);
        Go_GPSMethods = (Button) findViewById(R.id.go_gpsmethods);
        Go_GPSMethods.setOnClickListener(this);
        longitude_Array = new ArrayList<>();
        latitude_Array = new ArrayList<>();
    }

    public void CurrentPosition() {
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            tv.setText("수신중..");
            // GPS 제공자의 정보가 바뀌면 콜백하도록 리스너 등록하기~!!!
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, // 등록할 위치제공자
                    10, // 통지사이의 최소 시간간격 (miliSecond)
                    0, // 통지사이의 최소 변경거리 (m)
                    mLocationListener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자
                    10, // 통지사이의 최소 시간간격 (miliSecond)
                    0, // 통지사이의 최소 변경거리 (m)
                    mLocationListener);
            //show_location();
        } catch (SecurityException ex) {
        }
    }

    private final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            //여기서 위치값이 갱신되면 이벤트가 발생한다.
            //값은 Location 형태로 리턴되며 좌표 출력 방법은 다음과 같다.
            Log.d("test", "onLocationChanged, location:" + location);
            longitude = location.getLongitude(); //경도
            latitude = location.getLatitude();   //위도
            altitude = location.getAltitude();   //고도
            accuracy = location.getAccuracy();    //정확도
            provider = location.getProvider();   //위치제공자
            //Gps 위치제공자에 의한 위치변화. 오차범위가 좁다.
            //Network 위치제공자에 의한 위치변화
            //Network 위치는 Gps에 비해 정확도가 많이 떨어진다.
            show_location();
            tv.setText("위치제공 : " + provider + "\n경도 : " + longitude + "\n위도 : " + latitude + "\n고도 : " + altitude + "\n정확도 : " + accuracy);
            Toast.makeText(GPS_Movement_Service.this, longitude_str + latitude_str, Toast.LENGTH_LONG).show();
        }

        public void onProviderDisabled(String provider) {
            // Disabled시
            Log.d("test", "onProviderDisabled, provider:" + provider);
        }

        public void onProviderEnabled(String provider) {
            // Enabled시
            Log.d("test", "onProviderEnabled, provider:" + provider);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // 변경시
            Log.d("test", "onStatusChanged, provider:" + provider + ", status:" + status + " ,Bundle:" + extras);
        }
    };

    public void show_location() {
        longitude_str = String.valueOf(longitude);
        latitude_str = String.valueOf(latitude);
        longitude_Array.add(longitude_str);
        latitude_Array.add(latitude_str);
        longitude_list = String.join("#", longitude_Array);
        latitude_list = String.join("#", latitude_Array);
        show_arry.setText("\n경도 리스트 : " + longitude_list + "\n위도 리스트 : " + latitude_list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_gpsmethods:
                Intent intent1 = new Intent(this,GPS_Methods.class);
                intent1.putExtra("Now_Longitude", longitude_str);
                intent1.putExtra("Now_Latitude",latitude_str);
                startActivity(intent1);
                break;
            case R.id.gps_enable:
                Intent intent2 = new Intent(this, GPS_confirm.class);
                startActivity(intent2.addFlags(FLAG_ACTIVITY_NEW_TASK));
                break;
        }
    }
}




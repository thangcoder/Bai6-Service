package com.thangcoder.bai6_service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.thangcoder.bai6_service.service.BoundService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnStart, btnStop;
    public static final String THANGCODER ="thangcoder";
    private boolean idBoundService = false;
    private BoundService boundService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStart = (Button) findViewById(R.id.btn_start_service);
        btnStop = (Button) findViewById(R.id.btn_stop_service);
        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
//        Intent intent = new Intent(this, StartService.class);
        Intent intent = new Intent(this, BoundService.class);
        intent.putExtra(THANGCODER,"ThangCoder.Com");
        switch (view.getId()){
            case R.id.btn_start_service:
//                startService(intent);
                bindService(intent,serviceConnection,BIND_AUTO_CREATE);
                break;
            case R.id.btn_stop_service:
//                stopService(intent);
                if(idBoundService){
                    unbindService(serviceConnection);
                    idBoundService =false;
                }
                break;
        }
    }
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            BoundService.BoundExample bider = (BoundService.BoundExample) iBinder;
            boundService = bider.getService();
            Toast.makeText(MainActivity.this, "onServiceConnected: "+boundService.getCurrentTime(), Toast.LENGTH_SHORT).show();
            idBoundService = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            idBoundService = false;
            Toast.makeText(MainActivity.this, "onServiceDisconnected", Toast.LENGTH_SHORT).show();
        }
    };
}

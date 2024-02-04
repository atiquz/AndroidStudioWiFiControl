package com.atidevelopers.androidstudiowificontrol;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button01, button02;
    private ActivityResultLauncher<Intent> wifiPanelLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button01 = findViewById(R.id.button01);
        button02 = findViewById(R.id.button02);

        wifiPanelLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // Handle the result here, if needed
                });

        button01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchWiFi(true);
            }
        });

        button02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchWiFi(false);
            }
        });
    }

    private void switchWiFi(boolean isOn) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Intent panelIntent = new Intent(Settings.Panel.ACTION_WIFI);
            wifiPanelLauncher.launch(panelIntent);
        } else {
            WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (wifiMgr != null) {
                wifiMgr.setWifiEnabled(isOn);
            }
        }
    }
}

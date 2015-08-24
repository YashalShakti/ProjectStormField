package com.stormfieldlabs.projectstormfield.wifi;

import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.stormfieldlabs.projectstormfield.R;
import com.stormfieldlabs.projectstormfield.database.IpAddress;
import com.stormfieldlabs.projectstormfield.database.PrefHandler;
import com.stormfieldlabs.projectstormfield.http.LocalClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class connector extends AppCompatActivity {

    TextView message;
    EditText ssid,pwd;
    Switch aSwitch;
    Context context;
    FloatingActionButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifiap);
        message=(TextView)findViewById(R.id.message);
        message.setMovementMethod(new ScrollingMovementMethod());
        context=this.getApplicationContext();
        button=(FloatingActionButton)findViewById(R.id.fab2);
        ssid=(EditText)findViewById(R.id.ssid);
        pwd=(EditText)findViewById(R.id.pwd);
        aSwitch=(Switch)findViewById(R.id.switch1);
        try {
            WifiApManager wifiApManager1 = new WifiApManager(context);
            if(wifiApManager1.getWifiApState()==3) {
                aSwitch.setChecked(true);
                WifiConfiguration wifiConfiguration = new WifiConfiguration();
                wifiApManager1.setWifiApState(wifiConfiguration,false);
                aSwitch.setChecked(false);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    WifiApManager wifiApManager = new WifiApManager(context);
                    WifiConfiguration wifiConfiguration = new WifiConfiguration();
                    wifiConfiguration.SSID = "Photon";
                    wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
                    wifiConfiguration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
                    wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
                    wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
                    wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                    wifiConfiguration.preSharedKey = "Photon";
                    wifiApManager.setWifiApState(wifiConfiguration, isChecked);

                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        });
         List<String> finalIP = new ArrayList<>();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ssid.getText().toString().isEmpty()||pwd.getText().toString().isEmpty())
                    Toast.makeText(context,"SSID and Password are needed",Toast.LENGTH_SHORT).show();
                else
                    IpAddress.verifyIp(context,ssid.getText().toString(),pwd.getText().toString());
            }
        });
        message.append(finalIP.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

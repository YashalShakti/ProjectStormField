package com.stormfieldlabs.projectstormfield.wifi;

/**
 * Created by Yashal on 8/23/2015.
 */

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.lang.reflect.Method;

public final class WifiApManager {
    private static final int WIFI_AP_STATE_FAILED = 4;
    private final WifiManager mWifiManager;
    private final String TAG = "Wifi Access Manager";
    private Method wifiControlMethod;
    private Method wifiApConfigurationMethod;
    private Method wifiApState;

    public WifiApManager(Context context) throws SecurityException, NoSuchMethodException {
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiControlMethod = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class,boolean.class);
        wifiApConfigurationMethod = mWifiManager.getClass().getMethod("getWifiApConfiguration",null);
        wifiApState = mWifiManager.getClass().getMethod("getWifiApState");
    }
    public boolean setWifiApState(WifiConfiguration config, boolean enabled) {
        try {
            if (enabled) {
                mWifiManager.setWifiEnabled(!enabled);
            }
            return (Boolean) wifiControlMethod.invoke(mWifiManager, config, enabled);
        } catch (Exception e) {
            Log.e(TAG, "", e);
            return false;
        }
    }
    public WifiConfiguration getWifiApConfiguration()
    {
        try{
            return (WifiConfiguration)wifiApConfigurationMethod.invoke(mWifiManager, null);
        }
        catch(Exception e)
        {
            return null;
        }
    }
    public int getWifiApState() {
        try {
            int tmp= (Integer)wifiApState.invoke(mWifiManager);
            if(tmp>10)
                tmp-=10;
            return tmp;
        } catch (Exception e) {
            Log.e(TAG, "", e);
            return WIFI_AP_STATE_FAILED;
        }
    }
}
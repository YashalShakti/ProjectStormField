package com.stormfieldlabs.projectstormfield.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.stormfieldlabs.projectstormfield.http.LocalClient;
import com.stormfieldlabs.projectstormfield.wifi.connections;

import java.util.List;

/**
 * Created by Yashal on 8/23/2015.
 */
public class IpAddress {
    public static void verifyIp(Context context, final String ssid, final String password){
        final PrefHandler prefHandler=new PrefHandler(context,"connection_parameters");
        final List<String> ips = connections.getConnections();
        if (ips != null) {
            for (final String ip : ips)
                new AsyncTask<String, Void, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        try {
                            return LocalClient.getDef(ip+"/"+ssid+"photon"+password+"notohp");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return "Didnt work";
                    }

                    @Override
                    protected void onPostExecute(String string) {
                      //  message.append("Got >>"+string+"<<");//Ph0t0n_D3vic3
                        if(Integer.parseInt(string.substring(0,6))==709536){
                            prefHandler.put("ip", prefHandler.get("ip") + "," + ip, true);
                           // message.append(string);
                        }
                        else
                            Log.d("String", string + "\n\n\n" + string.substring(0, 6));
                    }
                }.execute();

        } else
            Toast.makeText(context, "No devices connected", Toast.LENGTH_LONG).show();
    }
}

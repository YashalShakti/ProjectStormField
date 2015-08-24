package com.stormfieldlabs.projectstormfield.database;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Yashal on 4/30/2015.
 */
public class PrefHandler {
    SharedPreferences sharedPref ;
    Context activity;
    String TYPE;
    public PrefHandler(Context activity,String TYPE)
    {
       this.activity=activity;
        this.TYPE=TYPE;
       sharedPref = activity.getSharedPreferences(TYPE,0x0000);
    }
    public void put(String tag,String data,boolean overwrite)
    {
        if(!overwrite&&!get(tag).isEmpty())
            return;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(tag, data);
        editor.commit();
    }
    public String get(String tag)
    {
        return sharedPref.getString(tag,"");
    }
    public void put(String tag,int data,boolean overwrite)
    {
        if(!overwrite&&!get(tag).isEmpty())
            return;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(tag, data);
        editor.commit();
    }

    public int get(String tag,int def)
    {
        return sharedPref.getInt(tag,def);
    }
}

package com.stormfieldlabs.projectstormfield.wifi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yashal on 8/23/2015.
 */
public class connections {
    public static List<String> getConnections(){
        int macCount = 0;
        BufferedReader br = null;
        List<String> ip=new ArrayList<>();
        List<String> hw=new ArrayList<>();
        List<String> dv=new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader("/proc/net/arp"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitted = line.split(" +");
                if (splitted != null ) {
                    // Basic sanity check
                    String mac = splitted[3];
                    System.out.println("Mac : Outside If "+ mac );
                    //18:FE:34
                    if (mac.matches("18:fe:34:..:..:..")) {
                        macCount++;
                        ip.add(splitted[0]);
                        hw.add(splitted[3]);
                        dv.add(splitted[4]);
                    }
                }
            }
            if(macCount>0) {
                String addresses="[{count:"+macCount+"}";
                for (int i = 0; i < macCount; i++){
                    addresses+="\n{{" + "index" + ":" + i + "}"+
                            "  {" + "ip" + ":" + ip.get(i) + "}"+
                            "  {" + "mac" + ":" + hw.get(i) + "}"+
                            "  {" + "name" + ":" + dv.get(i) + "}}";
                }
                addresses += "]";
                return ip;
            }
            else
              return  null;
        } catch(Exception e) {

        }
        return null;

    }
}

package net.rayab.mahimah.Request;

import java.util.HashMap;
import java.util.Map;

public class Sort {

    public static void Sender(String FromPosition, String FromId, String ToPosition, String ToId, String Key){
        WebApi wAPI = new WebApi();

        Map<String, String> Values = new HashMap<>();
        Values.put("FromPosition", FromPosition);
        Values.put("FromId", FromId);
        Values.put("ToPosition", ToPosition);
        Values.put("ToId", ToId);
        Values.put("mType", Key);
        wAPI.API("https://mahimah.com/app/Sorter.php", Values);
    }
    public static void Sender(String data, String Key){
        WebApi wAPI = new WebApi();

        Map<String, String> Values = new HashMap<>();
        Values.put("data", data);
        Values.put("mType", Key);
        wAPI.API("https://mahimah.com/app/Sorter.php", Values);
    }

}

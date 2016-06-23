package com.luckydistance.customutil;

import org.json.simple.JSONObject;
import java.util.Comparator;

/**
 * Created by David.Morrissey on 23/6/2016.
 * Custom comparator for comparing user ids
 */

public class IdComparator implements Comparator<JSONObject> {
    @Override
    public int compare(JSONObject o1, JSONObject o2) {
        return ((Long)o1.get("user_id")).compareTo(((Long)o2.get("user_id")));
    }
}
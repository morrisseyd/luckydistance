package com.luckydistance.distancecalculations;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.text.DecimalFormat;

import java.util.ArrayList;


/**
 * Created by David.Morrissey on 23/06/2016.
 * This class calculates the distance between two points.
 */
public class CalculateDistance {

    private static double DUBLIN_OFFICE_LAT = 53.3381985;
    private static double DUBLIN_OFFICE_LON = -6.2592576;
    // Distance Threshold
    private static double DISTANCE_RADIUS = 100;
    private static String DISTANCE_UNIT = "KM";

    /**
     * This Method checks the clients are within the DISTANCE_RADIUS
     * If they are within DISTANCE_RADIUS then they are added to a
     * new list.
     * @param listOfJSONObjects The list of JSON client objects
     * @return A list of JSON objects containing clients within the DISTANCE_RADIUS
     * @throws java.lang.Exception
     */
    public ArrayList<JSONObject> checkTheDistance(ArrayList<JSONObject> listOfJSONObjects ) throws java.lang.Exception
    {
        ArrayList<JSONObject> listOfJsonObjectsWithinDistance = new ArrayList<JSONObject>();
        listOfJSONObjects.forEach((tempObject) -> {

            try{

                JSONObject theJsonObject = (JSONObject) new JSONParser().parse(tempObject.toString());
                double theCurrentUsersDistance = getTheDistance(DUBLIN_OFFICE_LAT,DUBLIN_OFFICE_LON, (String)theJsonObject.get("latitude"), (String)theJsonObject.get("longitude"));
                DecimalFormat df = new DecimalFormat("#.00");

                //Check if theCurrentUsersDistance <= 100KM.
                //If it is add it to our new list.
                if(theCurrentUsersDistance<=DISTANCE_RADIUS){
                    //Add the distance to the record so we can use it
                    //later
                    theJsonObject.put("distance",df.format(theCurrentUsersDistance));
                    listOfJsonObjectsWithinDistance.add(theJsonObject);
                }
            }catch(ParseException e){
                e.printStackTrace();
            }
        });
        return listOfJsonObjectsWithinDistance;
    }

    /**
     * This method calculates the distance between a latitude, longitude
     * and a second latitude, longitude
     * @param lat1 The first latitude
     * @param lon1 The first longitude
     * @param jsonLat The second latitude
     * @param jsonLon The second longitude
     * @return The distance
     */
    private static double getTheDistance(double lat1, double lon1, String jsonLat, String jsonLon) {

        double lat2 = Double.parseDouble(jsonLat);
        double lon2 = Double.parseDouble(jsonLon);

        double theta = lon1 - lon2;

        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        if (DISTANCE_UNIT.equals("KM")) {
            dist = dist * 1.609344;
        }

        return (dist);
    }

    /**	This method converts decimal degrees to radians
     * @param deg
     * @return degrees
	*/
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /**
     * This method converts radians to decimal degrees
     * @param rad
     * @return radians
     */
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

}

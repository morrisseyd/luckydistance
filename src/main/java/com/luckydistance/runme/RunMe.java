package com.luckydistance.runme;

import java.io.File;

import com.luckydistance.customutil.IdComparator;
import com.luckydistance.distancecalculations.CalculateDistance;
import com.luckydistance.fileoperations.JSONFileReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Created by David.Morrissey on 6/16/2016.
 * This is the main class that kicks off the process.
 * @author David.Morrissey
 */
public class RunMe {

    public static void main (String[] args) throws Exception {

        // Check the arguments passed in. We expect a
        // filepath & filename.
        if (args.length == 0) {
            // The arguments are empty
            // raise message & exit
            System.out.println("**************************************************************");
            System.out.println("Hi. There appears to have been an error.");
            System.out.println("You need to provide a valid filepath & file name. Example: C:\\SampleFolder\\filename");
            System.out.println("**************************************************************");
            System.exit(-1);
        } else {
            CalculateDistance theDistanceCalculator = new CalculateDistance();

                //Get the objects that are within distance
                ArrayList<JSONObject> listOfJsonObjectsWithinDistance = theDistanceCalculator.checkTheDistance(getTheJSONFile(args[0]));

                //If the list has more than 2 entries
                //Sort the list.
                if(listOfJsonObjectsWithinDistance.size()>2){
                    Collections.sort(listOfJsonObjectsWithinDistance, new IdComparator());
                    printTheList(listOfJsonObjectsWithinDistance);
                }
        }
    }

    /**
     * Method to get the JSON File. Takes the
     * Filename before creating an instance of the JSON File reader
     * and reading in the file.
     * @param theFileNameAndPath
     * @return a list of JSON objects
     */
    public static ArrayList getTheJSONFile (String theFileNameAndPath) throws IOException, ParseException {

        //Process the file
        JSONFileReader theFileReader = new JSONFileReader();
        ArrayList<JSONObject> listOfJsonObjects = theFileReader.readAndParseFile(theFileNameAndPath);

        return listOfJsonObjects;
    }

    /**
     * The final method called.
     * It Prints the Clients to the console
     * @param theSortedListOfClients the list of of clients
     */
    public static void printTheList(ArrayList<JSONObject> theSortedListOfClients) {

        System.out.println("/********** THE CLIENT'S WITHIN 100KM ARE: **********/");
        System.out.println(String.format("%15s","USER ID")+String.format("%20s","DISTANCE")+String.format("%22s","USERNAME"));

        theSortedListOfClients.forEach((tempJSONObject) ->  {
            try{
                JSONObject theJsonObject = (JSONObject) new JSONParser().parse(tempJSONObject.toString());
                //Could use a StringBuilder but I'll break the 80 char rule here
                System.out.println(String.format("%15s",String.valueOf(theJsonObject.get("user_id")))+String.format("%17s",String.valueOf(theJsonObject.get("distance")))+" KM"+String.format("%22s",(String)theJsonObject.get("name")));

            }catch (ParseException e){
                System.out.println("**************************************************************");
                System.out.println("Hi. There appears to have been an error parsing the file.");
                System.out.println("**************************************************************");
                e.printStackTrace();
                System.exit(-1);
            }
        });

    }

    }



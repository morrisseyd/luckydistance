package com.luckydistance.fileoperations;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;




/**
 * This Class reads in the JSON File
 * Created by David.Morrissey on 23/6/2016.
 */
public class JSONFileReader {

    /**
     * This method reads & parses the JSON file
     * @param theFileName
     * @return A list of JSON objects read from the file
     */
    public ArrayList readAndParseFile(String theFileName) throws IOException, ParseException {

        ArrayList<JSONObject> listOfJsonObjects = new ArrayList<JSONObject>();
        JSONObject theJsonObject;
        FileReader theFileReader = null;

            theFileReader = new FileReader(theFileName);
            BufferedReader theBufferReader = new BufferedReader(theFileReader);
            int count = 1;
            String line = theBufferReader.readLine();
            while (line != null) {
                theJsonObject = (JSONObject) new JSONParser().parse(line);
                listOfJsonObjects.add(theJsonObject);
                line = theBufferReader.readLine();
                count++;
            }
            theBufferReader.close();

        return listOfJsonObjects;
    }
}
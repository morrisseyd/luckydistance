package com.luckydistance.fileoperations;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.junit.Assert;
import org.skyscreamer.jsonassert.JSONAssert;
import org.junit.rules.ExpectedException;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by David.Morrissey on 25/6/2016.
 */
public class JSONFileReaderTest {

    String theFileName = "gistfile1.txt";
    String workingDir = System.getProperty("user.dir");
    String theBadContentFileName = "badcontent.txt";
    String theBadFilePath = "C:\\abcd";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void throwsIOExceptionIfFileNameIsNull() throws IOException,ParseException{
        thrown.expect(IOException.class);
        JSONFileReader theFileReader = new JSONFileReader();
        theFileReader.readAndParseFile("");
    }

    @Test
    public void throwsIOExceptionIfFilePathIsIncorrect() throws IOException,ParseException{
        thrown.expect(IOException.class);
        JSONFileReader theFileReader = new JSONFileReader();
        theFileReader.readAndParseFile(theBadFilePath);
    }
    @Test
    public void throwsParserExceptionIfNonJSONFileContents() throws IOException,ParseException{
        thrown.expect(ParseException.class);
        JSONFileReader theFileReader = new JSONFileReader();
        theFileReader.readAndParseFile(new StringBuilder().append(workingDir).append("\\").append(theBadContentFileName).toString());
    }

    @Test
    public void checkForValidJSONData() throws org.json.JSONException,IOException,ParseException {
        JSONFileReader theFileReader = new JSONFileReader();
        ArrayList<JSONObject> listOfJsonObjects = theFileReader.readAndParseFile(new StringBuilder().append(workingDir).append("\\").append(theFileName).toString());
        JSONObject theJsonObject = (JSONObject) new JSONParser().parse(listOfJsonObjects.get(0).toString());
        JSONAssert.assertEquals("{user_id:12,name:\"Christina McArdle\"}", theJsonObject.toJSONString(), false);
    }

    @After
    public void tearDown() throws Exception {

    }

}
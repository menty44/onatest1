/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test1;

/**
 *
 * @author fred
 */

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JSON2CSV {
    private static Object response;
    
    public static void main(String myHelpers[]) throws JSONException, IOException{
     String jsonString = "{\"infile\": [{\"field1\": 11,\"field2\": 12,\"field3\": 13},{\"field1\": 21,\"field2\": 22,\"field3\": 23},{\"field1\": 31,\"field2\": 32,\"field3\": 33}]}";
        JSONTokener jsonOut = null;

     JSONObject output = new JSONObject(jsonOut);
     JSONArray docs = response.getJSONArray("infile");

     File file=new File("yourpath/fromJSON.csv");
     String csv = CDL.toString(docs);
     FileUtils.writeStringToFile(file, csv);
  }
    
}

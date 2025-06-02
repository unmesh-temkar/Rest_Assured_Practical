package org.example;

import io.restassured.path.json.JsonPath;

public class ReusuableMethods {

    public static String parseJsonResponse(String response, String fieldToParse){
        JsonPath jsonPath = new JsonPath(response);
        return jsonPath.getString(fieldToParse);
    }
}

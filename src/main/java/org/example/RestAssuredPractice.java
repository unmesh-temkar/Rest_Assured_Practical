package org.example;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * Hello world!
 */
public class RestAssuredPractice {
    public static void main(String[] args) {

        //Set RestAssured class's static parameter baseURI first:
        RestAssured.baseURI = "https://rahulshettyacademy.com/";

        //create place API:
        System.out.println("******************* create place API *******************");
        String response =
                given().log().all()
                        .queryParam("key", "qaclick123")
                        .header("Content-Type", "application/json")
                        .body(Payload.addPlace())
                        .when()
                        .post("maps/api/place/add/json")
                        .then().log().all()
                        .assertThat().statusCode(200).body("scope", equalTo("APP"))
                        .extract().response().asPrettyString();

        System.out.println("response: \n" + response);

        //parsing the response using JsonPath class from rest-assured library:
        JsonPath jsonpath = new JsonPath(response);

        //extracting certain fields from the response of create API:
        String placeId = jsonpath.getString("place_id");
        System.out.println("placeId: " + placeId);

        //update place with new address:
        String updatedAddress = "Dhankawadi, Pune, Maharashtra";
        System.out.println("******************* update place API *******************");
        given().log().all()
                .queryParam("key","qaclick123")
                .body(Payload.updatePlace(placeId,updatedAddress))
                .when()
                .put("maps/api/place/update/json")
                .then().log().all()
                .assertThat().statusCode(200)
                .body("msg",equalTo("Address successfully updated"));

        //use GET api to check if address was correctly updated:
        System.out.println("******************* get place API *******************");

        //Collect get place api response in a string:
        String getPlaceResponse =
                given().log().all()
                        .queryParam("key","qaclick123").queryParam("place_id",placeId)
                        .when()
                        .get("/maps/api/place/get/json")
                        .then().log().all()
                        .assertThat().statusCode(200).extract().response().asPrettyString();

        //parse this response using jsonpath method to assert if address was correctly updated:
        JsonPath js = new JsonPath(getPlaceResponse);
        String addressFromResponse = js.getString("address");

        //use TestNG
        Assert.assertEquals(addressFromResponse,updatedAddress);
    }
}
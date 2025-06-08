package SpecBuilder;

import static io.restassured.RestAssured.*;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.example.Payload;
import org.testng.annotations.Test;

public class SpecBuilderTest {

    @Test
    public void requestSpecBuild() {

        RequestSpecification request =
                new RequestSpecBuilder()
                        .setBaseUri("https://rahulshettyacademy.com")
                        .addQueryParam("key", "qaclick123")
                        .setContentType(ContentType.JSON)
                        .build();

        RequestSpecification rec = given().spec(request).body(Payload.addPlace());

        ResponseSpecification responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();

        //calling add place api
        String response = rec
                .when()
                .post("maps/api/place/add/json")
                .then()
                .spec(responseSpec)
                .extract().response().asPrettyString();

        System.out.println("response: " + response);
    }
}

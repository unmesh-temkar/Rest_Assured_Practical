package serialization;

import POJO.AddPlace.AddPlaceRequestBody;
import POJO.AddPlace.Location;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class GoogleAddPlaceTest {

    @Test
    public void addPlace() {
        AddPlaceRequestBody addPlaceRequestBody = getAddPlaceRequestBody();


        String response = given().log().all()
                .queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(addPlaceRequestBody)
                .when()
                .post("https://rahulshettyacademy.com/maps/api/place/add/json")
                .then().log().all()
                .assertThat().statusCode(200).extract().response().asPrettyString();

        System.out.println("response: " + response);

    }

    private static AddPlaceRequestBody getAddPlaceRequestBody() {
        Location location = new Location();
        location.setLat(-38);
        location.setLng(30);

        List<String> types = new ArrayList<>();
        types.add("bla1");
        types.add("bla2");
        types.add("bla3");

        AddPlaceRequestBody addPlaceRequestBody = new AddPlaceRequestBody();
        addPlaceRequestBody.setLocation(location);
        addPlaceRequestBody.setAccuracy(50);
        addPlaceRequestBody.setName("Unmesh");
        addPlaceRequestBody.setPhone_number("900 000 0000");
        addPlaceRequestBody.setAddress("Dhankawadi");
        addPlaceRequestBody.setTypes(types);
        addPlaceRequestBody.setWebsite("google");
        addPlaceRequestBody.setLanguage("ENG");
        return addPlaceRequestBody;
    }
}

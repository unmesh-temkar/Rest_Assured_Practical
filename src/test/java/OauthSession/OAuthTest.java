package OauthSession;

import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

public class OAuthTest {
    private String accessToken;

    @Test(testName = "get access token")
    public void getAccessToken() {

        String response =
                given().log().all()
                        .formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                        .formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                        .formParam("grant_type", "client_credentials")
                        .formParam("scope", "trust")
                        .post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asPrettyString();
        System.out.println("--------------> response: \n" + response);
        JsonPath jsonPath = new JsonPath(response);
        accessToken = jsonPath.getString("access_token");
        System.out.println("--------------> accessToken: \n" + accessToken);
    }

    @Test(testName = "get course details", dependsOnMethods = "getAccessToken")
    public void getCourseDetails() {
        given().log().all()
                .queryParam("access_token", accessToken)
                .when()
                .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
                .then().log().all()
                .assertThat().statusCode(401);
    }
}

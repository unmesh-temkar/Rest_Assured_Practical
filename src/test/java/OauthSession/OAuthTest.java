package OauthSession;

import static io.restassured.RestAssured.*;

import POJO.getCourse.Api;
import POJO.getCourse.GetCourseResponse;
import POJO.getCourse.WebAutomation;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OAuthTest {
    private String accessToken;
    String[] expectedCourseTitlesInWebAutomation = {"Selenium Webdriver Java", "Cypress", "Protractor"};
    ArrayList<String> actualCourseTitlesInWebAutomation = new ArrayList<>();

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
        String response =
                given().log().all()
                        .queryParam("access_token", accessToken)
                        .when()
                        .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
                        .then().log().all()
                        .assertThat().statusCode(401).extract().response().asPrettyString();
        System.out.println("--------------> response: \n" + response);
    }

    @Test(testName = "get course deserialize", dependsOnMethods = "getAccessToken")
    public void getCourseDeserialize() {
        GetCourseResponse response =
                given()
                        .queryParam("access_token", accessToken)
                        .when()
                        .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
                        .then()
                        .assertThat().statusCode(401).extract().response().as(GetCourseResponse.class);
        System.out.println("--------------> instructor: " + response.getInstructor());

        //get price of SoapUi courses
        List<Api> apiList = response.getCourses().getApi();
        for (Api api : apiList) {
            if (api.getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
                System.out.println("-----------------------");
                System.out.println("Title: " + api.getCourseTitle());
                System.out.println("Price: " + api.getPrice());
            }
        }

        //print all course titles of web automation:
        List<WebAutomation> webAutomation = response.getCourses().getWebAutomation();
        for (WebAutomation web : webAutomation) {
            actualCourseTitlesInWebAutomation.add(web.getCourseTitle());
        }

        //compare expected array with actual array list:
        //1. first we convert expected array to array list:
        List<String> expectedCourseTitles = Arrays.asList(expectedCourseTitlesInWebAutomation);

        boolean isResponseValid = expectedCourseTitles.equals(actualCourseTitlesInWebAutomation);
        Assert.assertTrue(isResponseValid);
        System.out.println("-------------------");
        System.out.println("actualCourseTitlesInWebAutomation: " + actualCourseTitlesInWebAutomation);
        System.out.println("expectedCourseTitles: " + expectedCourseTitles);
        System.out.println("isResponseValid: " + isResponseValid);
    }
}

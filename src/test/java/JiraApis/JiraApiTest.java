package JiraApis;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;

public class JiraApiTest {
    public static void main(String[] args) {
        RestAssured.baseURI = "https://unmeshtemkar1303.atlassian.net";

                given().log().all()
                    .header("Content-Type","application/json")
                    .header("Authorization","Basic dW5tZXNodGVta2FyLjEzMDNAZ21haWwuY29tOlFWUkJWRlF6ZUVabVIwWXdVWFp6Tlcxd2VURm5kVVZSUjB0ZlJGOW1ZWFEyUjAxSmVGWjZSWHBqUW5ab1VHczNVV3czY0c5U2R5MHhRVWgzZDJORE1VTlFZa2QyUm1Kd1lXSnFiRzlMZUU1TldGOXVPRmxHTWxwM2NtcG9ObFZ3ZG1aSWJsQnJjR3RwUkdGaFVHOWpWMHRyUlVWbFlUVlhZVVZIZUVkWFVrZzRhRkZQYmxKMlprMXVWME51WmxSQ1pETlNYMEpLVlRKZlIweGpWMkY0ZGtsdU4zSTRSRTVaY1hGQlFXSjRhbloyUWs5R2IwRmpQVUV3TWtNMVEwTkQ=")
                    .body("{\n" +
                            "    \"fields\": {\n" +
                            "       \"project\":\n" +
                            "       {\n" +
                            "          \"key\": \"SCRUM\"\n" +
                            "       },\n" +
                            "       \"summary\": \"Bug created using rest api call\",\n" +
                            "       \"description\": \"Creating of an issue using project keys and issue type names using the REST API\",\n" +
                            "       \"issuetype\": {\n" +
                            "          \"name\": \"Bug\"\n" +
                            "       }\n" +
                            "   }\n" +
                            "}\n")
                .when()
                    .post("rest/api/3/issue")
                .then().log().all()
                    .assertThat().statusCode(401);
    }
}

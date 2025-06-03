package advancedPayloads;

import io.restassured.RestAssured;
import org.example.Payload;
import org.example.ReusuableMethods;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class DynamicJsonTest {

    @Test
    public void addBook(){
        RestAssured.baseURI = "http://216.10.245.166";

        String response =
                given().log().all()
                        .header("Content-Type", "application/json")
                        .body(Payload.addBookPayload("unm", "123"))
                .when()
                    .post("Library/Addbook.php")
                .then().log().all()
                    .assertThat().statusCode(200).extract().response().asPrettyString();

        String id = ReusuableMethods.parseJsonResponse(response,"ID");
        System.out.println("id: " + id);
    }
}

package advancedPayloads;

import io.restassured.RestAssured;
import org.example.Payload;
import org.example.ReusuableMethods;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class DynamicJsonTest {

    @Test(dataProvider = "BooksData")
    public void addBook(String isbn, String aisle){
        RestAssured.baseURI = "http://216.10.245.166";

        String response =
                given().log().all()
                        .header("Content-Type", "application/json")
                        .body(Payload.addBookPayload(isbn, aisle))
                .when()
                    .post("Library/Addbook.php")
                .then().log().all()
                    .assertThat().statusCode(200).extract().response().asPrettyString();

        String id = ReusuableMethods.parseJsonResponse(response,"ID");
        System.out.println("id: " + id);
        System.out.println("-------------------------------------------");
    }

    @Test(dataProvider = "BooksData")
    public void deleteBooks(){
        RestAssured.baseURI = "http://216.10.245.166";

        String response =
                given().
    }

    @DataProvider(name = "BooksData")
    public String[][] getBooksData(){
        return new String[][]{{"abc","123"},{"efg","456"},{"ijk","789"}};
    }
}

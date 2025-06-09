package EcomAPI;

import EcomAPI.POJO.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class EComAPITest {
    public static void main(String[] args) {
        // login to ecom app and store token, userid:
        RequestSpecification request =
                new RequestSpecBuilder()
                        .setBaseUri("https://rahulshettyacademy.com")
                        .setContentType(ContentType.JSON)
                        .build();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail("unmeshtemkar.1303@gmail.com");
        loginRequest.setUserPassword("1995@Umaaa");

        RequestSpecification reqLogin =
                given().log().all()
                        .spec(request)
                        .body(loginRequest);

        LoginResponse loginResponse =
                reqLogin
                        .when()
                        .post("api/ecom/auth/login")
                        .then().log().all()
                        .extract()
                        .response()
                        .as(LoginResponse.class);

        String sessionToken = loginResponse.getToken();
        String userId = loginResponse.getUserId();
        System.out.println("sessionToken: " + sessionToken);
        System.out.println("userId: " + userId);
        //-------------------------------------------------------------------------
        //create product in ecom app:
        RequestSpecification createProductBaseReq =
                new RequestSpecBuilder()
                        .setBaseUri("https://rahulshettyacademy.com")
                        .addHeader("Authorization", sessionToken)
                        .build();

        RequestSpecification reqCreateProduct =
                given().log().all()
                        .spec(createProductBaseReq)
                        .formParam("productName", "k Shirts")
                        .formParam("productAddedBy", userId)
                        .formParam("productCategory", "fashion")
                        .formParam("productSubCategory", "shirts")
                        .formParam("productPrice", "15000")
                        .formParam("productDescription", "350W hub motor ebike")
                        .formParam("productFor", "women")
                        .multiPart("productImage", new File("C:" + File.separator + "Users" + File.separator + "Unmesh" + File.separator + "Pictures" + File.separator + "images.png"));
        CreateProductResponse createProductResponse =
                reqCreateProduct
                        .when()
                        .post("api/ecom/product/add-product")
                        .then().log().all()
                        .extract().response().as(CreateProductResponse.class);

        String productId = createProductResponse.getProductId();

        //-------------------------------------------------------------------------
        //create product order in ecom app:
        RequestSpecification createOrderBaseReqSpec =
                new RequestSpecBuilder()
                        .setBaseUri("https://rahulshettyacademy.com")
                        .setContentType(ContentType.JSON)
                        .addHeader("Authorization", sessionToken)
                        .build();

        Orders orders = new Orders();
        orders.setCountry("India");
        orders.setProductOrderedId(productId);

        List<Orders> ordersList = new ArrayList<>();
        ordersList.add(orders);

        CreateOrderRequest orderRequestBody = new CreateOrderRequest();
        orderRequestBody.setOrders(ordersList);

        RequestSpecification createOrderReq =
                given().log().all()
                        .spec(createOrderBaseReqSpec)
                        .body(orderRequestBody);

        CreateOrderResponse orderResponseBody =
                createOrderReq
                        .when()
                        .post("api/ecom/order/create-order")
                        .then().log().all()
                        .extract().response().as(CreateOrderResponse.class);
        System.out.println("orderId: " + orderResponseBody.getOrders().getFirst());

        //-------------------------------------------------------------------------
        //delete product in ecom app:
        RequestSpecification deleteRequestBaseSpec =
                new RequestSpecBuilder()
                        .setBaseUri("https://rahulshettyacademy.com")
                        .addHeader("Authorization", sessionToken)
                        .build();
        RequestSpecification deleteReq =
                given().log().all()
                        .spec(deleteRequestBaseSpec)
                        .pathParam("productId", productId);

        DeleteProduct deleteResponse =
                deleteReq
                        .when()
                        .delete("api/ecom/product/delete-product/{productId}")
                        .then().log().all()
                        .extract().response().as(DeleteProduct.class);
        System.out.println("message: " + deleteResponse.getMessage());
    }
}

package org.example;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;


public class SumValidationTest {

    @Test
    public void sumOfCourses(){
        JsonPath jsonPath = new JsonPath(Payload.complexJsonParse());
        int coursesCount = jsonPath.get("courses.size()");
        int purchaseAmount = jsonPath.getInt("dashboard.purchaseAmount");

        //verify if sum of all course prices match the purchase amount:
        System.out.println("-------------------- verify if sum of all course prices match the purchase amount: --------------------");
        int calculatedAmount = 0;
        for (int i = 0; i < coursesCount; i++) {
            System.out.println("----------------------------------------");
            int coursePrice = jsonPath.getInt("courses[" + i + "].get(\"price\")");
            System.out.println("coursePrice: " + coursePrice);
            int copies = jsonPath.getInt("courses[" + i + "].get(\"copies\")");
            System.out.println("copies: " + copies);
            calculatedAmount = calculatedAmount + (copies * coursePrice);
            System.out.println("calculatedAmount: " + calculatedAmount);
        }
        Assert.assertEquals(calculatedAmount,purchaseAmount);
    }
}

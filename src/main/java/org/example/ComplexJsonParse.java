package org.example;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;

public class ComplexJsonParse {

    public static void main(String[] args) {
        JsonPath jsonPath = new JsonPath(Payload.complexJsonParse());

        //print number of courses:
        System.out.println("-------------------- print number of courses --------------------");
        int coursesCount = jsonPath.get("courses.size()");
        System.out.println("coursesCount: " + coursesCount);

        //print purchase amt:
        System.out.println("-------------------- print purchase amt: --------------------");
        int purchaseAmount = jsonPath.getInt("dashboard.purchaseAmount");
        System.out.println("purchaseAmount: " + purchaseAmount);

        //print title of the first course
        System.out.println("-------------------- print title of the first course --------------------");
        String firstCourseTitle = jsonPath.get("courses[0].get(\"title\")");
        System.out.println("firstCourseTitle: " + firstCourseTitle);

        //print all course titles with their prices:
        System.out.println("-------------------- print all course titles with their prices: --------------------");
        for (int i = 0; i < coursesCount; i++) {
            String courseTitle = jsonPath.getString("courses[" + i + "].get(\"title\")");
            int coursePrice = jsonPath.getInt("courses[" + i + "].get(\"price\")");
            System.out.println("----------------------------------------");
            System.out.println("courseTitle: " + courseTitle);
            System.out.println("coursePrice: " + coursePrice);
        }

        //print number of copies sold by RPA course
        System.out.println("-------------------- print number of copies sold by RPA course --------------------");
        for (int i = 0; i < coursesCount; i++) {
            String courseTitle = jsonPath.getString("courses[" + i + "].get(\"title\")");
            int copies = 0;
            if (courseTitle.equalsIgnoreCase("RPA")) {
                copies = jsonPath.getInt("courses[" + i + "].get(\"copies\")");
                System.out.println("copies by " + courseTitle + ": " + copies);
                break;
            }
        }

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
        System.out.println("calculatedAmount: " + calculatedAmount);
        System.out.println("purchaseAmount: " + purchaseAmount);
        Assert.assertEquals(calculatedAmount,purchaseAmount);
    }
}

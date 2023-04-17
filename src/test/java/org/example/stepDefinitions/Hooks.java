package org.example.stepDefinitions;

import io.cucumber.java.Before;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Hooks {
    private String accessToken;

    @Before
    public void setUp() {
//        // Set base URI and port number for API requests
//        RestAssured.baseURI = "https://pay2.foodics.dev";
//        RestAssured.port = 443;
//
//        // Set up request headers
//        RequestSpecification request = RestAssured.given()
//                .header("Content-Type", "application/json")
//                .header("Accept", "application/json");
//
//        // Set up login credentials
//        String username = "merchant@foodics.com";
//        String password = "123456";
//
//        // Make a POST request to the login endpoint to get an access token
//        Response response = request.body("{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}")
//                .post("/cp_internal/login");
//
//        // Retrieve the access token from the response body
//        accessToken = response.getBody().jsonPath().getString("access_token");
    }
}

package org.example.stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

public class D03_UnAuthorizedAccess {
    JSONObject requestBody=new JSONObject();

    String accessToken ;


    @Given("I have entered my  username and password")
    public void usernameAndPw(){
        // Set base URI and path for the login endpoint
        RestAssured.baseURI = "https://pay2.foodics.dev";
        RestAssured.basePath = "/cp_internal/login";

        // Set request body with username and password
        //JSONPObject requestBody = new JSONPObject();
        requestBody.put("username", "merchant@foodics.com");
        requestBody.put("password", "123456");


    }

    @When("I make a POST request")
    public void iMakeAPOSTRequest() {
        // Send POST request and retrieve access token
       Response response = given().contentType(ContentType.JSON)
                .body(requestBody.toString())
                .post();

         accessToken= given().auth()
                .basic("merchant@foodics.com", "123456")
                .when()
                .get("https://pay2.foodics.dev/cp_internal/login")
                .then()
                .extract()
                .cookie("XSRF-TOKEN").toString();
        // Verify that the status code is 200 and access token is not empty
//        assertEquals(200, response.getStatusCode());
//        assertNotNull(accessToken);
        System.out.println(accessToken);
    }
    @And("Verify user details are retrieved successfully using access token")
    public void verifyUserDetailsAreRetrievedSuccessfullyUsingAccessToken() {
        //Test Case 2: Verify user details are retrieved successfully using access token




        // Set base URI and path for the whoami endpoint
        RestAssured.baseURI = "https://pay2.foodics.dev";
        RestAssured.basePath = "/cp_internal/whoami";

        // Retrieve access token
        Response loginResponse = given().contentType(ContentType.JSON)
                .body("{\"username\": \"merchant@foodics.com\", \"password\": \"123456\"}")
                .post("/login");


        // Set authorization header with access token and send GET request
        Response response = given().header("Authorization", "Bearer " + accessToken)
                .get();

        // Verify that the status code is 200 and user details are retrieved successfully
        assertEquals(200, response.getStatusCode());
        assertEquals("merchant@foodics.com", response.jsonPath().get("data.email"));
        assertEquals("Merchant", response.jsonPath().get("data.firstName"));
        assertEquals("Foodics", response.jsonPath().get("data.lastName"));
    }

    @Then("Verify unauthorized access without access token")
    public void verifyUnauthorizedAccessWithoutAccessToken() {

    //Test Case 3: Verify unauthorized access without access token



    // Set base URI and path for the whoami endpoint
    RestAssured.baseURI = "https://pay2.foodics.dev";
    RestAssured.basePath = "/cp_internal/whoami";

    // Send GET request without authorization header
   Response response = given().get();

    // Verify that the status code is 401 and error message is "Unauthorized"
    assertEquals(401, response.getStatusCode());
    assertEquals("Unauthorized", response.htmlPath().get("error.message"));
}

}

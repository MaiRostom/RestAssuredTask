package org.example.stepDefinitions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import okhttp3.*;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.http.HttpStatus;
import org.json.JSONObject;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

public class D01_Login {
    JSONObject requestBody=new JSONObject();

    @Given("I have entered my username and password")
    public void firstGiven(){

       Response link= given().auth()
                .basic("merchant@foodics.com", "123456")
                .when()
                .post("https://pay2.foodics.dev/cp_internal/login");
       link
                .then()
                .log().status()
                //.assertThat()
                .statusCode(HttpStatus.SC_OK);
       link.body().prettyPrint();
    }
    @Then("I make a POST request to the login endpoint")
    public void theThen() throws IOException {

        String Token = given().auth()
                .basic("merchant@foodics.com", "123456")
                .when()
                .get("https://pay2.foodics.dev/cp_internal/login")
                .then()
                .extract()
                .cookie("XSRF-TOKEN").toString();
        System.out.println("GET TOKEN \n" + Token);

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://pay2.foodics.dev/cp_internal/login")
                .method("POST", body)
                .addHeader("X-XSRF-TOKEN", Token).build();
        okhttp3.Response response = client.newCall(request).execute();
        System.out.println(response.code());

    }
    @Given("I have entered my wrong username and password")
    public void iHaveEnteredMyWrongUsernameAndPassword() {
        given().auth()
                .basic("12345", "666666")
                .when()
                .post("https://pay2.foodics.dev/cp_internal/login")
                .then()
                .log().status()
                .assertThat()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Then("I make a POST request to the login endpoint to check")
    public void iMakeAPOSTRequestToTheLoginEndpointToCheck() {
       // Test Case 1: Verify successful login and retrieve the access token



            // Set base URI and path for the login endpoint
            RestAssured.baseURI = "https://pay2.foodics.dev";
            RestAssured.basePath = "/cp_internal/login";

            // Set request body with username and password
            //JSONPObject requestBody = new JSONPObject();
    requestBody.put("username", "merchant@foodics.com");
            requestBody.put("password", "123456");

            // Send POST request and retrieve access token
            Response response = given().contentType(ContentType.JSON)
                    .body(requestBody.toString())
                    .post();
            String accessToken = response.jsonPath().get("data.accessToken");

            // Verify that the status code is 200 and access token is not empty
            assertEquals(200, response.getStatusCode());
            assertNotNull(accessToken);



        //Test Case 2: Verify user details are retrieved successfully using access token




            // Set base URI and path for the whoami endpoint
            RestAssured.baseURI = "https://pay2.foodics.dev";
            RestAssured.basePath = "/cp_internal/whoami";

            // Retrieve access token
            Response loginResponse = given().contentType(ContentType.JSON)
                    .body("{\"username\": \"merchant@foodics.com\", \"password\": \"123456\"}")
                    .post("/login");
             accessToken = loginResponse.jsonPath().get("data.accessToken");

            // Set authorization header with access token and send GET request
            response = given().header("Authorization", "Bearer " + accessToken)
                    .get();

            // Verify that the status code is 200 and user details are retrieved successfully
            assertEquals(200, response.getStatusCode());
            assertEquals("merchant@foodics.com", response.jsonPath().get("data.email"));
            assertEquals("Merchant", response.jsonPath().get("data.firstName"));
            assertEquals("Foodics", response.jsonPath().get("data.lastName"));



        //Test Case 3: Verify unauthorized access without access token



            // Set base URI and path for the whoami endpoint
            RestAssured.baseURI = "https://pay2.foodics.dev";
            RestAssured.basePath = "/cp_internal/whoami";

            // Send GET request without authorization header
         response = given().get();

            // Verify that the status code is 401 and error message is "Unauthorized"
            assertEquals(401, response.getStatusCode());
            assertEquals("Unauthorized", response.jsonPath().get("error.message"));
        }
    }



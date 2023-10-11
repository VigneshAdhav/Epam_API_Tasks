package test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;

public class PetstoreAPITest {

    // Base URL for the Petstore API
    private static final String BASE_URL = "http://petstore.swagger.io/#/";

    @Test(priority = 1)
    public void createPet() {
        // Request body for creating the PET
        String requestBody = "{\n" +
                "  \"id\": 12345,\n" +
                "  \"category\": {\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"dog\"\n" +
                "  },\n" +
                "  \"name\": \"snoopie\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"pending\"\n" +
                "}";

        // Send POST request to create the PET
        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post(BASE_URL);

        // Validate the response
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 2)
    public void getPetAndValidateInfo() {
        // Send GET request to retrieve the PET information
        Response response = given()
                .get(BASE_URL + "/12345");

        // Validate the response
        assertEquals(response.getStatusCode(), 200);
        assertEquals(response.getContentType(), ContentType.JSON.toString());

        // Validate specific attributes of the PET
        String petName = response.jsonPath().getString("name");
        String petCategory = response.jsonPath().getString("category.name");
        String petStatus = response.jsonPath().getString("status");

        assertEquals(petCategory, "dog");
        assertEquals(petName, "snoopie");
        assertEquals(petStatus, "pending");
    }
}

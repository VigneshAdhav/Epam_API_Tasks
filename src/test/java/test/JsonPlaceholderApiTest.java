package test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class JsonPlaceholderApiTest {

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void verifyNumberOfResources() {
        String[] resources = {"/posts", "/comments", "/albums", "/photos", "/todos", "/users"};

        for (String resource : resources) {
            given()
                .when()
                .get(resource)
                .then()
                .statusCode(200)
                .body("size()", equalTo(100));
        }
    }

    @Test
    public void getSpecificResourceData() {
        Response response = given()
            .when()
            .get("/posts/1")
            .then()
            .statusCode(200)
            .extract()
            .response();

        
    }

    @Test
    public void modifySpecificResourceData() {
        String updatedTitle = "Updated Title";

        given()
            .contentType(ContentType.JSON)
            .body("{\"title\": \"" + updatedTitle + "\"}")
            .when()
            .put("/posts/1")
            .then()
            .statusCode(200)
            .body("title", equalTo(updatedTitle));
    }

    @Test
    public void deleteSpecificResource() {
        given()
            .when()
            .delete("/posts/1")
            .then()
            .statusCode(200);
    }

    @Test
    public void createResource() {
        String newResourceData = "{\"title\": \"New Title\", \"body\": \"New Body\", \"userId\": 1}";

        given()
            .contentType(ContentType.JSON)
            .body(newResourceData)
            .when()
            .post("/posts")
            .then()
            .statusCode(201)
            .body("title", equalTo("New Title"));
    }
}

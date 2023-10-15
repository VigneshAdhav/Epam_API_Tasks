package test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CalculatorWebServiceTest {
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://www.dneonline.com/calculator.asmx";
    }

    @Test
    public void testGetAddition() {
        given()
            .param("intA", 2)
            .param("intB", 3)
            .when()
            .get("/Add")
            .then()
            .statusCode(200)
            .body("//*:AddResult.text()", equalTo("5"));
    }

    @Test
    public void testPostDivision() {
        String requestBody = "<Divide xmlns=\"http://tempuri.org/\"><intA>6</intA><intB>2</intB></Divide>";

        given()
            .contentType(ContentType.XML)
            .body(requestBody)
            .when()
            .post("/Divide")
            .then()
            .statusCode(200)
            .body("//*:DivideResult.text()", equalTo("3"));
    }
}

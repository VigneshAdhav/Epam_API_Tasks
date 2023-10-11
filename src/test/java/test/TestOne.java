package test;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.equalTo;
public class TestOne {
	
	@Test
	private void test_1() {
		
		RestAssured.baseURI="https://jsonplaceholder.typicode.com";

		Response response = RestAssured.get("/posts");
		System.out.println(response.getStatusCode());
		//response.then().statusCode(200);
		//int expectedResourceCount = 10;
		//response.then().body("size()", equalTo(expectedResourceCount));
		String responsebody = response.getBody().asString();
		System.out.println(responsebody);
		Headers headers = response.getHeaders();
		System.out.println(headers);
		}

}

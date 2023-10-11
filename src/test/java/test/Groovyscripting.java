package test;
import java.util.List;

import groovy.json.JsonSlurper;
import groovyjarjarantlr4.v4.codegen.model.decl.Decl;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Groovyscripting {
public static void main(String[] args) {
	
	// Define the endpoint URL
	String endpointUrl = "https://events.epam.com/api/v2/events";

	// Send a GET request and get the response
	Response response = RestAssured.get(endpointUrl);

	// Check if the response status code is 200 (OK)
	if (response.getStatusCode() == 200) {
	    // Parse the JSON response
	    JsonSlurper jsonSlurper = new JsonSlurper();
	 //   Decl jsonResponse = jsonSlurper.parseText(response.asString());

	    // Extract event names in English (En) language using GPath expression
	    List<String> englishEventNames = jsonResponse.findAll { it.language == "En" }.name

	    // Define the expected list of English event names
	    List<String> expectedEventNames = ["EventName1", "EventName2", "EventName3"]; // Replace with your expected names

	    // Verify if the extracted names match the expected names
	    assert englishEventNames == expectedEventNames;
	    System.out.println(("Event names in English language match the expected values."));
	} else {
		 System.out.println(("Failed to retrieve data. Status code: ${response.statusCode}"));
	}

	
}
}

package Activities;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Activity3 {
	RequestSpecification requestspec;
	ResponseSpecification responsespec;
	@BeforeClass
    public void setUp() {
        // Create request specification
        requestspec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("https://petstore.swagger.io/v2/pet")
                .build();
        
        responsespec = new ResponseSpecBuilder()
                // Check status code in response
                .expectStatusCode(200)
                .expectContentType("application/json")
                .expectBody("status", equalTo("alive"))
                .build();
}
	@DataProvider
    public Object[][] petInfoProvider() {
        // Setting parameters to pass to test case
        Object[][] testData = new Object[][] { 
            { 12345, "Rocky", "alive" }, 
            { 56789, "Candy", "alive" }
        };
        return testData;
    }
	@Test(priority=1)
    // Test case using a DataProvider
    public void addPets() {
        String reqBody = "{\"id\": 12345, \"name\": \"Rocky\", \"status\": \"alive\"}";
        Response response = given().spec(requestspec) // Use requestSpec
                .body(reqBody) // Send request body
                .when().post(); // Send POST request
 
        reqBody = "{\"id\": 56789, \"name\": \"Candy\", \"status\": \"alive\"}";
        response = given().spec(requestspec) // Use requestSpec
                .body(reqBody) // Send request body
                .when().post(); // Send POST request
 
        // Assertions
        response.then().spec(responsespec); // Use responseSpec
    }
	// Test case using a DataProvider
    @Test(dataProvider = "petInfoProvider", priority=2)
    public void getPets(int id, String name, String status) {
        Response response = given().spec(requestspec) // Use requestSpec
                .pathParam("petId", id) // Add path parameter
                .when().get("/{petId}"); // Send GET request
 
        // Print response
        System.out.println(response.asPrettyString());
        // Assertions
        response.then()
        .spec(responsespec) // Use responseSpec
        .body("name", equalTo(name)); // Additional Assertion
    }
 // Test case using a DataProvider
    @Test(dataProvider = "petInfoProvider", priority=3)
    public void deletePets(int id, String name, String status) {
        Response response = given().spec(requestspec) // Use requestSpec
                .pathParam("petId", id) // Add path parameter
                .when().delete("/{petId}"); // Send GET request
 
        // Assertions
        response.then().body("code", equalTo(200));
    }
}

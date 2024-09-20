package Activities;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Activity2 {

	final static String Root_URI = "https://petstore.swagger.io/v2/user";
	@Test(priority = 1)
	public void addUserFromFile() throws IOException {
		//Import JSON file
		FileInputStream inputJSON = new FileInputStream("src/test/java/activities/user.json");
		// Read JSON file as String
        String reqBody = new String(inputJSON.readAllBytes());
        Response response = 
                given().contentType(ContentType.JSON) 
                    .body(reqBody) 
                .when().post(Root_URI); 
            inputJSON.close();
          //assertion
            response.then().body("code", equalTo(200));
            response.then().body("message", equalTo("12345"));
            System.out.println(response.getBody().asPrettyString());
            
	}
	@Test(priority = 2)
	public void getUserInfo() throws IOException {
		FileInputStream outputJSON = new FileInputStream("src/test/java/activities/user.json");
		Response response = 
                given().contentType(ContentType.JSON) 
                    .pathParam("username", "Mahesh") 
                .when().get(Root_URI + "/{username}");
		outputJSON.close();
		//Assertion
        response.then().body("id", equalTo(12345));
        response.then().body("username", equalTo("Mahesh"));
        response.then().body("firstName", equalTo("Mahesh"));
        response.then().body("lastName", equalTo("Babu"));
        response.then().body("email", equalTo("mahesh@mail.com"));
        response.then().body("password", equalTo("mahesh123"));
        response.then().body("phone", equalTo("9812763450"));
		//For Display puporse
		 System.out.println("Getting pet");
		 System.out.println(response.getBody().asPrettyString());	
	}
	@Test(priority = 3)
	public void delUser() {
		Response response = 
                given().contentType(ContentType.JSON) 
                    .pathParam("username", "Mahesh") 
                .when().delete(Root_URI + "/{username}");
		//Assertion
		response.then().body("code", equalTo(200));
		response.then().body("message",equalTo("Mahesh"));
		System.out.println(response.getBody().asPrettyString());
		
		
	}
}

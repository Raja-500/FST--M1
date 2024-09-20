package Activities;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Activity1 {
	
	final static String Root_URI = "https://petstore.swagger.io/v2/pet";
	@Test(priority = 1)
	public void addNewPet() {
		String body ="{\r\n"
				+ "  \"id\": 77232,\r\n"
				+ "  \"name\": \"Riley\",\r\n"
				+ "  \"status\": \"alive\"\r\n"
				+ "}";
		Response response =
				given().contentType(ContentType.JSON)
				.body(body)
				.when().post(Root_URI);
		
		//assertion
		System.out.println("Adding pet");
		System.out.println(response.getBody().asPrettyString());
	}
	
	@Test(priority = 2)
	public void getPetInfo() {
		Response response =
				given().contentType(ContentType.JSON)
				.when().pathParam("petId", 77232)
				.get(Root_URI+"/{petId}");
		//Assertion 	
		System.out.println("Getting pet");
		System.out.println(response.getBody().asPrettyString());
	}
	@Test(priority = 3)
	public void delPet() {
		Response response =
				given().contentType(ContentType.JSON)
				.when().pathParam("petId", 77232)
				.delete(Root_URI+"/{petId}");
		
		//Assertion
		response.then().body("code", equalTo(200));
	//	response.then().body("message", equalTo("77232"));
		System.out.println("Deleting pet");
		System.out.println(response.getBody().asPrettyString());
	}

}

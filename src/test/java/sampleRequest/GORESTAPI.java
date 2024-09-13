package sampleRequest;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GORESTAPI {

	String token = "4b60c8f3e1067be28f4828a4d9810f5fa19ca8fdef3e8e0aceed6d8f8af35c99";
	int id=0;

	@BeforeTest
	public void setup() {
		RestAssured.baseURI = "https://gorest.co.in/public/v2";

	}

	@Test(priority = 1)
	public void getAllUsers() {
		String path = "/users";
		RequestSpecification request = RestAssured.given().header("Authorization", "Bearer " + token)
				.header("Content-Type", "application/json").header("Accept", "application/json");//.log().all();

		Response response = request.get(path);
		JsonPath jsonEvaluator = response.jsonPath();

		AssertJUnit.assertEquals(response.getStatusCode(), 200);

		response.getBody().prettyPrint();
		//assertTrue(jsonEvaluator.getString("name").contains("Anal Pillai"));
	}

	@Test(priority=2)
	public void createUser() {
		String path = "/users";
		String body = """
					{
					    "name": "sia",
					    "gender": "female",
					    "email": "sia.cartoon1@15ce.com",
					    "status": "active"
					}
					""";
		
		RequestSpecification request = RestAssured.given().header("Authorization", "Bearer " + token)
				.header("Content-Type", "application/json").header("Accept", "application/json");//.log().all();
		
		Response response = request.body(body).post(path);
		JsonPath jsonEvaluator = response.jsonPath();
		AssertJUnit.assertTrue(jsonEvaluator.getString("name").contentEquals("sia"));
		AssertJUnit.assertTrue(jsonEvaluator.getString("gender").contentEquals("female"));
		AssertJUnit.assertTrue(jsonEvaluator.getString("email").contentEquals("sia.cartoon1@15ce.com"));
		AssertJUnit.assertTrue(jsonEvaluator.getString("status").contentEquals("active"));
		
		AssertJUnit.assertEquals(response.getStatusCode(), 201);
		id = jsonEvaluator.getInt("id");
		System.out.println("ID is : "+id);
		response.getBody().prettyPrint();
	}
	
	
	@Test(priority=3)
	public void getUser() {
		String path = "/users/"+id;
		RequestSpecification request = RestAssured.given().header("Authorization", "Bearer " + token)
				.header("Content-Type", "application/json").header("Accept", "application/json");//.log().all();
		
		Response response = request.get(path);
		JsonPath jsonEvaluator = response.jsonPath();
		AssertJUnit.assertTrue(jsonEvaluator.getString("name").contentEquals("sia"));
		AssertJUnit.assertTrue(jsonEvaluator.getString("gender").contentEquals("female"));
		AssertJUnit.assertTrue(jsonEvaluator.getString("email").contentEquals("sia.cartoon1@15ce.com"));
		AssertJUnit.assertTrue(jsonEvaluator.getString("status").contentEquals("active"));
		
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
		
		response.getBody().prettyPrint();
	}
	
	
	@Test(priority=4)
	public void updateUser() {
		String path = "/users/"+id;
		String body = """
					{
					    "name": "john",
					    "gender": "male",
					    "email": "john.cartoon1@15ce.com",
					    "status": "active"
					}
					""";
		
		RequestSpecification request = RestAssured.given().header("Authorization", "Bearer " + token)
				.header("Content-Type", "application/json").header("Accept", "application/json");
		
		Response response = request.body(body).patch(path);
		JsonPath jsonEvaluator = response.jsonPath();
		AssertJUnit.assertTrue(jsonEvaluator.getString("name").contentEquals("john"));
		AssertJUnit.assertTrue(jsonEvaluator.getString("gender").contentEquals("male"));
		AssertJUnit.assertTrue(jsonEvaluator.getString("email").contentEquals("john.cartoon1@15ce.com"));
		AssertJUnit.assertTrue(jsonEvaluator.getString("status").contentEquals("active"));
		
		
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
		
		response.getBody().prettyPrint();
	}
	
	
	@Test(priority=5)
	public void deleteUser() {
		String path = "/users/"+id;
		RequestSpecification request = RestAssured.given().header("Authorization", "Bearer " + token)
				.header("Content-Type", "application/json").header("Accept", "application/json");//.log().all();
		
		Response response = request.delete(path);
		
		AssertJUnit.assertEquals(response.getStatusCode(), 204);
		
		response.getBody().prettyPrint();
	}
	

}

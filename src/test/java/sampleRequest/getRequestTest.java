package sampleRequest;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class getRequestTest {
	public String token="";
	
	@Test(priority=1)
	public void getToken(){
		String restAPIUrl = "/ECM/api/login";
		String APIBody = "{\"username\":\"admin\",\"password\":\"password\"}";
		
		RestAssured.baseURI="http://localhost:8085";
		Response res = given()
				.body(APIBody)
	            .when()
	            .post(restAPIUrl)
	            .then()
	            .log()
	            .all()
	            .assertThat().statusCode(200).contentType("application/json;charset=UTF-8")
	            .extract()
	            .response();
		
		String response = res.asString();
	    JsonPath json = new JsonPath(response);
	    token = json.get("access_token");
	    System.out.println("Access Token is :- "+token);
	}
	
	@Test(priority=2)
	public void createUser(){
		String restAPIUrl = "/ECM/api/v5/createUser";
		RestAssured.baseURI="http://localhost:8085";
		System.out.println("token in 2nd request = "+token);
		Response res = given()
				.header("Authorization","Bearer "+token)
				.body("{\r\n\"username\":\"Pawan\",\r\n\"firstname\":\"Pawan\",\r\n\"startdate\":\"10-01-2021\",\r\n\"enddate\":\"12-01-2021\",\r\n\"statuskey\":\"1\",\r\n\"passwordExpired\":\"true\",\r\n\"enabled\": \"true\",\r\n\"accountExpired\":\"true\",\r\n\"accountLocked\":\"false\",\r\n\"allowpastdate\":\"true\",\r\n\"city\":\"Ranchi\"\r\n}")
	            .when()
	            .post(restAPIUrl)
	            .then()
	            .log()
	            .all()
	            .extract()
	            .response();
		
		String response = res.asString();
	    JsonPath json = new JsonPath(response);
	    assertEquals(Integer.parseInt(json.get("errorCode")), 0);
	    System.out.println(json.get("message").toString());
	}
	
	@Test(priority=3)
	public void getUser(){
		String restAPIUrl = "/ECM/api/v5/getUser";
		RestAssured.baseURI="http://localhost:8085";
		System.out.println("token in 2nd request = "+token);
		Response res = given()
				.header("Authorization","Bearer "+token)
				.body("{\r\n\"filtercriteria\":{\"username\":\"Pawan\"},\r\n\"responsefields\":[\"username\",\"statuskey\",\"firstname\"]\r\n}")
				.when()
				.post(restAPIUrl)
				.then()
				.log()
				.all()
				.extract()
				.response();
		
		String response = res.asString();
		JsonPath json = new JsonPath(response);
		assertEquals(Integer.parseInt(json.get("errorCode")), 0);
		System.out.println(json.get("msg").toString());
	}
	
	@Test(priority=4)
	public void updatUser(){
		String restAPIUrl = "/ECM/api/v5/updateUser";
		RestAssured.baseURI="http://localhost:8085";
		System.out.println("token in 2nd request = "+token);
		Response res = RestAssured.given()
				.header("Authorization","Bearer "+token)
				.body("{\"username\":\"Pawan\",\"lastname\":\"Pratap\"}")
				.when()
				.post(restAPIUrl)
				.then()
				.log()
				.all()
				.extract()
				.response();
		
		String response = res.asString();
		JsonPath json = new JsonPath(response);
		assertEquals(Integer.parseInt(json.get("errorCode")), 0);
		System.out.println(json.get("message").toString());
	}
	
	@Test(priority=5)
	public void getAllUser(){
		String restAPIUrl = "/ECM/api/v5/getUser";
		RestAssured.baseURI="http://localhost:8085";
		System.out.println("token in 2nd request = "+token);
		Response res = given()
				.header("Authorization","Bearer "+token)
				.body("{\r\n\"filtercriteria\":{\"username\":\"Pawan\"},\r\n\"responsefields\":[\"username\",\"statuskey\",\"firstname\"]\r\n}")
				.when()
				.post(restAPIUrl)
				.then()
				.log()
				.all()
				.extract()
				.response();
		
		String response = res.asString();
		JsonPath json = new JsonPath(response);
		assertEquals(Integer.parseInt(json.get("errorCode")), 0);
		System.out.println(json.get("msg").toString());
	}
}

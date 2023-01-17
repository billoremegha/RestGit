package restAssuredPractice;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.ReUsableMethods;
import files.payload;
public class demo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//VAlidate if add API is working expected
		//given-- all input details(post)
		//when--submit the API--resource , http methods.
		//then--validate the response
//Add place-- Update place with new address--Get place to validate if new address is present in response.
		RestAssured.baseURI="https://rahulshettyacademy.com";	
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(payload.AddPlace()).when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope",equalTo("APP"))    //import hemcrest.matcher pac.for equalTo
		.header("Server", "Apache/2.4.41 (Ubuntu)").extract().response().asString();
		System.out.println(response);
		
		JsonPath js = new JsonPath(response);
		 String placeId= js.getString("place_id");
		 System.out.println(placeId);
		 System.out.println(response);
		 //Update Place
		String newaddress = "Summer walk , Africa";
		 
		 given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
		 .body("{\r\n"
		 		+ "\"place_id\":\""+placeId+"\",\r\n"
		 		+ "\"address\":\""+newaddress+"\",\r\n"
		 		+ "\"key\":\"qaclick123\"\r\n"
		 		+ "}\r\n"
		 		+ "").
		 when().put("maps/api/place/update/json")
		 .then().assertThat().log().all().statusCode(200).body("msg",equalTo("Address successfully updated"));
		 
		 //Get Place
		 
		 String getPlaceResponse = given().log().all().queryParam("key","qaclick123").queryParam("place_id", placeId)
		 .when().get("maps/api/place/get/json")
	 .then().assertThat().log().all().statusCode(200).extract().response().asString();
		 
		 System.out.println(getPlaceResponse);
		 JsonPath js1 = ReUsableMethods.rawToJason(getPlaceResponse);
			 String actualaddress = js1.getString("address");
			System.out.println(actualaddress);
			Assert.assertEquals(actualaddress, newaddress);
		
		  
		 
		 
		 
	}	
}
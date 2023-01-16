package files;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson {
	@Test(dataProvider="BooksData")
	public void addbook(String isbn,String aisle) 
	{
		RestAssured.baseURI = "http://216.10.245.166";
	String response =	given().log().all().header("Content-Type","application/json")
		.body(payload.Addbook("isbn","aisle"))
		.when()
		.post("/Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).extract().asString();
		  JsonPath js = ReUsableMethods.rawToJason(response);
		  String id = js.get("ID");
		System.out.println(id);
	}
	@DataProvider(name="BooksData")
	public Object[][] getData()
	{
		// array--- colection of elements
		//multidimensional array--- collection of arrays
		return new Object [][] {{"sweet","1234"},{"yummy","567"},{"tangy","8910"}};
	}
}

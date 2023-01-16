package files;
import static io.restassured.RestAssured.given;

import java.util.List;
import java.util.ArrayList;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.AddPlace;
import pojo.Location;
public class SerealizeTest {
	public static void main(String[] args) {
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		AddPlace p = new AddPlace();
		p.setAccuracy(50);
		p.setName("Frontline house");
		p.setPhone_number("+91) 983 893 3937");
		p.setAddress("29, side layout, cohen 09");
		p.setWebsite("http://google.com");
		p.setLanguage("French-IN");
		
		List<String> myList=new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");
		p.setTypes(myList); // Type is a array list it want List<String> argument will create object and add values one by one,see above code.
		
		//p.setLocation(null);// here setLocation wants pojo Location class object
		Location l = new Location();//object is dummy/empty we use setLat() and setLng methods.
		l.setLat(-38.383494);
		l.setLng(33.427362);
		p.setLocation(l);// here setLocation wants pojo Location class object see above code.
		
		Response res=given().log().all().queryParam("key", "qaclick123")
		.body(p)
		.when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).extract().response();
		String response =res.asString();
		System.out.println(response);
		
	}
	

}

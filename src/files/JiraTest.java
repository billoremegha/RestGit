package files;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;
public class JiraTest {

	public static void main(String[] args) {
		RestAssured.baseURI = "http://localhost:8080";
		//login scenario--
		SessionFilter session = new SessionFilter();
		String response = given().header("Content-Type","application/json").body("{ \"username\": \"Megha\", \"password\": \"Megha@123$\" }")
		.log().all().filter(session).when().post("/rest/auth/1/session").then().log().all().extract().response().asString();
		
		//Add comment--
		String expectedmessage= "Hi how are you ?";
		String addCommentResponse= given().pathParam("key", "10011").header("Content-Type","application/json").body("{\r\n"
				+ "    \"body\": \""+expectedmessage+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}").filter(session).when().post("/rest/api/2/issue/{key}/comment").then().log().all().assertThat().statusCode(201)
		.extract().response().asString();
		JsonPath js = new JsonPath(addCommentResponse);
		 String commentId = js.get("id");
		//Add Attachment--
		  given().header("X-Atlassian-Token","no-check").filter(session).pathParam("key", "10011").header("Content-Type","multipart/form-data")
		.multiPart("file",new File("Jira.Text")).when()   // its file class object for java knowledge that its a file
		.post("/rest/api/2/issue/{key}/attachments").then().log().all().assertThat().statusCode(200);
		 
		//Get Issue--
		String issueDetails = given().filter(session).pathParam("key", "10011").queryParam("fields","comment")
		.log().all()
		.when().get(" /rest/api/2/issue/{key}").then().log().all().extract().response().asString();
		System.out.println(issueDetails);
		
		
		JsonPath js1  = new JsonPath(issueDetails);
		 int commentCount = js1.getInt("fields.comment.comments.size()");
		 for(int i=0;i<commentCount;i++)
		 {
			String commentIdIsuue = js1.get("fields.comment.comments["+i+"].id").toString(); 
			if(commentIdIsuue.equalsIgnoreCase(commentId))
			{
				 String message = js1.get("fields.comment.comments["+i+"].body").toString();
				 System.out.println(message);
				 Assert.assertEquals(message, expectedmessage);
			}
		 }
		
		
		
		
		
		
}
}
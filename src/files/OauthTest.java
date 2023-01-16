package files;
import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;
public class OauthTest {

	public static void main(String[] args) throws InterruptedException {
		String [] courseTitle= {"selenium Webdriver", "Java","cypress","Protractor"};

		//		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Avinash\\Downloads\\chromedriver_win32 (5)\\chromedriver.exe");
		//		WebDriver driver = new ChromeDriver();
		//		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
		//		driver.findElement(By.cssSelector("input[type='email']")).sendKeys("megha.billore20@gmail.com");
		//		driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
		//		Thread.sleep(3000);
		//		driver.findElement(By.cssSelector("input[type='password']")).sendKeys("Megha@123$");
		//		driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
		//		Thread.sleep(4000);
		//		// String url=driver.getCurrentUrl();
		String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AfgeXvvggpxVfSJQamGwj3rGDwG14494f_2YvA1PYvX6wVbOFTP4_73hdma9YZ26tY_2JQ&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=consent";
		String partialCode = url.split("code=")[1];
		String code = partialCode.split("&scope")[0];
		System.out.println(code);

		String accessaTokenaResponse = given().urlEncodingEnabled(false)
				.queryParam("code",code)
				.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.queryParam("grant_type", "authorization_code") 
				.post("https://www.googleapis.com/oauth2/v4/token").asString();
		JsonPath js = new JsonPath(accessaTokenaResponse);
		js.getString(accessaTokenaResponse);

		GetCourse gc = given().queryParam("access_token", "access_token").expect().defaultParser(Parser.JSON)
				.when()
				.get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
		System.out.println(gc.getLinkedin());
		System.out.println(gc.getInstructor());
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
		
		//For Dynamic value of index get(1)--
		 List<Api> apiCourses = gc.getCourses().getApi();
		 for(int i=0;i<apiCourses.size();i++)
		 {
			 if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"));
			 {
				 System.out.println(apiCourses.get(i).getPrice());
			 }
		 }
		 
		 //Get the Course of WebAutomation--
		 ArrayList<String> a = new  ArrayList<String>();  // creating ArrayList object 
		 
	List<pojo.WebAutomation> webAutomationCourses = gc.getCourses().getWebAutomation();
		 for(int j=0;j<webAutomationCourses.size();j++)
		 {
			a.add(webAutomationCourses.get(j).getCourseTitle());
		 }
		List<String> expectedList = Arrays.asList(courseTitle);   //changing array into arrayList 
		Assert.assertTrue(a.equals(expectedList));
		 
		 
		
		//System.out.println(response);

	}

}

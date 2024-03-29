package restAssuredPractice;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {
	//verify if sum of all course prices matches with purchase amount
	
	@Test
	public void sumOfCourses() 
	{	int sum= 0;
		int sum1=1;
		int sum2=2;
		int sum3= 3;
		int sum4= 4;
		JsonPath js = new JsonPath(payload.CoursePrice());
		
		int count = js.getInt("courses.size()");
		for(int i=0;i<count;i++)
		{
			int price =js.getInt("courses["+i+"].price");
			 int copies=js.getInt("courses["+i+"].copies");
			 int amount= price*copies;
			 System.out.println(amount);
			 sum=sum+amount;
			 int price1 =js.getInt("courses["+i+"].price");
			 int copies1=js.getInt("courses["+i+"].copies");
			 int amount1= price*copies;
			 System.out.println(amount);
			 sum=sum+amount;
			 int price2 =js.getInt("courses["+i+"].price");
			 int copies2=js.getInt("courses["+i+"].copies");
			 int amount2= price*copies;
			 System.out.println(amount);
			 sum=sum+amount;
		}
		System.out.println(sum);
		System.out.println(sum1);
		System.out.println(sum2);
		
		System.out.println(sum);
		System.out.println(sum1);
		System.out.println(sum2);
		System.out.println(sum3);
		System.out.println(sum4);
	int purchaseAmount	= js.get("dashboard.purchaseAmount");
	System.out.println("purchaseAmount");
	Assert.assertEquals(sum, purchaseAmount);
	int purchaseAmount1	= js.get("dashboard.purchaseAmount");
	System.out.println("purchaseAmount");
	Assert.assertEquals(sum, purchaseAmount);
	} 
		
	}



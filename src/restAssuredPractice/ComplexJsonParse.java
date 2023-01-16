package restAssuredPractice;

import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {

		JsonPath js = new JsonPath(payload.CoursePrice());

		//print no.of courses returned by API
		int count = js.getInt("courses.size()");
		System.out.println(count);

		// Print Purchase amount
		int totalamount = js.getInt("dashboard.purchaseAmount");
		System.out.println(totalamount);

		//Print title of the 1st course
		String titlefirstcourse = js.get("courses[0].title");
		System.out.println(titlefirstcourse);
		String titlesecondcoure = js.get("courses[1].title");
		System.out.println(titlesecondcoure);

		//Print all course titles and their respective prices 
		for(int i=0;i<count;i++) 
		{
			String courseTitle = js.get("courses["+i+"].title");// 
			System.out.println(courseTitle);
			System.out.println(js.get("courses["+i+"].price").toString());  //2nd way to wrap in sysout because it accept only string
		}
		
		//Print no.of copies sold by RPA Course
		System.out.println("Print no.of copies sold by RPA Course");
		for(int i=0;i<count;i++) 
			 {
				String courseTitle = js.get("courses["+i+"].title"); 
				if(courseTitle.equalsIgnoreCase("RPA"))
				{
					 int copies=js.get("courses["+i+"].copies");
					 System.out.println(copies);
					 break;
				}
			 }	
		//verify if sum of all course prices matches with purchase amount
		      //its in SumVAlidation class
		
			
		

	}

}

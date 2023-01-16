package files;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import pojo.LoginRequest;
import pojo.LoginResponse;
import pojo.OrderDetails;
import pojo.Orders;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
public class ECommerceAPITest {

	public static void main(String[] args) {

		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();

		LoginRequest loginrequest = new LoginRequest();
		loginrequest.setUserEmail("Asmita@gmail.com");
		loginrequest.setUserPassword("Asmita@123$");

		RequestSpecification reqLogin = given().log().all().spec(req).body(loginrequest);
		LoginResponse loginresponse = reqLogin.when().post("/api/ecom/auth/login").then().log().all().extract()
				.response().as(LoginResponse.class);
		String token = loginresponse.getToken();
		System.out.println(loginresponse.getToken());
		System.out.println(loginresponse.getUserId());
		String userId = loginresponse.getUserId();

		//Add Product--
		RequestSpecification addProductBaseRequest = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).build();

		RequestSpecification reqAddProduct = given().log().all().spec(addProductBaseRequest)
				.param("productName", "Picture")
				.param("productAddedBy", userId)
				.param("productCategory", "decor")
				.param("productSubCategory", "home")
				.param("productPrice", "11500")
				.param("productDescription", "Addias Originals")
				.param("productFor", "Home")
				.multiPart("productImage",new File("C:\\Users\\Avinash\\Downloads\\SamplePNGImage_100kbmb.png"));

		String addProductResponse = reqAddProduct.when().post("/api/ecom/product/add-product")
				.then().log().all().extract().response().asString();
		JsonPath js = new JsonPath(addProductResponse);
		String productId = js.get("productId");
		System.out.println(productId);
		
		//Create Order--
		RequestSpecification createOrderBaseRequest =	new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token)
				.setContentType(ContentType.JSON).build();
		OrderDetails orderDetail = new OrderDetails();
		orderDetail.setCountry("India");
		orderDetail.setProductOrderedId(productId);//639ce03e03841e9c9a57664e
		List<OrderDetails> orderDetailList = new ArrayList<OrderDetails>();
		orderDetailList.add(orderDetail);
		
		Orders orders = new Orders();
		orders.setOrders(orderDetailList);
		
		RequestSpecification  createOrderReqest = given().log().all().spec(createOrderBaseRequest).body(orders);
		String createOrderresponse = createOrderReqest.when().post("/api/ecom/order/create-order")
				.then().log().all().extract().response().asString();
		System.out.println(createOrderresponse);
		
		//Delete product--
		RequestSpecification deleteProdBaseRequest  =	new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token)
				.setContentType(ContentType.JSON).build();
		RequestSpecification deleteProdreq = given().log().all().spec(deleteProdBaseRequest).pathParam("productId", productId);
		String  deleteProductResponse = deleteProdreq.when().delete("/api/ecom/product/delete-product/{productId}").then().log().all()
		.extract().response().asString();
		JsonPath js1 = new JsonPath(deleteProductResponse);
		Assert.assertEquals("Product Deleted Successfully",js1.get("message"));

	}

}

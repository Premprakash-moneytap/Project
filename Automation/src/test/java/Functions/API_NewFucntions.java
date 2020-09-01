package Functions;

import static io.restassured.RestAssured.given;
import java.io.IOException;
import org.json.simple.parser.ParseException;
import io.restassured.path.json.JsonPath;

public class API_NewFucntions 
{
	public static String Auth_HDBFS(String Username,String Pass) throws IOException, ParseException
	{
		String access_token;
		String getTokenResponse = given().log().all().baseUri("https://dev.moneytap.com")
            .param("grant_type", "client_credentials").auth().preemptive()
            .basic(Username, Pass).header("Accept", "application/json").when()
            .post("/oauth/token").then().assertThat().statusCode(200).extract().asString();
		System.out.println(getTokenResponse);
		JsonPath js = new JsonPath(getTokenResponse);
		access_token = js.getString("access_token");
		return (access_token);
	}
	
	
	public static String BuildProfile(String Auth,String Payload) throws IOException, ParseException
	{
		String customerId;
        String buildProfileResponse = given().log().all().baseUri("https://dev.moneytap.com")
                .header("Accept", "application/json").header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + Auth + "")
                .body(Payload)
                .when().post("/v3/partner/buildprofile").then().assertThat().statusCode(200).extract().asString();
        System.out.println(buildProfileResponse);
        JsonPath js = new JsonPath(buildProfileResponse);
        customerId = js.getString("customerId");
        return (customerId);
	}
}

package settings;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;


public class Preparation {

    private final String httpbinURL = "https://httpbin.org";

    public JsonPath getResultWileySearch(){

        String wileyURL = "https://www.wiley.com";
        String searchWileyPath = "/en-us/search/autocomplete/comp_00001H9J";
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(wileyURL)
                .setBasePath(searchWileyPath)
                .setContentType(ContentType.JSON)
                .addParam("term" , "Java")
                .build();
        JsonPath responseJson = given()
                .when().get()
                .then().statusCode(200).extract().jsonPath();

        return responseJson;
    }

    public void prepareImageRequest(){
        String imagePath = "/image/png";
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(httpbinURL)
                .setBasePath(imagePath)
                .setContentType("image/png")
                .build();
    }

    public long sendDelayQuery (int time){
        String timeStr = String.valueOf(time);

        String delayPath = "/delay/";
        long timeRequest = TimeUnit.MILLISECONDS.toSeconds(
                given().baseUri(httpbinURL)
                        .basePath(delayPath)
                        .when()
                        .post(timeStr)
                        .then().statusCode(200)
                        .extract()
                        .time());

        return timeRequest;
    }

    public InputStream getImageInInputStream(){
        InputStream img = given()
                .when().get()
                .then()
                .statusCode(200)
                .extract()
                .asInputStream();
        return img;
    }

    public String getImageContentType(){
        String contentType = given()
                .when().get()
                .then().statusCode(200).extract().contentType();
        return contentType;
    }

}
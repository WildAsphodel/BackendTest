package lesson3;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;


public abstract class Abstract {

    private static String apiKey = "0601c671ec5c4cbf89783e8885e5d973";
    private static String baseUrl = "https://api.spoonacular.com";
    protected static RequestSpecification requestSpecification;
    protected static RequestSpecification requestSpecification2;


    public static String getApiKey() {
        return apiKey;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    @BeforeAll
    static void initTest() {
        requestSpecification = new RequestSpecBuilder()
                .addQueryParam("apiKey", apiKey)
                .build();

        requestSpecification2 = new RequestSpecBuilder()
                .addQueryParam("apiKey", apiKey)
                .setContentType("application/x-www-form-urlencoded")
                .build();
    }
}

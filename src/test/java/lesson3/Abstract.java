package lesson3;

public abstract class Abstract {

    private static String apiKey = "0601c671ec5c4cbf89783e8885e5d973";
    private static String baseUrl = "https://api.spoonacular.com";


    public static String getApiKey() {
        return apiKey;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }
}

package lesson3;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class Tests extends Abstract {

    @Test
    void RequestByCuisine() {
        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("cuisine", "korean")
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch")
                .body()
                .jsonPath();
        assertThat(response.get("totalResults"), equalTo(25));
    }

    @Test
    void RequestByCuisineAndQuery() {
        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("cuisine", "italian")
                .queryParam("query", "pasta")
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch")
                .body()
                .jsonPath();
        assertThat(response.get("totalResults"), equalTo(34));
    }

    @Test
    void ByCuisineUnauthorized() {
        given()
                .queryParam("cuisine", "italian")
                .when()
                .get(getBaseUrl()+"/recipes/complexSearch")
                .then()
                .statusCode(401);
    }

    @Test
    void RequestCuisineTypeTime() {
        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("cuisine", "italian")
                .queryParam("type", "main course")
                .queryParam("maxReadyTime", "20")
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch")
                .body()
                .jsonPath();
        assertThat(response.get("totalResults"), equalTo(3));
    }

    @Test
    void RequestByIngridients() {
        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("includeIngredients", "tomato, bread, milk")
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch")
                .body()
                .jsonPath();
        assertThat(response.get("number"), equalTo(10));
    }

    @Test
    void PostUnauthorized() {
        given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("title", "burger")
                .when()
                .post(getBaseUrl() + "/recipes/cuisine")
                .then()
                .statusCode(401);
    }

    @Test
    void PostByTitle() {
        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title", "burger")
                .when()
                .post("https://api.spoonacular.com/recipes/cuisine")
                .body()
                .jsonPath();
        assertThat(response.get("cuisine"), equalTo("American"));
    }

    @Test
    void PostByTitleTest2() {
        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title", "pasta")
                .when()
                .post("https://api.spoonacular.com/recipes/cuisine")
                .body()
                .jsonPath();
        assertThat(response.get("cuisine"), equalTo("Mediterranean"));
    }

    @Test
    void PostByByIngredientList() {
        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("ingredientList", "rice")
                .when()
                .post("https://api.spoonacular.com/recipes/cuisine")
                .body()
                .jsonPath();
        assertThat(response.get("cuisine"), equalTo("Mediterranean"));
    }

    @Test
    void RequestByIngredientListAndTitle() {
        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title", "Chinese Chicken Salad")
                .formParam("ingredientList", "chicken")
                .formParam("ingredientList", "rice")
                .when()
                .post("https://api.spoonacular.com/recipes/cuisine")
                .body()
                .jsonPath();
        assertThat(response.get("cuisine"), equalTo("Chinese"));
    }
}

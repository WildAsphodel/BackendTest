package lesson5;

import com.github.javafaker.Faker;
import lesson5.api.ProductService;
import lesson5.dto.Product;
import lesson5.utils.RetrofitUtils;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.apache.http.impl.io.ContentLengthInputStream;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;


public class CreateModiFyDeleteProductTest {

    static ProductService productService;
    Product product = null;
    Product modifiedProduct = null;
    Faker faker = new Faker();
    int id;

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit()
                .create(ProductService.class);
    }

    @BeforeEach
    void setUp() {
        product = new Product()
                .withTitle(faker.food().ingredient())
                .withCategoryTitle("Food")
                .withPrice((int) (Math.random() * 10000));
    }
    @BeforeEach
    void modify() {
        modifiedProduct = new Product()
                .withId(1)
                .withTitle(faker.food().ingredient())
                .withCategoryTitle("Food")
                .withPrice((int) (Math.random() * 10000));
    }

    @Test
    void createAndModifyProductTest() throws IOException {
        Response<Product> response = productService.createProduct(product)
                .execute();
        id =  response.body().getId();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));

        Response<Product> modifiedResponse = productService.modifyProduct(modifiedProduct)
                .execute();
        assertThat(modifiedResponse.isSuccessful(), CoreMatchers.is(true));
    }


    @SneakyThrows
    @AfterEach
    @Test
    void tearDown() {
        Response<ResponseBody> response = productService.deleteProduct(id).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }

}

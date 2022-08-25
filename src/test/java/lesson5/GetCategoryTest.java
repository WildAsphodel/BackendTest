package lesson5;

import lesson5.api.CategoryService;
import lesson5.dto.GetCategoryResponse;
import lesson5.utils.RetrofitUtils;
import lesson6.db.dao.CategoriesMapper;
import lesson6.db.model.Categories;
import lesson6.db.model.CategoriesExample;
import lombok.SneakyThrows;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GetCategoryTest {

    static CategoryService categoryService;
    String resource = "mybatis-config.xml";
    String result;
    @BeforeAll
    static void beforeAll() {
        categoryService = RetrofitUtils.getRetrofit().create(CategoryService.class);
    }

    private static CategoriesMapper getCategoriesMapper(String resource) throws IOException {
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new
                SqlSessionFactoryBuilder().build(inputStream);
        sqlSessionFactory.openSession();
        SqlSession session = sqlSessionFactory.openSession();
        return session.getMapper(CategoriesMapper.class);
    }


    @SneakyThrows
    @Test
    void getCategoryWiThBDTest() {
        Response<GetCategoryResponse> response = categoryService.getCategory(1).execute();
        lesson6.db.model.CategoriesExample example = new lesson6.db.model.CategoriesExample();
        example.createCriteria().andTitleLike("Milky");
        List<Categories> list = getCategoriesMapper("mybatis-config.xml").selectByExample(example);
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.body().getId(), equalTo(1));

        assertThat(response.body().getTitle(), equalTo("Food"));
        assertThat(list.size(), equalTo(0));
        response.body().getProducts().forEach(product ->
                assertThat(product.getCategoryTitle(), equalTo("Food")));



    }

}

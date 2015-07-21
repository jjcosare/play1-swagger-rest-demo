package swagger.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import swagger.annotations.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static swagger.util.ReflectUtils.*;

@RunWith(JUnit4.class)
public class ReflectUtilsTest {

  @Api
  private static class TestApi {

    @GET(path = "/some/all")
    public void allItems() {
    }

    @DELETE(path = "/some/foo/{id}")
    public void removeItem(long id) {
    }

    @PUT(path = "/some/{id}")
    public void item(long id) {
    }
  }

  @Test
  public void methodIsAnnotationWithOneOfTest() throws Exception {
    assertThat(methodIsAnnotationWithOneOf(TestApi.class.getMethod("allItems"), GET.class, POST.class, PUT.class), is(true));
    assertThat(methodIsAnnotationWithOneOf(TestApi.class.getMethod("removeItem", long.class), GET.class, POST.class, PUT.class), is(false));
    assertThat(methodIsAnnotationWithOneOf(TestApi.class.getMethod("item", long.class), GET.class, POST.class, PUT.class), is(true));
  }

  @Test
  public void tryGetAnnotationTest() throws Exception {
    assertThat(tryGetAnnotation(TestApi.class.getMethod("allItems"), GET.class).path(), is("/some/all"));
    assertThat(tryGetAnnotation(TestApi.class.getMethod("removeItem", long.class), DELETE.class).path(), is("/some/foo/{id}"));
    assertThat(tryGetAnnotation(TestApi.class.getMethod("item", long.class), PUT.class).path(), is("/some/{id}"));
  }

  @Test
  public void findAnnotationValueTest() throws Exception {
    String path = findAnnotationValue(TestApi.class.getMethod("allItems"), "path", GET.class, POST.class, PUT.class);
    assertThat(path, is("/some/all"));
  }

  @Test(expected = RuntimeException.class)
  public void findAnnotationValueExceptionTest() throws Exception {
    findAnnotationValue(TestApi.class.getMethod("allItems"), "unknownMethod", GET.class, POST.class, PUT.class);
  }

  @Test
  public void test() {
    //List<ApplicationClasses.ApplicationClass> annotatedClasses = Play.classes;
    //System.out.println(Play.classes);
  }
}

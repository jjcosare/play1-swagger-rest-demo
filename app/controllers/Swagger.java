package controllers;

import play.mvc.Controller;
import swagger.SwaggerBootstrap;

public class Swagger extends Controller {

  public static void index() throws Exception {
    render();
  }

  public static void json() throws Exception {
    renderJSON(SwaggerBootstrap.getSwaggerObject());
  }
}

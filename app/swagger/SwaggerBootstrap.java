package swagger;

import play.Logger;
import play.Play;
import play.classloading.ApplicationClasses;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.mvc.Router;
import swagger.annotations.Api;
import swagger.models.InfoObject;
import swagger.models.OperationObject;
import swagger.models.PathItemObject;
import swagger.models.SwaggerObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@OnApplicationStart
public class SwaggerBootstrap extends Job {

  private static SwaggerObject swaggerObject;

  public static SwaggerObject getSwaggerObject() {
    if (swaggerObject == null) {
      throw new IllegalStateException("swaggerObject is null, meaning SwaggerBootstrap#doJob() didn't run on startup!");
    }
    return swaggerObject;
  }

  @Override
  public void doJob() {
    initSwaggerObject();
  }

  private static void initSwaggerObject() {

    Logger.info("First request, running SwaggerBootstrap#doJob()");

    swaggerObject = new SwaggerObject(new InfoObject("Play 1 REST API demo", "0.1"), Router.getBaseUrl(), "/");

    // Find all files annotated with 'Api'.
    List<ApplicationClasses.ApplicationClass> annotatedClasses = Play.classes.getAnnotatedClasses(Api.class);

    for (ApplicationClasses.ApplicationClass applicationClass : annotatedClasses) {
      swaggerObject.addPathsObjectFrom(applicationClass.javaClass);
    }

    // First collect all REST end points before adding them to Play's Router, so that they can be sorted on priority.
    final List<RoutesEntry> routesEntries = new ArrayList<RoutesEntry>();

    for (String path : swaggerObject.paths.keySet()) {

      PathItemObject pathItemObject = swaggerObject.paths.get(path);

      for (String method : pathItemObject.keySet()) {

        final OperationObject operationObject = pathItemObject.get(method);
        final int priority = operationObject.playRoutesPriority;
        final String completePath = String.format("/%s/%s", swaggerObject.basePath, path).replace("//", "/");
        final String action = operationObject.playAction;

        routesEntries.add(new RoutesEntry(priority, method.toUpperCase(), completePath, action));
      }
    }

    Collections.sort(routesEntries);

    for (RoutesEntry entry : routesEntries) {
      Logger.info(" - adding to Router: %s", entry);
      Router.addRoute(entry.httpMethod, entry.path, entry.action);
    }
  }

  static class RoutesEntry implements Comparable<RoutesEntry> {

    final int priority;
    final String httpMethod;
    final String path;
    final String action;

    RoutesEntry(int priority, String httpMethod, String path, String action) {
      this.priority = priority;
      this.httpMethod = httpMethod;
      this.path = path;
      this.action = action;
    }

    @Override
    public int compareTo(RoutesEntry that) {
      if (this.priority < that.priority) {
        return -1;
      }
      if (this.priority > that.priority) {
        return 1;
      }
      return 0;
    }

    @Override
    public String toString() {
      return String.format("%-10s %-30s %s", httpMethod, path, action);
    }
  }
}
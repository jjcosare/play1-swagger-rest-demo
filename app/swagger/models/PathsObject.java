package swagger.models;

import static swagger.util.ReflectUtils.*;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;

public class PathsObject extends LinkedHashMap<String, PathItemObject> {

  //  /{path}	Path Item Object	A relative path to an individual endpoint. The field name MUST begin with a slash. The path is appended to the basePath in order to construct the full URL. Path templating is allowed.

  //  ^x-	Any	Allows extensions to the Swagger Schema. The field name MUST begin with x-, for example, x-internal-id. The value can be null, a primitive, an array or an object. See Vendor Extensions for further details.

  public void addPathMethod(String apiPath, Method method, SwaggerObject swaggerObject) {

    final String methodPath = findAnnotationValue(method, "path", HTTP_METHOD_ANNOTATION_TYPES);
    final String completePath = apiPath + methodPath;

    PathItemObject existingPathItemObject = super.remove(completePath);

    if (existingPathItemObject == null) {
      existingPathItemObject = new PathItemObject();
    }

    existingPathItemObject.addPathMethod(method, swaggerObject);

    super.put(completePath, existingPathItemObject);
  }
}

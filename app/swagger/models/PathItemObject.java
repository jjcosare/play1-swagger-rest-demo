package swagger.models;

import static swagger.util.ReflectUtils.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;

public class PathItemObject extends LinkedHashMap<String, OperationObject> {

  //  $ref	string	Allows for an external definition of this path item. The referenced structure MUST be in the format of a Path Item Object. If there are conflicts between the referenced definition and this Path Item's definition, the behavior is undefined.

  // A definition of a GET operation on this path.

  // A definition of a PUT operation on this path.

  // A definition of a POST operation on this path.

  // A definition of a DELETE operation on this path.

  // A definition of a OPTIONS operation on this path.

  // A definition of a HEAD operation on this path.

  // A definition of a PATCH operation on this path.

  //  parameters	[Parameter Object | Reference Object]	A list of parameters that are applicable for all the operations described under this path. These parameters can be overridden at the operation level, but cannot be removed there. The list MUST NOT include duplicated parameters. A unique parameter is defined by a combination of a name and location. The list can use the Reference Object to link to parameters that are defined at the Swagger Object's parameters. There can be one "body" parameter at most.

  public void addPathMethod(Method method, SwaggerObject swaggerObject) {

    String httpMethod = null;

    for (Class<? extends Annotation> annotationType : HTTP_METHOD_ANNOTATION_TYPES) {
      if (methodIsAnnotationWith(method, annotationType)) {
        httpMethod = annotationType.getSimpleName().toLowerCase();
        break;
      }
    }

    final String summary = findAnnotationValue(method, "summary", HTTP_METHOD_ANNOTATION_TYPES);
    final String description = findAnnotationValue(method, "description", HTTP_METHOD_ANNOTATION_TYPES);

    super.put(httpMethod, new OperationObject(method, summary, description, swaggerObject));
  }
}

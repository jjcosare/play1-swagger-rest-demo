package swagger.models;

import swagger.annotations.Response;

import static swagger.util.ReflectUtils.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OperationObject {

  /**
   * A list of tags for API documentation control. Tags can be used for logical grouping
   * of operations by resources or any other qualifier.
   */
  public List<String> tags;

  /**
   * A short summary of what the operation does. For maximum readability in the swagger-ui,
   * this field SHOULD be less than 120 characters.
   */
  public String summary;

  /**
   * A verbose explanation of the operation behavior. GFM syntax can be used for rich text
   * representation.
   */
  public String description;

  /**
   * Additional external documentation for this operation.
   */
  public ExternalDocumentationObject externalDocs; // TODO

  /**
   * A friendly name for the operation. The id MUST be unique among all operations described
   * in the API. Tools and libraries MAY use the operation id to uniquely identify an operation.
   */
  public String operationId; // TODO

  /**
   * A list of MIME types the operation can consume. This overrides the consumes definition
   * at the Swagger Object. An empty value MAY be used to clear the global definition. Value
   * MUST be as described under Mime Types.
   */
  public String[] consumes;

  /**
   * A list of MIME types the operation can produce. This overrides the produces definition at
   * the Swagger Object. An empty value MAY be used to clear the global definition. Value MUST
   */
  public String[] produces;

  /**
   * A list of parameters that are applicable for this operation. If a parameter is already
   * defined at the Path Item, the new definition will override it, but can never remove it.
   * The list MUST NOT include duplicated parameters. A unique parameter is defined by a
   * combination of a name and location. The list can use the Reference Object to link to
   * parameters that are defined at the Swagger Object's parameters. There can be one "body"
   * parameter at most.
   */
  public List<ParameterObject> parameters;

  /**
   * The list of possible responses as they are returned from executing this operation.
   */
  public ResponsesObject responses;

  /**
   * The transfer protocol for the operation. Values MUST be from the list: "http", "https",
   * "ws", "wss". The value overrides the Swagger Object schemes definition.
   */
  public String[] schemes; // TODO

  /**
   * Declares this operation to be deprecated. Usage of the declared operation should be
   * refrained. Default value is false.
   */
  public boolean deprecated; // TODO

  /**
   * A declaration of which security schemes are applied for this operation. The list of values
   * describes alternative security schemes that can be used (that is, there is a logical OR
   * between the security requirements). This definition overrides any declared top-level
   * security. To remove a top-level security declaration, an empty array can be used.
   */
  public SecurityRequirementObject[] security; // TODO

  // Some transient fields needed for Play specific functionality. By making them
  // transient, they won't end up in the JSON object we feed to Swagger UI.
  public final transient int playRoutesPriority;
  public final transient String playAction;

  public OperationObject(Method method, String summary, String description, SwaggerObject swaggerObject) {

    this.tags = Collections.singletonList(method.getDeclaringClass().getSimpleName());
    this.summary = summary;
    this.description = description;
    this.playRoutesPriority = findAnnotationValue(method, "priority", HTTP_METHOD_ANNOTATION_TYPES);
    this.playAction = method.getDeclaringClass().getName() + "." + method.getName();
    this.consumes = findAnnotationValue(method, "consumes", HTTP_METHOD_ANNOTATION_TYPES);
    this.produces = findAnnotationValue(method, "produces", HTTP_METHOD_ANNOTATION_TYPES);
    this.parameters = new ArrayList<ParameterObject>();
    this.responses = new ResponsesObject();

    this.initParameters(method, swaggerObject);

    Response[] responses = findAnnotationValue(method, "responses", HTTP_METHOD_ANNOTATION_TYPES);
    this.initResponses(responses);
  }

  private void initParameters(Method method, SwaggerObject swaggerObject) {

    Class<?>[] parameterTypes = method.getParameterTypes();
    Annotation[][] parameterAnnotations = method.getParameterAnnotations();

    for (int i = 0; i < parameterTypes.length; i++) {

      final Class<?> parameterType = parameterTypes[i];
      final Annotation[] annotations = parameterAnnotations[i];
      final Annotation paramAnnotation = findIn(annotations, METHOD_PARAM_ANNOTATION_TYPES);

      if (paramAnnotation != null) {

        final String name = findAnnotationValue(paramAnnotation, "name");
        final ParamType in = ParamType.parse(paramAnnotation.annotationType().getSimpleName());
        final String description = findAnnotationValue(paramAnnotation, "description");
        final boolean required = findAnnotationValue(paramAnnotation, "required");
        final ParamObjectType paramObjectType = ParamObjectType.findFor(parameterType, swaggerObject);

        ParameterObject parameterObject = new ParameterObject(name, in, description, required, paramObjectType, parameterType);

        this.parameters.add(parameterObject);
      }
    }
  }

  private void initResponses(Response[] responses) {
    for (Response response : responses) {
      this.responses.put(String.valueOf(response.statusCode()), new ResponseObject(response.description()));
    }
  }
}

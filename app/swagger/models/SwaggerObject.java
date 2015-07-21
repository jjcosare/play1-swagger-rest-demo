package swagger.models;

import swagger.annotations.*;
import static swagger.util.ReflectUtils.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the root document object for the API specification. It combines what previously
 * was the Resource Listing and API Declaration (version 1.2 and earlier) together into one
 * document.
 */
public class SwaggerObject {

  /**
   * Required.
   * Specifies the Swagger Specification version being used. It can be used by the Swagger UI
   * and other clients to interpret the API listing. The value MUST be "2.0".
   */
  public final String swagger = "2.0";

  /**
   * Required.
   * Provides metadata about the API. The metadata can be used by the clients if needed.
   */
  public final InfoObject info;

  /**
   * The host (name or ip) serving the API. This MUST be the host only and does not include
   * the scheme nor sub-paths. It MAY include a port. If the host is not included, the host
   * serving the documentation is to be used (including the port). The host does not support
   * path templating.
   */
  public final String host;

  /**
   * The base path on which the API is served, which is relative to the host. If it is not
   * included, the API is served directly under the host. The value MUST start with a leading
   * slash (/). The basePath does not support path templating.
   */
  public final String basePath;

  /**
   * The transfer protocol of the API. Values MUST be from the list: "http", "https", "ws",
   * "wss". If the schemes is not included, the default scheme to be used is the one used to
   * access the Swagger definition itself.
   */
  public final String[] schemes = { "http" };

  /**
   * A list of MIME types the APIs can consume. This is global to all APIs but can be overridden
   * on specific API calls. Value MUST be as described under Mime Types.
   */
  public String[] consumes; // TODO

  /**
   * A list of MIME types the APIs can produce. This is global to all APIs but can be overridden
   * on specific API calls. Value MUST be as described under Mime Types.
   */
  public String[] produces; // TODO

  /**
   * Required.
   * The available paths and operations for the API.
   */
  public final PathsObject paths;

  /**
   * An object to hold data types produced and consumed by operations.
   */
  public final DefinitionsObject definitions;

  /**
   * An object to hold parameters that can be used across operations. This property does not
   * define global parameters for all operations.
   */
  public ParametersDefinitionsObject parameters; // TODO

   /** An object to hold responses that can be used across operations. This property does not
    * define global responses for all operations.
    */
  public ResponsesDefinitionsObject responses; // TODO

   /** securityDefinitions	Security Definitions Object	Security scheme definitions that can
    * be used across the specification.
    */
  public SecurityDefinitionsObject securityDefinitions; // TODO

   /**
    * A declaration of which security schemes are applied for the API as a whole. The list of
    * values describes alternative security schemes that can be used (that is, there is a
    * logical OR between the security requirements). Individual operations can override this
    * definition.
    */
  public SecurityRequirementObject security; // TODO

  /**
   * A list of tags used by the specification with additional metadata. The order of the
   * tags can be used to reflect on their order by the parsing tools. Not all tags that
   * are used by the Operation Object must be declared. The tags that are not declared
   * may be organized randomly or based on the tools' logic. Each tag name in the list
   * MUST be unique.
   */
  public List<TagObject> tags;

  /**
   * Additional external documentation.
   */
  public ExternalDocumentationObject externalDocs; // TODO

  public SwaggerObject(InfoObject info, String host, String basePath) {
    this.info = info;
    this.host = host;
    this.basePath = basePath;
    this.paths = new PathsObject();
    this.definitions = new DefinitionsObject();
    this.tags = new ArrayList<TagObject>();
  }

  public void addPathsObjectFrom(Class<?> apiClass) {

    final Api api = apiClass.getAnnotation(Api.class);
    final Method[] methods = apiClass.getMethods();

    this.tags.add(new TagObject(apiClass.getSimpleName(), api.description()));

    for (Method method : methods) {
      if (methodIsAnnotationWithOneOf(method, HTTP_METHOD_ANNOTATION_TYPES)) {
        this.paths.addPathMethod(api.path(), method, this);
      }
    }
  }
}

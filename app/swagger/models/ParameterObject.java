package swagger.models;

public class ParameterObject {

  // Required. The name of the parameter. Parameter names are case sensitive.
  //  - If in is "path", the name field MUST correspond to the associated path segment from the path field in the Paths Object. See Path Templating for further information.
  //  - For all other cases, the name corresponds to the parameter name used based on the in property.
  public final String name;

  // Required. The location of the parameter. Possible values are "query", "header", "path", "formData" or "body".
  public final ParamType in;

  // A brief description of the parameter. This could contain examples of use. GFM syntax can be used for rich text representation.
  public final String description;

  //  required	boolean	Determines whether this parameter is mandatory. If the parameter is in "path", this property is required and its value MUST be true. Otherwise, the property MAY be included and its default value is false.
  public final boolean required;

  // If in is "body": Required. The schema defining the type used for the body parameter.
  public final SchemaObject schema;

  // Required. The type of the parameter. Since the parameter is not located at the request body, it is limited to simple types (that is, not an object). The value MUST be one of "string", "number", "integer", "boolean", "array" or "file". If type is "file", the consumes MUST be either "multipart/form-data" or " application/x-www-form-urlencoded" and the parameter MUST be in "formData".
  public final ParamObjectType type;

  //  format	string	The extending format for the previously mentioned type. See Data Type Formats for further details.

  //  allowEmptyValue	boolean	Sets the ability to pass empty-valued parameters. This is valid only for either query or formData parameters and allows you to send a parameter with a name only or an empty value. Default value is false.

  //  items	Items Object	Required if type is "array". Describes the type of items in the array.

  //  collectionFormat	string	Determines the format of the array if type array is used. Possible values are:
  //      csv - comma separated values foo,bar.
  //      ssv - space separated values foo bar.
  //      tsv - tab separated values foo\tbar.
  //      pipes - pipe separated values foo|bar.
  //      multi - corresponds to multiple parameter instances instead of multiple values for a single instance foo=bar&foo=baz. This is valid only for parameters in "query" or "formData".
  //  Default value is csv.

  //  default	*	Sets a default value to the parameter. See http://json-schema.org/latest/json-schema-validation.html#anchor101. Unlike JSON Schema this value MUST conform to the defined type for this parameter.

  //  maximum	number	See http://json-schema.org/latest/json-schema-validation.html#anchor17.

  //  exclusiveMaximum	boolean	See http://json-schema.org/latest/json-schema-validation.html#anchor17.

  //  minimum	number	See http://json-schema.org/latest/json-schema-validation.html#anchor21.

  //  exclusiveMinimum	boolean	See http://json-schema.org/latest/json-schema-validation.html#anchor21.

  //  maxLength	integer	See http://json-schema.org/latest/json-schema-validation.html#anchor26.

  //  minLength	integer	See http://json-schema.org/latest/json-schema-validation.html#anchor29.

  //  pattern	string	See http://json-schema.org/latest/json-schema-validation.html#anchor33.

  //  maxItems	integer	See http://json-schema.org/latest/json-schema-validation.html#anchor42.

  //  minItems	integer	See http://json-schema.org/latest/json-schema-validation.html#anchor45.

  //  uniqueItems	boolean	See http://json-schema.org/latest/json-schema-validation.html#anchor49.

  //  enum	[*]	See http://json-schema.org/latest/json-schema-validation.html#anchor76.

  //  multipleOf	number	See http://json-schema.org/latest/json-schema-validation.html#anchor14.

  public ParameterObject(String name, ParamType in, String description, boolean required, ParamObjectType type, Class<?> parameterType) {
    this.name = name;
    this.in = in;
    this.description = description;
    this.required = required;
    this.type = type;
    this.schema = this.in == ParamType.BODY ? new SchemaObject(parameterType.getSimpleName()) : null;
  }
}

package swagger.models;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public class SchemaObject {

  //  $ref - As a JSON Reference
  @SerializedName("$ref")
  public String ref;

  //  format (See Data Type Formats for further details)

  //  title

  //  description (GFM syntax can be used for rich text representation)

  //  default (Unlike JSON Schema, the value MUST conform to the defined type for the Schema Object)

  //  multipleOf

  //  maximum

  //  exclusiveMaximum

  //  minimum

  //  exclusiveMinimum

  //  maxLength

  //  minLength

  //  pattern

  //  maxItems

  //  minItems

  //  uniqueItems

  //  maxProperties

  //  minProperties

  //  required

  //  enum

  //  type
  public String type;

  //  items

  //  allOf

  //  properties
  public Map<String, Property> properties;

  //  additionalProperties

  //  discriminator	string	Adds support for polymorphism. The discriminator is the schema property name that is used to differentiate between other schema that inherit this schema. The property name used MUST be defined at this schema and it MUST be in the required property list. When used, the value MUST be the name of this schema or any schema that inherits it.

  //  readOnly	boolean	Relevant only for Schema "properties" definitions. Declares the property as "read only". This means that it MAY be sent as part of a response but MUST NOT be sent as part of the request. Properties marked as readOnly being true SHOULD NOT be in the required list of the defined schema. Default value is false.

  //  xml	XML Object	This MAY be used only on properties schemas. It has no effect on root schemas. Adds Additional metadata to describe the XML representation format of this property.

  //  externalDocs	External Documentation Object	Additional external documentation for this schema.

  //  example	Any	A free-form property to include a an example of an instance for this schema.

  public SchemaObject(String className) {
    this.ref = "#/definitions/" + className;
  }

  public SchemaObject(Class<?> type, SwaggerObject swaggerObject) {

    this.type = "object";
    this.properties = new LinkedHashMap<String, Property>();

    Field[] fields = type.getDeclaredFields();

    for (Field field : fields) {
      this.properties.put(field.getName(), new Property(ParamObjectType.findFor(field.getType(), swaggerObject)));
    }
  }

  static class Property {

    ParamObjectType type;
    String format;
    String description;

    public Property(ParamObjectType type) {
      this.type = type;
    }
  }
}

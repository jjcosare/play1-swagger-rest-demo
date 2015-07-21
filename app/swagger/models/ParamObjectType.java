package swagger.models;

import com.google.gson.annotations.SerializedName;

public enum ParamObjectType {

  @SerializedName("string")
  STRING,

  @SerializedName("number")
  NUMBER,

  @SerializedName("integer")
  INTEGER,

  @SerializedName("boolean")
  BOOLEAN,

  @SerializedName("array")
  ARRAY;

  public static ParamObjectType findFor(Class<?> type, SwaggerObject swaggerObject) {

    if (typeIsOneOf(type, String.class, StringBuilder.class, StringBuffer.class)) {
      return STRING;
    }

    if (typeIsOneOf(type, int.class, byte.class, short.class, long.class)) {
      return INTEGER;
    }

    if (typeIsOneOf(type, double.class, float.class)) {
      return NUMBER;
    }

    if (typeIsOneOf(type, boolean.class)) {
      return BOOLEAN;
    }

    if (type.isArray()) {
      return ARRAY;
    }

    if (swaggerObject != null) {
      swaggerObject.definitions.put(type.getSimpleName(), new SchemaObject(type, swaggerObject));
    }

    return null;
  }

  static boolean typeIsOneOf(Class<?> type, Class<?>... types) {
    for (Class<?> t : types) {
      if (type == t) {
        return true;
      }
    }
    return false;
  }
}

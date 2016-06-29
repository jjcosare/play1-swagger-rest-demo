package swagger.models;

import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.util.Date;

public enum ParamObjectType {

  @SerializedName("string")
  STRING,

  @SerializedName("number")
  NUMBER,

  @SerializedName("integer")
  INTEGER,

  @SerializedName("boolean")
  BOOLEAN,

  @SerializedName("file")
  FILE,
  
  @SerializedName("array")
  ARRAY;

  public static ParamObjectType findFor(Class<?> type, SwaggerObject swaggerObject) {

    if (typeIsOneOf(type, String.class, StringBuilder.class, StringBuffer.class,
            Date.class)) {
      return STRING;
    }

    if (typeIsOneOf(type, int.class, byte.class, short.class, long.class,
            Integer.class, Byte.class, Short.class, Long.class)) {
      return INTEGER;
    }

    if (typeIsOneOf(type, double.class, float.class,
            Double.class, Float.class)) {
      return NUMBER;
    }

    if (typeIsOneOf(type, boolean.class,
            Boolean.class)) {
      return BOOLEAN;
    }

    if (typeIsOneOf(type, File.class)) {
      return FILE;
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

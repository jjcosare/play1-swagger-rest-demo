package swagger.models;

import com.google.gson.annotations.SerializedName;

public enum ParamType {

  @SerializedName("body")
  BODY,

  @SerializedName("formData")
  FORMDATA,

  @SerializedName("header")
  HEADER,

  @SerializedName("path")
  PATH,

  @SerializedName("query")
  QUERY;

  public static ParamType parse(String value) {
    return ParamType.valueOf(value.toUpperCase());
  }
}

package swagger.models;

public class ResponseObject {

  // Required. A short description of the response. GFM syntax can be used for rich text representation.
  public String description;

  //A definition of the response structure. It can be a primitive, an array or an object. If this field does not exist, it means no content is returned as part of the response. As an extension to the Schema Object, its root type value may also be "file". This SHOULD be accompanied by a relevant produces mime-type.
  public SchemaObject schema; // TODO

  // A list of headers that are sent with the response.
  public HeadersObject headers; // TODO

  // An example of the response message.
  public ExampleObject examples; // TODO

  public ResponseObject(String description) {
    this.description = description;
  }
}

package swagger.models;

public class TagObject {

  // name	string	Required. The name of the tag.
  public final String name;

  //  description	string	A short description for the tag. GFM syntax can be used for rich text representation.
  public final String description;

  //  externalDocs	External Documentation Object	Additional external documentation for this tag.

  public TagObject(String name, String description) {
    this.name = name;
    this.description = description;
  }
}

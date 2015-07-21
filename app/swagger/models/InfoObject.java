package swagger.models;

public class InfoObject {

  // Required. The title of the application.
  public final String title;

  //  description	string	A short description of the application. GFM syntax can be used for rich text representation.

  //  termsOfService	string	The Terms of Service for the API.

  //  contact	Contact Object	The contact information for the exposed API.

  //  license	License Object	The license information for the exposed API.

  // Required Provides the version of the application API (not to be confused with the specification version).
  public final String version;

  public InfoObject(String title, String version) {
    this.title = title;
    this.version = version;
  }
}

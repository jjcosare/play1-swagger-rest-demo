package swagger.models;

import java.util.LinkedHashMap;

public class ResponsesObject extends LinkedHashMap<String, ResponseObject> {

  // default	Response Object | Reference Object	The documentation of responses other than the ones declared for specific HTTP response codes. It can be used to cover undeclared responses. Reference Object can be used to link to a response that is defined at the Swagger Object's responses section.

  // {HTTP Status Code}	Response Object | Reference Object	Any HTTP status code can be used as the property name (one property per HTTP status code). Describes the expected response for that HTTP status code. Reference Object can be used to link to a response that is defined at the Swagger Object's responses section.

  // ^x-	Any	Allows extensions to the Swagger Schema. The field name MUST begin with x-, for example, x-internal-id. The value can be null, a primitive, an array or an object. See Vendor Extensions for further details.


}

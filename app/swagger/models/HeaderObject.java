package swagger.models;

public class HeaderObject {

  //  description	string	A short description of the header.

  //  type	string	Required. The type of the object. The value MUST be one of "string", "number", "integer", "boolean", or "array".

  //  format	string	The extending format for the previously mentioned type. See Data Type Formats for further details.

  //  items	Items Object	Required if type is "array". Describes the type of items in the array.

  //  collectionFormat	string	Determines the format of the array if type array is used. Possible values are:
  //      csv - comma separated values foo,bar.
  //      ssv - space separated values foo bar.
  //      tsv - tab separated values foo\tbar.
  //      pipes - pipe separated values foo|bar.
  //    Default value is csv.

  //  default	*	Sets a default value for the header. See http://json-schema.org/latest/json-schema-validation.html#anchor101. Unlike JSON Schema this value MUST conform to the defined type for the header.

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
}

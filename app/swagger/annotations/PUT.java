package swagger.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface PUT {

  String path() default "/";

  String summary() default "";

  String description() default "";

  String[] produces() default { "application/json" };

  String[] consumes() default { "application/json" };

  /**
   * The priority of the route (lower number is higher priority)
   */
  int priority() default 10;

  Response[] responses() default { @Response(statusCode=200, description="If the request was successful") };
}

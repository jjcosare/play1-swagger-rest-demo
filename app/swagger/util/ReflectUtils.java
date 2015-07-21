package swagger.util;

import swagger.annotations.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public final class ReflectUtils {

  public static final List<Class<? extends Annotation>> HTTP_METHOD_ANNOTATION_TYPES = Arrays.asList(
      GET.class, POST.class, PUT.class, DELETE.class, PATCH.class, OPTIONS.class
  );

  public static final List<Class<? extends Annotation>> METHOD_PARAM_ANNOTATION_TYPES = Arrays.asList(
      Body.class, FormData.class, Header.class, Path.class, Query.class
  );

  public static boolean methodIsAnnotationWith(Method method, Class<? extends Annotation> annotationType) {
    return method.isAnnotationPresent(annotationType);
  }

  public static boolean methodIsAnnotationWithOneOf(Method method, Class<? extends Annotation>... annotationTypes) {
    return methodIsAnnotationWithOneOf(method, Arrays.asList(annotationTypes));
  }

  public static boolean methodIsAnnotationWithOneOf(Method method, List<Class<? extends Annotation>> annotationTypes) {

    for (Class<? extends Annotation> annotationType : annotationTypes) {
      if (method.isAnnotationPresent(annotationType)) {
        return true;
      }
    }

    return false;
  }

  public static <A extends Annotation> A tryGetAnnotation(Method method, Class<A> annotationType) {

    if (method.isAnnotationPresent(annotationType)) {
      return method.getAnnotation(annotationType);
    }

    return null;
  }

  public static <T> T findAnnotationValue(Method method, String annotationMethodName, Class<? extends Annotation>... annotationTypes) {
    return findAnnotationValue(method, annotationMethodName, Arrays.asList(annotationTypes));
  }

  public static <T> T findAnnotationValue(Method method, String annotationMethodName, List<Class<? extends Annotation>> annotationTypes) {

    final String fullMethodName = String.format("%s#%s", method.getDeclaringClass().getSimpleName(), method.getName());
    Annotation annotation = null;

    for (Class<? extends Annotation> annotationType : annotationTypes) {

      annotation = tryGetAnnotation(method, annotationType);

      if (annotation != null) {
        break;
      }
    }

    if (annotation == null) {
      throw new RuntimeException(fullMethodName + " is not annotated with one of: " + annotationTypes);
    }

    try {
      Method annotationMethod = annotation.getClass().getMethod(annotationMethodName);
      return (T) annotationMethod.invoke(annotation);
    }
    catch (Exception e) {
      throw new RuntimeException(fullMethodName + "'s " + annotation.getClass().getSimpleName() +
          " annotation does not have a method called " + annotationMethodName);
    }
  }

  public static Annotation findIn(Annotation[] annotations, List<Class<? extends Annotation>> annotationTypes) {

    for (Annotation annotation : annotations) {
      for (Class<? extends Annotation> annotationType : METHOD_PARAM_ANNOTATION_TYPES) {
        if (annotation.annotationType() == annotationType) {
          return annotation;
        }
      }
    }

    return null;
  }

  public static <T> T findAnnotationValue(Annotation annotation, String annotationMethodName) {
    try {
      Method annotationMethod = annotation.getClass().getMethod(annotationMethodName);
      return (T) annotationMethod.invoke(annotation);
    }
    catch (Exception e) {
      throw new RuntimeException(annotation.getClass().getSimpleName() +
          " annotation does not have a method called " + annotationMethodName);
    }
  }
}

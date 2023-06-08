import classes.Readable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ReflectionAPI {

  public static void main(String[] args) {
    updatePrivateField();
  }

  private static void invokeMethodFromAClass() {
    try {
      Class<?> clazz = Class.forName("classes.Printer");
      Method[] methods = clazz.getMethods();
      Constructor<?> constructor = clazz.getDeclaredConstructor();
      Object instance = constructor.newInstance();
      methods[0].invoke(instance, "Print this for me with Reflection API");
    } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException | NoSuchMethodException | InstantiationException e) {
      e.printStackTrace();
    }
  }

  private static void inspectClassMethods() {
    try {
      String className = readClassName();
      Class<?> clazz = Class.forName("classes." + className);
      Method[] methods = clazz.getDeclaredMethods();
      for (Method method : methods) {
        readMethod(method);
      }
    } catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
    }
  }

  private static void customAnnotation() {
    try {
      Class<?> clazz = Class.forName("classes.FileReader");
      Method[] methods = clazz.getDeclaredMethods();
      for (Method method : methods) {
        if (method.isAnnotationPresent(Readable.class)) {
          readMethod(method);
        } else {
          System.out.println("Method " + method.getName() + " is not readable");
        }
      }
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  private static void updatePrivateField() {
    try {
      Class<?> clazz = Class.forName("classes.FileReader");
      Constructor<?> constructor = clazz.getDeclaredConstructor();
      Object instance = constructor.newInstance();
      Field field = clazz.getDeclaredField("path");
      Method method = clazz.getMethod("getPath");
      System.out.println("Calling getPath before updating private field: " + method.invoke(instance));
      field.setAccessible(true);
      field.set(instance, "Updated from Reflection API");
      System.out.println("Calling getPath after updating private field: " + method.invoke(instance));
    } catch (ClassNotFoundException | NoSuchFieldException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }
  }

  private static void readMethod(Method method) {
    System.out.println("Printing information about method: " + method.getName());
    System.out.println("Modifier: " + Modifier.toString(method.getModifiers()));
    System.out.println("Return type: " + method.getReturnType());
    System.out.println("Parameters count: " + method.getParameterCount());
    System.out.println("Parameters types: " + Arrays.stream(method.getParameterTypes()).map(
        Class::getName).collect(Collectors.toList()));
    System.out.println("Parameters: "
        + Arrays.stream(method.getParameters())
        .map(it -> "\nname: " + it.getName() +
            ", type: " + it.getType() +
            ", modifiers: " + it.getParameterizedType() +
            ", modifiers: " + Modifier.toString(it.getModifiers()) +
            ", annotated type: " + it.getAnnotatedType().getType().getTypeName() +
            ", declaring executable: " + it.getDeclaringExecutable() +
            ", annotations: " + Arrays.toString(it.getAnnotations()) + "\n")
        .collect(Collectors.toList()));
  }

  private static String readClassName() throws IOException {
    BufferedReader reader = new BufferedReader(
        new InputStreamReader(System.in));
    System.out.println("Enter class name:");
    return reader.readLine();
  }
}

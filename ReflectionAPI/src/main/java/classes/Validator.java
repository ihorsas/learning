package classes;

import java.util.LinkedList;
import java.util.List;

public class Validator {
  private List<Boolean> results;

  public Validator() {
    this.results = new LinkedList<>();
  }

  public void eq(Object expected, Object actual) {
    results.add(expected.equals(actual));
  }

  public void neq(Object expected, Object actual) {
    results.add(!expected.equals(actual));
  }

  public boolean validate() {
    return results.stream().anyMatch(it -> !it);
  }
}

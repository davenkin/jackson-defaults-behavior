package davenkin.jackson.defaultbehavior;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

public class SuccessDeserializeWithDefaultConstructorTest {
  @Test
  public void should_use_default_constructor_even_with_other_constructors() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
    User user = objectMapper.readValue("{\"name\":\"Andy\",\"age\":30,\"address\":\"1rd Street\"}", User.class);
    assertTrue(user.defaultConstructorCalled);
    assertNull(user.name);
  }

  public static class User {
    private String name;
    private int age;
    private String address;

    private boolean defaultConstructorCalled = false;

    public User() {
      System.out.println("0 arg constructor called.");
      this.defaultConstructorCalled = true;
    }

    public User(String name, int age) {
      this.name = name;
      this.age = age;
      System.out.println("2 arg constructor called.");
    }

    public User(String name, int age, String address) {
      this.name = name;
      this.age = age;
      this.address = address;
      System.out.println("3 arg constructor called.");
    }
  }
}

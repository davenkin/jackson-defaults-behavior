package davenkin.jackson.defaultbehavior;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.beans.ConstructorProperties;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

public class SuccessDeserializeWithConstructorThenSetterTest {
  @Test
  public void should_use_default_constructor_even_with_other_constructors() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    User user = objectMapper.readValue("{\"name\":\"Andy\",\"age\":30,\"address\":\"1rd Street\"}", User.class);
    assertTrue(user.constructorCalled);
    assertTrue(user.ageSetterCalled);
    assertEquals("Andy", user.name);
    assertEquals(30, user.age);
    assertEquals("1rd Street", user.address);
  }

  public static class User {
    private final String name;
    private int age;
    private String address;

    private final boolean constructorCalled;
    private boolean ageSetterCalled;

    @ConstructorProperties({"name"})
    public User(String name) {
      System.out.println("1 arg constructor called.");
      this.name = name;
      this.constructorCalled = true;
    }

    public void setAge(int age) {
      this.age = age;
      this.ageSetterCalled = true;
    }

    public void setAddress(String address) {
      this.address = address;
    }
  }
}

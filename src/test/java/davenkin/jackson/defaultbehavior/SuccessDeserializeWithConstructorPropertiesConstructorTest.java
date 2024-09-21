package davenkin.jackson.defaultbehavior;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.beans.ConstructorProperties;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

public class SuccessDeserializeWithConstructorPropertiesConstructorTest {
  @Test
  public void should_call_constructor_properties_constructor() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
    User user = objectMapper.readValue("{\"name\":\"Andy\",\"age\":30,\"address\":\"1rd Street\"}", User.class);
    assertEquals("Andy", user.name);
    assertNull(user.address);
    assertTrue(user.constructorPropertiesConstructorCalled);
  }

  public static class User {
    private String name;
    private int age;
    private String address;

    private boolean constructorPropertiesConstructorCalled = false;

    //will not be called
    public User() {
      System.out.println("No arg constructor called.");
    }

    //this constructor will be called
    @ConstructorProperties({"name", "age"})
    public User(String name, int age) {
      this.name = name;
      this.age = age;
      System.out.println("@ConstructorProperties constructor called.");
      this.constructorPropertiesConstructorCalled = true;
    }

    //will not be called
    public User(String name, int age, String address) {
      this.name = name;
      this.age = age;
      this.address = address;
      System.out.println("Raw multi arg constructor called.");
    }
  }
}

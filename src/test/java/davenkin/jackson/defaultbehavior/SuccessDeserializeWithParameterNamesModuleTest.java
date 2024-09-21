package davenkin.jackson.defaultbehavior;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.junit.jupiter.api.Test;

public class SuccessDeserializeWithParameterNamesModuleTest {
  @Test
  public void should_deserialize_with_parameter_names_module() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new ParameterNamesModule());
    objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);

    User user = objectMapper.readValue("{\"name\":\"Andy\",\"age\":30,\"address\":\"1rd Street\"}", User.class);
    assertEquals("Andy", user.name);
    assertNull(user.address);
    assertTrue(user.constructorCalled);
  }

  public static class User {
    private final String name;
    private final int age;
    private String address;
    private final boolean constructorCalled;

    public User(String name, int age) {
      this.name = name;
      this.age = age;
      System.out.println("2 arg constructor called.");
      this.constructorCalled = true;
    }
  }
}

package davenkin.jackson.defaultbehavior;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.junit.jupiter.api.Test;

public class FailDeserializeMultipleMultiArgumentsConstructorsTest {
  @Test
  public void should_fail_deserialize_with_both_constructor_properties_and_json_creator_constructors() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new ParameterNamesModule());
    objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);

    assertThrows(InvalidDefinitionException.class, () -> {
      objectMapper.readValue("{\"name\":\"Andy\",\"age\":30,\"address\":\"1rd Street\"}", User.class);
    });
  }

  public static class User {
    private final String name;
    private final int age;
    private String address;

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

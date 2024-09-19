package davenkin.jackson;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.beans.ConstructorProperties;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import org.junit.jupiter.api.Test;

public class FailDeserializeWithBothJasonCreatorAndConstructorPropertiesConstructorTest {
  @Test
  public void should_fail_deserialize_with_both_constructor_properties_and_json_creator_constructors() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);

    InvalidDefinitionException exception = assertThrows(InvalidDefinitionException.class, () -> {
      objectMapper.readValue("{\"name\":\"Andy\",\"age\":30,\"address\":\"1rd Street\"}", User.class);
    });
    assertTrue(exception.getMessage().contains("Conflicting property-based creators"));
  }

  public static class User {
    private String name;
    private int age;
    private String address;

    @ConstructorProperties({"name", "age"})
    public User(String name, int age) {
      this.name = name;
      this.age = age;
      System.out.println("@ConstructorProperties constructor called.");
    }

    @JsonCreator
    public User(@JsonProperty("name") String name, @JsonProperty("age") int age, @JsonProperty("address") String address) {
      this.name = name;
      this.age = age;
      this.address = address;
      System.out.println("@JsonCreator constructor called.");
    }
  }
}

package davenkin.jackson.defaultbehavior;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

public class SuccessSerializeAndDeserializeFieldDirectlyTest {
  @Test
  public void should_serialize_and_deserialize_field_directly() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setVisibility(PropertyAccessor.ALL, NONE);
    objectMapper.setVisibility(PropertyAccessor.FIELD, ANY);
    User user = new User();
    user.name = "Andy";
    String json = objectMapper.writeValueAsString(user);
    assertTrue(json.contains("Andy"));

    User deserializedUser = objectMapper.readValue("{\"name\":\"Andy\"}", User.class);
    assertEquals("Andy", deserializedUser.name);
    assertTrue(deserializedUser.defaultConstructorCalled);
    assertFalse(deserializedUser.setterCalled);
  }

  public static class User {
    private String name;

    private final boolean defaultConstructorCalled;
    private boolean setterCalled;

    public User() {
      System.out.println("0 arg constructor called.");
      this.defaultConstructorCalled = true;
    }

    public void setName(String name) {
      this.setterCalled = true;
      this.name = name;
    }
  }
}

package davenkin.jackson;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

public class SuccessSerializeFieldDirectlyTest {
  @Test
  public void should_serialize_field_directly() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setVisibility(PropertyAccessor.ALL, NONE);
    objectMapper.setVisibility(PropertyAccessor.FIELD, ANY);
    User user = new User("Andy");
    System.out.println(objectMapper.writeValueAsString(user));
  }

  public static class User {
    private String name;

    public User(String name) {
      this.name = name;
    }
  }
}

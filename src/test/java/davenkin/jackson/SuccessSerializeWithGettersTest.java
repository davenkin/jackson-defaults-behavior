package davenkin.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

public class SuccessSerializeWithGettersTest {
  @Test
  public void should_serialize_object_with_getters() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    User user = new User("Andy");
    System.out.println(objectMapper.writeValueAsString(user));
  }

  public static class User {
    private String name;

    public User(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
  }
}

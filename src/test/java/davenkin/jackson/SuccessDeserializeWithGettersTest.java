package davenkin.jackson;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

public class SuccessDeserializeWithGettersTest {
  @Test
  public void should_deserialize_object_with_getters() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    User user = objectMapper.readValue("{\"name\":\"Andy\"}", User.class);
    assertEquals("Andy", user.getName());
  }

  public static class User {
    private String name;

    public String getName() {
      return name;
    }
  }
}

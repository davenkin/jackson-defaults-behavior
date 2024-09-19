package davenkin.jackson;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import org.junit.jupiter.api.Test;

public class FailSerializeWithoutGettersTest {
  @Test
  public void should_fail_serialize_object_without_getters() {
    ObjectMapper objectMapper = new ObjectMapper();
    User user = new User("Andy");
    assertThrows(InvalidDefinitionException.class, () -> objectMapper.writeValueAsString(user));
  }

  public static class User {
    private String name;

    public User(String name) {
      this.name = name;
    }
  }
}

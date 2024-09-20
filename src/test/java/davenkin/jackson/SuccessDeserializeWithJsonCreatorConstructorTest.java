package davenkin.jackson;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

public class SuccessDeserializeWithJsonCreatorConstructorTest {
  @Test
  public void should_call_json_creator_constructor() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
    User user = objectMapper.readValue("{\"name\":\"Andy\",\"age\":30,\"address\":\"1rd Street\"}", User.class);
    assertEquals("Andy", user.name);
    assertTrue(user.jsonCreatorCalled);
    assertNull(user.address);
  }

  public static class User {
    private String name;
    private int age;
    private String address;
    private boolean jsonCreatorCalled = false;

    //will not be called
    public User() {
      System.out.println("No arg constructor called.");
    }

    //this constructor will be called
    @JsonCreator
    public User(@JsonProperty("name") String name, @JsonProperty("age") int age) {
      this.name = name;
      this.age = age;
      System.out.println("@JsonCreator constructor called.");
      this.jsonCreatorCalled = true;
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

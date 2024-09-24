package davenkin.jackson.polymorphism;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import org.junit.jupiter.api.Test;

public class PolymorphismUsingDefaultTypingTest {
  @Test
  public void should_use_default_typing_for_serialization_and_deserialization() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

    PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
        .allowIfBaseType("davenkin")
        .allowIfBaseType("java")
        .build();
    objectMapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL_AND_ENUMS, JsonTypeInfo.As.PROPERTY);

    Admin admin = new Admin();
    admin.name = "Andy";
    admin.level = 45;

    Worker worker = new Worker();
    worker.name = "Mike";
    worker.location = "Shanghai";

    Company company = new Company();
    company.employees = List.of(admin, worker);
    company.starEmployee = worker;
    company.gatekeeper = worker;
    company.administrator = admin;

    String json = objectMapper.writeValueAsString(company);
    System.out.println(json);

    // with NON_FINAL_AND_ENUMS, it even works with Object.class as the top level class (Company) also has type info injected during serialization
    Company deserializedCompany = (Company) objectMapper.readValue(json, Object.class);
    assertEquals("Andy", deserializedCompany.employees.get(0).name);
    assertEquals("Mike", deserializedCompany.employees.get(1).name);
  }

  @Test
  public void fail_due_to_OBJECT_AND_NON_CONCRETE_not_working_with_root_level_Company() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

    PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
        .allowIfBaseType("davenkin")
        .allowIfBaseType("java")
        .build();
    objectMapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE, JsonTypeInfo.As.PROPERTY);

    Admin admin = new Admin();
    admin.name = "Andy";
    admin.level = 45;

    Worker worker = new Worker();
    worker.name = "Mike";
    worker.location = "Shanghai";

    Company company = new Company();
    company.employees = List.of(admin, worker);
    company.starEmployee = worker;
    company.gatekeeper = worker;
    company.administrator = admin;

    String json = objectMapper.writeValueAsString(company);
    System.out.println(json);

    // OBJECT_AND_NON_CONCRETE does not work for deserialize into Object.class, because the type info in not included for Company during serialization
    assertThrows(InvalidTypeIdException.class, () -> objectMapper.readValue(json, Object.class));

    // but is we specifically use Company.class, it works
    Company deserializedCompany = objectMapper.readValue(json, Company.class);
    assertEquals("Andy", deserializedCompany.employees.get(0).name);
    assertEquals("Mike", deserializedCompany.employees.get(1).name);
  }

  public static class Company {
    public List<Employee> employees;
    public Employee starEmployee;
    public Admin administrator;
    public Worker gatekeeper;
  }

  public static abstract class Employee {
    public String name;
  }

  public static class Admin extends Employee {
    public int level;
  }

  public final static class Worker extends Employee {
    public String location;
  }
}

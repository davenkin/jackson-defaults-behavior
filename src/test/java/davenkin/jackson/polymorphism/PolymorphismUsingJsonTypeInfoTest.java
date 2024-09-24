package davenkin.jackson.polymorphism;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;

public class PolymorphismUsingJsonTypeInfoTest {
  @Test
  public void should_use_json_type_info_for_polymorphism() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

    Admin admin = new Admin();
    admin.name = "Andy";
    admin.type = EmployeeType.ADMIN;
    admin.level = 45;

    Worker worker = new Worker();
    worker.name = "Mike";
    worker.type = EmployeeType.WORKER;
    worker.location = "Shanghai";

    Company company = new Company();
    company.employees = List.of(admin, worker);
    company.starEmployee = worker;
    company.gatekeeper = worker;
    company.administrator = admin;

    String json = objectMapper.writeValueAsString(company);
    System.out.println(json);
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

  @JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
      include = JsonTypeInfo.As.EXISTING_PROPERTY,
      property = "type",
      visible = true)
  @JsonSubTypes(value = {
      @JsonSubTypes.Type(value = Admin.class, name = "ADMIN"),
      @JsonSubTypes.Type(value = Worker.class, name = "WORKER"),
  })
  public static abstract class Employee {
    public EmployeeType type;
    public String name;
  }

  public static class Admin extends Employee {
    public int level;
  }

  public final static class Worker extends Employee {
    public String location;
  }

  public enum EmployeeType {
    ADMIN, WORKER
  }
}

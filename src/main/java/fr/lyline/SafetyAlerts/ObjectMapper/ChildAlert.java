package fr.lyline.SafetyAlerts.ObjectMapper;

import fr.lyline.SafetyAlerts.model.Person;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ChildAlert {
  List<Map<String, String>> parents = new ArrayList<>();
  private String childFirstName;
  private String childLastName;
  private String age;

  public ChildAlert() {
  }

  public ChildAlert(Person child, String age, List<Person> adults) {
    Map<String, String> childId = new HashMap<>();
    Map<Map<String, String>, List<Map<String, String>>> result = new HashMap<>();

    this.childFirstName = child.getFirstName();
    this.childLastName = child.getLastName();
    this.age = age;

    childId.put("firstName", childFirstName);
    childId.put("lastName", childLastName);
    childId.put("age", age);

    for (Person parent : adults) {
      Map<String, String> parentId = new HashMap<>();
      parentId.put("parentFirstName", parent.getFirstName());
      parentId.put("parentLastName", parent.getLastName());
      parents.add(parentId);
    }
    result.put(childId, parents);
  }

  @Override
  public String toString() {
    return "ChildAlert{" +
        "childFirstName='" + childFirstName + '\'' +
        ", childLastName='" + childLastName + '\'' +
        ", age='" + age + '\'' +
        ", parents=" + parents +
        '}';
  }
}

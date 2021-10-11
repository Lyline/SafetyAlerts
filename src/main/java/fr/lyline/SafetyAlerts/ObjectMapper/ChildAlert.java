package fr.lyline.SafetyAlerts.ObjectMapper;

import fr.lyline.SafetyAlerts.model.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 The object mapper Child alert use by the endpoints service of API. Its concatenate the first name, the last name, the age of
 this child and the list of his parents.

 @author Quesne GC
 @see fr.lyline.SafetyAlerts.service.MainFunctionsAPIService
 @since 0.1 */
public class ChildAlert {
  /**
   The list of parents child.
   */
  List<Map<String, String>> parents = new ArrayList<>();
  private String childFirstName;
  private String childLastName;
  private String age;

  /**
   Instantiates a new Child alert.
   */
  public ChildAlert() {
  }

  /**
   Instantiates a new Child alert.

   @param child  the child
   @param age    the age of the child
   @param adults the parents child
   */
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

  /**
   Gets parents list.

   @return the list of parents
   */
  public List<Map<String, String>> getParents() {
    return parents;
  }

  /**
   Gets child first name.

   @return the child first name
   */
  public String getChildFirstName() {
    return childFirstName;
  }

  /**
   Gets child last name.

   @return the child last name
   */
  public String getChildLastName() {
    return childLastName;
  }

  /**
   Gets age.

   @return the age
   */
  public String getAge() {
    return age;
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

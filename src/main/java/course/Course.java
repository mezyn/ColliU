package course;

public class Course {
  private final int id;
  private String name;

  public Course(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}

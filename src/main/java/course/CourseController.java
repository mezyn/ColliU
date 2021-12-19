package course;

import com.colliu.colliu.Master;

import java.util.ArrayList;

public class CourseController {
  ArrayList<Course> courses;
  Master master;

  public CourseController(Master masterClass) {
    master = masterClass;
    courses = new ArrayList<>();
  }

  // Returns course IDs in an array.
  Integer[] getCourses() {
    ArrayList<Integer> courseList = new ArrayList<>();
    for (Course course : courses) {
      courseList.add(course.getId());
    }
    return courseList.toArray(new Integer[0]);
  }
// Write comment here
  public void addCourse(String name) {
    if (!courses.contains(name)) {
      courses.add(new Course(courses.size(), name));
    }
  }
// Write comment here
  int findIndex(int id) {
    for(int course : getCourses()) {
      if(course == id) {
        return course;
      }
    }
    return -1;
  }
}

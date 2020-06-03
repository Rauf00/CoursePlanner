package ca.cmpt213.courseplanner.model;

import java.util.*;

/**
 * Department class stores the information about single department.
 * It also has methods to manipulate its course list.
 */
public class Department {
    private int deptId;
    private String name;
    // key: catalogNumber   val: Course
    private HashMap<String, Course> courseDictionary = new HashMap<>();

    public Department(int deptId, String name) {
        this.deptId = deptId;
        this.name = name;
    }

    public int getDeptId() {
        return deptId;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, Course> getCourseDictionary() {
        return courseDictionary;
    }

    public void addCourseToDictionary(Course course) {
        courseDictionary.put(course.getCatalogNumber(), course);
    }

    public Course getCourseFromDictionary(String catalogNumber){
        return courseDictionary.get(catalogNumber);
    }

    public boolean hasCourse(String catalogNumber){
        return courseDictionary.containsKey(catalogNumber);
    }

    public List<Course> getCoursesList() {
        List<Course> coursesList = new ArrayList<>();
        ArrayList<String> sortedCoursesNames = sortKeys(courseDictionary.keySet());
        for (String coursesName : sortedCoursesNames) {
            coursesList.add(courseDictionary.get(coursesName));
        }
        return coursesList;
    }

    public ArrayList<String> sortKeys(Set<String> keys){
        ArrayList<String> result = new ArrayList<>(keys);
        Collections.sort(result);
        return result;
    }
}




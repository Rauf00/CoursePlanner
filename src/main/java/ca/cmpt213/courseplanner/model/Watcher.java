package ca.cmpt213.courseplanner.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Watcher stores the information about the course
 * that being observed and a list of events
 * happening with this course
 */
public class Watcher implements Observer {
    private int id;
    private int deptId;
    private int courseId;
    private Department department;
    private Course course;
    private List<String> events = new ArrayList<>();

    public Watcher(int deptId, int courseId) {
        this.deptId = deptId;
        this.courseId = courseId;
        department = DepartmentManager.getInstance().getDepartmentWithId(deptId);
        course = DepartmentManager.getInstance().getCourseFromDepartment(deptId, courseId);
    }

    public Department getDepartment() {
        return department;
    }

    public Course getCourse() {
        return course;
    }

    public int getWatcherId() {
        return id;
    }

    public int getDeptId() {
        return deptId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setCourse(Course course){
        this.course = course;
    }

    public void setDepartment(Department department){
        this.department = department;
    }

    public List<String> getEvents(){
        return events;
    }

    @Override
    public void update(String event) {
        events.add(event);
    }
}

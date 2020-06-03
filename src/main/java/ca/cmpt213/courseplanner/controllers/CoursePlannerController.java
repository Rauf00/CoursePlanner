package ca.cmpt213.courseplanner.controllers;

import ca.cmpt213.courseplanner.DataParser;
import ca.cmpt213.courseplanner.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * CoursePlannerController contains the methods that
 * return certain objects or modify the data
 * in respond to certain HTTP requests
 */
@RestController
public class CoursePlannerController {
    private int watcherId = 0;
    private DepartmentManager departmentManager = DataParser.parseFile();
    private List<Department> departments = new ArrayList<>();
    private List<Course> coursesForDepartment = new ArrayList<>();
    private List<Offering> offeringsForCourse = new ArrayList<>();
    private List<Section> sectionsForOffering = new ArrayList<>();

    public CoursePlannerController() throws IOException {
    }

    @GetMapping("/api/about")
    public About getName() {
        return new About("SFU Best Course Planner", "Amirmehdi Naghibi and Rauf Shimarov");
    }

    @GetMapping("/api/dump-model")
    public void dumpModel() throws IOException {
        DepartmentManager.getInstance().printAllInformation();
    }

    @GetMapping("/api/departments")
    public List<Department> listAllDepartments() {
        return departmentManager.getDepartmentsList();
    }

    @GetMapping("/api/departments/{id}/courses")
    public List<Course> listAllCoursesForDepartment(@PathVariable("id") int deptId) {
        departments = departmentManager.getDepartmentsList();
        setCoursesByDeptId(deptId);
        return coursesForDepartment;
    }

    @GetMapping("/api/departments/{deptId}/courses/{courseId}/offerings")
    public List<Offering> listAllOfferingsForCourse(@PathVariable("courseId") int courseId,
                                                    @PathVariable("deptId") int deptId) {
        departments = departmentManager.getDepartmentsList();
        setCoursesByDeptId(deptId);
        setOfferingsByCourseId(courseId);
        return offeringsForCourse;
    }

    @GetMapping("/api/departments/{deptId}/courses/{courseId}/offerings/{courseOfferingId}")
    public List<Section> listAllComponentsForOffering(@PathVariable("courseId") int courseId,
                                                      @PathVariable("deptId") int deptId,
                                                      @PathVariable("courseOfferingId") int courseOfferingId) {
        departments = departmentManager.getDepartmentsList();
        setCoursesByDeptId(deptId);
        setOfferingsByCourseId(courseId);
        setSectionsByOfferingId(courseOfferingId);
        return sectionsForOffering;
    }

    @PostMapping("/api/addoffering")
    @ResponseStatus(HttpStatus.CREATED)
    public void addOffering(@RequestBody NewOffering newOffering){
        String newRecordString = newOffering.getSemester() + ","
                + newOffering.getSubjectName() + ","
                + newOffering.getCatalogNumber() + ","
                + newOffering.getLocation() + ","
                + newOffering.getEnrollmentCap() + ","
                + newOffering.getEnrollmentTotal() + ","
                + newOffering.getInstructor() + ","
                + newOffering.getComponent();
        DataParser.processLine(newRecordString);
        Course course = departmentManager.getDepartmentFromDictionary(newOffering.getSubjectName()).getCourseFromDictionary(newOffering.getCatalogNumber());
        String month = "Spring";
        if(String.valueOf(newOffering.getSemester()).charAt(3) == '4'){
            month = "Summer";
        }
        if(String.valueOf(newOffering.getSemester()).charAt(3) == '7'){
            month = "Fall";
        }
        String event = "Added section " + newOffering.getComponent() + " with enrollment (" +
                newOffering.getEnrollmentTotal() + " / " + newOffering.getEnrollmentCap() + ") to offering " + month +
                " 20" + String.valueOf(newOffering.getSemester()).substring(1,3);
        course.notifyObservers(event);
        return;
    }

    @GetMapping("/api/watchers")
    public List<Watcher> getAllWatchers(){
        return departmentManager.getAllWatchers();
    }

    @PostMapping("/api/watchers")
    @ResponseStatus(HttpStatus.CREATED)
    public int addWatcher(@RequestBody Watcher watcher){
        // add watcher to a course in a department
        watcher.setId(watcherId);
        watcherId++;
        Department department = departmentManager.getDepartmentWithId(watcher.getDeptId());
        if(department == null){
            throw new RESTFileNotFoundException("No department matches id " + watcher.getDeptId());
        }
        Course course = departmentManager.getCourseFromDepartment(watcher.getDeptId(), watcher.getCourseId());
        if(course == null){
            throw new RESTFileNotFoundException("No course matches id " + watcher.getCourseId());
        }
        watcher.setDepartment(department);
        watcher.setCourse(course);
        course.subscribe(watcher);
        departmentManager.getAllWatchers().add(watcher);
        return watcher.getWatcherId();
    }

    @GetMapping("/api/watchers/{id}")
    public List<String> getWatcherEvents(@PathVariable("id") int watcherId){
        for(Watcher watcher : departmentManager.getAllWatchers()){
            if(watcher.getWatcherId() == watcherId){
                return watcher.getEvents();
            }
        }
        throw new RESTFileNotFoundException("No watcher matches id " + watcherId);
    }

    @DeleteMapping("/api/watchers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWatcher(@PathVariable("id") int watcherId){
        Watcher watcherDelete = departmentManager.getWatcherWithId(watcherId);
        if(watcherDelete == null){
            throw new RESTFileNotFoundException("No watcher matches id " + watcherId);
        }
        departmentManager.removeWatcher(watcherDelete);
    }

    // Create Exception Handle
    @ResponseStatus(HttpStatus.NOT_FOUND)
    class RESTFileNotFoundException extends RuntimeException{
        public RESTFileNotFoundException(String message){
            super(message);
        }
    }

    public void setCoursesByDeptId(int deptId){
        for (Department department : departments) {
            if (department.getDeptId() == deptId) {
                coursesForDepartment = department.getCoursesList();
                return;
            }
        }
        throw new RESTFileNotFoundException("No department matches id " + deptId);
    }

    public void setOfferingsByCourseId(int courseId){
        for (Course course : coursesForDepartment) {
            if (course.getCourseId() == courseId) {
                offeringsForCourse = course.getOfferingsList();
                for (Offering offering : offeringsForCourse) {
                    offering.setYear(Offering.getYearFromCode(offering.getSemesterCode()));
                    offering.setTerm(Offering.getTermFromCode(offering.getSemesterCode()));
                }
                return;
            }
        }
        throw new RESTFileNotFoundException("No course matches id " + courseId);
    }

    public void setSectionsByOfferingId(int courseOfferingId){
        for (Offering offering : offeringsForCourse) {
            if (offering.getCourseOfferingId() == courseOfferingId) {
                sectionsForOffering = offering.getSectionsList();
                return;
            }
        }
        throw new RESTFileNotFoundException("No offering matches id " + courseOfferingId);
    }
}
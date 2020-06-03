package ca.cmpt213.courseplanner.model;

import ca.cmpt213.courseplanner.DataParser;

import java.io.IOException;
import java.util.*;

/**
 * DepartmentManager contains methods to manipulate/manage
 * a list of departments. It also has a methods that prints
 * formatted data to the terminal
 */
public class DepartmentManager {
    // key: departmentName, val: Department
    private HashMap<String, Department> departmentDictionary = new HashMap<>();

    private static final DepartmentManager DEPARTMENT_MANAGER = new DepartmentManager();

    private static final List<Watcher> allWatchers = new ArrayList<>();

    public static DepartmentManager getInstance(){
        return DEPARTMENT_MANAGER;
    }

    public HashMap<String, Department> getDepartmentDictionary() {
        return departmentDictionary;
    }

    public void addDepartmentToDictionary(Department department){
        departmentDictionary.put(department.getName(), department);
    }

    public Department getDepartmentFromDictionary(String departmentName){
        return departmentDictionary.get(departmentName);
    }

    public Department getDepartmentWithId(int id){
        for(Department department : departmentDictionary.values()){
            if(department.getDeptId() == id){
                return department;
            }
        }
        return null;
    }

    public boolean hasDepartment(String departmentName){
        return departmentDictionary.containsKey(departmentName);
    }

    public List<Department> getDepartmentsList() {
        List<Department> departmentsList = new ArrayList<>();
        ArrayList<String> sortedDepartmentNames = sortKeys(departmentDictionary.keySet());
        for (String departmentName : sortedDepartmentNames) {
            departmentsList.add(departmentDictionary.get(departmentName));
        }
        return departmentsList;
    }

    public ArrayList<String> sortKeys(Set<String> keys){
        ArrayList<String> result = new ArrayList<>(keys);
        Collections.sort(result);
        return result;
    }

    public Course getCourseFromDepartment(int deptId, int courseId){
        for(Department department : departmentDictionary.values()){
            if(department.getDeptId() == deptId){
                for(Course course : department.getCourseDictionary().values()){
                    if(course.getCourseId() == courseId){
                        return course;
                    }
                }
            }
        }
        return null;
    }

    public List<Watcher> getAllWatchers(){
        return allWatchers;
    }

    public Watcher getWatcherWithId(int watcherId){
        for(Watcher watcher : getAllWatchers()){
            if(watcher.getWatcherId() == watcherId){
                return watcher;
            }
        }
        return null;
    }

    public void removeWatcher(Watcher watcher){
        allWatchers.remove(watcher);
    }

    public void printAllInformation() throws IOException {
        DataParser.parseFile();
        ArrayList<String> sortedDepartmentNames = sortKeys(departmentDictionary.keySet());
        for(String departmentName : sortedDepartmentNames){
            Department department = departmentDictionary.get(departmentName);
            ArrayList<String> sortedCatalogNumbers = sortKeys(department.getCourseDictionary().keySet());
            for(String catalogNumber : sortedCatalogNumbers){
                System.out.println(departmentName + " " + catalogNumber);
                Course course = department.getCourseFromDictionary(catalogNumber);
                ArrayList<String> sortedOfferings = sortKeys(course.getOfferingDictionary().keySet());
                for(String offeringLocation : sortedOfferings){
                    String semester = offeringLocation.substring(0, 4);
                    String location = offeringLocation.substring(4);
                    Offering offering = course.getOfferingFromDictionary(semester, location);
                    System.out.println("\t " + semester + " in " + location + " by " + offering.getInstructors());
                    ArrayList<String> sortedSections = sortKeys(offering.getSectionDictionary().keySet());
                    for(String sectionType : sortedSections){
                        int[] capacityAndTotalEnroll = offering.getSectionSums(sectionType);
                        int totalCapacity = capacityAndTotalEnroll[0];
                        int totalEnrolled = capacityAndTotalEnroll[1];
                        System.out.println("\t\t Type=" + sectionType + ", Enrollment=" + totalEnrolled + "/" + totalCapacity);
                    }
                }
            }
        }
    }
}

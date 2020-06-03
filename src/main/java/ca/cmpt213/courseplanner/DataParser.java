package ca.cmpt213.courseplanner;

import ca.cmpt213.courseplanner.model.*;

import java.io.*;

/**
 * DataParser reads data from CSV file
 * and populates the model
 */
public class DataParser {
    private static final String FILE_PATH = "./data/course_data_2018.csv";
    // initialize ID's
    private static int deptID = 0;
    private static int courseID = 0;
    private static int courseOfferingID = 0;
    private static DepartmentManager departmentManager = DepartmentManager.getInstance();

    public static DepartmentManager getDepartmentManager() {
        return departmentManager;
    }

    // read a text file line by line
    public static DepartmentManager parseFile() throws IOException {
        File courseDataFile = new File(FILE_PATH);
        FileReader reader = new FileReader(courseDataFile);
        BufferedReader br = new BufferedReader(reader);
        String strLine;
        br.readLine();
        //Read File Line By Line
        while ((strLine = br.readLine()) != null)   {
            processLine(strLine);
        }
        //Close the input stream
        reader.close();
        return DepartmentManager.getInstance();
    }

    public static void processLine(String strLine) {
        DepartmentManager departmentManager = DepartmentManager.getInstance();
        String[] array = strLine.split(",");
        String semesterCode = array[0].trim();
        String deptName = array[1].trim();
        if(deptName.equals(""))
            return;
        String catalogNumber = array[2].trim();
        String location = array[3].trim();
        int enrollmentCap = Integer.parseInt(array[4]);
        int enrollmentTotal = Integer.parseInt(array[5]);
        StringBuilder instructors = new StringBuilder();
        String type = "";
        int instructorStartIndex = 6;
        if(array[instructorStartIndex].contains("\"")){
            // multiple instructors
            instructors.append(array[instructorStartIndex].trim()).append(",");
            int i = instructorStartIndex + 1;
            do{
                instructors.append(array[i]).append(",");
                i++;
            } while(!array[i-1].contains("\""));
            type = array[i].trim();
        }else{
            instructors = new StringBuilder(array[instructorStartIndex]);
            type = array[instructorStartIndex+1].trim();
        }
        String instructorParsed = instructors.toString().trim();
        //remove " at the beginning and at the end in the records that have multiple instructors
        instructorParsed = instructorParsed.replaceAll("\"", "");
        // delete trailing comma in the records that have multiple instructors
        if(instructorParsed.charAt(instructorParsed.length() - 1) == ','){
            instructorParsed = instructorParsed.substring(0, instructorParsed.length()-1);
            instructorParsed.trim();
        }
        // POPULATE MODELS
        if(!departmentManager.hasDepartment(deptName)){
            departmentManager.addDepartmentToDictionary(new Department(deptID, deptName));
            deptID++;
        }
        Department department = departmentManager.getDepartmentFromDictionary(deptName);
        if(!department.hasCourse(catalogNumber)){
            department.addCourseToDictionary(new Course(courseID, catalogNumber));
            courseID++;
        }
        Course course = department.getCourseFromDictionary(catalogNumber);
        if(!course.hasOfferingInDictionary(semesterCode, location)){
            course.addOfferingToDictionary(new Offering(courseOfferingID, semesterCode, location, instructorParsed));
            courseOfferingID++;
        }
        Offering offering = course.getOfferingFromDictionary(semesterCode, location);
        // add instructor if different for a same course offering
        if(!offering.getInstructors().contains(instructorParsed)){
            String repeatedInstructor = ", " + offering.getInstructors();
            if(instructorParsed.contains(repeatedInstructor)){
                instructorParsed = instructorParsed.replaceAll( repeatedInstructor, "");
            }
            offering.appendInstructor(instructorParsed);
        }
        if(!offering.hasSectionInDictionary(type)){
            offering.addSectionToDictionary(type);
        }
        offering.updateSection(type, enrollmentCap, enrollmentTotal);
    }
}

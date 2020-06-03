package ca.cmpt213.courseplanner.model;

import java.util.*;

/**
 * Offering class stores the information about single offering.
 * It also has methods to manipulate its section list.
 */
public class Offering {
    private int courseOfferingId;
    private String semesterCode;
    private String location;
    private String instructors;
    // key: SECTION TYPE      val: [SumCapacity, SumEnrollment]
    private HashMap<String, Section> sectionDictionary = new HashMap<>();
    private int year = 0;
    private String term = null;

    public Offering(int courseOfferingId, String semesterCode, String location, String instructors) {
        this.courseOfferingId = courseOfferingId;
        this.semesterCode = semesterCode;
        this.location = location;
        this.instructors = instructors;
    }

    public int getCourseOfferingId() {
        return courseOfferingId;
    }

    public String getLocation() {
        return location;
    }

    public String getInstructors() {
        return instructors;
    }

    public void appendInstructor(String instructors){
        this.instructors += ", " + instructors;
    }

    public String getSemesterCode() {
        return semesterCode;
    }

    public int getYear() {
        return year;
    }

    public String getTerm() {
        return term;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public HashMap<String, Section> getSectionDictionary(){
        return sectionDictionary;
    }

    public boolean hasSectionInDictionary(String sectionType){
        return sectionDictionary.containsKey(sectionType);
    }

    public void addSectionToDictionary(String sectionType){
        Section section = new Section(sectionType);
        sectionDictionary.put(sectionType, section);
    }

    public void updateSection(String sectionType, int capacity, int enrollmentTotal){
        Section section = sectionDictionary.get(sectionType);
        section.setEnrollmentCap(section.getEnrollmentCap() + capacity);
        section.setEnrollmentTotal(section.getEnrollmentTotal() + enrollmentTotal);
    }

    public int[] getSectionSums(String sectionType){
        int[] sectionSums = {sectionDictionary.get(sectionType).getEnrollmentCap(), sectionDictionary.get(sectionType).getEnrollmentTotal()};
        return sectionSums;
    }

    public static int getYearFromCode(String semesterCode){
        int yearStr = 1900
                + 100 * Character.getNumericValue(semesterCode.charAt(0))
                + 10 * Character.getNumericValue(semesterCode.charAt(1))
                + Character.getNumericValue(semesterCode.charAt(2));
        return yearStr;
    }

    public static String getTermFromCode(String semesterCode){
        String term;
        int termNumber = Integer.parseInt(String.valueOf(semesterCode.charAt(3)));
        if(termNumber == 1){
            term = "Spring";
        }
        else if(termNumber == 4){
            term = "Summer";
        }
        else if(termNumber == 7){
            term = "Fall";
        }
        else{
            term = "Wrong term number";
        }
        return term;
    }

    public List<Section> getSectionsList() {
        List<Section> sectionsList = new ArrayList<>();
        ArrayList<String> sortedOfferingTypes = sortKeys(sectionDictionary.keySet());
        for (String offeringType : sortedOfferingTypes) {
            sectionsList.add(sectionDictionary.get(offeringType));
        }
        return sectionsList;
    }

    public ArrayList<String> sortKeys(Set<String> keys){
        ArrayList<String> result = new ArrayList<>(keys);
        Collections.sort(result);
        return result;
    }

    @Override
    public String toString() {
        return "Offering{" +
                "courseOfferingID=" + courseOfferingId +
                ", location='" + location + '\'' +
                ", instructors=" + instructors +
                ", semesterCode=" + semesterCode +
                '}';
    }
}

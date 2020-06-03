package ca.cmpt213.courseplanner.model;

import org.springframework.web.bind.annotation.RequestBody;

/**
 * NewOffering stores the info of the offering
 * being added to the application
 */
public class NewOffering {
    private int semester;
    private String subjectName;
    private String catalogNumber;
    private String location;
    private int enrollmentCap;
    private String component;
    private int enrollmentTotal;
    private String instructor;

    public NewOffering(int semester, String subjectName, String catalogNumber, String location, int enrollmentCap, String component, int enrollmentTotal, String instructor) {
        this.semester = semester;
        this.subjectName = subjectName;
        this.catalogNumber = catalogNumber;
        this.location = location;
        this.enrollmentCap = enrollmentCap;
        this.component = component;
        this.enrollmentTotal = enrollmentTotal;
        this.instructor = instructor;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public void setCatalogNumber(String catalogNumber) {
        this.catalogNumber = catalogNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getEnrollmentCap() {
        return enrollmentCap;
    }

    public void setEnrollmentCap(int enrollmentCap) {
        this.enrollmentCap = enrollmentCap;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public int getEnrollmentTotal() {
        return enrollmentTotal;
    }

    public void setEnrollmentTotal(int enrollmentTotal) {
        this.enrollmentTotal = enrollmentTotal;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    @Override
    public String toString() {
        return "NewOffering{" +
                "semester=" + semester +
                ", subjectName='" + subjectName + '\'' +
                ", catalogNumber='" + catalogNumber + '\'' +
                ", location='" + location + '\'' +
                ", enrollmentCap=" + enrollmentCap +
                ", component='" + component + '\'' +
                ", enrollmentTotal=" + enrollmentTotal +
                ", instructor='" + instructor + '\'' +
                '}';
    }
}

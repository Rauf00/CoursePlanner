package ca.cmpt213.courseplanner.model;

/**
 * Section class stores the info about
 * single section
 */
public class Section {
    private String type;
    private int enrollmentCap;
    private int enrollmentTotal;

    public Section(String type){
        this.type = type;
        this.enrollmentTotal = 0;
        this.enrollmentCap = 0;
    }

    public String getType() {
        return type;
    }

    public int getEnrollmentCap() {
        return enrollmentCap;
    }

    public int getEnrollmentTotal() {
        return enrollmentTotal;
    }

    public void setEnrollmentCap(int enrollmentCap) {
        this.enrollmentCap = enrollmentCap;
    }

    public void setEnrollmentTotal(int enrollmentTotal) {
        this.enrollmentTotal = enrollmentTotal;
    }

    @Override
    public String toString() {
        return "Section{" +
                "type='" + type + '\'' +
                ", enrollmentTotal=" + enrollmentTotal +
                ", enrollmentCap=" + enrollmentCap +
                '}';
    }
}

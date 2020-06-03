package ca.cmpt213.courseplanner.model;

import java.util.*;

/**
 * Course class stores the information about single course.
 * It also has methods to manipulate its offerings list.
 */
public class Course implements Subject{
    private int courseId;
    private String catalogNumber;
    // the key is combination of: semesterCode+location    val: Offering
    private HashMap<String, Offering> offeringDictionary = new HashMap<>();
    private List<Watcher> watchers = new ArrayList<>();

    public Course(int courseId, String catalogNumber) {
        this.courseId = courseId;
        this.catalogNumber = catalogNumber;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public HashMap<String, Offering> getOfferingDictionary() {
        return offeringDictionary;
    }

    public void addOfferingToDictionary(Offering offering) {
        offeringDictionary.put(offering.getSemesterCode()+offering.getLocation(), offering);
    }

    public Offering getOfferingFromDictionary(String semesterCode, String location){
        return offeringDictionary.get(semesterCode+location);
    }

    public boolean hasOfferingInDictionary(String semesterCode, String location){
        return offeringDictionary.containsKey(semesterCode+location);
    }

    public List<Offering> getOfferingsList() {
        List<Offering> offeringsList = new ArrayList<>();
        ArrayList<String> sortedOfferingsNames = sortKeys(offeringDictionary.keySet());
        for (String departmentName : sortedOfferingsNames) {
            offeringsList.add(offeringDictionary.get(departmentName));
        }
        return offeringsList;
    }

    public ArrayList<String> sortKeys(Set<String> keys){
        ArrayList<String> result = new ArrayList<>(keys);
        Collections.sort(result);
        return result;
    }

    @Override
    public void subscribe(Watcher watcher) {
        watchers.add(watcher);
    }

    @Override
    public void unsubscribe(Watcher watcher) {
        watchers.remove(watcher);
    }

    @Override
    public void notifyObservers(String event) {
        long millis=System.currentTimeMillis();
        java.util.Date date = new java.util.Date(millis);

        for(Watcher watcher : watchers){
            watcher.update(date + " : " + event);
        }
    }
}

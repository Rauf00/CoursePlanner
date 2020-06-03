package ca.cmpt213.courseplanner.model;

/**
 * Observer interface is used to
 * implement the Observer Pattern
 */
public interface Subject {
    public void subscribe(Watcher watcher);
    public void unsubscribe(Watcher watcher);
    public void notifyObservers(String message);

}

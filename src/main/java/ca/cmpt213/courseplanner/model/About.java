package ca.cmpt213.courseplanner.model;

/**
 * About stores the info about the authors
 * of the application, as well as the name of the app
 */
public class About {
    private String appName;
    private String authorName;

    public About(String appName, String authorName) {
        this.appName = appName;
        this.authorName = authorName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}

package ma.gi.pfa;

public class Theme {
    private String title;
    private String description;
    private String NBE;
    private String encadrant;
    private String id;

    private boolean isChecked;

    public Theme() {
    }

    public Theme (String id,String title, String description, String NBE, String encadrant) {
        this.id = id;
        this.title =title;
        this.description= description;
        this.NBE= NBE;
        this.encadrant= encadrant;

    }

    public String getTitle() {
        return title;
    }

    public void setIntitule(String intitule) {
        this.title = intitule;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNBE() {
        return NBE;
    }

    public void setNBE(String NBE) {
        this.NBE = NBE;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public String getEncadrant() {
        return encadrant;
    }

    public void setEncadrant(String encadrant) {
        this.encadrant = encadrant;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}

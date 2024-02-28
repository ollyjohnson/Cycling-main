package cycling;
public class Race {
    private int id;
    private String name;
    private String description;

    public Race(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        // Assuming stages and race_length are intended to be part of this class, declare and initialize them properly.
        // For demonstration, removing the reference to undeclared variables.
        return("Team id = " + id + " name="+name+" description="+description);
    }

    public int getRaceId() {
        return id;
    }

    public String getRaceName() {
        return name;
    }
}



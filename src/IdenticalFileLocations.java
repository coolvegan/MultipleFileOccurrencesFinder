import java.util.ArrayList;

public class IdenticalFileLocations {
    private ArrayList<Entities> entities;

    private String hash = "";

    public String getHash() {
        return hash;
    }

    public IdenticalFileLocations(String hash) {
        this.hash = hash;
        entities = new ArrayList<>();
    }

    public ArrayList<Entities> getEntities() {
        return entities;
    }

    public boolean hasManyChildren()
    {
        return entities.size() > 1;
    }

    public void addPath(String path)
    {
        entities.add(new Entities(path));
    }
}

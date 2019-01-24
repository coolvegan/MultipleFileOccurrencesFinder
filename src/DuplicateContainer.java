import java.util.ArrayList;
import java.util.Collections;

public class DuplicateContainer {

    ArrayList<IdenticalFileLocations> identicalFiles;

    DuplicateContainer(ArrayList<IdenticalFileLocations> identicalFileLocationsList)
    {
        identicalFiles = identicalFileLocationsList;
    }

    private int hasHash(String hash)
    {
        for (int i = 0; i < identicalFiles.size() ; i++) {
            if (hash.equals(identicalFiles.get(i).getHash()))
            {
                return i;
            }
        }
        return -1;
    }

    public void addHash(String hash, String url)
    {
        int insert_position;
        if ((insert_position = (hasHash(hash))) == -1)
        {
            IdenticalFileLocations identifier = new IdenticalFileLocations(hash);
            identifier.addPath(url);
            identicalFiles.add(identifier);
        }
        else
        {
            identicalFiles.get(insert_position).addPath(url);
        }
    }

    public void removeUniqueItems()
    {
        ArrayList<IdenticalFileLocations> duplicates = new ArrayList<>();
        for (int i = 0; i < identicalFiles.size() ; i++) {
            if (identicalFiles.get(i).hasManyChildren())
            {
                duplicates.add(identicalFiles.get(i));
            }
        }
        identicalFiles.clear();
        identicalFiles.addAll(duplicates);
    }
}

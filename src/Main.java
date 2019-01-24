import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        ArrayList<IdenticalFileLocations> identicalFileLocations = new ArrayList();
        DuplicateContainer duplicateContainer = new DuplicateContainer(identicalFileLocations);
        FilesystemScanner filesystemScanner = new FilesystemScanner(duplicateContainer);
        filesystemScanner.observeInfix(".jpg");
        filesystemScanner.observeInfix(".gif");
        filesystemScanner.observeInfix(".png");
        filesystemScanner.ignoreInfix("/home/marco/.");
        filesystemScanner.ignoreInfix("/home/marco/AndroidStudioProjects/");
        filesystemScanner.recursiveScan("/home/marco/Verkauf");
        duplicateContainer.removeUniqueItems();

        for (int i = 0; i < identicalFileLocations.size(); i++) {
            System.out.println(identicalFileLocations.get(i).getHash());
            for (int j = 0; j < identicalFileLocations.get(i).getEntities().size(); j++) {
                System.out.println(identicalFileLocations.get(i).getEntities().get(j).getPath());
            }
        }
    }
}

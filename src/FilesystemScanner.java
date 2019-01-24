import java.io.IOException;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class FilesystemScanner {
    private DuplicateContainer duplicateContainer;
    private ArrayList<String> ignoreList;
    private ArrayList<String> extList;

    FilesystemScanner(DuplicateContainer duplicateContainer) {
        this.duplicateContainer = duplicateContainer;
        ignoreList = new ArrayList<>();
        extList = new ArrayList<>();
    }

    public void observeInfix(String extention)
    {
        extList.add(extention);
    }

    public boolean hasExtention(String source)
    {
        for (int i = 0; i < extList.size(); i++) {
            if (source.contains(extList.get(i)))
            {
                return true;
            }
        }
        return false;
    }

    void ignoreInfix(String directory)
    {
        ignoreList.add(directory);
    }

    private boolean isIgnored(String directory)
    {
        for (int i = 0; i < ignoreList.size(); i++) {
            if (directory.contains(ignoreList.get(i)))
            {
                return true;
            }
        }
        return false;
    }

    public void recursiveScan(String dir) throws IOException, NoSuchAlgorithmException {
        Path path = Paths.get(dir);
        if (Files.exists(path)) {
            DirectoryStream<Path> stream = Files.newDirectoryStream(path);
            for (Path entry : stream) {
                recursiveDegression(entry);

                if (Files.isRegularFile(entry))
                {
                    if (hasExtention(entry.toString())) {
                        linkFileToHash(entry);
                    }
                }
            }
            stream.close();
        }
    }

    private void linkFileToHash(Path entry) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(Files.readAllBytes(entry));
        byte[] digest = md.digest();

        BinaryToHexConverter printHexBinary = new BinaryToHexConverter();
        String digestInHex = printHexBinary.getHexString(digest);

        if (!isIgnored(entry.toString())) {
            duplicateContainer.addHash(digestInHex, entry.toString());
        }
    }

    private void recursiveDegression(Path entry) throws IOException, NoSuchAlgorithmException {
        if (Files.isDirectory(entry)) {
            try {
                if (!isIgnored(entry.toString())) {
                    recursiveScan(entry.toString());
                }
            } catch (AccessDeniedException x) {
                System.out.println("Ignoriere: " + x);
            }
        }
    }

}

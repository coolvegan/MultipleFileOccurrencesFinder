import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Main
{
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException
	{
		List<IdenticalFileLocations> identicalFileLocations = new ArrayList<IdenticalFileLocations>();
		DuplicateContainer duplicateContainer = new DuplicateContainer(identicalFileLocations);
		FilesystemScanner filesystemScanner = new FilesystemScanner(duplicateContainer);
		// TODO: wenn observeInfix() und ignoreInfix() varargs bekämen, könntest du dir die Zeilen sparen
		// TODO: "Infix" finde ich etwas verwirrend, da denke ich als Informatiker eher an die Infix-Notation
		filesystemScanner.observeInfix(".jpg");
		filesystemScanner.observeInfix(".gif");
		filesystemScanner.observeInfix(".png");
		filesystemScanner.ignoreInfix("/home/marco/.");
		filesystemScanner.ignoreInfix("/home/marco/AndroidStudioProjects/");
		filesystemScanner.recursiveScan("/home/marco/Verkauf");
		duplicateContainer.removeUniqueItems();

		// TODO: warum nicht mit enhanced for oder Stream?
		for (int i = 0; i < identicalFileLocations.size(); i++)
		{
			System.out.println(identicalFileLocations.get(i).getHash());
			for (int j = 0; j < identicalFileLocations.get(i).getEntities().size(); j++)
			{
				System.out.println(identicalFileLocations.get(i).getEntities().get(j).getPath());
			}
		}
	}
}

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class FilesystemScanner
{
	private DuplicateContainer duplicateContainer;
	private List<String> ignoreList;
	private List<String> extensionList;

	FilesystemScanner(DuplicateContainer duplicateContainer)
	{
		this.duplicateContainer = duplicateContainer;
		ignoreList = new ArrayList<>();
		extensionList = new ArrayList<>();
	}

	public void observeInfix(String extention)
	{
		extensionList.add(extention);
	}

	public boolean hasExtension(String source)
	{
		return searchThroughList(source,extensionList);
	}

	private boolean searchThroughList(String source, List<String> searchlist) {
		for (int i = 0; i < searchlist.size(); i++)
		{
			if (source.contains(searchlist.get(i)))
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
		return searchThroughList(directory, ignoreList);
	}

	public void recursiveScan(String dir) throws IOException, NoSuchAlgorithmException
	{
		Path path = Paths.get(dir);
		// TODO: umstellen auf Guard Clause
		if (!Files.exists(path)) {
			return;
		}
			DirectoryStream<Path> stream = Files.newDirectoryStream(path);
			for (Path entry : stream)
			{
				recursiveDegression(entry);

				if ((Files.isRegularFile(entry)) && (hasExtension(entry.toString())))
				{
					linkFileToHash(entry);
				}
			}
			// TODO: umstellen auf try-with-resources
			stream.close();
	}

	private void linkFileToHash(Path entry) throws NoSuchAlgorithmException, IOException
	{
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(Files.readAllBytes(entry));
		byte[] digest = md.digest();

		BinaryToHexConverter printHexBinary = new BinaryToHexConverter();
		String digestInHex = printHexBinary.getHexString(digest);

		if (!isIgnored(entry.toString()))
		{
			duplicateContainer.addHash(digestInHex, entry.toString());
		}
	}

	private void recursiveDegression(Path entry) throws IOException, NoSuchAlgorithmException
	{
		if (!Files.isDirectory(entry)) {
			return;
		}
		try
		{
			if (isIgnored(entry.toString())) {
				return;
			}

			recursiveScan(entry.toString());
		}
		catch (AccessDeniedException x)
		{
			System.out.println("Ignoriere: " + x);
		}
	}

}

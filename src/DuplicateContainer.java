import java.util.List;
import java.util.ArrayList;

public class DuplicateContainer
{
	private List<IdenticalFileLocations> identicalFiles;
	private int insertPosition;

	DuplicateContainer(List<IdenticalFileLocations> identicalFileLocationsList)
	{
		identicalFiles = identicalFileLocationsList;
	}

	private int getHashPosition(String hash)
	{
		for (int i = 0; i < identicalFiles.size(); i++)
		{
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
		if ((insert_position = getHashPosition(hash)) == -1)
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
		List<IdenticalFileLocations> duplicates = new ArrayList<IdenticalFileLocations>();
		for (int i = 0; i < identicalFiles.size(); i++)
		{
			if (identicalFiles.get(i).hasManyChildren())
			{
				duplicates.add(identicalFiles.get(i));
			}
		}
		identicalFiles.clear();
		identicalFiles.addAll(duplicates);
	}
}

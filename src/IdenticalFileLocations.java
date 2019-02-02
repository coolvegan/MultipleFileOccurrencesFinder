import java.util.ArrayList;
import java.util.List;

public class IdenticalFileLocations
{
	private List<Entities> entities;

	private String hash = "";

	public String getHash()
	{
		return hash;
	}

	public IdenticalFileLocations(String hash)
	{
		this.hash = hash;
		entities = new ArrayList<Entities>();
	}

	public List<Entities> getEntities()
	{
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

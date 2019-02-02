public class BinaryToHexConverter
{
	private char array[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	private String OctetToString(byte number)
	{
		int low = number & 0x0F;
		int high = number & 0xF0;
		high = high >> 4;
		String result = String.valueOf(array[high]);
		return result += String.valueOf(array[low]);
	}

	public String getHexString(byte[] b)
	{
		StringBuilder result = new StringBuilder();
		for (byte element : b)
		{
			result.append(OctetToString(element));
		}
		return result.toString();
	}
}

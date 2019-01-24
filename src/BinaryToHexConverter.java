public class BinaryToHexConverter {
    private char array[] = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
    private String OctetToString(byte number)
    {
        int low = (number & 0x0F);
        int high = (number & 0xF0);
        high = high >> 4;
        String result;
        result = String.valueOf(array[high]);
        result += String.valueOf(array[low]);
        return result;
    }

    public String getHexString(byte[] b)
    {
        String result = "";
        for (int i = 0; i < b.length; i++) {
            byte tmp;
            tmp = b[i];
            result += OctetToString(tmp);
        }
        return result;
    }
}

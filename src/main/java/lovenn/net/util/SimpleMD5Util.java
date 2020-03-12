package lovenn.net.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SimpleMD5Util {

    public static String encrypt(String dataStr) {
        if(dataStr == null) return null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(dataStr.getBytes());
            return byte2Hex(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String byte2Hex(byte[] bytes) {
        if(bytes == null || bytes.length == 0) return null;
        StringBuilder builder = new StringBuilder("");
        for (byte b: bytes) {
            int v = b & 0x000000FF;
            String hv = Integer.toHexString(v);
            if(hv.length() < 2) {
                builder.append("0");
            }
            builder.append(hv);
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        String name = "xiahongyun";
        encrypt(name);
    }
}

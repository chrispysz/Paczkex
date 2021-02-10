import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class used for hashing the password with MD5 algorithm.
 */
public class Hasher {

    /**
     *
     * @param pass Not yet hashed password.
     * @return Hashed password.
     * @throws NoSuchAlgorithmException If wrong algorithm was used in MessageDigest.getInstance().
     */
    public static String hashMD5 (String pass) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(pass.getBytes());
        byte[] digest = md.digest();
        return Hex.encodeHexString(digest);
    }
}

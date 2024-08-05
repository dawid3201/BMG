package BMG.BookManamgnet.Security;

import java.security.SecureRandom;

public class SecretKeyGenerator {
    public byte[] generateKey(int keyLength) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[keyLength];
        random.nextBytes(key);
        return key;
    }
}

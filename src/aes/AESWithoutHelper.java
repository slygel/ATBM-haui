package aes;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESWithoutHelper {

    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY = "mySecretKey12345"; // Key phải có độ dài 16, 24 hoặc 32 bytes

    public static byte[] encrypt(String plainText, String secretKey) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        return cipher.doFinal(plainText.getBytes());
    }

    public static String decrypt(byte[] cipherText, String secretKey) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decryptedBytes = cipher.doFinal(cipherText);
        return new String(decryptedBytes);
    }

    public static void main(String[] args) throws Exception {
        String originalText = "Hello, AES!";
        System.out.println("Original Text: " + originalText);

        byte[] encryptedBytes = encrypt(originalText, SECRET_KEY);
        String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
        System.out.println("Encrypted Text: " + encryptedText);

        byte[] decodedEncryptedText = Base64.getDecoder().decode(encryptedText);
        String decryptedText = decrypt(decodedEncryptedText, SECRET_KEY);
        System.out.println("Decrypted Text: " + decryptedText);
    }
}


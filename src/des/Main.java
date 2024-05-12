package des;

public class Main {
	public static void main(String[] args) {
        try {
            // Khóa và văn bản ban đầu
            String key = "133457799BBCDFF1";
            StringBuilder plaintext = new StringBuilder("B");

            // Mã hóa
            DesAlgorithm desEncryptor = new DesAlgorithm(plaintext, key);
            String encryptedText = desEncryptor.encrypt();
            System.out.println("Encrypted Text: " + encryptedText);

            // Giải mã
            DesAlgorithm desDecryptor = new DesAlgorithm(new StringBuilder(encryptedText), key);
            String decryptedText = desDecryptor.decrypt();
            System.out.println("Decrypted Text: " + decryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

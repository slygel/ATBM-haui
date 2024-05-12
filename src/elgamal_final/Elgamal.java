package elgamal_final;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Elgamal {
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the prime number p: ");
        BigInteger p = scanner.nextBigInteger();

        BigInteger a = new BigInteger("5");

        // Chọn số nguyên tố x sao cho 1 < x < p - 1
        BigInteger x = new BigInteger("58");

        // Tính y = a^x mod p
        BigInteger y = a.modPow(x, p);

        // In ra khóa công khai và khóa bí mật
        System.out.println("Public Key (p, a, y): (" + p + ", " + a + ", " + y + ")");
        System.out.println("Private Key (p, a, x): (" + p + ", " + a + ", " + x + ")");

        // Mã hóa
        System.out.print("Enter the text to encrypt: ");
        String originalText = scanner.next();

        // Mã hóa văn bản
        ElGamalCipher[] ciphers = encrypt(originalText, p, a, y);
        System.out.print("Encrypted Text: ");
        for (ElGamalCipher cipher : ciphers) {
            System.out.print("(" + cipher.getC1() + ", " + cipher.getC2() + ") ");
        }
        System.out.println();

        // Giải mã
        String decryptedText = decrypt(ciphers, x, p);
        System.out.println("Decrypted Text: " + decryptedText);

        scanner.close();
    }

    // Mã hóa
    private static ElGamalCipher[] encrypt(String plaintext, BigInteger p, BigInteger a, BigInteger y) {
        int blockSize = 1; // Kích thước của mỗi khối (đơn vị ký tự), bạn có thể điều chỉnh theo nhu cầu

        // Tạo mảng các khối dữ liệu
        String[] blocks = new String[(int) Math.ceil((double) plaintext.length() / blockSize)];
        for (int i = 0; i < blocks.length; i++) {
            int startIndex = i * blockSize;
            int endIndex = Math.min(startIndex + blockSize, plaintext.length());
            blocks[i] = plaintext.substring(startIndex, endIndex);
        }

        // Mã hóa mỗi khối
        ElGamalCipher[] ciphers = new ElGamalCipher[blocks.length];
        for (int i = 0; i < blocks.length; i++) {
//            byte[] blockBytes = blocks[i].getBytes();
        	byte[] blockBytes = blocks[i].getBytes(StandardCharsets.UTF_8);
        	
            BigInteger blockBigInteger = new BigInteger(blockBytes);

            // Chọn số ngẫu nhiên k sao cho 1 < k < p - 1
            BigInteger k = new BigInteger("36");

            // Tính c1 = a^k mod p
            BigInteger c1 = a.modPow(k, p);

            // Tính block * y^k mod p
            BigInteger yPowK = y.modPow(k, p);
            BigInteger c2 = blockBigInteger.multiply(yPowK).mod(p);

            ciphers[i] = new ElGamalCipher(c1, c2);
        }

        return ciphers;
    }

    // Giải mã
    private static String decrypt(ElGamalCipher[] ciphers, BigInteger x, BigInteger p) {
    	StringBuilder plaintextBuilder = new StringBuilder();

        // Duyệt qua từng bộ mã hóa và giải mã
        for (ElGamalCipher cipher : ciphers) {
            // Tính m = c1^x mod p
            BigInteger m = cipher.getC1().modPow(x, p);

            // Tính m^-1 mod p
            BigInteger mInverse = m.modInverse(p);

            // Tính plaintext = c2 * m^-1 mod p
            BigInteger decryptedBlock = cipher.getC2().multiply(mInverse).mod(p);

            // Chuyển đổi plaintext từ số nguyên sang mảng byte và sau đó sang chuỗi
//            byte[] blockBytes = decryptedBlock.toByteArray();
//            String blockPlaintext = new String(blockBytes);
            
            String blockPlaintext = new String(decryptedBlock.toByteArray(), StandardCharsets.UTF_8);

            // Thêm plaintext của khối này vào chuỗi kết quả
            plaintextBuilder.append(blockPlaintext);
        }

        return plaintextBuilder.toString();
    }
}

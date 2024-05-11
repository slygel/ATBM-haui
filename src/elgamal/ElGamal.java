package elgamal;

import java.math.BigInteger;
import java.security.SecureRandom;

public class ElGamal {
	private static final int BIT_LENGTH = 1024;

    public static void main(String[] args) {
        // Khởi tạo số nguyên tố p và g
        BigInteger p = generateRandomPrime();
        BigInteger g = generateGenerator(p);

        // Chọn số nguyên tố x sao cho 1 < x < p - 1
        BigInteger x = generatePrivateKey(p);

        // Tính y = g^x mod p
        BigInteger y = g.modPow(x, p);

        // In ra khóa công khai và khóa bí mật
        System.out.println("Public Key (p, g, y): (" + p + ", " + g + ", " + y + ")");
        System.out.println("Private Key (x): " + x);

        // Thực hiện mã hóa và giải mã
        String originalText = "Hello, ElGamal!";
        System.out.println("Original Text: " + originalText);

        // Mã hóa văn bản
        ElGamalCipher cipher = encrypt(originalText, p, g, y);
        System.out.println("Encrypted Text: (" + cipher.getC1() + ", " + cipher.getC2() + ")");

        // Giải mã văn bản
        String decryptedText = decrypt(cipher, x, p);
        System.out.println("Decrypted Text: " + decryptedText);
    }

    // Hàm tạo số nguyên tố ngẫu nhiên
    private static BigInteger generateRandomPrime() {
        return BigInteger.probablePrime(BIT_LENGTH, new SecureRandom());
    }

    // Hàm tìm generator (g) trong mod p
    private static BigInteger generateGenerator(BigInteger p) {
        // Tìm một số nguyên dương g < p - 1 sao cho g^((p-1)/2) mod p != 1
        BigInteger g;
        do {
            g = new BigInteger(p.bitLength(), new SecureRandom());
        } while (g.compareTo(BigInteger.ONE) <= 0 || g.compareTo(p.subtract(BigInteger.ONE)) >= 0 || !isGenerator(g, p));
        return g;
    }

    // Hàm kiểm tra xem một số là generator của mod p hay không
    private static boolean isGenerator(BigInteger g, BigInteger p) {
        // Nếu g^((p-1)/2) mod p != 1, thì g là generator của mod p
        BigInteger exponent = p.subtract(BigInteger.ONE).divide(BigInteger.valueOf(2));
        BigInteger result = g.modPow(exponent, p);
        return result.equals(BigInteger.ONE);
    }

    // Hàm chọn số nguyên tố x làm private key
    private static BigInteger generatePrivateKey(BigInteger p) {
        // Chọn số nguyên tố x sao cho 1 < x < p - 1
        BigInteger x;
        do {
            x = new BigInteger(p.bitLength(), new SecureRandom());
        } while (x.compareTo(BigInteger.ONE) <= 0 || x.compareTo(p.subtract(BigInteger.ONE)) >= 0);
        return x;
    }

    // Hàm mã hóa văn bản
    private static ElGamalCipher encrypt(String plaintext, BigInteger p, BigInteger g, BigInteger y) {
        SecureRandom random = new SecureRandom();
        byte[] plaintextBytes = plaintext.getBytes();
        BigInteger plaintextBigInteger = new BigInteger(plaintextBytes);

        // Chọn số ngẫu nhiên k sao cho 1 < k < p - 1
        BigInteger k;
        do {
            k = new BigInteger(p.bitLength(), random);
        } while (k.compareTo(BigInteger.ONE) <= 0 || k.compareTo(p.subtract(BigInteger.ONE)) >= 0);

        // Tính c1 = g^k mod p
        BigInteger c1 = g.modPow(k, p);

        // Tính c2 = (plaintext * y^k) mod p
        BigInteger c2 = plaintextBigInteger.multiply(y.modPow(k, p)).mod(p);

        return new ElGamalCipher(c1, c2);
    }

    // Hàm giải mã văn bản
    private static String decrypt(ElGamalCipher cipher, BigInteger x, BigInteger p) {
        // Tính plaintext = (c2 * c1^(-x)) mod p
        BigInteger plaintextBigInteger = cipher.getC2().multiply(cipher.getC1().modPow(x.negate(), p)).mod(p);
        return new String(plaintextBigInteger.toByteArray());
    }
}

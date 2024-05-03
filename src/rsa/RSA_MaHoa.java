package rsa;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA_MaHoa {

    private static final int BIT_LENGTH = 1024;

    public static void main(String[] args) {
        // Khởi tạo số nguyên tố p và q
        BigInteger p = generateRandomPrime();
        BigInteger q = generateRandomPrime();

        // Tính n = p * q
        BigInteger n = p.multiply(q);

        // Tính ϕ(n)
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        // Chọn số nguyên e sao cho 1 < e < ϕ(n) và gcd(e, ϕ(n)) = 1
        BigInteger e = choosePublicExponent(phi);

        // Tính d sao cho (d * e) % ϕ(n) = 1
        BigInteger d = e.modInverse(phi);

        // Khóa công khai: (e, n)
        // Khóa bí mật: (d, n)

        // Thử nghiệm mã hóa và giải mã
        String originalText = "Hello, RSA!";
        System.out.println("Original Text: " + originalText);

        BigInteger encryptedText = encrypt(originalText, e, n);
        System.out.println("Encrypted Text: " + encryptedText);

        String decryptedText = decrypt(encryptedText, d, n);
        System.out.println("Decrypted Text: " + decryptedText);
    }

    // Hàm tạo số nguyên tố ngẫu nhiên
    private static BigInteger generateRandomPrime() {
        return BigInteger.probablePrime(BIT_LENGTH, new SecureRandom());
    }

    // Hàm chọn số nguyên e là số nguyên tố cùng nhau với ϕ(n)
    private static BigInteger choosePublicExponent(BigInteger phi) {
        BigInteger e = BigInteger.valueOf(65537); // Số nguyên tố thường được chọn
        while (!phi.gcd(e).equals(BigInteger.ONE)) {
            e = e.add(BigInteger.ONE);
        }
        return e;
    }

    // Hàm mã hóa RSA
    private static BigInteger encrypt(String plaintext, BigInteger e, BigInteger n) {
        byte[] bytes = plaintext.getBytes();
        BigInteger message = new BigInteger(bytes);
        return message.modPow(e, n);
    }

    // Hàm giải mã RSA
    private static String decrypt(BigInteger ciphertext, BigInteger d, BigInteger n) {
        BigInteger decrypted = ciphertext.modPow(d, n);
        return new String(decrypted.toByteArray());
    }
}


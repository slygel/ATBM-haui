package rsa;
import java.math.BigInteger;
import java.security.SecureRandom;

public class RSAPrimitive {

    private static final int BIT_LENGTH = 1024;

    public static void main(String[] args) {
        // Khởi tạo số nguyên tố p và q
        BigInteger p = generateRandomPrime();
        BigInteger q = generateRandomPrime();

        // Tính n = p * q
        BigInteger n = p.multiply(q);

        // Tính ϕ(n)
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        // Chọn số nguyên tố e sao cho 1 < e < ϕ(n) và gcd(e, ϕ(n)) = 1
        BigInteger e = choosePublicExponent(phi);

        // Tính d sao cho (d * e) % ϕ(n) = 1
        BigInteger d = calculatePrivateExponent(e, phi);

        // In ra khóa công khai và khóa bí mật
        System.out.println("Public Key (e, n): (" + e + ", " + n + ")");
        System.out.println("Private Key (d, n): (" + d + ", " + n + ")");

        // Thử nghiệm mã hóa và giải mã
        String originalText = "Hello, RSA!";
        System.out.println("Original Text: " + originalText);

        // Mã hóa văn bản
        BigInteger encryptedText = encrypt(originalText, e, n);
        System.out.println("Encrypted Text: " + encryptedText);

        // Giải mã văn bản
        String decryptedText = decrypt(encryptedText, d, n);
        System.out.println("Decrypted Text: " + decryptedText);
    }

    // Hàm tạo số nguyên tố ngẫu nhiên
    private static BigInteger generateRandomPrime() {
        return BigInteger.probablePrime(BIT_LENGTH, new SecureRandom());
    }

    // Hàm chọn số nguyên tố e là số nguyên tố cùng nhau với ϕ(n)
    private static BigInteger choosePublicExponent(BigInteger phi) {
        BigInteger e = BigInteger.valueOf(65537); // Số nguyên tố thường được chọn
        while (!phi.gcd(e).equals(BigInteger.ONE)) {
            e = e.add(BigInteger.ONE);
        }
        return e;
    }

    // Hàm tính số nguyên d sao cho (d * e) % ϕ(n) = 1
    private static BigInteger calculatePrivateExponent(BigInteger e, BigInteger phi) {
        return e.modInverse(phi);
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

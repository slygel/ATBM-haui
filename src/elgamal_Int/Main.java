package elgamal_Int;

import java.math.BigInteger;

public class Main {
	public static void main(String[] args) {
        // Khởi tạo số nguyên tố p và g
        int p = 97;
        int a = 5;

        // Chọn số nguyên tố x sao cho 1 < x < p - 1
        int x = 58;

        // Tính y = a^x mod p
        int y = modPow(a,x,p);

        // In ra khóa công khai và khóa bí mật
        System.out.println("Public Key (p, a, y): (" + p + ", " + a + ", " + y + ")");
        System.out.println("Private Key (p, a, x): (" + p + ", " + a + ", " + x + ")");
        
        //Mã hóa
        String originalText = "H";
        System.out.println("Original Text: " + originalText);

        // Mã hóa văn bản
        ElGamalCipher cipher = encrypt(originalText, p, a, y);
        System.out.println("Encrypted Text: (" + cipher.getC1() + ", " + cipher.getC2() + ")");
        
        String decryptedText = decrypt(cipher, x, p);
        System.out.println("Decrypted Text: " + decryptedText);

    }
    
    // Tính module
     public static int modPow(int base, int exponent, int modulus) {
        if (modulus == 1) return 0;
        int result = 1;
        base = base % modulus;
        while (exponent > 0) {
            if (exponent % 2 == 1)
                result = (result * base) % modulus;
            exponent = exponent >> 1;
            base = (base * base) % modulus;
        }
        return result;
    }
     
     private static ElGamalCipher encrypt(String plaintext, int p, int a, int y) {

        byte[] plaintextBytes = plaintext.getBytes();
        int plaintextBigInteger = new BigInteger(plaintextBytes).intValue();
        System.out.println("H <=> "+ plaintextBigInteger);
        // Chọn số ngẫu nhiên k sao cho 1 < k < p - 1
        int k = 36;
        

        // Tính c1 = a^k mod p
        int c1 = modPow(a,k, p);

        int yPowK = modPow(y, k, p);

        // Tính plaintext * y^k mod p
        int c2 = (plaintextBigInteger * yPowK) % p;

        return new ElGamalCipher(c1, c2);
    }
     
     private static String decrypt(ElGamalCipher cipher, int x, int p) {
         // Tính nghịch đảo của c1 mod p
        int c1Inverse = modPow(cipher.getC1(), p - x - 1, p);

        // Tính plaintext = (c2 * c1^-x) mod p
        int plaintext = (cipher.getC2() * c1Inverse) % p;

        // Trả về giá trị plaintext dưới dạng chuỗi
        return Integer.toString(plaintext);
     }
}

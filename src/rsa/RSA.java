package rsa;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

public class RSA {

    private static final int BIT_LENGTH = 1024;

    public static void main(String[] args) {
        // Khởi tạo số nguyên tố p và q
        int p = 17;
        int q = 11;

        // Tính n = p * q
        int n = p*q;

        // Tính ϕ(n)
        int phi = 160;

        // Chọn số nguyên e sao cho 1 < e < ϕ(n) và gcd(e, ϕ(n)) = 1
        int e = chooseE(phi);

        // Tính d sao cho (d * e) % ϕ(n) = 1
        int d = NghichDao(e,phi);

        // Khóa công khai: (e, n)
        System.out.println("Ma hoa cong khai (n,e): " + "("+n+","+e+")");
        
        // Khóa bí mật: (d, n)
        System.out.println("Ma hoa bí mật (n,d): " + "("+n+","+d+")");
    
        // Thử nghiệm mã hóa và giải mã
        String originalText = "HELLO, RSA!";
        System.out.println("Original Text: " + originalText);

        int[] ciphertext = encrypt(originalText, e, n);

        // In ra chuỗi đã mã hóa
        System.out.print("Chữ ký số (ciphertext): ");
        for (int cipher : ciphertext) {
            System.out.print(cipher + " ");
        }
        System.out.println();
        
        String decryptedText = decrypt(ciphertext, d, n);
        System.out.println("Decrypted Text: " + decryptedText);
    }
    
    // Tính nghịch đảo thuật toán oclit mở rộng
    public static int NghichDao(int a, int b) {
		int result=0;
		ArrayList<Integer>r =new ArrayList<>();
		ArrayList<Integer>q =new ArrayList<>();
		ArrayList<Integer>x =new ArrayList<>();
		ArrayList<Integer>y =new ArrayList<>();
		
		x.add(0);
		y.add(1);
		r.add(b);
		q.add(0);
		x.add(1);
		y.add(0);
		r.add(a);
		q.add(0);
		for(int i=2; i<b; i++) {
			r.add(r.get(i-2)%r.get(i-1));
			q.add(r.get(i-2)/r.get(i-1));
			x.add(x.get(i-2)-q.get(i)*x.get(i-1));
			y.add(y.get(i-2)-q.get(i)*y.get(i-1));
			if(r.get(i)==1) {
				result=x.get(i);
				break;
			}
		}
		// Đảm bảo kết quả là một số dương nhỏ hơn b
	    while (result < 0) {
	        result += b;
	    }
		return result;
	}

    // Hàm mã hóa RSA
    public static int[] encrypt(String plaintext, int e, int n) {
    	
        // Chuyển đổi chuỗi thành một mảng số nguyên
        int[] plaintextNums = new int[plaintext.length()];
        for (int i = 0; i < plaintext.length(); i++) {
            plaintextNums[i] = plaintext.charAt(i); // Đổi các chữ về số theo bảng mã ASCII
        }

        // Mã hóa từng ký tự trong chuỗi
        int[] ciphertext = new int[plaintextNums.length];
        for (int i = 0; i < plaintextNums.length; i++) {
            ciphertext[i] = modPow(plaintextNums[i], e, n); // (ciphertext ^ e) % n
        }
        return ciphertext;
    }

    // Hàm giải mã RSA
    public static String decrypt(int[] ciphertext, int d, int n) {
        // Giải mã từng số nguyên trong mảng ciphertext
        StringBuilder decryptedText = new StringBuilder();
        for (int cipher : ciphertext) {
            int decryptedNum = modPow(cipher, d, n); // (cipher ^ d) % n
            decryptedText.append((char) decryptedNum); // Đổi sang kiểu char trong bảng mã ASCII
        }
        return decryptedText.toString();
    }
    
    // Phương thức tính lũy thừa modulo (a^b mod m)
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
    
    // Phương thức để chọn số nguyên e
	    public static int chooseE(int phi) {
	        Random rand = new Random();
	        int e;
	
	        do {
	            e = rand.nextInt(phi - 2) + 2; // Chọn ngẫu nhiên từ 2 đến phi - 1
	        } while (gcd(e, phi) != 1);
	
	        return e;
	    }
	
	    // Phương thức tính ước số chung lớn nhất của hai số
	    public static int gcd(int a, int b) {
	        while (b != 0) {
	            int temp = b;
	            b = a % b;
	            a = temp;
	        }
	        return a;
	    }
}


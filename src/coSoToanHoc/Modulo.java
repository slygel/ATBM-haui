package coSoToanHoc;

public class Modulo {
	public static int binaryExponentiation(int base, int exp, int m) {
		// chuyen exp thành nhị phân
		String s = "";
		while (exp > 0) {
			s = exp % 2 + "" + s;
			exp /= 2;
		}
		System.out.println(s);
		int result = 1;
		for (char c : s.toCharArray()) {
			if (c == '1') {
				result = result * base % m;
			}
			base = base * base % m;
		}
		return result;
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
    
    public static void main (String[] args) {
    	System.out.print(modPow(5, 58, 97));
//    	System.out.print(binaryExponentiation(3, 2, 2));
    }
}

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
}

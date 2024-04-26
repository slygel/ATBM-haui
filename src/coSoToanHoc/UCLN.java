package coSoToanHoc;

import java.util.ArrayList;
import java.util.List;

public class UCLN {
	public static int gcd(int a, int b) {
		if (a%b==0) 
			return b;
		return gcd(b, a%b);
	}
	
	public static List<Integer> gcdExtended(int a, int b) {
		List<Integer> rs = new ArrayList<>();
		// Base Case
		if (a == 0) {
			rs.add(b);
			rs.add(0);
			rs.add(1);
			return rs;
		}
		
		List<Integer> rs_recursive = gcdExtended(b % a, a);
		int x1 = rs_recursive.get(1);
		int y1 = rs_recursive.get(2);
		int x = y1 - (b / a) * x1;
		int y = x1;

		rs.add(rs_recursive.get(0));
		rs.add(x);
		rs.add(y);
		return rs;
	}
}

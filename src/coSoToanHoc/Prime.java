package coSoToanHoc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Prime {
	public static boolean isPrime(int n) {
		if (n<2) 
			return false;
		
		if(n==2) 
			return true;
		
		for(int i = 3; i<(int)Math.sqrt(n); i+=2) {
			if (n%i==0)
				return false;
		}
		
		return true;
	}
	
	public static List<Integer>getPrime(int min, int max, int quantity) {
		List<Boolean> arr = new ArrayList<>();
		List<Integer> prime = new ArrayList<>();
		List<Integer> result = new ArrayList<>();
		for(int i = 0; i<=max; i++) {
			arr.add(true);
		}
		
		for(int i = 2; i<=max; i++) {
			if(arr.get(i)) {
				if(i>=min && i<=max) {
					prime.add(i);
				}
				for(int j = i*i; j<=max; j+=i) {
					arr.set(j, false);
				}
			}
		}
		for(int i = 0; i<quantity;i++) {
			int j = new Random().nextInt(prime.size());
			result.add(prime.get(j));
		}
		return result;
	}
}

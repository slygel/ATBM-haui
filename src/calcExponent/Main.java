package calcExponent;

import java.util.ArrayList;

public class Main {
	public static int calc(int a, int b, int c) {
		String binary=Integer.toBinaryString(b);
		ArrayList<Integer> b1=new ArrayList<>();
		int f=1;
		for(int i=0; i<binary.length(); i++) {
			b1.add(Integer.parseInt(Character.toString(binary.charAt(i))));
		}
		
		for(int i=0; i<b1.size(); i++) {
			f=(f*f)%c;
			if(b1.get(i)==1) {
				f=(f*a)%c;
			}
		}
		return f;
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println(calc(7, 560, 561));
	}
}

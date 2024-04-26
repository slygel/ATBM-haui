package soNghichDao;

import java.util.ArrayList;

public class Main {
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
		return result;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.print("ket qua: "+NghichDao(550, 1759));
	}

}

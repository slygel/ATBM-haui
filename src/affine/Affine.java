package affine;

public class Affine {
	public static String GFG(String input, int a, int b) {
		input=input.trim(); // bỏ khoảng trống
		input=input.toLowerCase(); // chuyển về chữ in thường
		String encrypt="";
		String chu="abcdefghijklmnopqrstuvwxyz";
		
		// duyệt chuỗi cần mã hóa
		for(int i=0; i<input.length(); i++) {
			if(input.charAt(i)!=' ') {
				// Lấy ra vị trí tương ứng của input trong dãy chu 
				int pos=chu.indexOf(input.charAt(i));
				
				// Tính e(k)
				// a, b thuộc z26, a b ko chia hết 26, 
				int encryptpos=(a*pos+b)%26;
				
				// xử lý TH âm 
				if(encryptpos < 0) {
					encryptpos += 26;
				}
				
				// Lấy ra kí tự tướng ứng với giá trị e(k)
				char encryptChar=chu.charAt(encryptpos);
				
				encrypt+=encryptChar;
				
			}else {
				encrypt+=input.charAt(i);
			}
			
		}
		
		return encrypt;
	}
	
	//x=a^-1(y-b)mod26
	public static String GFG1(String input, int a, int b) {
		input=input.trim();
		input=input.toLowerCase();
		String encrypt="";
		String chu="abcdefghijklmnopqrstuvwxyz";
		int aInverse=0;
		
		// Tính nghịch đảo modular của a
		for(int i=0; i<chu.length(); i++) {
			if((a*i)%26==1) {
				aInverse=i;
				break;
			}
		} 
		
		for(int i=0; i<input.length(); i++) {
			if(input.charAt(i)!=' ') {
				int pos=chu.indexOf(input.charAt(i));
				int encryptpos=(aInverse*(pos-b+26))%26;
				if(encryptpos < 0) {
					encryptpos += 26;
				}
				char encryptChar=chu.charAt(encryptpos);
				
				encrypt+=encryptChar;
				
			}else {
				encrypt+=input.charAt(i);
			}
			
		}
		
		return encrypt;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println(GFG("hello", 5, 8));
		System.out.println(GFG1(GFG("hello", 5, 8), 5, 8));
	}
}

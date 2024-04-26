package affine;

public class Affine {
	public static String GFG(String input, int a, int b) {
		input=input.trim();
		input=input.toLowerCase();
		String encrypt="";
		String chu="abcdefghijklmnopqrstuvwxyz";
		
		for(int i=0; i<input.length(); i++) {
			if(input.charAt(i)!=' ') {
				int pos=chu.indexOf(input.charAt(i));
				int encryptpos=(a*pos+b)%26;
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

		System.out.println(GFG("truong dh cnhn", 7, 6));
		System.out.println(GFG1(GFG("truong dh cnhn", 7, 6), 7, 6));
	}
}

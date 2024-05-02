package caesar;

public class Caesar {
	public static String encrypt(String input, int key) {
		input=input.toLowerCase();
		input=input.trim();
		String encryptStr="";
		int pos;
		
		String chu="abcdefghijklmnopqrstuvwxyz";
		
		for(int i=0; i<input.length(); i++) {
			 pos=chu.indexOf(input.charAt(i));
			 char encryptChar=chu.charAt((pos+key)%26);
			 encryptStr+=encryptChar;
			
		}
		return encryptStr;
	}
	
	public static String decrypt(String input, int key) {
		input=input.toLowerCase();
		input=input.trim();
		String encryptStr="";
		int pos;
		
		String chu="abcdefghijklmnopqrstuvwxyz";
		
		for(int i=0; i<input.length(); i++) {
			 pos=chu.indexOf(input.charAt(i));
			 char encryptChar=chu.charAt((pos-key)%26);
			 encryptStr+=encryptChar;
			
		}
		return encryptStr;
	}
	
	public static void main(String[] args) {
		System.out.println(encrypt("Hanoi", 3));
	}
}

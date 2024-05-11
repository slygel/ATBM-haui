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
			 if(encryptChar < 0) {
				 encryptChar += 26;
			 }
			 encryptStr+=encryptChar;
			
		}
		return encryptStr;
	}
	
	public static void main(String[] args) {
		String plaintext = "hello";
        int key = 3;
        
        // Mã hóa
        String encryptedText = encrypt(plaintext, key);
        System.out.println("Encrypted text: " + encryptedText);
        
        // Giải mã
        String decryptedText = decrypt(encryptedText, key);
        System.out.println("Decrypted text: " + decryptedText);
	}
}

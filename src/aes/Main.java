package aes;

public class Main {
	public static void main(String[] args) 
	 { 
	     // Create String variables 
	     String originalString = "GeeksforGeekssssshell"; 
	     
	     // Call encryption method 
	     String encryptedString 
	         = AES.encrypt(originalString); 
	     
	     // Call decryption method 
	     String decryptedString 
	         = AES.decrypt(encryptedString); 

	     // Print all strings 
	     System.out.println(originalString); 
	     System.out.println(encryptedString); 
	     System.out.println(decryptedString); 
	 } 
}

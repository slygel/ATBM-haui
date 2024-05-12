package aes;

import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
	// Thêm Round Key
	// sử dụng làm đầu vào để tạo khóa mã hóa.
	 private static final String SECRET_KEY = "my_super_secret_key_ho_ho_ho"; 
	 private static final String SALT = "ssshhhhhhhhhhh!!!!"; 

	 // Hàm mã hóa
	 public static String encrypt(String strToEncrypt) 
	 { 
	     try { 

	         // Tạo mảng byte mặc định 16 phần tử
	         byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 
	         
	         // Tạo một đối tượng IvParameterSpec từ IV được tạo ở bước trước
	         IvParameterSpec ivspec = new IvParameterSpec(iv); 

	         // Sử dụng PBKDF2 với thuật toán băm SHA-256 để tạo khóa từ mật khẩu và salt
	         SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256"); 
	         
	         // Tạo một đối tượng KeySpec từ mật khẩu và salt đã cho, 
	         // cùng với các thông số khác như số vòng lặp (65536) và độ dài của khóa (256 bit)
	         KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(),65536, 256); 
	         
	         // tạo ra một khóa bí mật từ KeySpec
	         SecretKey tmp = factory.generateSecret(spec); 
	         
	         // Tạo một đối tượng SecretKeySpec từ dữ liệu byte của khóa bí mật và thuật toán mã hóa
	         SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES"); 

	         
	         // SubBytes, ShiftRows và MixColumns tích hợp vào thuật toán mã hóa AES trong lớp Cipher
	         Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); 
	         
	         // Khởi tạo Cipher với chế độ mã hóa và khóa bí mật đã tạo, cùng với IV đã chọn.
	         cipher.init(Cipher.ENCRYPT_MODE, secretKey,ivspec); 
	         
	         // Return encrypted string 
	         // đã được encode dưới dạng Base64
	         return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8))); 
	     } 
	     catch (Exception e) { 
	         System.out.println("Error while encrypting: "+ e.toString()); 
	     } 
	     return null; 
	 } 

	 // Hàm giải mã
	 public static String decrypt(String strToDecrypt) 
	 { 
	     try { 

	         // khởi tạo một mảng byte với giá trị mặc định, được sử dụng làm Initialization Vector (IV)
	         byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 
	                       0, 0, 0, 0, 0, 0, 0, 0 }; 
	         // Tạo một đối tượng IvParameterSpec từ IV được tạo ở bước trước
	         IvParameterSpec ivspec = new IvParameterSpec(iv); 

	         // sử dụng để tạo khóa từ mật khẩu và salt.
	         SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256"); 

	         // Tạo một đối tượng KeySpec từ mật khẩu và salt đã cho
	         KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(),65536, 256); 
	         
	         SecretKey tmp = factory.generateSecret(spec); 
	         SecretKeySpec secretKey = new SecretKeySpec( 
	             tmp.getEncoded(), "AES"); 

	         Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING"); 
	         
	         cipher.init(Cipher.DECRYPT_MODE, secretKey, 
	                     ivspec); 
	         
	         // Return decrypted string 
	         return new String(cipher.doFinal( 
	             Base64.getDecoder().decode(strToDecrypt))); 
	     } 
	     catch (Exception e) { 
	         System.out.println("Error while decrypting: "
	                            + e.toString()); 
	     } 
	     return null; 
	 } 
}

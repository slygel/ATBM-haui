package hillCipher;

public class HillCipher {
    
	// Hàm chuyển đổi một ký tự thành một số tương ứng trong bảng mã ASCII
    static int charToNum(char ch) {
        return (int) (ch - 'A');
    }
    
    // Hàm chuyển đổi một số thành một ký tự tương ứng trong bảng mã ASCII
    static char numToChar(int num) {
        return (char) (num + 'A');
    }
    
    // Hàm mã hóa một đoạn văn bản sử dụng ma trận khóa
    static String encrypt(String plainText, int[][] key) {
        StringBuilder encryptedText = new StringBuilder();
        int n = key.length; // Số hàng của ma trận khóa
        
        // Chia đoạn văn bản thành các đoạn có độ dài bằng số hàng của ma trận khóa
        for (int i = 0; i < plainText.length(); i += n) {
            // Lấy một phần của đoạn văn bản có độ dài bằng số hàng của ma trận khóa
            String segment = plainText.substring(i, Math.min(i + n, plainText.length()));
            
            // Mảng chứa giá trị số tương ứng của các ký tự trong đoạn văn bản
            int[] segmentNums = new int[segment.length()];
            for (int j = 0; j < segment.length(); j++) {
                segmentNums[j] = charToNum(segment.charAt(j));
            }
            
            // Nhân ma trận khóa với vectơ cột của giá trị số để mã hóa
            for (int j = 0; j < n; j++) {
                int sum = 0;
                for (int k = 0; k < n; k++) {
                	if (k < segmentNums.length) { // Kiểm tra chỉ mục hợp lệ trong mảng segmentNums
                        sum += key[j][k] * segmentNums[k];
                    }
                }
                encryptedText.append(numToChar(sum % 26)); // 26 là số ký tự trong bảng mã
            }
        }
        return encryptedText.toString();
    }
    
    // Hàm giải mã một đoạn văn bản sử dụng ma trận khóa
    static String decrypt(String encryptedText, int[][] key) {
        StringBuilder decryptedText = new StringBuilder();
        int n = key.length; // Số hàng của ma trận khóa
        
        // Lấy ma trận nghịch đảo của ma trận khóa
        int[][] inverseKey = getInverseKey(key);
        
        // Chia đoạn văn bản mã hóa thành các đoạn có độ dài bằng số hàng của ma trận khóa
        for (int i = 0; i < encryptedText.length(); i += n) {
            // Lấy một phần của đoạn văn bản mã hóa có độ dài bằng số hàng của ma trận khóa
            String segment = encryptedText.substring(i, Math.min(i + n, encryptedText.length()));
            
            // Mảng chứa giá trị số tương ứng của các ký tự trong đoạn văn bản mã hóa
            int[] segmentNums = new int[segment.length()];
            for (int j = 0; j < segment.length(); j++) {
                segmentNums[j] = charToNum(segment.charAt(j));
            }
            
            // Nhân ma trận nghịch đảo của ma trận khóa với vectơ cột của giá trị số để giải mã
            for (int j = 0; j < n; j++) {
                int sum = 0;
                for (int k = 0; k < n; k++) {
                    sum += inverseKey[j][k] * segmentNums[k];
                }
                decryptedText.append(numToChar(Math.floorMod(sum, 26))); // 26 là số ký tự trong bảng mã
            }
        }
        return decryptedText.toString();
    }
    
    // Hàm tính ma trận nghịch đảo của ma trận khóa
    static int[][] getInverseKey(int[][] key) {
        int det = key[0][0] * key[1][1] - key[0][1] * key[1][0];
        int detInv = 0;
        
        // Tìm phần tử nghịch đảo của determinants
        for (int i = 0; i < 26; i++) {
            if ((det * i) % 26 == 1) {
                detInv = i;
                break;
            }
        }
        
        // Tính ma trận nghịch đảo bằng cách sử dụng công thức và phần tử nghịch đảo của determinants
        int[][] inverseKey = new int[2][2];
        inverseKey[0][0] = key[1][1];
        inverseKey[0][1] = -key[0][1];
        inverseKey[1][0] = -key[1][0];
        inverseKey[1][1] = key[0][0];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                inverseKey[i][j] = (inverseKey[i][j] * detInv) % 26;
                if (inverseKey[i][j] < 0) {
                    inverseKey[i][j] += 26;
                }
            }
        }
        return inverseKey;
    }
    
    public static void main(String[] args) {
        int[][] key = {{3, 2}, {5, 7}}; // Ma trận khóa
        
        String plainText = "HELLOAE"; // Văn bản gốc
        
        // Mã hóa văn bản
        String encryptedText = encrypt(plainText, key);
        
        System.out.println("Encrypted Text: " + encryptedText);
        
        // Giải mã văn bản
        String decryptedText = decrypt(encryptedText, key);
        if (decryptedText.endsWith("A")) {
            decryptedText = decryptedText.substring(0, decryptedText.length() - 1);
        }
        System.out.println("Decrypted Text: " + decryptedText);
    }
}


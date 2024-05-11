package des;

public class DesAlgorithm {
	private StringBuilder PLAIN;
    private String KEY56;
    private STablePermutation STP = new STablePermutation();

    DesAlgorithm(StringBuilder plain, String key) throws Exception{
        //Intake key file and convert it into 56bit binary string for use in decryption and encryption
        String binKey = hexToBinary(key);
        KEY56 = PC_1(binKey);
        PLAIN = plain;
    }


    private String hexToBinary(String hexBlock){
        StringBuilder binary = new StringBuilder();
        char[] hexArray = hexBlock.toCharArray();
        for(int i = 0; i < hexArray.length; i++){
            int num = Integer.parseInt(hexBlock.substring(i, i+1), 16);
            String binStr = Integer.toBinaryString(num);
            while(binStr.length() < 4){
                binStr = "0" + binStr;
            }
            binary.append(binStr);
        }
        return binary.toString();
    }

    private String BinToHex(String bin){
        StringBuilder Hexadec = new StringBuilder();
        for(int i = 0; i < bin.length(); i+=4){
            int num = Integer.parseInt(bin.substring(i, i+4), 2);
            Hexadec.append(Integer.toHexString(num));
        }
        return Hexadec.toString();
    }

    private static String XOR(char str1[], char str2[]){
        int left, right;
        StringBuilder output = new StringBuilder();
        for(int i = 0; i < str1.length; i++){

            if(str1[i] == 48)
                left = 0;
            else
                left = 1;
            if(str2[i] == 48)
                right = 0;
            else
                right = 1;

            output.append((left + right) % 2);
        }
        return output.toString();
    }

    private static String initialPermutation(String input){ //input should be a 64 bit long binary string
        int[] permuter  = new int[]{
                58, 50, 42, 34, 26, 18, 10, 2,
                60, 52, 44, 36, 28, 20, 12, 4,
                62, 54, 46, 38, 30, 22, 14, 6,
                64, 56, 48, 40, 32, 24, 16, 8,
                57, 49, 41, 33, 25, 17, 9, 1,
                59, 51, 43, 35, 27, 19, 11, 3,
                61, 53, 45, 37, 29, 21, 13, 5,
                63, 55, 47, 39, 31, 23, 15, 7
        };
        Block IP = new Block(64, permuter);
        return IP.PermuteString(input);
    }
    private static String finalPermutation(String input){ //input should be a 64 bit long binary string
        int[] permuter  = new int[]{
                40, 8, 48, 16, 56, 24, 64, 32,
                39, 7, 47, 15, 55, 23, 63, 31,
                38, 6, 46, 14, 54, 22, 62, 30,
                37, 5, 45, 13, 53, 21, 61, 29,
                36, 4, 44, 12, 52, 20, 60, 28,
                35, 3, 43, 11, 51, 19, 59, 27,
                34, 2, 42, 10, 50, 18, 58, 26,
                33, 1, 41, 9, 49, 17, 57, 25
        };
        Block FP = new Block(64, permuter);
        return FP.PermuteString(input);
    }

    private static String PC_1(String input){ //input should be a 64 bit long key
        int[] permuter  = new int[]{
                57, 49, 41, 33, 25, 17, 9,
                1, 58, 50, 42, 34, 26, 18,
                10, 2, 59, 51, 43, 35, 27,
                19, 11, 3, 60, 52, 44, 36,
                63, 55, 47, 39, 31, 23, 15,
                7, 62, 54, 46, 38, 30, 22,
                14, 6, 61, 53, 45, 37, 29,
                21, 13, 5, 28, 20, 12, 4
        };
        Block Pc1 = new Block(56, permuter);
        return Pc1.PermuteString(input);
    }

    private String cipherFunction(String rightBlock, String currentKey){
        Block E = new Block(48, new int[]{
                32, 1, 2, 3, 4, 5,
                4, 5, 6, 7, 8, 9,
                8, 9, 10, 11, 12, 13,
                12, 13, 14, 15, 16, 17,
                16, 17, 18, 19, 20, 21,
                20, 21, 22, 23, 24, 25,
                24, 25, 26, 27, 28, 29,
                28, 29, 30, 31, 32, 1
        });
        Block P = new Block(32, new int[]{
                16, 7, 20, 21,
                29, 12, 28, 17,
                1, 15, 23, 26,
                5, 18, 31, 10,
                2, 8, 24, 14,
                32, 27, 3, 9,
                19, 13, 30, 6,
                22, 11, 4, 25
        });

        String s = XOR(E.PermuteString(rightBlock).toCharArray(), currentKey.toCharArray());
        s = STP.permute(s);
        return P.PermuteString(s);
    }

    String encrypt() throws Exception {
        KeyScheduler KS = new KeyScheduler(KEY56, 0);
        StringBuilder encypheredMessage = new StringBuilder();
        String block;
        while(PLAIN.length() > 0){
            while (PLAIN.length() < 16) {
                PLAIN.append("0"); //for when the final block is less than 64 bits and needs to be padded
            }
            block = PLAIN.substring(0, 16);
            PLAIN.delete(0, 16);
            String inputBlock = hexToBinary(block);
            String InitPermute = initialPermutation(inputBlock);

            String key48;
            String L = InitPermute.substring(0, 32);
            String R = InitPermute.substring(32, 64);
            String Rn;
            for(int i = 0; i < 16; i++){ //16 permutations and iterations of the cipher function f
                key48 = KS.KEYS[i];
                Rn = XOR(L.toCharArray(), cipherFunction(R, key48).toCharArray());
                L = R;
                R = Rn;

            }
            String finalPermute = finalPermutation(R + L);
            encypheredMessage.append(finalPermute);
        }
        return BinToHex(encypheredMessage.toString());
    }

    String decrypt(){
        KeyScheduler KS = new KeyScheduler(KEY56, 1);
        StringBuilder EncypheredMessage = PLAIN;
        StringBuilder decypheredMessage = new StringBuilder();
        String block;
        while(EncypheredMessage.length() > 0){
            while(EncypheredMessage.length() < 16){
                EncypheredMessage.append("0");
            }
            block = EncypheredMessage.substring(0, 16);
            EncypheredMessage.delete(0, 16);
            String inputBlock = hexToBinary(block);
            String InitPermute = initialPermutation(inputBlock);

            String key48;
            String L = InitPermute.substring(0, 32);
            String R = InitPermute.substring(32, 64);
            String Rn;
            for(int i = 0; i < 16; i++){//16 permutations and iterations of the cipher function f
                key48 = KS.KEYS[15 - i];
                Rn = XOR(L.toCharArray(), cipherFunction(R, key48).toCharArray());
                L = R;
                R = Rn;
            }
            String finalPermute = finalPermutation(R + L);
            decypheredMessage.append(finalPermute);
        }

        // convert from binary to decimal (plaintext)
        String hex = BinToHex(decypheredMessage.toString());
        char[] hexArray = hex.toCharArray();
        StringBuilder dec = new StringBuilder();
        for(int i = 0; i < hexArray.length; i+=2){ //check for troublesome characters in the output and create output string
            int charCheck = Integer.parseInt(hex.substring(i, i+2), 16);
            if(charCheck == 0)
                dec.append("");
            else if(charCheck == 160)
                dec.append("\n");
            else if(charCheck == 144)
                dec.append("\t");
            else {
                dec.append((char) charCheck);
            }
        }
        return dec.toString();
    }
}

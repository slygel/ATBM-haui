package des;

import java.math.BigInteger;


public class DES {

    public static void main(String[] args) {
        String message = "Hello, World!";
        String keyString = "mysecret"; // Key should be 8 bytes long
        byte[] key = keyString.getBytes();

        // Encrypt
        byte[] encryptedData = encrypt(message.getBytes(), key);
        System.out.println("Encrypted message: " + byteArrayToHexString(encryptedData));

        // Decrypt
        byte[] decryptedData = decrypt(encryptedData, key);
        System.out.println("Decrypted message: " + new String(decryptedData));
    }

    public static byte[] encrypt(byte[] message, byte[] key) {
        int[] keys = generateSubkeys(key);

        // Initial permutation
        message = permute(message, InitialPermutationTable);

        // Splitting into left and right halves
        byte[] left = new byte[4];
        byte[] right = new byte[4];
        System.arraycopy(message, 0, left, 0, 4);
        System.arraycopy(message, 4, right, 0, 4);

        // 16 rounds of DES
        for (int i = 0; i < 16; i++) {
            byte[] temp = functionF(right, keys[i]);
            byte[] temp2 = xor(left, temp);
            left = right;
            right = temp2;
        }

        // Final permutation
        byte[] result = permute(right, FinalPermutationTable);
        System.arraycopy(left, 0, result, 4, 4);
        return result;
    }

    public static byte[] decrypt(byte[] cipher, byte[] key) {
        int[] keys = generateSubkeys(key);

        // Initial permutation
        cipher = permute(cipher, InitialPermutationTable);

        // Splitting into left and right halves
        byte[] left = new byte[4];
        byte[] right = new byte[4];
        System.arraycopy(cipher, 0, left, 0, 4);
        System.arraycopy(cipher, 4, right, 0, 4);

        // 16 rounds of DES in reverse order
        for (int i = 15; i >= 0; i--) {
            byte[] temp = functionF(left, keys[i]);
            byte[] temp2 = xor(right, temp);
            right = left;
            left = temp2;
        }

        // Final permutation
        byte[] result = permute(left, FinalPermutationTable);
        System.arraycopy(right, 0, result, 4, 4);
        return result;
    }

    // Initial permutation table
    private static final int[] InitialPermutationTable = {
        58, 50, 42, 34, 26, 18, 10, 2,
        60, 52, 44, 36, 28, 20, 12, 4,
        62, 54, 46, 38, 30, 22, 14, 6,
        64, 56, 48, 40, 32, 24, 16, 8,
        57, 49, 41, 33, 25, 17, 9, 1,
        59, 51, 43, 35, 27, 19, 11, 3,
        61, 53, 45, 37, 29, 21, 13, 5,
        63, 55, 47, 39, 31, 23, 15, 7
    };

    // Final permutation table
    private static final int[] FinalPermutationTable = {
        40, 8, 48, 16, 56, 24, 64, 32,
        39, 7, 47, 15, 55, 23, 63, 31,
        38, 6, 46, 14, 54, 22, 62, 30,
        37, 5, 45, 13, 53, 21, 61, 29,
        36, 4, 44, 12, 52, 20, 60, 28,
        35, 3, 43, 11, 51, 19, 59, 27,
        34, 2, 42, 10, 50, 18, 58, 26,
        33, 1, 41, 9, 49, 17, 57, 25
    };

    // Permutation function
    private static byte[] permute(byte[] input, int[] table) {
        byte[] output = new byte[table.length];
        for (int i = 0; i < table.length; i++) {
            output[i] = input[table[i] - 1];
        }
        return output;
    }

    // XOR function
    private static byte[] xor(byte[] a, byte[] b) {
        byte[] result = new byte[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = (byte) (a[i] ^ b[i]);
        }
        return result;
    }

    // Generation of 16 subkeys
    private static int[] generateSubkeys(byte[] key) {
        int[] subkeys = new int[16];
        byte[] permutedChoice1 = permute(key, PermutationChoice1Table);
        byte[] c = new byte[28];
        byte[] d = new byte[28];
        System.arraycopy(permutedChoice1, 0, c, 0, 28);
        System.arraycopy(permutedChoice1, 28, d, 0, 28);
        for (int i = 0; i < 16; i++) {
            // Circular left shift
            c = circularLeftShift(c, ShiftTable[i]);
            d = circularLeftShift(d, ShiftTable[i]);
            byte[] cd = new byte[56];
            System.arraycopy(c, 0, cd, 0, 28);
            System.arraycopy(d, 0, cd, 28, 28);
            subkeys[i] = permutationChoice2(cd);
        }
        return subkeys;
    }

 // Permutation choice 1 table
    private static final int[] PermutationChoice1Table = {
        57, 49, 41, 33, 25, 17, 9,
        1, 58, 50, 42, 34, 26,
        18, 10, 2, 59, 51, 43,
        35, 27, 19, 11, 3, 60,
        52, 44, 36, 63, 55, 47,
        39, 31, 23, 15, 7, 62,
        54, 46, 38, 30, 22, 14,
        6, 61, 53, 45, 37, 29,
        21, 13, 5, 28, 20, 12, 4
    };


    // Shift table for circular left shifts
    private static final int[] ShiftTable = {
        1, 1, 2, 2, 2, 2, 2, 2,
        1, 2, 2, 2, 2, 2, 2, 1
    };

    // Permutation choice 2 table
    private static final int[] PermutationChoice2Table = {
    	    13, 16, 10, 23, 0, 4,
    	    2, 27, 14, 5, 20, 9,
    	    22, 18, 11, 3, 25, 7,
    	    15, 6, 26, 19, 12, 1,
    	    40, 51, 30, 36, 46, 54,
    	    29, 39, 50, 44, 32, 47,
    	    43, 48, 38, 55, 33, 52,
    	    45, 41, 49, 35, 28, 31
    };

    // Permutation choice 2 function
    private static int permutationChoice2(byte[] cd) {
        int result = 0;
        for (int i = 0; i < 48; i++) {
            result <<= 1;
            result |= (cd[PermutationChoice2Table[i] - 1] & 0x01);
        }
        return result;
    }

    // Circular left shift function
    private static byte[] circularLeftShift(byte[] input, int shift) {
        byte[] output = new byte[input.length];
        System.arraycopy(input, shift, output, 0, input.length - shift);
        System.arraycopy(input, 0, output, input.length - shift, shift);
        return output;
    }

    // Function F (feistel function)
    private static byte[] functionF(byte[] right, int key) {
        byte[] expandedRight = permute(right, ExpansionPermutationTable);
        byte[] xorResult = xor(expandedRight, intToBinaryArray(key, 48));
        byte[] substitutedBytes = substitute(xorResult);
        return permute(substitutedBytes, PBoxPermutationTable);
    }

    // Expansion permutation table
    private static final int[] ExpansionPermutationTable = {
        32, 1, 2, 3, 4, 5,
        4, 5, 6, 7, 8, 9,
        8, 9, 10, 11, 12, 13,
        12, 13, 14, 15, 16, 17,
        16, 17, 18, 19, 20, 21,
        20, 21, 22, 23, 24, 25,
        24, 25, 26, 27, 28, 29,
        28, 29, 30, 31, 32, 1
    };

    // P-box permutation table
    private static final int[] PBoxPermutationTable = {
        16, 7, 20, 21, 29, 12, 28, 17,
        1, 15, 23, 26, 5, 18, 31, 10,
        2, 8, 24, 14, 32, 27, 3, 9,
        19, 13, 30, 6, 22, 11, 4, 25
    };

    // Substitution boxes (S-boxes)
    private static final int[][] SBoxes = {
        {
            14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7,
            0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8,
            4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0,
            15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13
        },
        {
            15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10,
            3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5,
            0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15,
            13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9
        },
        {
            10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8,
            13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1,
            13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7,
            1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12
        },
        {
            7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15,
            13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9,
            10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4,
            3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14
        },
        {
            2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9,
            14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6,
            4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14,
            11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3
        },
        {
            12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11,
            10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8,
            9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6,
            4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13
        },
        {
            4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1,
            13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6,
            1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2,
            6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12
        },
        {
            13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7,
            1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2,
            7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8,
            2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11
        }
    };

    // Substitution function
    private static byte[] substitute(byte[] input) {
        byte[] output = new byte[32];
        for (int i = 0; i < 8; i++) {
            int row = (input[i * 6] << 1) + input[i * 6 + 5];
            int col = (input[i * 6 + 1] << 3) + (input[i * 6 + 2] << 2) + (input[i * 6 + 3] << 1) + input[i * 6 + 4];
            int value = SBoxes[i][row * 16 + col];
            output[i * 4] = (byte) ((value & 0x08) >> 3);
            output[i * 4 + 1] = (byte) ((value & 0x04) >> 2);
            output[i * 4 + 2] = (byte) ((value & 0x02) >> 1);
            output[i * 4 + 3] = (byte) (value & 0x01);
        }
        return output;
    }

    // Convert integer to binary array
    private static byte[] intToBinaryArray(int value, int size) {
        byte[] result = new byte[size];
        for (int i = size - 1; i >= 0; i--) {
            result[i] = (byte) (value & 0x01);
            value >>= 1;
        }
        return result;
    }

    // Convert byte array to hex string
    private static String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    // Convert hex string to byte array
    private static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                                 + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }
}




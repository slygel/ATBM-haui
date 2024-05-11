package des;

public class KeyScheduler {
	private Block PC_2 = new Block(48, new int[]{
            14, 17, 11, 24, 1, 5,
            3, 28, 15, 6, 21, 10,
            23, 19, 12, 4, 26, 8,
            16, 7, 27, 20, 13, 2,
            41, 52, 31, 37, 47, 55,
            30, 40, 51, 45, 33, 48,
            44, 49, 39, 56, 34, 53,
            46, 42, 50, 36, 29, 32
    });

    private StringBuilder C = new StringBuilder();
    private StringBuilder D = new StringBuilder();
    private int ITERATION;
    private int[] SHIFT_AMOUNT = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};
    public String[] KEYS = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};

    KeyScheduler(String key56, int process_flag){
        C.append(key56.substring(0, 28));
        D.append(key56.substring(28, 56));

        for(int i = 0; i < 16; i++) {
            String temp = C.substring(0, SHIFT_AMOUNT[i]);
            C.delete(0, SHIFT_AMOUNT[i]);
            C.append(temp);

            temp = D.substring(0, SHIFT_AMOUNT[i]);
            D.delete(0, SHIFT_AMOUNT[i]);
            D.append(temp);

            KEYS[i] = PC_2.PermuteString(C.toString() + D.toString());
            }
        if(process_flag == 0) {
            ITERATION = 0;
        }
        else{
            ITERATION = 15;
        }
    }
}

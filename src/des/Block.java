package des;

public class Block {
	private int[] BLOCK;
    private int SIZE;

    public Block(int size, int[] input){
        BLOCK = input;
        SIZE = size;
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < SIZE; i++)
            str.append(BLOCK[i]);
        return str.toString();
    }

    public String PermuteString(String str){
        char permutedBlock[] = new char[SIZE];
        char initialString[] = str.toCharArray();
        int k = 0;
        for(int i = 0; i < SIZE; i++) {
            permutedBlock[k++] = initialString[(BLOCK[i])-1];
        }
        StringBuilder permuteStr = new StringBuilder();
        for(int i = 0; i < k; i++){
            permuteStr.append(permutedBlock[i]);
        }
        return permuteStr.toString();
    }
}

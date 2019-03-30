import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {

    //predetermined values for 32-bit FNV hash.  Using 32 bits
    //because integers are 32 bits.
    private static Integer FNV_offset = Integer.parseUnsignedInt("2166136261");
    private static Integer FNV_prime = 16777619;
    private static Integer numKeywords = 84;
    private static String[] keywords = new String[numKeywords];

    public static void main(String[] args) throws FileNotFoundException {
        //pull in the words -- we know there are 84
        Scanner in = new Scanner(new File("keywords.txt"));
        for(int i = 0; i < numKeywords; i++) {
            keywords[i] = in.next();
        }
        in.close();
        //now, brute force!
        // System.out.println(getMultiplier(1));
        System.out.println(getMultiplier());
    }

    public static int getMultiplier(int multiplier) {
        String[] outputTable = new String[numKeywords];
        for(String wordToHash: keywords) {
            int index = Integer.remainderUnsigned(FNV_hash(multiplier, wordToHash), numKeywords);
            if(outputTable[index] != null) { //if we collide, start over with a fresh multiplier
                System.out.println(multiplier);
                return getMultiplier(multiplier + 1);
            }
            outputTable[index] = wordToHash;
        }
        //if you make it out, you found a good one
        return multiplier;
    }

    public static int getMultiplier() {
        int multiplier = 1;
        while(true) {
            String[] outputTable = new String[numKeywords];
            boolean collided = false;
            for(int i = 0; i < numKeywords; i++) {
                String wordToHash = keywords[i];
                int index = Integer.remainderUnsigned(FNV_hash(multiplier, wordToHash), numKeywords);
                System.out.println(index);
                if(outputTable[index] != null) { //if we collide, start over with a fresh multiplier
                    System.out.println("Collided on word number: " + (i + 1));
                    collided = true;
                    break;
                }
                outputTable[index] = wordToHash;
            }
            if(collided) {
                outputTable = new String[numKeywords];
                multiplier++;
            }
            else
                return multiplier;
        }
    }

    public static int FNV_hash(int multiplier, String str) {
        Integer hash = FNV_offset;
        byte[] bytes = str.getBytes();
        for(byte b : bytes) { //for every octet
            hash = hash ^ b;
            hash = hash * FNV_prime;
        }
        return hash * multiplier;
    }
}
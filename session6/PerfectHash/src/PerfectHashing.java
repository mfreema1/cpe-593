import java.util.*;
import java.util.stream.Collectors;
import java.io.File;
import java.io.FileNotFoundException;

public class PerfectHashing {
    
    private static Integer numKeywords = 10;
    private static String[] keywords = new String[numKeywords];
    private static int maxGValue = numKeywords; //we're dealing with a lot of very short words
    private static Stack<String> highestFrequencyWords = new Stack<>();
    public static void main (String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("./src/cichelli.txt"));
        for(int i = 0; i < numKeywords; i++) {
            keywords[i] = in.next();
        }
        in.close();

        Map<Character, Integer> letterFreqs = new HashMap<>();
        for(String s : keywords) {
            char beginningLetter = s.charAt(0);
            char endingLetter = s.charAt(s.length() - 1);

            //count up how often each letter occurred at the start and end of the words
            Integer numberOfOccurrences = letterFreqs.get(beginningLetter);
            if(numberOfOccurrences == null)
                letterFreqs.put(beginningLetter, 1);
            else
                letterFreqs.put(beginningLetter, numberOfOccurrences + 1); //increment

            //same with ending letters
            numberOfOccurrences = letterFreqs.get(endingLetter);
            if(letterFreqs.get(endingLetter) == null)
                letterFreqs.put(endingLetter, 1);
            else
                letterFreqs.put(endingLetter, numberOfOccurrences + 1);
        }

        //now find the sum for each word
        Map<String, Integer> wordFreqs = new HashMap<>();
        for(String s : keywords) {
            int beginningFreq = letterFreqs.get(s.charAt(0));
            int endingFreq = letterFreqs.get(s.charAt(s.length() - 1));
            wordFreqs.put(s, beginningFreq + endingFreq);
        }

        //sort the word map from highest frequency to lowest and dump them
        //into an arrayList.  This is some Java magic ;)
        wordFreqs.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue()) //sort on value
            .map(entry -> entry.getKey()) //take only the key (word)
            .forEach(highestFrequencyWords::push); //dump it into the stack
        
        //we are now at the last step of Cichelli's algorithm, keep going!
        System.out.println(getGValues());
        System.out.println(knownHashes);
    }

    private static Map<Character, Integer> gVals = new HashMap<>();
    private static Set<Integer> knownHashes = new HashSet<>();
    //this is a backtracking algorithm
    private static boolean getGValues() {
        if(highestFrequencyWords.isEmpty())
            return true;
        else {
            String currentWord = highestFrequencyWords.pop();
            char firstLetter = currentWord.charAt(0);
            char lastLetter = currentWord.charAt(currentWord.length() - 1);

            //case 1: neither g value is present -- basically a mish-mash of cases 2 and 3
            if(gVals.get(firstLetter) == null && gVals.get(lastLetter) == null) {
                //start by iterating on the first while you hold the second at 0
                for(int i = 0; i < maxGValue; i++) {
                    for(int j = 0; j < maxGValue; j++) {
                        int hash = hash(currentWord, i, j);
                        if(knownHashes.contains(hash))
                            continue;
                        //if it works, we're done with this word for now
                        gVals.put(firstLetter, i);
                        gVals.put(lastLetter, j);
                        knownHashes.add(hash);
                        boolean finished = getGValues();
                        if(finished)
                            return true;
                        knownHashes.remove(hash);
                    }
                }
                //nothing worked, backtrack
                highestFrequencyWords.push(currentWord);
                return false;
            }

            //case 2: the first g value is present
            else if(gVals.get(firstLetter) != null && gVals.get(lastLetter) == null) {
                //since we have one g value, just iterate on the other
                for(int i = 0; i < maxGValue; i++) {
                    int hash = hash(currentWord, gVals.get(firstLetter), i);
                    if(knownHashes.contains(hash))
                        continue;
                    //if it works, keep it
                    gVals.put(lastLetter, i);
                    knownHashes.add(hash);
                    boolean finished = getGValues();
                    if(finished)
                        return true;
                    knownHashes.remove(hash);
                }
                highestFrequencyWords.push(currentWord);
                return false;
            }

            //case 3: the second g value is present
            else if(gVals.get(firstLetter) == null && gVals.get(lastLetter) != null) {
                //since we have one g value, just iterate on the other
                for(int i = 0; i < maxGValue; i++) {
                    int hash = hash(currentWord, i, gVals.get(lastLetter));
                    if(knownHashes.contains(hash))
                        continue;
                    //if it works, keep it
                    gVals.put(firstLetter, i);
                    knownHashes.add(hash);
                    boolean finished = getGValues();
                    if(finished)
                        return true;
                    knownHashes.remove(hash);
                }
                highestFrequencyWords.push(currentWord); //have to redo it if we ran out of guesses
                //may need to peel back the contributed hash
                return false;
            }

            //case 4: both g values are present
            else {
                //attempt the hash, if it doesn't work, we have to backtrack
                int hash = hash(currentWord, gVals.get(firstLetter), gVals.get(lastLetter));
                if(knownHashes.contains(hash)) {
                    //if we collide, we have to go back, can't reassign g values
                    highestFrequencyWords.push(currentWord);
                    return false;
                }
                //otherwise add it to the set
                knownHashes.add(hash);
                boolean finished = getGValues();
                if(finished)
                    return true;
                //if we can't make it to the bottom from here, backtrack
                knownHashes.remove(hash);
                highestFrequencyWords.push(currentWord);
                return false;
            }
        }
    }

    /**
     * Due to the way the hash is computed, Cichelli's algorithm WILL NOT WORK for sets of data that have at least
     * 2 elements which have the same set of first and last letters and are the same length.  They will receive the
     * same hash value and can never resolve this.  MUST compute the hash differently, which unfortunately
     * affects the entire algorithm.
     */
    private static int hash(String s, int gOfFirstLetter, int gOfLastLetter) {
        return (s.length() + gOfFirstLetter + gOfLastLetter) % numKeywords;
    }
}
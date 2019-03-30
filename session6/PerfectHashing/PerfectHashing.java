import java.util.*;
import java.util.stream.Collectors;
import java.io.File;
import java.io.FileNotFoundException;

public class PerfectHashing {
    
    private static Integer numKeywords = 84;
    private static String[] keywords = new String[numKeywords];
    private static int maxGValue = 4;
    private static Stack<String> highestFrequencyWords = new Stack<>();
    public static void main (String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("keywords.txt"));
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
        findGValues();
        System.out.println(gVals);
        //attempt to figure out g values as you go down the list
        // Map<Character, Integer> gVals = new HashMap<>();
        // Set<Integer> knownHashes = new HashSet<>();
        
        
        //try to develop a g for the first and last letter
        
        // for(String s : highestFrequencyWords) {
        //     char firstLetter = s.charAt(0);
        //     char lastLetter = s.charAt(s.length() - 1);
            
        // }
    }

    public static Map<Character, Integer> gVals = new HashMap<>();
    public static Set<Integer> knownHashes = new HashSet<>();
    //this is a backtracking algorithm
    public static boolean findGValues() {
        if(highestFrequencyWords.isEmpty())
            return true; //done!
        else {
            String currentWord = highestFrequencyWords.pop();
            char firstLetter = currentWord.charAt(0);
            char lastLetter = currentWord.charAt(currentWord.length() - 1);
            
            //try to find gValues for the first letter if they don't exist
            if(gVals.get(firstLetter) == null) {

                //make one for the last one, we'll need it
                if(gVals.get(lastLetter) == null) {
                    gVals.put(lastLetter, 0);
                }

                for(int i = 0; i < maxGValue; i++) { //try to calculate the hash

                    //try to assign the first gValue
                    int hash = hash(currentWord, i, gVals.get(lastLetter)); //TODO: inline function
                    if(knownHashes.contains(hash))
                        continue; //can't use this one, gotta keep going!
                    
                    //works so far, try using this one
                    gVals.put(firstLetter, i);
                    knownHashes.add(hash);
                    boolean finished = findGValues(); //WE HAVE TO GO DEEPER
                    if(finished)
                        return true;
                }
            }
            //no g values work, backtrack
            highestFrequencyWords.push(currentWord); //push back onto the stack, we have to redo it
            return false;
        }
    }

    public static int hash(String s, int gOfFirstLetter, int gOfLastLetter) {
        //h(s) = s.length() + g(firstLetter) + (lastLetter)
        return s.length() + gOfFirstLetter + gOfLastLetter;
    }
}
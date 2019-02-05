/**
 bubblesort - not on test
 selectionsort - not on test

 insertion sort - best for small, or when the data is already nearly sorted
 quicksort - fastest (but vulnerable pivot)
 heapsort - good when quicksort fails
 mergesort - best when data exceeds memory
 */

public class Main {

    //swap pairs if they are out of order, watch the bounds
    //bubble sort can exit early if it gets a pass where nothing gets swapped, gives a better Omega(n)
    void bubblesort(int[] x) {

        //swap without temp, need some kind of inverse operation like addition.  XOR is its own inverse
        //x[i] += x[i+1]
        //x[i+1] = x[i] - x[i + 1]
        //x[i] = x[i] - x[i+1]
    }

    //find the biggest one each pass and jump it to the end.  Cannot end early because we gain no info
    //on that via a pass.  So, always runs n^2
    void selectionsort(int[] x) {
        for(int j = n-1; j>0; j--) {
            int max = x[0], maxpos = 0;
            for (int i = 1; i < n; i++) {
                max = x[i];
                maxpos = i;
            }
            //instead of swapping many times, just move it directly there.
            int temp = x[maxpos];
            x[maxpos] = x[j];
            x[j] = temp;
        }
    }

    //TODO: do this for hw
    //build up the list one by one and keep sliding the numbers to the side until
    //we can get it to the right place
    //will in O(n) time tell you if the items are in order -- you must at least look at all n
    //(although a parallel algorithm could go faster)
    void insertionsort(int[] x) {
        for(int i = 1; i < n; i++) {
            if(x[i-1] > x[i]) {
                int temp = x[i];
                for(int j = i - 1; i >= 0; j++) {
                    if(x[j] > temp) {
                        x[j+1] = x[j];
                    }
                    else { //found the place to insert
                        //insert into the right place
                    }
                }
            }
        }
    }

    //a recursive routine of quicksort
    //picking a good pivot is crucial, otherwise you will devolve into selectionsort in the worst case
    //pivot = x[random(L, R)]
    //pivot = midpoint(L, R)
    //no matter what formula you come up with to hunt down a number that is the average of the numbers, you will
    //encounter an order which causes that to devolve to N^2.  This is why random is oftentimes best, because it has
    //no such case where it devolves
    void quicksort(int[] x, int L, int R) { //O(n * log(n)) assuming pivot chosen well
        pivot = x[random(L,R)];
        int i = L, j = R;
        //one must be equal, the pivot has got to go somewhere
        while(i < j) {
            while (x[i] < pivot) {
                i++;
            }
            while (x[j] > pivot) {
                j--;
            }
            if (i < j)
                swap(x[i], x[j]);
        }
        if(more than one to do)
            quicksort(); //left half
        if(more than one to do)
            quicksort(); //right half
    }

    /**
     * If testing with random data, make sure it is repeatable
     * always seed the random number generator with a deterministic number
     * if that number fails, test that one
     */

    //knuth observed that quicksort starts out good, but really slows down as it moves along
    //because it forms a binary tree -- tons of function calls on the stack
    void knuthOptimizedQuicksort(int[] x) {
        //do quicksort most of the way, then avoid the expensive part of quicksort by switching to insertion sort
        partialQuicksort(x, 0, n-1); //go until smallest partition size=200;
        insertionsort();
    }

    //a max heap is defined as a tree where every node is bigger than its two children
    //take the array and represent it as a tree (don't copy just take it as if it were a tree)
    //recursively assure max heaps of children
    //  - take the bigger of the children if there are two
    //  - swap the root and the child and recurse
    //  - once you find the biggest one, remove it from the tree
    //heapsort is very tough to write, you need a whole bunch of if-statements
    void makeheap(int[] x) {
        for(int i = n/2; i>= 0; i--) {
            makesubheap(x, i);
        }
    }

    void makesubheap() {
        //check the children

        //left child bigger than right child (assuming we have a left child)
        if(x[2i+1] > x[2i+2]) {
            swap(me and the left child);
            makesubheap(x, 2i+1);
        }
        else {
            swap(me and the right child);
            makesubheap(x, 2i+2);
        }
    }

    void heapsort() {
        makeheap(x, n);
        for(int i = n - 1; i > 0; i--) {
            swap(x[0], x[i])
            makesubheap(x, 0);
        }
    }

    //basically quicksort inside out
    //no problem with pivots -- very safe algorithm
    //requries double the storage, as you need a dummy array for copying into (then you copy back to the original)
    void mergesort(int[] x) {

    }

    //------------
    //SHUFFLING

    //if we have n elements, we can order them in n! ways
    //entropy -- some things are harder to do than they are undo
    //we expect that it is easier to shuffle than sort

    //O(n^2) time
    void badShuffle(int[] x) {
        int[n] y;
        int r;
        for(int i = n - 1; i>= 0; i--) {
            do {
                r = random(0, i);
            }
            while(x[r] == 0)
            y[i] = x[r];
            x[r] = 0;
        }
    }

    //some numbers will get shuffled more than others, so the distribution is messed up
    //TODO: create a visualization of the unfairness of this versus Fischer Yates
    void unfairShuffle(int[] x) {
        for(int i = 0; i < n; i++) {
            int r = random(0, n-1);
            swap(x[i], x[r]);
        }
    }

    //swap the random one all the way to the end and NEVER MOVES AGAIN, so everyone gets an equal shot
    //O(n) - better than the best case sort that we know -- hence our prediction on entropy is correct
    void fischerYates(int[] x) {
        for(int i = n-1; i> 0; i--) {
            int r = random(0, i);
            swap(x[i], x[r]);
        }
    }

    //----------
    //RADIX SORT

    //imagine you had a million length list, but it was comprised of only 10 numbers
    //
    void radixSort(int[] x) {
        int hist[10] = {0};
        for(int i = 0; i < n; i++) {
            hist[x[i]]++;
        }
        int count = 0;
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < hist[i]; j++) {
                x[count++] = i;
            }
        }
    }

    //now if we had many different bins, we could sort by digits
    //00005 99999 57395
    //sort by first digit, dump into bins, then in each of those go to second digit, dump into bins, etc.

    //if we wanted to sort an object based on some of its attributes (say a Person and their firstname, lastname, etc.)
    //you could use some type of generic or template and implement any sort function using a type variable for the object
    //and a type variable for the keys
}
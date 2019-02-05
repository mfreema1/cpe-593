from collections import Counter
import random

def swap(lst, ind1, ind2):
    temp = lst[ind1]
    lst[ind1] = lst[ind2]
    lst[ind2] = temp

def unfairShuffle(lst):
    for i, el in enumerate(lst):
        randomIndex = random.randint(0, len(lst) - 1) #for some reason it's inclusive
        swap(lst, i, randomIndex)
    return lst

def fischerYates(lst):
    for i in range(len(lst) - 1, 0, -1):
        randomIndex = random.randint(0, i)
        swap(lst, i, randomIndex)
    return lst

def trial(lst, func, numberOfTrials = 100000):
    #setup a dict to capture the results
    d = {}
    for el in lst:
        d[el] = {}
    #run the trials
    for i in range(numberOfTrials):
        result = func(lst.copy())
        for i, num in enumerate(result):
            if(not d[num].get(i)):
                d[num][i] = 1
            else:
                d[num][i] += 1
    return d


def trial_lsts(lst, func, numberOfTrials = 100000):
    import numpy as np
    #setup a matrix to hold them
    matrix = np.zeroes(shape=(10,10))
    print(matrix)

    


def main():
    lst = [1,2,3,4,5,6,7,8,9,10]
    print(trial(lst, unfairShuffle))
    print(trial(lst, fischerYates))

if __name__ == "__main__":
    main()
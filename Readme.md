# About:

I originally wrote this code three years ago for a class called `Programming Challenges`, which involved solving competition style programming challenges. The most important metric for this class was always runtime, so efficiency was key. 

The problem posed here was how to list all of the compound words in a dictionary most efficiently. 

> Note: I rewrote large chunks of the code today, while my solution was accepted at the time, upon testing I found that it didn't check if suffixes were valid words.
> I like to think that if you're not a little embarassed by your old code, you haven't learned much since then :) 

# How to Run:

once you've cloned this directory and cd'd into it, either run `java CompoundWord < test_sm.txt` or `java CompoundWord < test_lg.txt` . 

All of the relevant code is in `CompoundWord.java`, with `test_lg.txt` and `test_sm.txt` being large and small test dictionaries. 

# What makes this interesting?

What makes this interesting to me is the Trie data structure itself, and the efficiency with which it can solve this task. It's also very easy to see how a Trie would be used for input prediciton or word completion (this is the application that actually excites me more, and I might post an implementation of it in a branch here).

# Runtime analysis:

The basic procedure is as follows:

loop through all words: O(n)
  list and save all prefixes: O(length of word)
  insert word into dictionary: O(length of word)
loop through all prefixes: O(number of prefixes)
  loop through related suffixes: O(num of related suffixes, usually small)
    check if suffix is valid word: O(len suffix)

all in all this reduces to O(2n) -> O(n)


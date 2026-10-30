# dsa-practice

Data structures and algorithms practice in plain Java 25, with no build tools or external libraries.

## Week 5: arrays, strings and hashing

| Problem | File | Pattern | Time | Space |
|---|---|---|---|---|
| Second largest | `src/week05/SecondLargest.java` | Single pass with two running values | O(n) | O(1) |
| Anagram | `src/week05/IsAnagram.java` | Frequency counting with `int[26]` | O(n + m) | O(1) |
| Two sum | `src/week05/TwoSum.java` | Brute force nested loops vs HashMap complement lookup | O(n²) vs O(n) | O(1) vs O(n) |
| Most frequent word | `src/week05/MostFrequentWord.java` | Frequency counting with a HashMap | O(n) | O(n) |
| Merge sorted arrays | `src/week05/MergeSortedArrays.java` | Two pointers | O(n + m) | O(n + m) |

Each class has a `main` that runs the examples and edge cases and prints expected vs actual with PASS or FAIL.

## How to compile and run

From the project root, compile everything into `out/`:

```
javac -d out src/week05/*.java
```

Then run any problem by its full class name:

```
java -cp out week05.SecondLargest
java -cp out week05.IsAnagram
java -cp out week05.TwoSum
java -cp out week05.MostFrequentWord
java -cp out week05.MergeSortedArrays
```

## Two Sum timing results

On a 20,000-number worst case, the brute force version took about 40 ms while the HashMap version took about 3 ms, roughly 14 times faster. The brute force checks every pair of numbers (about 200 million pairs, O(n²)), while the HashMap version looks at each number once and finds its matching complement with a near-constant-time lookup (O(n)), trading extra memory for speed.

## Week 6: stacks, two pointers and sliding windows

| Problem | File | Idea | Time | Space |
|---|---|---|---|---|
| Matching brackets | `src/week06/MatchingBrackets.java` | Push every opening bracket onto a stack; each closing bracket must match the one popped from the top, and the stack must be empty at the end | O(n) | O(n) |
| Container with most water | `src/week06/ContainerWithMostWater.java` | Start with the widest container and always move the shorter wall inward, because moving the taller one can never give more water | O(n²) vs O(n) | O(1) vs O(1) |
| Longest substring without repeating characters | `src/week06/LongestSubstring.java` | Slide a window over the string and, when a character repeats inside it, jump the left edge to just past where that character was last seen | O(n³) vs O(n) | O(n) vs O(n) |
| Three numbers that sum to zero | `src/week06/ThreeSum.java` | Sort the numbers, fix each one in turn, and use two pointers on the rest to find pairs that cancel it out, skipping repeated values | O(n³) vs O(n²) | O(1) vs O(n) |
| Days until a warmer temperature | `src/week06/DaysUntilWarmer.java` | Keep a stack of days still waiting for a warmer day, and when a warmer day arrives, pop and answer every colder day on top | O(n²) vs O(n) | O(1) vs O(n) |

Where two values are given, the first is the brute force version and the second is the faster one. Space does not count the returned answer.

Compile and run Week 6 the same way:

```
javac -d out src/week06/*.java
java -cp out week06.MatchingBrackets
java -cp out week06.ContainerWithMostWater
java -cp out week06.LongestSubstring
java -cp out week06.ThreeSum
java -cp out week06.DaysUntilWarmer
```

## Three Sum timing results

On 1,500 random numbers from -1000 to 1000, the brute force version took about 480 to 530 ms while the sort plus two pointers version took about 8 to 18 ms, roughly 40 times faster, and both found the same 71,474 triples. The brute force checks every group of three numbers (about 560 million groups, O(n³)), while the two pointers version fixes one number and walks the two pointers toward each other across the sorted rest in a single pass (about 1.1 million steps, O(n²)).

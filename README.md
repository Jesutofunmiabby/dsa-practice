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

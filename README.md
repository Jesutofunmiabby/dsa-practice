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

## Week 7: linked lists, binary search, recursion and sorting

| Problem | File | Idea | Time | Space |
|---|---|---|---|---|
| Reverse a linked list | `src/week07/ReverseList.java` | Walk the list once and turn each node's next arrow around to point at the node before it, saving the next node first so the rest of the list isn't lost | O(n) vs O(n) | O(1) vs O(n) |
| First and last position | `src/week07/SearchRange.java` | Binary search twice, and when the middle equals the target keep going: search left for the first copy and right for the last copy | O(n) vs O(log n) | O(1) vs O(1) |
| Search in a rotated sorted array | `src/week07/RotatedSearch.java` | At every step at least one half around the middle is sorted, so check whether the target sits inside that sorted half and throw away the other half | O(n) vs O(log n) | O(1) vs O(1) |
| Merge sort | `src/week07/MergeSort.java` | Split the array in half, sort each half by calling merge sort on it, then merge the two sorted halves by repeatedly taking the smaller front element | O(n²) vs O(n log n) | O(n) vs O(n) |
| Where does the loop start | `src/week07/DetectCycle.java` | A slow and a fast pointer must meet inside a cycle; then a pointer from the head and one from the meeting point, both moving one step, meet exactly at the cycle's start | O(n) vs O(n) | O(n) vs O(1) |

Where two values are given:

- Reverse a linked list: the loop version, then the recursive one. The recursive one needs O(n) space for its call stack.
- First and last position, and rotated search: the slow scan, then binary search.
- Merge sort: insertion sort, then merge sort. Both return a new array, so O(n) space is the copy.
- Where does the loop start: the HashSet version, then the slow and fast pointers.

`ListNode.java` is the shared helper for the linked list problems. `fromArray` builds a list, `show` prints it as `[1,2,3]`, and `fromArrayWithCycle` builds a list whose last node points back to a given position.

Compile and run Week 7 the same way:

```
javac -d out src/week07/*.java
java -cp out week07.ReverseList
java -cp out week07.SearchRange
java -cp out week07.RotatedSearch
java -cp out week07.MergeSort
java -cp out week07.DetectCycle
```

## Merge sort vs insertion sort timing results

On 20,000 random numbers, merge sort took about 2.7 to 2.9 ms while insertion sort took about 59 to 85 ms, roughly 20 to 30 times slower, and both gave the same result as `Arrays.sort`. Insertion sort shifts each number left past every bigger number before it (about 100 million shifts on random data, O(n²)), while merge sort halves the array about 15 times and touches every number once per level when merging (about 300,000 steps, O(n log n)).

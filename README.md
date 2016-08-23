# DNA-Sequencing
## Usage
```
javac DNA.java
java DNA <input-file> [method-choice]
```
* The \<input-file\> is the test file of chromosome fragments.
* The [method-choice] is an optional argument.  If no argument is passed in, both the exhaustive solution labeled "sequence" and the greedy solution labeled "greedy" will be run.  If you just want to run the exhaustive solution this flag should be `0` and if you just want to run the greedy solution this flag should be `1`.

## Approach
* This problem is np-complete because it can be reduced down to the traveling salesman problem.
* My first approach can be seen in the `sequence()` function.  This takes the exhaustive approach of trying all permutations of the list and removing all overlapping letters between two adjacent words.  For example if "abcde" and "cdefgh" were consective words, the combined word, after removing overlapping letters is abcdefgh.  One of the duplicate `cde`'s -- ab[cde] and [cde]fgh -- were taken out.
* My second approach can be seen in the `greedySeq()` function.  This takes a greedy approach of merging words with the greatest overlap, until there is only one word or the remaining words have no overlap.  This approach can be viewed as a directed graph where the nodes are the words and the directed edges are the size of the overlap.  The source is the first adjacent word (prefix) and the sink is the second adjacent word (suffix).  Once we merge two words, we remove those nodes and add a new node with the merged result.  As stated previously, this is done until there is only one node left or all edges have weight 0.

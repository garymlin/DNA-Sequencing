import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class DNA {

	public static void main(String[] args) throws FileNotFoundException {		
		if (args.length < 1) {
			System.out.println("usage: java DNA <input-file> [method-choice]");
			return;
		}
		
		int method;
		if (args.length > 1) {
			method = Integer.parseInt(args[1]);
		}
		else {
			method = 2;
		}
		
		ArrayList<String> list = parseFile(args[0]);
		if (method != 1){
			System.out.println("sequence: " + sequence(list));
		}
		if (method != 0) {
			System.out.println("greedy: " + greedySeq(list));
		}
	}
	
	// n! exhaustive solution to find sequence.
	public static String sequence(ArrayList<String> list) {
		String output = null;
		ArrayList<ArrayList<String>> permutations = permute(list);
		
		for (int i = 0 ; i < permutations.size(); i++) {
			ArrayList<String> currList = permutations.get(i);
			String str = currList.get(0);
			for (int j = 0 ; j < list.size() - 1; j++) {
				int overlapLen = overlapCount(currList.get(j), currList.get(j + 1));
				str += currList.get(j + 1).substring(overlapLen);
			}
			if (output == null || output.length() > str.length()) {
				output = str;
			}
		}
		return output;
	}
	
	// n^3*m greedy solution to find sequence (is not always correct).
	// Where n is the size of the list and m is the longest word.
	public static String greedySeq(ArrayList<String> list) {
		String output = "";
		ArrayList<String> result = maxOverlap(list);
		int maxLen = Integer.parseInt(result.get(0));
		
		while (maxLen > 0) {
			list.remove(result.get(1));
			list.remove(result.get(2));
			list.add(result.get(1) + result.get(2).substring(maxLen));
			
			result = maxOverlap(list);
			maxLen = Integer.parseInt(result.get(0));
		}
		
		for (int i = 0 ; i < list.size(); i++) {
			output += list.get(i);
		}
		return output;
	}
	
	// Returned arraylist has 3 elements, the first element is the size of overlap
	// and the next two elements are the strings with the max overlap.
	// If there is a tie, it selects the first encounter.
	public static ArrayList<String> maxOverlap(ArrayList<String> list) {
		ArrayList<String> output = new ArrayList<String>();
		output.add("-1");output.add("-1");output.add("-1");
		int maxLen = 0;
		
		for (int i = 0 ; i < list.size() - 1; i++) {
			for (int j = i + 1 ; j < list.size(); j++) {
				int len = overlapCount(list.get(i), list.get(j));
				if (len > maxLen) {
					output.set(0, Integer.toString(len));
					output.set(1, list.get(i));
					output.set(2, list.get(j));
					maxLen = len;
				}
			}
		}
		
		return output;
	}
	
	// Given a prefix and suffix, returns the number of letters of overlap.
	// Starts with the end of the prefix and beginning of suffix.
	// prefix: ab[cde]
	// suffix:   [cde]fgh
	public static int overlapCount(String prefix, String suffix) {
		int count = 0;
		int preLen = prefix.length();
		int suffLen = suffix.length();
		
		for (int i = 0 ; i < preLen ; i++) {
			if (i == suffLen) {
				return count;
			}
			if (prefix.substring(preLen - 1 - i, preLen).equals(suffix.substring(0, i + 1))){
				count = i + 1;
			}
		}
		return count;
	}

	// Get all permutations of the list.  This is for the n! solution.
	public static ArrayList<ArrayList<String>> permute(ArrayList<String> list) {
		ArrayList<ArrayList<String>> output = new ArrayList<ArrayList<String>>();
		permRecurse(list, 0, list.size(), output);
		return output;
	}
	
	// Helper recursive function for permute().
	public static void permRecurse(ArrayList<String> list, int l, int r, 
			ArrayList<ArrayList<String>> output){
		if (l == r){			
			output.add(new ArrayList<String>(list));
		}
		for (int i = l; i < r; i++) {
			 Collections.swap(list, l, i);
			 permRecurse(list, l + 1, r, output);
			 Collections.swap(list, l, i);
		}
	}
	
	// Parses file and outputs arraylist of data.
	// Only gets items that do not start with a ">"
	public static ArrayList<String> parseFile(String fileName) throws FileNotFoundException {
		ArrayList<String> output = new ArrayList<String>();
		File fileObj = new File(fileName);
		Scanner file = new Scanner(fileObj);
		
		while (file.hasNextLine()) {
			String line = file.nextLine();
			if (line.substring(0,1).equals(">")){
				continue;
			}
			output.add(line);
		}
		return output;
	}
}

package main.java;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 * 
 * Use this class to debug or print extensive information about any data that is stored and processed.  
 * @author Piraveen
 */
public class DebugClass {
	private static List<String> lines = SentenceBuilder.getLines();									// Raw lines
	private static ArrayList<Sentence> sentenceObjects = SentenceBuilder.getSentenceObjects();		// Sentence-objects
	private static ArrayList<Word> dirtyWordObjects = WordBuilder.getDirtyWordObjects();			// Word-objects with stop-words	
	private static ArrayList<Word> cleanWordObjects = WordBuilder.getCleanWordObjects();			// Word-objects without stop-word
	private static LinkedHashMap<Word, Integer> freqMap = WordBuilder.getfreqMap();					// why use LinkedHashMap?  order = insertion-order
	private static ArrayList<Word> maxWordList = WordBuilder.getMaxWordList();
	
	private ArrayList<Integer> stats;																// for getStats() method
	
	// used for debugging
	// this method can only be ran after...
	//										1) SentenceBuilder has been instantiated 
	// 										2) WordBuilder has been instantiated and getWords(), removeStopWords() and doCount() has been run
	public static void printInfo(){
		System.out.println("--------------Raw lines from file---------------");
		for (String line : lines) {
			System.out.println(line);
		}
		
		System.out.println("\n----------Tree Map showing every sentence-----------");
		// print
		for(int i=0; i<sentenceObjects.size(); i++){
			System.out.println(sentenceObjects.get(i));
		}
		
		System.out.println("\n----------Word list(dirty)-----------");
		// print
		for(int i=0; i<dirtyWordObjects.size(); i++){
			System.out.println(i + " " + dirtyWordObjects.get(i));
		}
		
		System.out.println("\n----------Word list after removing stop words(clean)-----------");
		// print
		for(int i=0; i<cleanWordObjects.size(); i++){
			System.out.println(i + " " + cleanWordObjects.get(i));
		}
		
	}
	
	// used for debugging
	public static void printStats(){
		
		System.out.println("\n----------------Stats---------------------");
		System.out.format("%-40s %d\n", "Number of lines: ", 		lines.size());
		System.out.format("%-40s %d\n", "Number of sentences: ", 	sentenceObjects.size());
		System.out.format("%-40s %d\n", "Number of words: ", 		dirtyWordObjects.size());
		System.out.format("%-40s %d\n", "Number of stop-words removed: ", 		dirtyWordObjects.size()-cleanWordObjects.size());
		System.out.format("%-40s %d\n", "Number of words without stop-words: ", cleanWordObjects.size());
		System.out.format("%-40s %d\n", "Number of unique words w/o stop-words: ", freqMap.size());
		

	}
	
	/**
	 * Get stats about # of lines, sentences, words, stop-words, words without stop-words and a frequency map with unique words.</br>
	 * NOTE: The data from this function is intended to be used for a potential ranking system.
	 * @return an arraylist with all the stats
	 */
	public ArrayList<Integer> getStats(){
		ArrayList<Integer> stats = new ArrayList<Integer>();
		
		stats.add(lines.size());											//0
		stats.add(sentenceObjects.size());									//1
		stats.add(dirtyWordObjects.size());									//2
		stats.add(dirtyWordObjects.size()-cleanWordObjects.size());			//3
		stats.add(cleanWordObjects.size());									//4
		stats.add(freqMap.size());											//5
		
		return stats;
	}
	
	// used for debugging
	public static void printFreqMap(){
		System.out.println("\n----------Each word and number of occurences-----------");
		// print
		Set<Word> keySet = freqMap.keySet();										// get the unique keys (Word-object in this case)
		Word[] uniqueKeys = keySet.toArray(new Word[keySet.size()]);				// create an array
		for (Word string : uniqueKeys) {
			int frequency = freqMap.get(string);
			//System.out.println("Word: " + string.getWordText() + "\t\t Occurences: " + frequency);
			System.out.printf("Word: %-25s Occurences: %-10d ...belongs to sentence %d\n", string.getWordText(), frequency, string.getBelongingSentenceNo());
			
		}
		System.out.println("Size of keyset is " + keySet.size());
	}
	
	// used for debugging
	public static void printTopNWords(){
		System.out.println("\n------------------Top N words-------------------");
		for (int i = 0; i < maxWordList.size(); i++) {
			System.out.format("'%s' is max with %d occurences. It belongs to sentence %d.\n", 
					maxWordList.get(i).getWordText(), maxWordList.get(i).getWordOccurence(), maxWordList.get(i).getBelongingSentenceNo());
		}
	}
}

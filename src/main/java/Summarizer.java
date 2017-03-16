package main.java;
import java.util.ArrayList;
import java.util.Collections;

public class Summarizer {
	private ArrayList<Word> maxWordList = WordBuilder.getMaxWordList();
	
	/**
	 * Empty constructor
	 */
	public Summarizer(){}
	
	
	// Weight / Points for every word
	
	/**
	 * Uses a custom Comparator class, WordComparator, to sort a list of Word-objects based on their belonging sentence no. 
	 */
	public void sortTopNWordList(){
		Collections.sort(maxWordList, new WordComparator()  {
	        @Override public int compare(Word s1, Word s2) {
	            return s1.getBelongingSentenceNo() > s2.getBelongingSentenceNo() ? 1 : -1;
	        }

	    });
	}
	
	// TODO: Use getStats() to analyze how much the text has been reduced (in %)
	
	// TODO: Write the summary to a file (.txt)
	// TODO: GUI
	// TODO: There should also be the option to not have consecutive sentences. 
	

	
	
	public void createSummary(){
		ArrayList<Sentence> sentences = SentenceBuilder.getSentenceObjects();
		

		// print final summary
		System.out.println("\n------------------Summary-------------------");
		for (int i = 0; i < maxWordList.size(); i++) {
			int j = maxWordList.get(i).getBelongingSentenceNo();
			System.out.println(sentences.get(j));
		}
	}
	
	
	

	
	
}
// * How autotldrbot works: https://www.reddit.com/r/askscience/comments/4s5b5q/how_exactly_does_a_autotldrbot_work/
// * Unit testing - JUnit. 
// * EMMA - Open-source code coverage tool for Java. 
// * EclEmma for code coverage with Eclipse
// * Analyze memory management/execution time (benchmark analysis)/ algorithm complexity (Big-O)
// * Lage Python script som tar en hel tekst og gir en linebreak hvis en setning er større enn x antall tegn. 

// DONE: Given X amount of sentences, the function will get top X number of words used in the text. - OK
// DONE: Should be option to turn off words from same sentence. - OK. 
// DONE: Return summary with sentences that are somewhat ordered (not random). - OK.

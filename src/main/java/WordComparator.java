package main.java;

import java.util.Comparator;
/**
 * Be aware that this Comparator implements a compare() method that compares {@link Word Word} objects based on their {@link Word#getBelongingSentenceNo() sentence number}. 
 * The <i>{@link Word#compareTo compareTo()}</i> method in Word is a compare method that compares based on Word occurrence in document. 
 * @author Piraveen
 */
public class WordComparator implements Comparator<Word>{
	/**
	 * Compare based on the Word objects' sentence no.
	 * @param w1 first Word object
	 * @param w2 second Word object
	 * @return 1 if first object is bigger
	 */
	public int compare(Word w1, Word w2){
		if(w1.getBelongingSentenceNo() < w2.getBelongingSentenceNo())
			return -1;
		else if(w1.getBelongingSentenceNo() > w2.getBelongingSentenceNo())
			return 1;
		
		return 0;
		
	}
}

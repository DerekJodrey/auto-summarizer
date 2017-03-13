package main.java;
import java.util.ArrayList;

public class Word {
	private String wordText;
	private int belongsToSentenceN;
	private int occurrence=-1;
	static ArrayList<Word> allDirtyWords= new ArrayList<Word>(); 
	
	public Word(String word, int belongsToSentenceN){
		this.wordText = word;
		this.belongsToSentenceN = belongsToSentenceN;
		allDirtyWords.add(this);
	}
	
	@Override
	public String toString(){
		return String.format("%-20s ...belongs to sentence %s", this.wordText, this.belongsToSentenceN);
	}
	
	public static ArrayList<Word> getAllDirtyWords(){
		return allDirtyWords;
	}
	
	public String getWordText(){
		return this.wordText;
	}
	
	public int getBelongingSentenceNo(){
		return this.belongsToSentenceN;
	}
	
	public void setWordOccurence(int occurence){
		this.occurrence = occurence;
	}
	
	public int getWordOccurence(){
		return this.occurrence;
	}
	
	/** 
	 * equals() has to be overwritten since the method is accessed through the {@link Runner#doCount doCount()}-method 
	 * (specifically the Collections.frequency()-method).
	 */
	@Override
	public boolean equals(Object obj) {
	    //null instanceof Object will always return false
	    if (!(obj instanceof Word))
	      return false;
	    
	    //NOTE: '==' doesn't to the comparing properly. We have to use 'equals'.
	    return  this.wordText.equals(((Word) obj).wordText) ? true : false;
	  }
	
	/** 
	 * hashCode() has to be overwritten since the method is accessed through the {@link Runner#doCount doCount()}-method 
	 * (specifically the Collections.frequency()-method).
	 */
	@Override
	public int hashCode() {
		return this.wordText.hashCode();	// we're basing the hashcode on 'wordText'
	 }
	
	/**
	 * Compare Word objects based on # of occurrence. 
	 * @param word
	 * @return 1 if first object is bigger, 0 if equal
	 */
	public int compareTo(Word word){
		if(this.occurrence < word.occurrence)
			return -1;
		else if(this.occurrence > word.occurrence)
			return 1;
		
		return 0;
		//return Integer.valueOf(this.occurence).compareTo(word.occurence);
		
	}
}











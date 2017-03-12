import java.util.ArrayList;

public class Word {
	private String wordText;
	private int belongsToSentenceN;
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
}











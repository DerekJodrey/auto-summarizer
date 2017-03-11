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
		//return String.format("%s \t\t ...belongs to sentence %s", this.wordText,this.belongsToSentenceN);
		//return this.wordText + " \t ...belongs to sentence " + this.belongsToSentenceN;
	}
	
	public static ArrayList<Word> getAllDirtyWords(){
		return allDirtyWords;
	}
	
	public String getWordText(){
		return this.wordText;
	}
	
//	public int hashCode(){
//		return 1;
//	}
}

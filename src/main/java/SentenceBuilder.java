package main.java;
import java.util.ArrayList;
import java.util.List;
/**
 * Takes lines provided by FileHandler and populates lists with Sentence-objects. 
 * @author Piraveen
 *
 */
public class SentenceBuilder {
	// declaring local variables
	private static final String SEPERATORS = ". "; 
	private static List<String> lines;																
	private static ArrayList<Sentence> sentenceObjects = new ArrayList<Sentence>();
	
	
	public SentenceBuilder(String language, String filePath){
		// build list of Sentence objects
		getSentences(filePath);						
		
		
		//FIXME: Handle empty lines/sentences/words/strings
		
	}
	
	// getter
	public static ArrayList<Sentence> getSentenceObjects(){
		return sentenceObjects;
	}
	
	public static List<String> getLines(){
		return lines;
	}
	

	/**
	 *  This class reads every line in the document and splits it into sentences.
	 *  The method is currently statically set to split for either every dot or every <i>". " (dot and a space)</i> using regular expression. 
	 *  @param language language code in capital letters
	 *  @param path filepath
	 */
	private ArrayList<Sentence> getSentences( String path){
		
		//TODO: Sentences that end with question(?) mark or exclamation(!) mark
		
		//FileHandler fh = new FileHandler(path);
		lines = FileHandler.readFile(path);
		// read lines 
		for (String line : lines) {
			
			// everything before a 'dot' (or 'dot' and a blank space) is treated like a sentence
			// we're using lookaroundsto keep the delimiter as part of the sentence when split
			String[] splitLines = line.split("(?<=\\. {0,1})");	//regex	- add more with | 		read more-->https://stackoverflow.com/questions/2206378/how-to-split-a-string-but-also-keep-the-delimiters
			
			
			// remove entries with blank spaces
			for (int j = 0; j < splitLines.length; j++) {
				if(splitLines[j].equals(" "))
					splitLines[j] = null;
			}
			
			//System.out.println(i + "st run");
			for (String aSentence : splitLines) {
				//System.out.println(sentence);
				if(aSentence!=null)								// sentence=null if the current sentence doesn't have any deliminators
				{					

					Sentence s = new Sentence(aSentence);
					sentenceObjects.add(s);
				}
			}
		}
		
		return sentenceObjects;
	}

}


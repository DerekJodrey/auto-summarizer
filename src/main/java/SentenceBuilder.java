package main.java;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
/**
 * Takes lines provided by FileHandler and populates lists with Sentence-objects. 
 * @author Piraveen
 *
 */
public class SentenceBuilder {
	// declaring local variables
	private static final String SEPERATORS = ". "; 
	private List<String> lines;																
	private static ArrayList<Sentence> sentenceObjects = new ArrayList<Sentence>();

	// VARIABLES POPULATED BY WORDBUILDER
	private List<Word> dirtyWordObjects = new ArrayList<Word>();							// Word-objects with stop-words
	private ArrayList<Word> cleanWordObjects;												// Word-objects without stop-words	
	private LinkedHashMap<Word, Integer> freqMap = new LinkedHashMap<Word, Integer>();		// why use linkedhashmap?   order = insertion-order
	
	public SentenceBuilder(String language, String filePath){
		// build list of Sentence objects
		getSentences( filePath);						
		
		WordBuilder wb = new WordBuilder();
		dirtyWordObjects = wb.getWords(language, filePath);
		cleanWordObjects = wb.removeStopWords(language);	
		freqMap = wb.doCount(cleanWordObjects);
		
		printInfo();
		wb.printFreqMap();
		printStats();
		wb.findTopNWords(5);
		
		//FIXME: Handle empty lines/sentences/words/strings
		
	}
	
	// getter
	public static ArrayList<Sentence> getSentenceObjects(){
		return sentenceObjects;
	}

	
	//TODO: New delimiter when sentence ends with question mark
	/**
	 *  This class reads every line in the document and splits it into sentences.
	 *  The method is currently statically set to split for either every dot or every <i>". " (dot and a space)</i> using regular expression. 
	 *  @param language language code in capital letters
	 *  @param path filepath
	 */
	private ArrayList<Sentence> getSentences( String path){
		//String path = null;
		
//		if(language.equals("NO"))
//			path = "files/file_no.txt";
//		else if(language.equals("EN"))
//			path = "files/file_en.txt";
//		else
//			System.err.println("Please set a valid language code.");
		
		//TODO: Sentences that end with question(?) mark or exclamation(!) mark
		
		FileHandler fh = new FileHandler(path);
		lines = fh.readFile(path);
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

	/*-----------------------------------------------Debugging methods-----------------------------------------------------------*/
	
	
	// used for debugging
	private void printInfo(){
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
	private void printStats(){
		
		System.out.println("\n----------------Stats---------------------");
		System.out.format("%-40s %d\n", "Number of lines: ", 		lines.size());
		System.out.format("%-40s %d\n", "Number of sentences: ", 	sentenceObjects.size());
		System.out.format("%-40s %d\n", "Number of words: ", 		dirtyWordObjects.size());
		System.out.format("%-40s %d\n", "Number of stop-words removed: ", 		dirtyWordObjects.size()-cleanWordObjects.size());
		System.out.format("%-40s %d\n", "Number of words without stop-words: ", cleanWordObjects.size());
		System.out.format("%-40s %d\n", "Number of unique words w/o stop-words: ", freqMap.size());
		

	}
	
	// used for debugging
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
}

//TODO: Add info about what's happening. e.g.	 "Now converting words to lower case..."


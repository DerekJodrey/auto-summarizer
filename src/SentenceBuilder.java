import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class SentenceBuilder {
	private static final String SEPERATORS = ". "; 
	private int stopWordsRemoved=0;
	List<String> lines;																//raw lines from file
	private List<Sentence> sentenceObjects = new ArrayList<Sentence>();				//Sentence-branch: 
	
	List<Word> dirtyWordObjects = new ArrayList<Word>();							//Word-branch
	ArrayList<Word> cleanWordObjects;												//Word-branch	
	
	LinkedHashMap<Word, Integer> freqMap = new LinkedHashMap<Word, Integer>();	// Word-branch			why use linkedhashmap?   order = insertion-order
	
	public SentenceBuilder(String language){
		getSentences(language);
		getWords(language);
		removeStopWords(language);	
		
		printNewLines();
		doCount(cleanWordObjects);
		printMap();
		printStats();
				

		
	}
	
	//FIXME: Handle empty lines/sentences/words/strings
	
	
	/**
	 *  This class reads every line in the document and splits it into sentences.
	 *  The method is currently statically set to split for either every dot or every <i>". " (dot and a space)</i> using regular expression. 
	 */
	public void getSentences(String language){
		String path = null;
		
		if(language.equals("NO"))
			path = "files/file_no.txt";
		else if(language.equals("EN"))
			path = "files/file_en.txt";
		else
			System.err.println("Please set a valid language code.");
		
		
		FileHandler fh = new FileHandler(path);
		lines = fh.readFile(path);
		int i=0;
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
				if(aSentence!=null)				//sentence may be null if the current sentence doesn't have any deliminators
				{					

					Sentence s = new Sentence(aSentence);
					sentenceObjects.add(s);
					//i++;
				}

			}

		}

	}
	
	
	// retrieve words from the sentence list
	/**
	 *  Turn the sentences in {@link SentenceBuilder#sentenceObjects sentenceObjects} to a list of Word-objects. Since this is done with custom
	 *  Word-objects, each Word has info about which sentence it was affiliated with. 
	 *  Regular expressions are used to split word, and they're handled differently for each languages. 
	 */
	public void getWords(String language){		 
		 
		 String[] wordsForCurrentSentence = null;
		 
		 // loop through every sentence
		 for (Sentence sentence : sentenceObjects) {
			 if(language.equals("NO")) 
				 wordsForCurrentSentence = sentence.getText().split("([^\\wæøåÆØÅ]+)");	// norwegian: split for every non-word (including æøå)
			 else if(language.equals("EN"))
				 wordsForCurrentSentence = sentence.getText().split("([^\\w']+)");		// 	english:  split for every non-word (including ')
			 else
				 System.err.println("Please set a valid language code.");
			 
			  // sentence number
			  int sentenceNo = sentence.getSentenceNo();
			  
			 // for every sentence, add every word
			 for (String word : wordsForCurrentSentence) {
				 // ---Word-branch part
				 Word w = new Word(word.toLowerCase(), sentenceNo);
				 dirtyWordObjects.add(w); 	
				// end Word-branch part---
			}
 
		}
	}
	
	// 
	/**
	 * Clean up word-list by removing stop-words. 
	 * {@link SentenceBuilder#dirtyWordObjects dirtyWordObjects} is the input, and a clean list without stop-words will be found in
	 * {@link SentenceBuilder#cleanWordObjects cleanWordObjects}.  
	 * @param language sets stop-word file to be used. Set <i>"NO"</i> for norwegian (bokmål) or <i>"EN"</i> for english. 
	 */
	public void removeStopWords(String language){
		List<String> stopWords = null;

		if(language.equals("NO"))
			stopWords = FileHandler.readFile("files/stopwords-no_nb.txt");
		else if(language.equals("EN"))
			stopWords = FileHandler.readFile("files/stopwords-en.txt");
		else
			System.err.println("Please set a valid language code.");

		
		cleanWordObjects = new ArrayList<>(Word.getAllDirtyWords());				// copy array
		for (Word word : dirtyWordObjects) {
			if(stopWords.contains(word.getWordText())){
				cleanWordObjects.remove(word);
				stopWordsRemoved++;
			}

		}



	}
	
	
	

	

	
	
	// count occurrence of each word
	public void doCount(ArrayList<Word> list){
		int freq;
		
		for (int i = 0; i < list.size(); i++) {
			// only run code IFF the key doesn't already exist in the freqMap
			//System.out.println("-----");
			//System.out.println("New word: " + !freqMap.containsKey(list.get(i)));
			//System.out.println("Now considering word: " + list.get(i).getWordText());
			if(!freqMap.containsKey(list.get(i))){
				// check frequency of the given string
				freq = Collections.frequency(list, list.get(i));	
				freqMap.put(list.get(i), freq);	// (word, frequency)
				


			}
			
		}
	}
	
	
	
	// used for debugging
	public void printNewLines(){
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
		System.out.println("Removed " + stopWordsRemoved + " stop words.");
		
		


	}
	
	
	// used for debugging
	public void printMap(){
		//--Word-branch
		System.out.println("\n----------Each word and number of occurences-----------");
		// print
		Set<Word> keySet = freqMap.keySet();										// get the unique keys (Word-object in this case)
		Word[] uniqueKeys = keySet.toArray(new Word[keySet.size()]);				// create an array
		for (Word string : uniqueKeys) {
			int frequency = freqMap.get(string);
			System.out.println("Word: " + string + "\t\t Occurences: " + frequency);
			
		}
		System.out.println("Size of keyset is " + keySet.size());
	}
	
	public void printStats(){
		System.out.println("\n---------------Stats--------------------");
		System.out.format("%-38s %d\n", "Number of lines: ", 		lines.size());
		System.out.format("%-38s %d\n", "Number of sentences: ", 	sentenceObjects.size());
		System.out.format("%-38s %d\n", "Number of words: ", 		dirtyWordObjects.size());
		System.out.format("%-38s %d\n", "Number of stop-words removed: ", 		dirtyWordObjects.size()-cleanWordObjects.size());
		System.out.format("%-38s %d\n", "Number of words without stop-words: ", cleanWordObjects.size());
	}
}

//TODO: Add text about what's happening. e.g.	 "Now converting words to lower case..."


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class SentenceBuilder {
	private static final String SEPERATORS = ". "; 
	private int stopWordsRemoved=0;
	List<String> lines;	//raw lines from file
	private List<Sentence> sentenceObjects = new ArrayList<Sentence>();			//Sentence-branch: arraylist just for testing
	
	List<String> dirtyWordList = new ArrayList<String>();
	ArrayList<String> cleanWordList;
	// map consisting of uniqueString:frequency
	LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();	//why use linkedhashmap?   order = insertion-order
	
	
	public SentenceBuilder(){
		getSentences();
		getWords();
		removeStopWords("NO");
		
		doCount(cleanWordList); // create a map with word:frequency
		printNewLines();
		
				

		
	}
	/**
	 *  This class reads every line in the document and splits it into sentences.
	 *  The current separator is a dot followed by a space (as given in the regex expression). 
	 */
	public void getSentences(){
		final String path = "files/file_no.txt";
		
		FileHandler fh = new FileHandler(path);
		lines = fh.readFile(path);
		int i=0;
		// read lines 
		for (String line : lines) {
																				//TODO: handle different language eg asterix shouldn't be included in regex for norwegian, but should in english
			
			// everything before a 'dot' (or 'dot' and a blank space) is treated like a sentence
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
	public void getWords(){		 
		 
		 String[] wordsForCurrentSentence;
		 // loop through every sentence
		 for (Sentence sentence : sentenceObjects) {
			  wordsForCurrentSentence = sentence.getText().split("([^\\wæøåÆØÅ]+)");	// split for every non-word (including æøå and ')
			 
			 // for every sentence, add every word
			 for (String word : wordsForCurrentSentence) {
				 dirtyWordList.add(word.toLowerCase()); 		// .toLowerCase()
			}

			 
		}
	}
	
	// clean up word-list by removing stop-words
	
	public void removeStopWords(String language){
		//if(language.equals("NO")){
			List<String> stopWords = FileHandler.readFile("files/stopwords-no_nb.txt");
			cleanWordList = new ArrayList<>(dirtyWordList);	// copy array
			for (String word : dirtyWordList) {
				if(stopWords.contains(word)){
					cleanWordList.remove(word);
					stopWordsRemoved++;
				}
					
			}
		//}
		
		
	}
	
	
	
	// count occurrence of each letter
	public void doCount(ArrayList<String> liste){
		int j;
		
		for (int i = 0; i < liste.size(); i++) {
			// check if string already exists
			if(!map.containsKey(liste.get(i))){
				// check frequency of the given string
				j = Collections.frequency(liste, liste.get(i));	
				map.put(liste.get(i), j);	// (char, frequency)

			}
			
		}
		
		

		
		//return map;
	}
	
	
	
	
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
		for(int i=0; i<dirtyWordList.size(); i++){
			System.out.println(i + " " + dirtyWordList.get(i));
		}
		
		System.out.println("\n----------Word list after removing stop words(clean)-----------");
		// print
		for(int i=0; i<cleanWordList.size(); i++){
			System.out.println(i + " " + cleanWordList.get(i));
		}
		System.out.println("Removed " + stopWordsRemoved + " stop words.");
		
		System.out.println("\n----------Each word and number of occurences-----------");
		// print
		Set<String> keySet = map.keySet();											// get the unique keys
		String[] uniqueKeys = keySet.toArray(new String[keySet.size()]);			// create an array
		for (String string : uniqueKeys) {
			int frequency = map.get(string);
			System.out.println("Word: " + string + "\t\t Occurences: " + frequency);
			
		}

	}
}

//TODO: Add text about what's happening. e.g.	 "Now converting words to lower case..."
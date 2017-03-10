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
	private List<String> sentences = new ArrayList<String>();					//arraylist just for testing
	private Map<Integer, String> setninger = new TreeMap<Integer, String>();	//the one to use
	List<String> dirtyWordList = new ArrayList<String>();
	ArrayList<String> cleanWordList;
	// map consisting of uniqueString:frequency
	LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();	//why?   order = insertion-order
	
	
	public SentenceBuilder(){
		getSentences();
		getWords();
		removeStopWords("NO");
		printNewLines();
		
		// create a map with string:frequency
		doCount(cleanWordList);
				
		Set<String> keySet = map.keySet();											// get the unique keys
		String[] uniqueKeys = keySet.toArray(new String[keySet.size()]);			// create an array
				
		System.out.println("-----");
		// create a node for each unique character then put into list
		for (String string : uniqueKeys) {
			int frequency = map.get(string);
			System.out.println("Word: " + string + "\t\t Occurences: " + frequency);
			
		}
		
	}
	
	public void getSentences(){
		System.out.println("-----------------------------");
		final String path = "files/file_en.txt";
		
		FileHandler fh = new FileHandler(path);
		List<String> lines = fh.readFile(path);
		int i=0;
		// read lines 
		for (String line : lines) {
			System.out.println(line);
			
			//if(line.contains(SEPERATORS))
			
			// everything before a 'dot' (or 'dot' and a blank space) is treated like a sentence
			String[] splitLines = line.split("(?<=\\. {0,1})");	//regex	- add more with | 		read more-->https://stackoverflow.com/questions/2206378/how-to-split-a-string-but-also-keep-the-delimiters
			
			// remove entries with blank spaces
			for (int j = 0; j < splitLines.length; j++) {
				if(splitLines[j].equals(" "))
					splitLines[j] = null;
			}
			
			//System.out.println(i + "st run");
			for (String sentence : splitLines) {
				//System.out.println(sentence);
				if(sentence!=null)				//sentence may be null if the current sentence doesn't have any deliminators
				{					
					sentences.add(sentence);	//temp
					setninger.put(i, sentence);

					i++;
				}

			}

		}

	}
	
	// retrieve words from the sentence list 
	public void getWords(){
		 Collection<String> words = setninger.values();
		 
		 String[] wordsForCurrentSentence;
		 // loop through every sentence
		 for (String str : words) {
			  wordsForCurrentSentence = str.split("([^\\wæøåÆØÅ']+)");	// split for every non-word (including æøå and ')
			 
			 // for every sentence, add every word
			 for (String word : wordsForCurrentSentence) {
				 dirtyWordList.add(word.toLowerCase()); 		// .toLowerCase()
			}

			 
		}
	}
	
	// clean up word-list by removing stop-words
	
	public void removeStopWords(String language){
		//if(language.equals("NO")){
			List<String> stopWords = FileHandler.readFile("files/stopwords-en.txt");
			cleanWordList = new ArrayList<>(dirtyWordList);	// copy array
			for (String word : dirtyWordList) {
				if(stopWords.contains(word))
					cleanWordList.remove(word);
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
//		System.out.println("----------Array List-----------");		
//		// print
//		for(int i=0; i<sentences.size(); i++){
//			System.out.println(i + " " + sentences.get(i));
//		}
		
		
		System.out.println("\n----------Tree Map-----------");
		// print
		for(int i=0; i<setninger.size(); i++){
			System.out.println(i + " " + setninger.get(i));
		}
		
		System.out.println("\n----------Word list-----------");
		// print
		for(int i=0; i<dirtyWordList.size(); i++){
			System.out.println(i + " " + dirtyWordList.get(i));
		}
		
		System.out.println("\n----------Word list after removing stop words-----------");
		// print
		for(int i=0; i<cleanWordList.size(); i++){
			System.out.println(i + " " + cleanWordList.get(i));
		}
		

	}
}

//TODO: Add text about what's happening. e.g.	 "Now converting words to lower case..."
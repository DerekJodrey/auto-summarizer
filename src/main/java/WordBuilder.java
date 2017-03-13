package main.java;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
/**
 * Takes sentenceObjects and splits them into Word-objects. These holds lists both with and without stop-words.
 * @author Piraveen
 *
 */
public class WordBuilder {
	private List<Word> dirtyWordObjects = new ArrayList<Word>();							// Word-objects with stop-words
	private ArrayList<Word> cleanWordObjects;												// Word-objects without stop-words	
	private LinkedHashMap<Word, Integer> freqMap = new LinkedHashMap<Word, Integer>();		// Word:Frequency-map 			why use linkedhashmap?   order = insertion-order
	private static ArrayList<Word> maxWordList = new ArrayList<>();

	// empty constructor
	public WordBuilder(){
		//FIXME: Need to ignore numbers
	}

	// retrieve words from the sentence list
	/**
	 *  Turn the sentences in {@link SentenceBuilder#sentenceObjects sentenceObjects} to a list of Word-objects. Since this is done with custom
	 *  Word-objects, each Word has info about which sentence it was affiliated with. 
	 *  Regular expressions are used to split word, and they're handled differently for each languages. 
	 */
	public List<Word> getWords(String language, String path){		 
		// get list of sentence objects from SentenceBuilder
		//SentenceBuilder sb = new SentenceBuilder();
		ArrayList<Sentence> sentenceObjects = SentenceBuilder.getSentenceObjects();

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
				Word w = new Word(word.toLowerCase(), sentenceNo);
				dirtyWordObjects.add(w); 	
			}

		}
		return dirtyWordObjects;
	}
 
	
	/**
	 * Clean up word-list by removing stop-words. 
	 * {@link SentenceBuilder#dirtyWordObjects dirtyWordObjects} is the input, and a clean list without stop-words will be found in
	 * {@link SentenceBuilder#cleanWordObjects cleanWordObjects}.  
	 * @param language sets stop-word file to be used. Set <i>"NO"</i> for norwegian (bokmål) or <i>"EN"</i> for english. 
	 */
	public ArrayList<Word> removeStopWords(String language){
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
				// remove stop-words
				cleanWordObjects.remove(word);										
			}

		}

		return cleanWordObjects;

	}


	// count occurrence of each word
	public LinkedHashMap<Word, Integer> doCount(ArrayList<Word> list){
		int freq;
		Word currentWord;

		for (int i = 0; i < list.size(); i++) {
			currentWord = list.get(i);
			
			// only run code IFF the key doesn't already exist in the freqMap
			if(!freqMap.containsKey(currentWord)){
				// check frequency of the given string
				freq = Collections.frequency(list, currentWord);	
				freqMap.put(currentWord, freq);	// (word, frequency)
				
				currentWord.setWordOccurence(freq);
				
			}

		}

		return freqMap;
	}
	
	/**
	 * Find top n words that occur the most in the document. 
	 * @param nTopEntries number of words to find
	 */
	public void findTopNWords(int nTopEntries /* boolean nonConsecutive*/){
		if(nTopEntries == 0){
			System.err.println("Can't be 0.");
			return;
		}
		else if(nTopEntries > freqMap.size()){
			System.err.println("Entry number higher than number of total entries.");
			System.err.println("Aborting...");
			return;
		}
			
		
		Word tempWord = null;
		List<Word> keys = new ArrayList<>(freqMap.keySet());
		
		// put final results in here
		maxWordList = new ArrayList<>();
		
		// hente ut n antall entries
		//for (int i = 0; i < nTopEntries; i++) {
		int counter=0;
		while(counter!=nTopEntries){
			Word maxWord = null;
			
			// loop gjennom listen for å finne maks
			for (Word word : keys) {
				if (tempWord == null || word.compareTo(tempWord) > 0)
			    {
					tempWord = word;
					
					
			    }
			}
			// after finding top word for this run in the for-loop
			maxWord = tempWord;
			
			if(!checkSameSentenceNo(maxWordList, maxWord)){
				maxWordList.add(maxWord);
				counter++;
			}
			
			//remove max-word to find second max-word and so on..
			keys.remove(maxWord);		
			//reset 
			tempWord = null;			
			maxWord = null;
			
			
		}
		System.out.println("-------------------------------------------");
		for (int i = 0; i < maxWordList.size(); i++) {
			System.out.format("'%s' is max with %d occurences. It belongs to sentence %d.\n", 
					maxWordList.get(i).getWordText(), maxWordList.get(i).getWordOccurence(), maxWordList.get(i).getBelongingSentenceNo());
		}
				
	}
	
	// check if entries in list has same sentence no.
	private boolean checkSameSentenceNo(ArrayList<Word> list, Word w){
		if(w==null)
			return false;
		
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getBelongingSentenceNo() == w.getBelongingSentenceNo())
				return true;
		}
		
		return false;
	}
	
	public static ArrayList<Word> getMaxWordList(){
		return maxWordList;
	}
	
	// used for debugging
	public void printMap(){
		System.out.println("\n----------Each word and number of occurences-----------");
		// print
		Set<Word> keySet = freqMap.keySet();										// get the unique keys (Word-object in this case)
		Word[] uniqueKeys = keySet.toArray(new Word[keySet.size()]);				// create an array
		for (Word string : uniqueKeys) {
			int frequency = freqMap.get(string);
			//System.out.println("Word: " + string.getWordText() + "\t\t Occurences: " + frequency);
			System.out.printf("Word: %-25s Occurences: %-10d ...belongs to sentence %d\n", string.getWordText(), frequency, string.getBelongingSentenceNo());
			
		}
		System.out.println("Size of keyset is " + keySet.size());
	}
}












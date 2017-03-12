import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class WordBuilder {
	private List<Word> dirtyWordObjects = new ArrayList<Word>();							// Word-objects with stop-words
	private ArrayList<Word> cleanWordObjects;												// Word-objects without stop-words	
	private LinkedHashMap<Word, Integer> freqMap = new LinkedHashMap<Word, Integer>();		// 	why use linkedhashmap?   order = insertion-order


	// empty constructor
	public WordBuilder(){

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

		for (int i = 0; i < list.size(); i++) {
			// only run code IFF the key doesn't already exist in the freqMap
			if(!freqMap.containsKey(list.get(i))){
				// check frequency of the given string
				freq = Collections.frequency(list, list.get(i));	
				freqMap.put(list.get(i), freq);	// (word, frequency)
				
			}

		}

		return freqMap;
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

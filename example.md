## Usage
We want to get a 5-line summary of [file-medium_en.txt](./files/file-medium_en.txt) which discusses the Life Cycle Assessment on a iPhone 6.  
`main.java`:

```java	

public class Main {

	public static void main(String[] args) {
		
		String FILEPATH = "files/file-medium_en.txt";	// the file you want to summarize
		String LANGCODE = "EN";				// "NO"=norwegian or "EN"=english (decides which stop-word dict to use)				
		int LENGTH = 5;					// Summary length
		
		// read file and create Sentence objects
		SentenceBuilder sb = new SentenceBuilder(LANGCODE, FILEPATH);		
		
		// split to Word objects, remove stop words and count frequency of each unique word 
		WordBuilder wb = new WordBuilder();
		wb.getWords(LANGCODE, FILEPATH);
		wb.removeStopWords(LANGCODE);	
		wb.doCount(wb.getCleanWordObjects());
		
		// debugging
		//DebugClass.printInfo();
		DebugClass.printFreqMap();
		DebugClass.printStats();
		
		// find top N words from N different sentences
		wb.findTopNWords(LENGTH);
		
		// sort top N words with regards to belonging sentence no
		Summarizer sumrizr = new Summarizer();
		sumrizr.sortTopNWordList();
		
		// finally, generate the summary
		DebugClass.printTopNWords();
		sumrizr.createSummary();
		
		}

}
```

## Output:

```
----------------Stats---------------------
Number of lines:                         23
Number of sentences:                     53
Number of words:                         918
Number of stop-words removed:            486
Number of words without stop-words:      432
Number of unique words w/o stop-words:   271

------------------Summary-------------------
In this report, the reader will be presented a life-cycle assessment on the iPhone 6 manufactured by Apple.
The reader will be presented both positive and negative aspects of the product.
The phone is equipped with modern features, both hardware and design wise, yet at what cost.
While in use, the phone will require energy for recharging.
A way to avoid unnecessary emissions caused from this could be by offering a new pair of earplugs only to users whom have never owned an iPhone before.

Text was reduced to 5 sentences. 
```
package test;

import static org.junit.Assert.*;

import org.junit.Test;
import main.java.Word;



public class WordBuilderTest {
	
	@Test
	public void findTopNWords_Test() {
		Word w1 = new Word("hello", 0);
		w1.setWordOccurence(10);
		
		Word w2 = new Word("world", 0);
		w2.setWordOccurence(5);
		
		assertEquals(w1.compareTo(w2), 1);
	}

}

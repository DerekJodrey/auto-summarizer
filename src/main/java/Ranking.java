package main.java;
import java.util.ArrayList;

public class Ranking {
	ArrayList<Integer> stats;
	public Ranking(){
		String FILEPATH = "files/file_en.txt";
		SentenceBuilder sb = new SentenceBuilder("EN", FILEPATH);		//"NO" for norwegian(bokmål) or "EN" for english
		stats = sb.getStats();											// # of lines, sentences, words etc. 
	}
	// Weight / Points for every word
	
	
	
	//TODO: Given X amount of sentences, the function will get top X number of words used in the text. 
	// There is also the option to set if the words can be consecutive. 
	// Has to create a function inside SentenceBuilder() to retrieve entries from the freqMap. 
	// compare() probably has to be overwritten
	

	
	
	
	
}
//How autotldrbot works: https://www.reddit.com/r/askscience/comments/4s5b5q/how_exactly_does_a_autotldrbot_work/
//TODO: Refactoring - make methods and variables private
//TODO: Unit testing - JUnit. 
//TODO: EMMA - Open-source code coverage tool for Java
//TODO: Lage Python script som tar en hel tekst og gir en linebreak hvis en setning er større enn x antall tegn. 

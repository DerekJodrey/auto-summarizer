import java.util.ArrayList;

public class Ranking {
	
	public Ranking(){
		String FILEPATH = "files/file_en.txt";
		SentenceBuilder sb = new SentenceBuilder("EN", FILEPATH);		//"NO" for norwegian(bokmål) or "EN" for english
		ArrayList<Integer> stats = sb.getStats();
	}
}

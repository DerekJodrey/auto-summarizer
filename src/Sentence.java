
public class Sentence {
	private String text;
	private int sentenceNo;
	private static int nextN=0;
	
	
	public Sentence(String text){
		this.text = text;
		this.sentenceNo = nextN++;
	}
	
	@Override
	public String toString(){
		return "No. " + this.sentenceNo + "\t Text: " + this.text;
	}
	
	public String getText(){
		return this.text;
	}
	
	public int getSentenceNo(){
		return this.sentenceNo;
	}
	
	
}


public class Sentence {
	private String text;
	private int n;
	private static int nextN=0;
	
	public Sentence(String text){
		this.text = text;
		this.n = nextN++;
	}
	
	@Override
	public String toString(){
		return "No. " + this.n + "\t Text: " + this.text;
	}
	
	public String getText(){
		return this.text;
	}
}

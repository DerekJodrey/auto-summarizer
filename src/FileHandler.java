import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileHandler {
	private static List<String> lines = null;	//an entry for every line
	
	/**
	 * Constructor. 
	 * @param filePath
	 */
	public FileHandler(String filePath){
		//readFile(filePath);
	}
	
	/**
	 * Reads file and creates a new entry for every line in the arraylist {@link#lines lines.}   
	 * @param filePath relative path of the desired file to be bread
	 * @return
	 */
	public static List<String> readFile(String filePath){
			
		
		// create path for src-file
		Path sourceFile = Paths.get(filePath);
		Charset charset = Charset.forName("ISO-8859-1");
		
		try {
			 lines = Files.readAllLines(sourceFile, charset);
			 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return lines;
	}
	

	/**
	 * Print everything in the file.
	 */
	public static void printAll(){
		// print all lines
		for(String s: lines){
			System.out.println(s);
		}
		
		//System.out.println(Charset.availableCharsets().toString());
	}
	
}

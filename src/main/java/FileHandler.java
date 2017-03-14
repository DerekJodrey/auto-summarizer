package main.java;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileHandler {
	
	/**
	 * Empty constructor.
	 */
	public FileHandler(){}
	
	
	/**
	 * Reads file and creates a new entry for every line in the arraylist {@link#lines lines.}   
	 * @param filePath relative path of the desired file to be bread
	 * @return
	 */
	public static List<String> readFile(String filePath){
		// one entry for every line	
		List<String> lines = null;	
		
		// create path for src-file
		Path sourceFile = Paths.get(filePath);
		Charset charset = Charset.forName("ISO-8859-1");
		
		//TODO: Use try-with-resource to auto-close resource
		
		try {
			 lines = Files.readAllLines(sourceFile, charset);
			 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return lines;
	}
	
	
}

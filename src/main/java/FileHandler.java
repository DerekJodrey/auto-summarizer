package main.java;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
/**
 * Responsible for turning raw text into something the application can work with in memory. 
 * @author Piraveen
 *
 */
public class FileHandler {
	
	/**
	 * Empty class constructor.
	 */
	public FileHandler(){}
	
	
	/**
	 * Reads a file and creates a new entry for every line.    
	 * @param filePath either relative or absolute filepath
	 * @return list of raw lines
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

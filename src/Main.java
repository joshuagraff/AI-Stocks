import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Main {
	public static void main(String... aArgs) throws IOException{
		Sanitize text = new Sanitize();
		Path sanitized = Paths.get("sanitizedStocks.txt");
		boolean doesExist = Files.isRegularFile(sanitized) &
		         Files.isReadable(sanitized) & Files.isExecutable(sanitized);
		if(!doesExist){
			text.readWriteSanitizedFile("sp500hst.txt");
			sanitized = Paths.get("sanitizedStocks.txt");
		}
		text.getStockMapping("sanitizedStocks.txt", "ixic.csv");
		
	}
}

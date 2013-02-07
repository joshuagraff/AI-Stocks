import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;


public class Sanitize {
	
	ArrayList<String> dowCompanies = new ArrayList<String>(Arrays.asList(
			",MMM," , ",AA," , ",AXP," , ",T," , ",BAC," , ",BA," , ",CAT," , ",CVX," , ",CSCO," , ",KO," ,
			",DD," , ",XOM," , ",GE," , ",HPQ," , ",HD," , ",INTC," , ",IBM," , ",JNJ," , ",JPM," , ",MCD," , 
			",MRK," , ",MSFT," , ",PFE," , ",PG," , ",TRV," , ",UNH," , ",UTX," , ",VZ," , ",WMT," , ",DIS," 
			));

	
	
	
	
	void readWriteSanitizedFile(String aFileName) throws IOException {
	    Path path = Paths.get(aFileName);
	    try (BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())){
	    	Path newPath = Paths.get("sanitizedStocks.txt");
	    	try (BufferedWriter writer = Files.newBufferedWriter(newPath, Charset.defaultCharset())){
	    		String line = null;
	  	      boolean foundDow = false;
	  	      while ((line = reader.readLine()) != null) {
	  	    	  foundDow = false;
	  	    	  for(int i = 0; i < dowCompanies.size(); i++){
	  	    		  if(line.contains(dowCompanies.get(i))){
	  	    			  foundDow = true;
	  	    			  break;
	  	    		  }
	  	    	  }
	  	        if(foundDow){
	  	        	//System.out.println(line);
	  	        	writer.write(line);
	  	        	writer.newLine();
	  	        }
	  	      }
	  	      writer.close();
	    	} 
	    	reader.close();
	    }
	  }
	
	public HashMap<String, ArrayList<String> > getStockMapping(String stocks, String dow) throws IOException{
		HashMap<String, Dow >dateHash = new HashMap<String, Dow>();
		HashMap<String, ArrayList<String> >stockHash = new HashMap<String, ArrayList<String>>();
		Path stocksFile = Paths.get(stocks);
		Path dowFile = Paths.get(dow);
		
		//Read in the dow file and create a hash where key=date
		try (BufferedReader reader = Files.newBufferedReader(dowFile, Charset.defaultCharset())){
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] lineToken = line.split(",");
				float change = 9999;
				try{
					change = Float.parseFloat(lineToken[1])-Float.parseFloat(lineToken[4]);
				}catch(Exception e){System.out.println("Error: "+e.getMessage());}
				Dow todaysDow = new Dow(line.substring(0,8), change);
				
				dateHash.put(line.substring(0, 8), todaysDow);
			}
		}
		//Populate the dates with appropriate stocks
		try (BufferedReader reader = Files.newBufferedReader(stocksFile, Charset.defaultCharset())){
			String line = null;
			while ((line = reader.readLine()) != null) {
				Dow curVal = dateHash.get(line.substring(0, 8));
				if(curVal == null){System.out.println("Error: No dow found for date "+line.substring(0,8));}
				String[] lineToken = line.split(",");
				String stockName = lineToken[1];
				float change = 9999;
				try{
					change = Float.parseFloat(lineToken[2])-Float.parseFloat(lineToken[5]);
				}catch(Exception e){System.out.println("Error: "+e.getMessage());}
				curVal.addStock(stockName, change);
				dateHash.put(line.substring(0, 8), curVal);
			}
		}
		Object[] values = dateHash.values().toArray();
		for(int i = 0; i < dateHash.size(); i++){
			System.out.println(values[i]);
		}
		//System.out.println(dateHash);
		return stockHash;
		
	}
	
}

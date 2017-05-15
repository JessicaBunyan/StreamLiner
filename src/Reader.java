import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.lang3.math.NumberUtils;

import com.opencsv.CSVReader;

public class Reader {

	
	private String[] expectedHeaders = new String[]{"Phase", "Wave", "Pool", "Pool Seed", "Phase Seed", "Pool Placement", "Event Placement", "Entrant", "ID", "Player Name", "Player GamerTag", "Player Short GamerTag"};
	
	
		

	
	
	public ArrayList<Player> read(File f){
		
		CSVReader reader = null;
		int i = 0;
		int numPools =0;
		int numWaves =1;
		int numPlayers = 0;
		ArrayList<String[]> playersText = new ArrayList<String[]>();
			

		
		ArrayList<Player> players= new ArrayList<Player>();
		
		
		
		try {
			reader = new CSVReader(new FileReader(f));
			String[] nextLine;
			String[] headers;
			
			
			headers = reader.readNext();
			
			
			validateCSVHeaders(headers);
			
			while ((nextLine = reader.readNext()) != null){

				numPlayers++;
				
				
				
				
				playersText.add(nextLine);
				
//				System.out.println(nextLine.toString());
				
			}
			

			System.out.println("There are " + numPlayers + " players");
			System.out.println("There are " + numPools + " pools");
			System.out.println("There are " + numWaves+ " waves");
			
			
			
			
			for (String[] ar: playersText){
				players.add(new Player(ar));
			}
			
			reader.close();
			return players;
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;// if exception thrown
		
	}
	
	public void validateCSVHeaders(String[] headers) throws IOException{
		
		if (headers.length != expectedHeaders.length){
			throw new IOException();
		}
		
		for (int i = 0; i< headers.length; i++){
			if (!headers[i].equals(expectedHeaders[i])){
				throw new IOException();
			}
		}
		
		
		
	}
	
	
	

	
}

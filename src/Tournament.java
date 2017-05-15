import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Tournament {

	
	ArrayList<Pool> pools = new ArrayList<Pool>();
	ArrayList<Player> players = new ArrayList<Player>();
	
 	private int totalRounds; 
 	private int TVs;//TODO fix 
 	private boolean letterIdentifiers = true;
 	private Calendar startTime;
 	

	
	
	
	public Tournament	(File source, File destination) throws HeadersException, IOException{
		
		startTime = Calendar.getInstance();
		startTime.set(Calendar.HOUR_OF_DAY, 12);//TODO fix hardcoding
		startTime.set(Calendar.MINUTE, 00);
		
		
	
		Reader r = new Reader();
		players = r.read(source);
		
		int numWaves = 1;
		int numPools =0;
		
		
		for (Player p: players){
			if (numPools < p.getPoolNum()){
				numPools = p.getPoolNum();
			}			
		}
		
		for (int i =0; i<numPools; i++){
			pools.add(new Pool(i));
		}
		
		System.out.println("size of pools array");
		System.out.println(pools.size());
		
			
		for (Player p: players){
//			System.out.println(p.getPoolNum()-1);
//			pools.get(p.getPoolNum()-1).toString();
			pools.get(p.getPoolNum()-1).add(p);//add players to their pool
		}
				
		
		
		System.out.println("test");
		int i=0;
		
		for (Pool p: pools){
			i = i++;
			System.out.println(p.toString());
			System.out.println(p.generateMatches());
			
			p.printMatches();
			
//			System.out.println("pool # " + i);
//			System.out.println(p.getMatchTable());
			
//			break;
		}
		
		
		
		
		new PDFoutput(this, destination);
		
		
		
		
		
		
	}
	
	public ArrayList<Pool> getPools(){
		return pools;
	}
	public String getRoundStartTime(int round, int roundDuration){
		
		Calendar newCal = (Calendar) startTime.clone();
		newCal.add(Calendar.MINUTE, (round-1)* roundDuration);
		
		SimpleDateFormat sf = new SimpleDateFormat("hh:mm");
		System.out.println(sf.format(newCal.getTime()));
		return sf.format(newCal.getTime());
		
	}
	
	
	
		


 class Pool {

 	private ArrayList<Player> players;
 	private ArrayList<Match> matches;
 	private int poolNum;
 	private Match[][] table;
 	
 	public Pool(int num){
 		
 		players = new ArrayList<Player>();
 		poolNum = num;
 		
 					
 	}
 	
 	
 	public String toAlphabetic(int i) {
 	    if( i<0 ) {
 	        return "-"+toAlphabetic(-i-1);
 	    }

 	    int quot = i/26;
 	    int rem = i%26;
 	    char letter = (char)((int)'A' + rem);
 	    if( quot == 0 ) {
 	        return ""+letter;
 	    } else {
 	        return toAlphabetic(quot-1) + letter;
 	    }
 	}
 	

 	public String getPoolIdentifier(){
 		if (letterIdentifiers){
 			return toAlphabetic(poolNum);
 		}
 		return Integer.toString(poolNum +1);
 	}
 	
 	
 	public int getNumberofMatches(){
 		return matches.size();
 	}
 	public int getNumberOfRounds(){
 		return totalRounds;
 	}
 	
// 	public Sheet getSheet(){
// 		
// 		
// 		generateMatches();
// 		generateRounds();
// 			
// 	}
 	
 	public Match getMatch(int matchNum){
// 		System.out.println(matchNum % 5);
// 		System.out.println(matchNum / 5);
 		
 		return table[matchNum%5][matchNum/5];
 	}
 	
 	public Match[][] getMatchTable(){
 		return table;
 	}
 	
 	public int generateMatches(){
 		
 		matches=new ArrayList<Match>();
 		
 		if (players.size() %2 == 1){
 			players.add(new Player("BYE"));
 		}
 		
 		
 		
 		
 		for (int i= 0; i< players.size(); i++){
 			for (int j=i+1; j<players.size(); j++){
 				matches.add(new Match(players.get(i), players.get(j)));
 			}
 		}
 		
 		TVs = players.size() /2;
 		totalRounds = matches.size() / TVs;
 		
 		
 		if (poolNum < totalRounds){
 			matches.get(0).setStreamed(true);//the 1st seed vs 2nd seed match is always the first one (0th), so thats the game that goes to stream
 		}
 				
 		generateRounds();
 		
 		
 		
 		
 		
 		return totalRounds;
 		
 	}
 	
 	public void generateRounds(){
 		//TODO Hardcoded for 6, adjust to work for 8/10
 		
 		
 		int[] sixManMapping = new int[] {0, 1, 2, 3, 4, 
 										10, 8,5,6, 7, 
 										13,12,14,11,9}; //this maps the matches in the creation order to the output order

 		table = new Match[5][3];

 		for (int row =0; row < 3; row++){

 			for (int col = 0; col<5; col++){
 				
 				int actualCol = (col+poolNum)%5;//this will "wrap around" the table
 				
 				
 				System.out.println(sixManMapping[row*5 + col]);
 				System.out.println(matches.get(sixManMapping[row*5 + col]));
 				table[actualCol][row] = matches.get(sixManMapping[row*5 + col]);
 			}
 		}

// 		print();
// 		new PDFoutput(this);
 		
// 		Sheet sheet = new Sheet(matches, poolNum);
 		
 		
 		
 		
 		
 		
 		
 		
 	}
 	
 	
 	
 	public void printMatches(){
 		
 		for (Match m : matches){
 			System.out.println(m.toString());
 		}
 		
 		
 	}
 	
 	
 	public void add(Player p){
 		
// 		System.out.println(p.toString());
 		players.add(p);
 		
 	}
 	
 	public void add(String playername){
 		players.add(new Player(playername));
 		
 		
 	}
 	
 	public ArrayList<Player> get(){
 		return players;
 	}
 	
 	public ArrayList<String> getNames(){
 		
 		ArrayList<String> names = new ArrayList<String>();
 		
 		for (Player p : players){
 			names.add(p.getName());
 		}
 			
 		return names;
 		
 	}
 	
 	
 	
 	
 	@Override
 	public String toString(){
 		
 		String ret = "";
 		for (Player p : players){
 			ret = ret + p.getName() + ", ";
 		}
 		
 			
 			return ret;
 		
 	}
 	}
 
}

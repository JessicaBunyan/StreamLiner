
public class Match {

	
	private final String player1;
	private final String player2;
	private boolean isStreamed;
	
	
	
	public Match(String p1, String p2){
		
		
		player1 = p1;
		player2 = p2;		
		isStreamed=false;
		
	}
	
	
	
	
	
	public boolean isStreamed(){
		return isStreamed;
	}
	public void setStreamed(boolean b){
		isStreamed = b;
	}
	
	
	
	public Match(Player p1, Player p2){
		
		
		player1 = p1.getName();
		player2 = p2.getName();		
		
	}
	
	
	@Override
	public String toString(){
		return player1 + "\nvs\n" + player2;
	}
	
	
}

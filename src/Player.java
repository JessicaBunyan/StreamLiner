import java.io.IOError;
import java.io.IOException;

import org.apache.commons.lang3.math.NumberUtils;

public class Player {

	private String name;
	private int poolNum;
	private int poolSeed;

	

	public Player(String[] args) throws IOException{
		
		name = args[7];
		poolNum = NumberUtils.toInt(args[2]);
		poolSeed = NumberUtils.toInt(args[3]);
		
		if (poolNum == 0 || poolSeed == 0){
			throw new IOException();
		}
		
	}
	
	public Player(String name){
		this.name=name;
	}
	
	public String getName(){
		return name;
	}
	
	public int getPoolNum(){
		return poolNum;
		
	}
	
	public int getPoolSeed(){
		return poolSeed;
	}
	
	
}

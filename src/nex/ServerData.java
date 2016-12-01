package nex;

import java.io.PrintWriter;
import java.util.HashSet;

public class ServerData {

	
	int p1X, p1Y;
	int p2X, p2Y;
	volatile boolean playerOne, playerTwo;
    volatile HashSet<PrintWriter> playerWriters;

	
	public ServerData(){
		p1X = 0; p1Y = 0;
		p2X = 0; p2Y = 0;
		playerOne = false;
		playerTwo = false;
		playerWriters = new HashSet<PrintWriter>();
	}


}

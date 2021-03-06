package nex;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import jig.Vector;

public class ClientHandler extends Thread {
	
	DataInputStream in;
	DataOutputStream out;
	
	
	public void run(){

		//setup socket and connect to server
//		String serverAddress = ConnectState.serverAddress;
		Socket socket = null;
		try {
			
			socket = new Socket(ConnectState.serverAddress, 1201);
			in = new DataInputStream(socket.getInputStream());
		    out = new DataOutputStream(socket.getOutputStream());
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//gather whatever player number this player happens to be
		try {
			PlayingState.playerNumber = in.read();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//if its the first player send the current collision setup
		if(PlayingState.playerNumber == 1){
			for(int i = 0; i < 40; i++){
				for(int j = 0; j < 40; j++){
					try {
						out.write(PlayingState.tileSet[j][i].getCollision());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		//write to server until connection is terminated
		while (true) {
	
					
			try {
				
			    out.writeInt(PlayingState.player1x);
			    out.writeInt(PlayingState.player1y);

			    out.writeInt(Player.health);
			    out.writeInt(PlayingState.goldAcquired);
			    out.writeInt(PlayingState.currentLevel);
			    out.writeInt(PlayingState.currentFloor);
			    
			    PlayingState.otherPlayerHealth = in.readInt();
			    PlayingState.otherGoldAcquired = in.readInt();
			    PlayingState.otherPlayerCurrentLevel = in.readInt();
			    PlayingState.otherCurrentFloor = in.readInt();
			   
			    PlayingState.numberOfPlayers = in.read();
			    if(PlayingState.playerNumber == 1){
			    
			    	int p1x = in.readInt();
				    int p1y = in.readInt();	    
				    PlayingState.otherPlayerX = in.readInt();
				    PlayingState.otherPlayerY = in.readInt();
			    
			    } else {
			    
			    	PlayingState.otherPlayerX = in.readInt();
				    PlayingState.otherPlayerY = in.readInt();
				  	int p1x = in.readInt();
				    int p1y = in.readInt();
			    
			    }
			    			    
			    for (EnemyCharacters e : PlayingState.monsters)
				{
			    	out.writeInt(e.damage);
			    	e.damage = 0;
			    	e.aliveOrDead = in.readInt();
			    	e.movingDirection = in.readInt();
			    	
			    	out.writeInt(e.attackOrIdle);
			    	e.attackOrIdle = in.readInt();
			    	int somex = in.readInt();
			    	int somey = in.readInt();
			    	//System.out.println("x: " + somex + " y: " + somey);
					e.setX(somex-PlayingState.offsetX);
					e.setY(somey-PlayingState.offsetY);	
					e.setY(somey-PlayingState.offsetY);	
			    	//System.out.println("x: " + e.getX() + " y: " + e.getY());
					e.setTilePosition(new Vector((int)Math.floor(( (somey+1268-300-33)/65)), (int)Math.floor(( (somex+1268-400-33) /65))) );
				}

					
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        }
    
	} 
}

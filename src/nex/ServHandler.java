package nex;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;


public class ServHandler extends Thread {
	
	Socket userSocket;
	ServerSocket server;
	ServerData data;
	DataOutputStream out;
	DataInputStream in;
	int playerSpot = 0;
	ServerMain frame;
	int updateGraph;
	
	
	public ServHandler(Socket passedSocket, ServerData d, ServerMain f) {	
		userSocket = passedSocket;					//assigns the user socket to the object
		data = d;									//passes server data to the handler for user
		frame = f;
	}
	

	public void run(){
			
		//Creates a print writer that connects to the sockets output stream
		try {
			in = new DataInputStream(userSocket.getInputStream());
		    out = new DataOutputStream(userSocket.getOutputStream());
			
			if(!data.playerOne) {
				data.playerOne = true;
				playerSpot = 1;
				frame.p1Connect.setText("Connected");
			} else if (!data.playerTwo){
				data.playerTwo = true;
				playerSpot = 2;
				frame.p2Connect.setText("Connected");
			} else {
				//out.println("No player spots available!");
				out.flush();
				return;
			}
			data.numberOfPlayers++;
			//data.playerWriters.add(out);
			
			//let the player know which spot he takes
			out.write(playerSpot);
			
			
			// collect collision information
			if(playerSpot == 1) {
				for(int i = 0; i < 40; i++){
					for(int j = 0; j < 40; j++){
						int collision = in.read();
//						System.out.println(collision);
						ServerData.tileSet1.add(collision);
					}
				}
				ServerData.buildTileSet();
			}

			int delta = 0;

			// This should be where the server gets input and updates server data and then outputs back to user
			while (true) {

				if(playerSpot == 1) {
					for(int i = 0; i < 40; i++){
						for(int j = 0; j < 40; j++){
							int collision = in.read();
//							System.out.println(collision);
							ServerData.tileSet1.add(collision);
						}
					}
					ServerData.buildTileSet();
				}
				
				if(playerSpot == 1){
					 data.p1X = in.readInt();
					 data.p1Y = in.readInt();
					 data.p1Health = in.readInt();
					 data.p1Gold = in.readInt();
					 data.p1Level = in.readInt();
					 data.p1Floor = in.readInt();
					 
					 out.writeInt(data.p2Health);
					 out.writeInt(data.p2Gold);
					 out.writeInt(data.p2Level);
					 out.writeInt(data.p2Floor);
				} else {
					 data.p2X = in.readInt();
					 data.p2Y = in.readInt();
					 data.p2Health = in.readInt();
					 data.p2Gold = in.readInt();
					 data.p2Level = in.readInt();
					 data.p2Floor = in.readInt();

					 out.writeInt(data.p1Health);
					 out.writeInt(data.p1Gold);
					 out.writeInt(data.p1Level);
					 out.writeInt(data.p1Floor);
				}
               
                out.write(data.numberOfPlayers);
                out.writeInt(data.p1X);
                out.writeInt(data.p1Y);
                out.writeInt(data.p2X);
                out.writeInt(data.p2Y);

                    
                for (ServerEnemyData e : ServerData.monsters) {
                	int damage = in.readInt();
                	e.health -= damage;
                	if(e.health <= 0){
                		out.writeInt(0);
                	} else {
                		out.writeInt(1);
                	}
                	out.writeInt(e.getDirectionMovement());
                	out.writeInt((int)e.getMapPosition().getX());
                	out.writeInt((int)e.getMapPosition().getY());
             	}
    		
                out.flush();
                frame.updateFrame();
                
                if(delta < 0)
                {
                	ServerData.updateEnemies();
                	delta = 5;
                }
                else{
                	delta--;
                }
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            if (out != null) {
               // data.playerWriters.remove(out);
            }
            try {
                if(playerSpot == 1){
                	frame.p1Connect.setText("Not Connected");
    				data.playerOne = false;
                } else if (playerSpot == 2){
                	frame.p2Connect.setText("Not Connected");
    				data.playerTwo = false;
                }
                data.numberOfPlayers--;
                userSocket.close();
            } catch (IOException e) {
            }
        }	
		
	}	
}




//Developed ideas based on the information at http://cs.lmu.edu/~ray/notes/javanetexamples/

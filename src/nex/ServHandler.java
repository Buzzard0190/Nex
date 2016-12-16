package nex;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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
			
			List<Integer> tileSet2 = new ArrayList<Integer>();
			
			// collect collision information
			if(playerSpot == 1) {
				for(int i = 0; i < 40; i++){
					for(int j = 0; j < 40; j++){
//						ServerData.tileSet[j][i] = new Tile();
//						tileSet2[j][i] = in.readInt();
						int collision = in.read();
						
//						ServerData.tileSet2[j][i] = collision;
						
//						tileSet2.add(collision);
						System.out.println(collision);
//						System.out.println(ServerData.tileSet2[j][i]);
						
//						if(collision == 1) {
////							ServerData.tileSet[j][i].setCollision();
//						}
					}
				}
			}

			
			
			// This should be where the server gets input and updates server data and then outputs back to user
			while (true) {

				if(playerSpot == 1){
					 data.p1X = in.readInt();
					 data.p1Y = in.readInt();
					 data.p1Health = in.readInt();
					 data.p1Gold = in.readInt();
					 
					 out.writeInt(data.p2Health);
					 out.writeInt(data.p2Gold);
				} else {
					 data.p2X = in.readInt();
					 data.p2Y = in.readInt();
					 data.p2Health = in.readInt();
					 data.p2Gold = in.readInt();

					 out.writeInt(data.p1Health);
					 out.writeInt(data.p1Gold);
				}
               
                out.write(data.numberOfPlayers);
                out.writeInt(data.p1X);
                out.writeInt(data.p1Y);
                out.writeInt(data.p2X);
                out.writeInt(data.p2Y);

                    
                for (ServerEnemyData e : ServerData.monsters) {
                	out.writeInt((int)e.getMapPosition().getX());
                	out.writeInt((int)e.getMapPosition().getY());
             	}
    		
                out.flush();
                frame.updateFrame();
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

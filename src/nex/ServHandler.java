package nex;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class ServHandler extends Thread {
	
	Socket userSocket;
	ServerSocket server;
	ServerData data;
	DataOutputStream out;
	DataInputStream in;
	int playerSpot = 0;
	ServerMain frame;

	int testme = 0;
	
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
		    
//			out = new PrintWriter(userSocket.getOutputStream());
//			in = new BufferedReader(new InputStreamReader(userSocket.getInputStream()));
			
			
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
			data.playerWriters.add(out);
			out.write(playerSpot);
			// This should be where the server gets input and updates server data and then outputs back to user
			while (true) {

				if(playerSpot == 1){
					 data.p1X = in.readInt();
					 data.p1Y = in.readInt();
				} else {
					 data.p2X = in.readInt();
					 data.p2Y = in.readInt();
				}
               
//                for (DataOutputStream writer : data.playerWriters) {
//                	
                	out.write(data.numberOfPlayers);
                	out.writeInt(data.p1X);
                    out.writeInt(data.p1Y);
                    out.writeInt(data.p2X);
                    out.writeInt(data.p2Y);
                    
//enemy test! up in here up in here
                    
//                    out.writeInt(data.enemyX);
//                    out.writeInt(data.enemyY);
                    
                    for (ServerEnemyData e : ServerData.monsters)
                    {
                    	out.writeInt((int)e.getMapPosition().getX());
	                    out.writeInt((int)e.getMapPosition().getY());
                    }
    				
                	
                    out.flush();
                    
                   
               // }
                frame.updateFrame();
                //System.out.println(data.p1X + "  " + data.p1Y);   
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            if (out != null) {
                data.playerWriters.remove(out);
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

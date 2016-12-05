package nex;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
	
	BufferedReader in;
	PrintWriter out;
	
	
	public void run(){

		String serverAddress = "localhost";
		Socket socket = null;
		try {
			
			socket = new Socket("localhost", 1201);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		while (true) {
			

			try {
				//System.out.println(PlayingState.row + ", " + PlayingState.col);
//				out.print(PlayingState.row);
//				out.print(PlayingState.col);
//				out.flush();
//				int p1x = in.read();
//				int p1y = in.read();

				DataInputStream in = new DataInputStream(socket.getInputStream());
			    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			    
			    out.write(PlayingState.row);
			    out.write(PlayingState.col);
			    
			    int p1x = in.read();
			    int p1y = in.read();
			    
			    //System.out.println(p1x + ", " + p1y);
			    
				PlayingState.updateP1(p1x, p1y);
					
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
//            if (line.startsWith("PlayerNumber")) {
//                //out.println(getName());
//            	//set the players number
//            } else if (line.startsWith("Error")) {
//            	//error if there are too many players....
//            	
//            } else if (line.startsWith("MESSAGE")) {
//                line.substring(8);
//            }
        }
    
	} 
}

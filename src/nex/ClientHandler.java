package nex;

import java.io.BufferedReader;
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
            String line = null;
			try {
				line = in.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
            if (line.startsWith("PlayerNumber")) {
                //out.println(getName());
            	//set the players number
            } else if (line.startsWith("Error")) {
            	//error if there are too many players....
            	
            } else if (line.startsWith("MESSAGE")) {
                line.substring(8);
            }
        }
    
	} 
}

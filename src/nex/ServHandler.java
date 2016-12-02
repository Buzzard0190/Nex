package nex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class ServHandler  extends Thread {
	
	Socket userSocket;
	ServerSocket server;
	ServerData data;
	PrintWriter out;
	BufferedReader in;
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
			
			out = new PrintWriter(userSocket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(userSocket.getInputStream()));
			
			
			if(!data.playerOne) {
				data.playerOne = true;
				playerSpot = 1;
				frame.p1Connect.setText("Connected");
			} else if (!data.playerTwo){
				data.playerTwo = true;
				playerSpot = 2;
				frame.p2Connect.setText("Connected");
			} else {
				out.println("No player spots available!");
				out.flush();
				return;
			}

			data.playerWriters.add(out);
			System.out.println(data.playerWriters.isEmpty());
			System.out.println(data.playerWriters.size());

			
			// This should be where the server gets input and updates server data and then outputs back to user
			while (true) {

                data.p1X = in.read();
                data.p1Y = in.read();
//                System.out.println("Hellllooooooooooooooo");

                for (PrintWriter writer : data.playerWriters) {
                	
                	writer.print(data.p1X);
                    writer.print(data.p1Y);
                    writer.flush();

                }
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
                userSocket.close();
            } catch (IOException e) {
            }
        }	
		
	}	
}


//Developed ideas based on the information at http://cs.lmu.edu/~ray/notes/javanetexamples/

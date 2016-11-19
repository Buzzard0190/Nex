package nex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextArea;

public class ServRead extends Thread {
	
	Socket userSocket;
	ServerSocket server;
	String input = null, username = null;
	PrintWriter printW;
	BufferedReader br;
	
	public ServRead(Socket passedSocket, ServerSocket passedServerSocket) {
		
		userSocket = passedSocket;					//assigns the user socket to the object
		server = passedServerSocket;
	}
	

	public void run(){
		
		//Creates a print writer that connects to the sockets output stream
		try {
			printW = new PrintWriter(userSocket.getOutputStream());
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
		// BufferedReader is for reading the user's input. 
		try {
			br = new BufferedReader(new InputStreamReader(userSocket.getInputStream()));
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
		//print to users here
		try {
			while((input=(br.readLine())) != null){
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}	
}

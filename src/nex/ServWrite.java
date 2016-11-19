package nex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JTextArea;

public class ServWrite extends Thread{
	
	Socket userSocket;
	PrintWriter printW;
	BufferedReader br;
	String input;

	public ServWrite(BufferedReader passedBR){
		
		//userSocket = passedSocket;					//assigns the user socket to the object
		br = passedBR;
	}
	
	public void run(){
			try {
				// Prints username and their text to the server screen.
				
				while((input=(br.readLine())) != null){
						
						input = null;
						
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
				
	}

}

package nex;

import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class ServerMain {

	private static final long serialVersionUID = 1L;
	String username = null, IPAddress = null, sendText = null;
	Socket sock;
	static ServerSocket server;
	static ServRead serv;
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
			}
		});
		
		// creates a server using socket 1201
		try {
			server = new ServerSocket(1201);
			while(true){
				Socket connect = null;
				connect = server.accept();
				serv = new ServRead(connect, server);
				serv.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the frame.
	 */
	public ServerMain() {

		//Collects text from input on user frame.
		//sendText = input.getText();			
				
		// Only sends text to server if text length is greater than 0.
		if(sendText.length() > 0){
			serv.printW.println(sendText);
			serv.printW.flush();
		}

	
	}


}

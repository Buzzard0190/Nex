package nex;

public class ClientMain  {	        

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	
		ClientHandler terminal = new ClientHandler();
		terminal.start();
		System.out.println("Success");
	}


}

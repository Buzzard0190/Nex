package nex;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ConnectState extends BasicGameState {

	private JFrame frame;
	private JTextField textField;
	public static String serverAddress = "localhost";
	boolean connected;
	private static boolean buttonPressed = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConnectState window = new ConnectState();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ConnectState() {
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		
		JLabel lblEnterAddressTo = new JLabel("Enter address to connect to:");
		lblEnterAddressTo.setBounds(56, 66, 182, 16);
		frame.getContentPane().add(lblEnterAddressTo);
		
		textField = new JTextField();
		textField.setBounds(56, 112, 164, 22);
		textField.setText("localhost");
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		Button button = new Button("OK");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Connecting to " + textField.getText() + "...");
				buttonPressed = true;
				serverAddress = textField.getText();
				
				frame.dispose();
				String[] args = new String[0];
				Nex.main(args);
				
			}
		});
		button.setBounds(290, 110, 79, 24);
		frame.getContentPane().add(button);
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub
		connected = false;
	}


	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		
//		Nex nx = (Nex)game;
//		
//		g.drawString("Press SPACE to connect.", nx.ScreenWidth/3, nx.ScreenHeight/2);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// TODO Auto-generated method stub
		Input input = container.getInput();
		
		Nex nx = (Nex)game;
		
		if (!connected && buttonPressed){
			try{
				ClientHandler terminal = new ClientHandler();
				terminal.start();
				
			}
			catch(Exception e){
				System.out.println("Error in starting client: " + e);
				System.exit(-1);
			}
			
			connected = true;
			System.out.println("Success");
			nx.enterState(Nex.PLAYINGSTATE);
		}
		else
		{
			System.out.println("Please use ConnectState to start game.");
			System.exit(-1);
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}
}

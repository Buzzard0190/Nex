package nex;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;


public class ServerMain extends JFrame {

	static ServerSocket server;
	static ServHandler serv;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static ServerData data;
	Socket sock;
	JLabel p1Connect, p2Connect;
	private JTextField textField;
	static ServerMain frame;
	JLabel playerOneX, playerOneY;
	JLabel playerTwoX, playerTwoY;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		System.out.println("Server is running");
		
		data = new ServerData();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new ServerMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		// creates a server using socket 1201
		try {
			server = new ServerSocket(1201);
			while(true){
				Socket connect = null;
				connect = server.accept();
				serv = new ServHandler(connect, data, frame);
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
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel playerOne = new JLabel("Player 1");
		playerOne.setBounds(45, 52, 54, 21);
		contentPane.add(playerOne);
	
		JLabel playerTwo = new JLabel("Player 2");
		playerTwo.setBounds(310, 52, 54, 21);
		contentPane.add(playerTwo);
		
		playerOneX = new JLabel("x:  " + data.p1X);
		playerOneX.setBounds(45, 99, 54, 21);
		contentPane.add(playerOneX);
		
		playerOneY = new JLabel("y:  " + data.p1Y);
		playerOneY.setBounds(45, 118, 54, 21);
		contentPane.add(playerOneY);
		
		playerTwoX = new JLabel("x:  " + data.p2X);
		playerTwoX.setBounds(310, 99, 54, 21);
		contentPane.add(playerTwoX);
		
		playerTwoY = new JLabel("y:  " + data.p2Y);
		playerTwoY.setBounds(310, 118, 54, 21);
		contentPane.add(playerTwoY);
		
		
		if(data.playerOne){
			p1Connect = new JLabel("Connected");
		} else {
			p1Connect = new JLabel("Not Connected");
		}

		p1Connect.setBounds(45, 74, 110, 21);
		contentPane.add(p1Connect);
		
		if(data.playerTwo){
			p2Connect = new JLabel("Connected");
		} else {
			p2Connect = new JLabel("Not Connected");
		}
		
		p2Connect.setBounds(310, 76, 110, 21);
		contentPane.add(p2Connect);
	}

	public void updateFrame(){
		
		
		playerOneX.setText("x: " + data.p1X);
		playerOneY.setText("y: " + data.p1Y);
		
		
		playerTwoX.setText("x: " + data.p2X);
		playerTwoY.setText("y: " + data.p2Y);
	}
}

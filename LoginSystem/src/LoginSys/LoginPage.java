package LoginSys;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPage implements ActionListener {
	private Encryption encrypt;
	JFrame frame = new JFrame();
	JButton loginButton = new JButton("Login");
	JButton resetButton = new JButton("Reset");
	JButton registerButton = new JButton("Register");
	JTextField userIdField = new JTextField();
	JPasswordField userPasswordField = new JPasswordField();
	JLabel userIdLabel = new JLabel("User ID");
	JLabel userPasswordLabel = new JLabel("Password");
	JLabel messageLabel = new JLabel("");
	File getPathFile;
	String currentPath;
	
	HashMap<String,String> loginInfo = new HashMap<String,String>();
	
	LoginPage(HashMap<String,String> loginInfoOriginal){
		
		encrypt = new Encryption();
		
		loginInfo = loginInfoOriginal;
		
		userIdLabel.setBounds(30, 75, 75, 25);
		userPasswordLabel.setBounds(30, 150, 75, 25);
		
		messageLabel.setBounds(125, 250, 250, 75);
		messageLabel.setFont(new Font(null,Font.ITALIC,25));
		
		userIdField.setBounds(100, 75, 250, 25);

		userIdField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {

				if(e.getKeyCode() == 32) {
					userIdField.setEditable(false);
					userIdField.setBackground(Color.red);
					
				}else {
					userIdField.setEditable(true);
					userIdField.setBackground(Color.white);
				}
			}

		});
		
		userPasswordField.setBounds(100, 150, 250, 25);
		userPasswordField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == 32) {
					userPasswordField.setEditable(false);
					userPasswordField.setBackground(Color.red);
					
				}else {
					userPasswordField.setEditable(true);
					userPasswordField.setBackground(Color.white);
				}
			}

		});
		
		loginButton.setBounds(70, 200, 100, 25);
		loginButton.addActionListener(this);
		loginButton.setFocusable(false);
		resetButton.setBounds(170, 200, 100, 25);
		resetButton.addActionListener(this);
		resetButton.setFocusable(false);
		registerButton.setBounds(270, 200, 100, 25);
		registerButton.addActionListener(this);
		registerButton.setFocusable(false);
		
		frame.add(messageLabel);
		frame.add(userIdLabel);
		frame.add(userPasswordLabel);
		frame.add(userIdField);
		frame.add(userPasswordField);
		frame.add(loginButton);
		frame.add(resetButton);
		frame.add(registerButton);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(420,420);
		frame.setLayout(null);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == resetButton) {
			userIdField.setText("");
			userPasswordField.setText("");
		}
		if(e.getSource() == loginButton) {
			String userID = userIdField.getText();
			String password = String.valueOf(userPasswordField.getPassword());
		
			if(loginInfo.containsKey(userID)) { // if file has username 
				if(loginInfo.get(userID).equals(password)) { // if password in file same as the password user inputed
					messageLabel.setForeground(Color.green);
					messageLabel.setText("Login successful");
					frame.dispose(); // exits login screen and goes to the main page
					MainPage mainPage = new MainPage(userID);
				}
			else { // if password does not match the password in file
				messageLabel.setForeground(Color.red);
				messageLabel.setText("Wrong password!");
			}
			}else { // if there is no such username in file
				messageLabel.setForeground(Color.red);
				messageLabel.setText("User name not found!");
			}
	}
		if(e.getSource() == registerButton) {
			String userID = userIdField.getText();
			String password = String.valueOf(userPasswordField.getPassword());
			
			
			
			if(loginInfo.containsKey(userID) || userIdField.getText().isEmpty()) { // if file has username or username field empty
				messageLabel.setForeground(Color.red);
				messageLabel.setText("Username taken!");
			}else if(userID.length() < 3){ // if username shorter than 3 char
				messageLabel.setForeground(Color.red);
				messageLabel.setText("Username must be longer than 3 characters!");
			}else if(password.length() < 3) { // if password shorter than 3 char
				messageLabel.setForeground(Color.red);
				messageLabel.setText("Password must be longer than 3 characters!");
			}
			else{
			
			String data = userID +" "+ password;
			data = encrypt.encrypt(data); // encrypts username and password 
			
			getPathFile = new File("");
			currentPath = getPathFile.getAbsolutePath();
			File file = new File(currentPath+"/bin/LoginSys/IDAndPasswords.txt");

			
			FileWriter fr = null;
			BufferedWriter br = null;
			PrintWriter pr = null;
			try {
				fr = new FileWriter(file, true);
				br = new BufferedWriter(fr);
				pr = new PrintWriter(br);
				pr.println(data); // adds encrypted username and password to file
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				try {
					pr.close();
					br.close();
					fr.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			getPathFile = new File("");
			currentPath = getPathFile.getAbsolutePath();
			File file1 = new File(currentPath+"/bin/LoginSys/userData.txt");

			// opens a the user data file, adds username and gives them 5$
			try {
				fr = new FileWriter(file1, true);
				br = new BufferedWriter(fr);
				pr = new PrintWriter(br);
				pr.println(userID);
				pr.println("5\n");
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				try {
					pr.close();
					br.close();
					fr.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
			userIdField.setText("");
			userPasswordField.setText("");
			frame.dispose();
			
			MainPage mainPage = new MainPage(userID);

			}
		}
	}
	
}

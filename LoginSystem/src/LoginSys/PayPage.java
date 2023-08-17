package LoginSys;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class PayPage implements ActionListener {
	
	JFrame frame = new JFrame();
	JLabel informationLabel;
	JLabel moneyLabel;
	JLabel errorLabel;
	
	JButton okButton;
	JButton backButton;
	
	JTextField userNameField;
	JTextField moneyField;
	
	
	String userName;
	Double userMoney;
	
	
	
	PayPage(String userID, String money){
		
		this.userName = userID;
		this.userMoney = Double.valueOf(money);
		
		informationLabel = new JLabel();
		moneyLabel = new JLabel();
		okButton = new JButton();
		userNameField = new JTextField();
		moneyField = new JTextField();
		backButton = new JButton("BACK");
		errorLabel = new JLabel();
		
		backButton.setBounds(1,1,100,25);
		backButton.addActionListener(this);
		backButton.setFocusable(false);
		
		okButton = new JButton("PAY");
		okButton.setBounds(110,165,125,30);
		okButton.addActionListener(this);
		
		
		errorLabel.setBounds(45, 250, 400, 75);
		errorLabel.setFont(new Font(null,Font.ITALIC,25));
		errorLabel.setFocusable(false);
		errorLabel.setVisible(false);
		
		moneyLabel.setBounds(225,0,200,35);
		moneyLabel.setFont(new Font(null, Font.PLAIN,25));
		moneyLabel.setVisible(true);
		moneyLabel.setFocusable(false);
		setNewMoney();
		
		
		userNameField.setFocusable(true);
		userNameField.setBounds(80,100,200,25);
		
		moneyField.setFocusable(true);
		moneyField.setBounds(80,130,200,25);
		moneyField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				if(ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' || ke.getKeyChar()  == '\b') {
					moneyField.setEditable(true);
					moneyField.setBackground(Color.white);
					
				}else {
					moneyField.setEditable(false);
					moneyField.setBackground(Color.red);
				}
			}

		});
		frame.add(errorLabel);
		frame.add(errorLabel);
		frame.add(okButton);
		frame.add(moneyLabel);
		frame.add(userNameField);
		frame.add(moneyField);
		frame.add(backButton);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(420,420);
		frame.setLayout(null);
		frame.setVisible(true);
	}
	
	private void setNewMoney() { // shows user money on right side of screen
		moneyLabel.setText("$ " + userMoney);
		
		
		File getPathFile = new File("");
		String currentPath = getPathFile.getAbsolutePath();
		File file = new File(currentPath+"/bin/LoginSys/userData.txt");
		Scanner fileIn = null;
		
		int indexOfLine = 0;
		try {
			fileIn = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(file.isFile()) {
			while(fileIn.hasNextLine()){
				indexOfLine++;
				
				String line = fileIn.nextLine();
				if(line.indexOf(userName) >= 0) {
					break;
				}
			}
			
		}
		
		try {
			WagerPage.setVariable(indexOfLine+1,String.valueOf(userMoney));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void payUser(String userID, Double amount){
		
		File getPathFile = new File("");
		String currentPath = getPathFile.getAbsolutePath();
		File file = new File(currentPath+"/bin/LoginSys/userData.txt");
		Scanner fileIn = null;
		String money = null;
		
		// finds on which line the userID is
		int indexOfLine = 0;
		try {
			fileIn = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(file.isFile()) {
			while(fileIn.hasNextLine()){
				indexOfLine++;
				
				String line = fileIn.nextLine();
				if(line.indexOf(userID) >= 0) {
					break;
				}
			}
			
		}
		
		// finds how much money the user has
		try {
			fileIn = new Scanner(file);
			if(file.isFile()) {
				while(fileIn.hasNextLine()) {
					String line = fileIn.nextLine();
					System.out.println(line);
					if(line.indexOf(userID) >= 0) {
						money = fileIn.nextLine();
						break;
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			fileIn.close();
		}
		
		Double newMoneyAmount = null;
		newMoneyAmount = Double.valueOf(money) + amount;
		
		// puts in file the new amount of money
		try {
			WagerPage.setVariable(indexOfLine+1,String.valueOf(newMoneyAmount));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		userMoney = userMoney - amount;
		setNewMoney();
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == backButton) {
			frame.dispose();
			MainPage mainPage = new MainPage(userName);
			mainPage.setMoney(userName);
		}
		if(e.getSource() == okButton) {
			final IDAndPasswords keys = new IDAndPasswords();

			
			if(Double.valueOf(moneyField.getText()) > userMoney) {
				errorLabel.setForeground(Color.red);
				errorLabel.setText("Not enough money to pay!");
				errorLabel.setVisible(true);
			}else if(userNameField.getText().length() < 3 || !keys.getLoginInfo().containsKey(userName)) {
				errorLabel.setForeground(Color.red);
				errorLabel.setText("Username not found!");
				errorLabel.setVisible(true);
			}else if(moneyField.getText().length() <= 0) {
				errorLabel.setForeground(Color.red);
				errorLabel.setText("Enter money amount!");
				errorLabel.setVisible(true);
			}else {
				payUser(userNameField.getText(), Double.valueOf(moneyField.getText()));
			}
		}
	}
	
}

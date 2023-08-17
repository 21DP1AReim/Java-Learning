package LoginSys;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;



public class MainPage implements ActionListener{
	JFrame frame = new JFrame();
	JLabel welcomeLabel = new JLabel("Hello!");
	JLabel moneyLabel = new JLabel("");
	JButton earnButton = new JButton("EARN");
	JButton wagerButton = new JButton("WAGER");
	JButton payButton = new JButton("PAY");
	private String userName;
	private String userMoney;
	File getPathFile;
	String currentPath;
	
	MainPage(String userID){
		
		userName  = userID;
		
		moneyLabel.setBounds(225,0,200,35);
		moneyLabel.setFont(new Font(null, Font.PLAIN,25));
		setMoney(userName);
		
		earnButton.setBounds(70, 300, 100, 25);
		earnButton.addActionListener(this);
		earnButton.setFocusable(false);
		wagerButton.setBounds(170, 300, 100, 25);
		wagerButton.addActionListener(this);
		wagerButton.setFocusable(false);
		payButton.setBounds(270, 300, 100, 25);
		payButton.addActionListener(this);
		payButton.setFocusable(false);
		
		
		frame.add(earnButton);
		frame.add(wagerButton);
		frame.add(payButton);
		frame.add(moneyLabel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(420,420);
		frame.setLayout(null);
		frame.setVisible(true);
		
	}

	public void setMoney(String userID) { // function to show current user money on right side of screen
		
		getPathFile = new File("");
		currentPath = getPathFile.getAbsolutePath(); // gets path where this file is
		File file = new File(currentPath+"/bin/LoginSys/userData.txt"); 
		Scanner fileIn = null;
		
		try {
			fileIn = new Scanner(file);
			if(file.isFile()) {
				while(fileIn.hasNextLine()) { // while file has more lines
					String line = fileIn.nextLine();
					if(line.indexOf(userID) >= 0) { // if line has userID
						userMoney = fileIn.nextLine();
						moneyLabel.setText("$ " + Double.valueOf(userMoney));
						break;
					}

				}
			}
			fileIn.close();
			
		} catch (FileNotFoundException e1) { // creates file if file not  found
				PrintWriter fileOut = null;
				
				file = new File("userData.txt");
				try {
					fileOut = new PrintWriter(file);
				}catch(FileNotFoundException e11){
					e11.printStackTrace();
				}finally {
					fileOut.close();
				
			}
			
		}
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == payButton) {
			PayPage pay = new PayPage(userName, userMoney);
		}
		if(e.getSource() == wagerButton) {
			frame.dispose();
			WagerPage wager = new WagerPage(userName, userMoney);
		}
		if(e.getSource() == earnButton) {
			frame.dispose();
			EarnPage earn = new EarnPage(userName, userMoney);
		}
	}
}

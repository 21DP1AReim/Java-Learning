package LoginSys;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class EarnPage implements ActionListener {
	
	JFrame frame = new JFrame();
	JLabel moneyLabel;
	JLabel informationLabel;
	JButton earnButton;
	JButton backButton;
	File getPathFile;
	String currentPath;
	private String userName;
	private Double money;
	
	EarnPage(String userID, String userMoney){
		this.userName = userID;
		this.money = Double.valueOf(userMoney);
		
		
		earnButton = new JButton("Press for $$$");
		earnButton.setBounds(130,180,150,50);
		earnButton.setFocusable(false);
		earnButton.addActionListener(this);
		
		
		backButton = new JButton("BACK");
		backButton.setBounds(1,1,100,25);
		backButton.addActionListener(this);
		backButton.setFocusable(false);
		
		moneyLabel = new JLabel();
		moneyLabel.setBounds(225,0,200,35);
		moneyLabel.setFont(new Font(null, Font.PLAIN,25));
		moneyLabel.setVisible(true);
		moneyLabel.setFocusable(false);
		setNewMoney();
		
		frame.add(moneyLabel);
		frame.add(earnButton);
		frame.add(backButton);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(420,420);
		frame.setLayout(null);
		frame.setVisible(true);
	}
	private void setNewMoney() { // shows user money on right side of screen
		moneyLabel.setText("$ " + money);
		
		getPathFile = new File("");
		currentPath = getPathFile.getAbsolutePath();
		File file = new File(currentPath+"/bin/LoginSys/userData.txt");
		Scanner fileIn = null;
		
		int indexOfLine = 0;
		try {
			fileIn = new Scanner(file);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		if(file.isFile()) {
			
			while(fileIn.hasNextLine())
			{
				indexOfLine++;
				
				String line = fileIn.nextLine();
				if(line.indexOf(userName) >= 0) {
					break;
				}
			}
			
		}
		

		
		
		try {
			setVariable(indexOfLine+1,String.valueOf(money));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void setVariable(int lineNumber, String data) throws IOException { // function to change a specific line in a specific file
		
		getPathFile = new File("");
		currentPath = getPathFile.getAbsolutePath();		
	    Path path = Paths.get(currentPath+"/bin/LoginSys/userData.txt");
	    List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
	    lines.set(lineNumber - 1, data);
	    Files.write(path, lines, StandardCharsets.UTF_8);
	}
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == backButton) {
			frame.dispose();
			MainPage mainPage = new MainPage(userName);
			mainPage.setMoney(userName);
		}
		if(e.getSource() == earnButton) {
			money++;
			setNewMoney();
		}
	}
}

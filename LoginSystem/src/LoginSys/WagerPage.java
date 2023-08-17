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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class WagerPage implements ActionListener{
	private JButton scissorsButton = new JButton("SCISSORS");
	private JFrame frame = new JFrame();
	private JButton rockButton = new JButton("ROCK");
	private JButton paperButton = new JButton("PAPER");
	private JButton okButton;
	private JButton playAgainButton = new JButton("PLAY AGAIN");
	private JTextField wagerAmount;
	private JLabel moneyLabel;
	private JLabel wagerInformation;
	private JLabel winnerDeclerationLabel;
	private JLabel opponentPickLabel;
	private JTextArea userWarning;
	private JButton backButton = new JButton("BACK");
	private Double userMoney;
	private String userId;
	private Double wageredAmount;
	private String opponentPick;
	File getPathFile;
	String currentPath;
	
	WagerPage(String username, String money){
		
		this.userId = username;
		this.userMoney = Double.valueOf(money);
		
		
		opponentPickLabel = new JLabel();
		okButton = new JButton("OK");
		userWarning = new JTextArea();
		wagerInformation = new JLabel();
		winnerDeclerationLabel = new JLabel("hi");
		moneyLabel = new JLabel();
		
		userWarning.setText("Enter an amount of money that you have!");
		userWarning.setBounds(50, 130,300,100);
		userWarning.setBackground(new Color(238,238,238,255));
		userWarning.setLineWrap(true);
		userWarning.setFont(new Font("Ink Free", Font.BOLD, 20));
		userWarning.setForeground(Color.red);
		userWarning.setVisible(false);
		userWarning.setEditable(false);
		userWarning.setFocusable(false);
		
		
		wagerInformation.setText("Enter wager amount");
		wagerInformation.setBounds(100,30,200,100);
		wagerInformation.setFocusable(false);
		
		backButton.setBounds(1,1,100,25);
		backButton.addActionListener(this);
		backButton.setFocusable(false);
		
		okButton.setBounds(205,100,70,25);
		okButton.addActionListener(this);
		okButton.setFocusable(false);
		
		rockButton.setBounds(70, 300, 100, 25);
		rockButton.addActionListener(this);
		rockButton.setFocusable(false);
		rockButton.setVisible(false);
		
		paperButton.setBounds(170, 300, 100, 25);
		paperButton.addActionListener(this);
		paperButton.setFocusable(false);
		paperButton.setVisible(false);
		
		scissorsButton.setBounds(270, 300, 100, 25);
		scissorsButton.addActionListener(this);
		scissorsButton.setFocusable(false);
		scissorsButton.setVisible(false);
		
		
		playAgainButton.setBounds(130, 200, 120, 30);
		playAgainButton.addActionListener(this);
		playAgainButton.setFocusable(false);
		playAgainButton.setVisible(false);
		
		moneyLabel.setBounds(225,0,200,35);
		moneyLabel.setFont(new Font(null, Font.PLAIN,25));
		moneyLabel.setVisible(true);
		moneyLabel.setFocusable(false);
		setNewMoney();
		
		opponentPickLabel.setBounds(100,100,300,50);
		opponentPickLabel.setFont(new Font("Ink Free", Font.PLAIN,20));
		opponentPickLabel.setVisible(false);
		opponentPickLabel.setFocusable(false);
		
		winnerDeclerationLabel.setBounds(105,40,300,100);
		winnerDeclerationLabel.setFont(new Font("Ink Free", Font.BOLD,40));
		winnerDeclerationLabel.setVisible(false);
		winnerDeclerationLabel.setFocusable(false);
		
		wagerAmount = new JTextField(25);
		wagerAmount.setFocusable(true);
		wagerAmount.setBounds(100,100,100,25);
		wagerAmount.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				String value = wagerAmount.getText();
				int valueLength = value.length();
				if(ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' || ke.getKeyChar()  == '\b') {
					wagerAmount.setEditable(true);
					wagerAmount.setBackground(Color.white);
					
				}else {
					wagerAmount.setEditable(false);
					wagerAmount.setBackground(Color.red);
				}
			}

		});
		frame.add(playAgainButton);
		frame.add(winnerDeclerationLabel);
		frame.add(userWarning);
		frame.add(opponentPickLabel);
		frame.add(okButton);
		frame.add(wagerInformation);
		frame.add(wagerAmount);
		frame.add(backButton);
		frame.add(moneyLabel);
		frame.add(rockButton);
		frame.add(paperButton);
		frame.add(scissorsButton);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(420,420);
		frame.setLayout(null);
		frame.setVisible(true);
	}

	
	
	
	private void setNewMoney() { // shows user money on right side of screen
		moneyLabel.setText("$ " + userMoney);
		
		getPathFile = new File("");
		currentPath = getPathFile.getAbsolutePath();
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
			
			while(fileIn.hasNextLine())
			{
				indexOfLine++;
				
				String line = fileIn.nextLine();
				if(line.indexOf(userId) >= 0) {
					break;
				}
			}
			
		}
		

		
		
		try {
			setVariable(indexOfLine+1,String.valueOf(userMoney));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void userLost() { // used when user loses

		
		winnerDeclerationLabel.setText("You Lose!");
		winnerDeclerationLabel.setForeground(Color.red);
		userMoney = userMoney - wageredAmount; // removes wagered money
		setNewMoney(); // sets new money
		giveOptionToPlayAgain(); // gives user option to play again

	}
	private void userWon() { // used when user wins

		wageredAmount = Double.valueOf(wagerAmount.getText());
		winnerDeclerationLabel.setText("You Win!");
		winnerDeclerationLabel.setForeground(Color.GREEN);
		userMoney = userMoney + wageredAmount; // increases user money
		setNewMoney(); 
		giveOptionToPlayAgain(); // gives option to play again

	}
	public static void setVariable(int lineNumber, String data) throws IOException { // function to change a specific line in a specific file

		File getPathFile = new File("");
		String currentPath = getPathFile.getAbsolutePath();		
	    Path path = Paths.get(currentPath+"/bin/LoginSys/userData.txt");
	    List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
	    lines.set(lineNumber - 1, data);
	    Files.write(path, lines, StandardCharsets.UTF_8);
	}
	
	private void revealWinner(String userPick) { // function to process the winner
		
		Random randomNum = new Random();
		int randomNumber = randomNum.nextInt(3)+1; // randomly chooses opponent pick
		System.out.println(randomNumber);
		opponentPick = "Paper";
		switch (randomNumber) {
			case 1:
				opponentPick = "Rock";
				break;
			case 2:
				opponentPick = "Paper";
				break;
			case 3:
				opponentPick = "Scissors";
				break;
		
		}
		
	
		winnerDeclerationLabel.setVisible(true);
		opponentPickLabel.setText("Opponent picked " + opponentPick); // shows opponent pick
		opponentPickLabel.setVisible(true);
		
		if(opponentPick == userPick) { // processes winner
			winnerDeclerationLabel.setText("DRAW!");
			winnerDeclerationLabel.setForeground(Color.BLACK);
			
		}else if(opponentPick == "Rock" && userPick == "Scissors") {
			userLost();
		}else if(opponentPick == "Paper" && userPick == "Rock") {
			userLost();
		}else if(opponentPick == "Scissors" && userPick == "Paper") {
			userLost();
			
		}else if(opponentPick == "Rock" && userPick == "Paper") {
			userWon();
		}else if(opponentPick == "Paper" && userPick == "Scissors") {
			userWon();
		}else if(opponentPick == "Scissors" && userPick == "Rock") {
			userWon();
		}
		
	}
	
	
	private void giveOptionToPlayAgain() { // function to give option to play again
		rockButton.setEnabled(false);
		paperButton.setEnabled(false);
		scissorsButton.setEnabled(false);
		playAgainButton.setVisible(true);
	}
	
	private void resetGame() { // function to reset the game

		paperButton.setEnabled(true);
		rockButton.setEnabled(true);
		scissorsButton.setEnabled(true);
		paperButton.setVisible(false);
		rockButton.setVisible(false);
		scissorsButton.setVisible(false);
		okButton.setVisible(true);
		wagerInformation.setVisible(true);
		
		wagerAmount.setVisible(true);

		
		winnerDeclerationLabel.setVisible(false);
		opponentPickLabel.setVisible(false);
		wagerAmount.setVisible(true);
		wagerAmount.setText("");
		playAgainButton.setVisible(false);
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == backButton) {
			frame.dispose();
			MainPage mainPage = new MainPage(userId);
			mainPage.setMoney(userId);
		}
		
		if(e.getSource() == okButton) {
			try {
				wageredAmount = Double.valueOf(wagerAmount.getText());
				if(wageredAmount > userMoney) {
					userWarning.setText("Enter an amount of money that you have!");
					userWarning.setVisible(true);
				}else {
					userWarning.setVisible(false);
					okButton.setVisible(false);
					wagerInformation.setVisible(false);
					
					wagerAmount.setVisible(false);
					paperButton.setVisible(true);
					rockButton.setVisible(true);
					scissorsButton.setVisible(true);
				}
			}catch(Exception e1) {
				userWarning.setText("You have to enter an amount of money!");
				userWarning.setVisible(true);
			}
				
			
			

			}
		
		if(e.getSource() == rockButton) {
			revealWinner("Rock");
		}
		if(e.getSource() == paperButton) {
			revealWinner("Paper");
		}
		if(e.getSource() == scissorsButton) {
			revealWinner("Scissors");
		}
		if(e.getSource() == playAgainButton) {
			resetGame();
		}
	}
}

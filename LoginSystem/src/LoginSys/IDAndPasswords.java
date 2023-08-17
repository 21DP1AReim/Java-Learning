package LoginSys;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class IDAndPasswords {
	private char character = ' ';
	private ArrayList<Character> key;
	private ArrayList<Character> letters;
	private String[] array;
	private Encryption encrypt;
	File getPathFile;
	String currentPath;
	
	HashMap<String,String> loginInfo = new HashMap<String,String>();
	
	IDAndPasswords(){
		encrypt = new Encryption();
		decrypt();
	    
	    
	    
		
	}
	
	protected HashMap getLoginInfo(){ // function to make sure user data is secured
		return loginInfo;
	}
	
	public void addInfo(String name,String password) { // function to add data to hashmap that stores login info
		this.loginInfo.put(name,password);
	}
	
	private void decrypt() { // function to decrypt login info file and put it inside the login info hashmap
		getPathFile = new File("");
		currentPath = getPathFile.getAbsolutePath();
		File file = new File(currentPath+"/bin/LoginSys/IDAndPasswords.txt");
		Scanner fileIn = null;
		
		try {
			fileIn = new Scanner(file);
			if(file.isFile()) {
				while(fileIn.hasNextLine()) {
					String line = fileIn.nextLine();

					line = encrypt.decrypt(line);
					System.out.println(line);
					
					String[] arr = line.split(" ");
					addInfo(arr[0],arr[1]);

				}
			}
			fileIn.close();
			// creates file if it doesn't exist
		} catch (FileNotFoundException e1) {
				PrintWriter fileOut = null;
				
				file = new File("IDAndPasswords.txt");
				try {
					fileOut = new PrintWriter(file);
				}catch(FileNotFoundException e11){
					e11.printStackTrace();
				}finally {
					fileOut.close();
				
			}
			
		}
		
}
}

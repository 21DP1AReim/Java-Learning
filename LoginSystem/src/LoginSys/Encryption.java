package LoginSys;
import java.util.*;
public class Encryption {
		

	private ArrayList<Character> key;
	private ArrayList<Character> letters;
		private char character = ' ';
		private String line;
		private char[] symbols;
		private String[] encryptionKey;
		
		Encryption(){
			// Do not change, if changed will not be able to decipher user id nor password
			encryptionKey = new String[]{"1LnU)o!Q<mCc*\"[lWv`a8(wqu-:GTyE~NpF x\\s9e{Rb0.=O26HJrYk;Pi4@$?gK5X>/_&'7,+|%}dM#]SDZ3^VfIBAzjth"};
			key = new ArrayList(); // creates list for the key so each character would be an element in array
			letters = new ArrayList(); // creates a list for all ASCII characters, used to decipher text file
			
			for(int i=32;i<127;i++) { // loop adds ASCII characters to letters array, mostly letters
				letters.add(Character.valueOf(character));
				character++;
			}
			
			for(int i=0;i<encryptionKey[0].length();i++) { // adds the encryption key to array, dividing each character to an element
				key.add(encryptionKey[0].charAt(i));

			}
			
		}
		
		public String encrypt(String message) { // function to encrypt string
			
			symbols = message.toCharArray(); // converts string to an array of characters
			
			for(int i =0;i<symbols.length;i++) {
				
				for(int j =0;j<key.size(); j++) { // encrypts the string using encryption key
					if(symbols[i] == letters.get(j)) {
						symbols[i] = key.get(j);
						break;
					}
				}
			}
		return String.valueOf(symbols); // returns encrypted string
		
		}
		public String decrypt(String message) { // function to decrypt string
			symbols = message.toCharArray();
			
			for(int i =0;i<symbols.length;i++) {
				for(int j =0;j<key.size(); j++) {
					
					if(symbols[i] == key.get(j)) { // converts crypted array of characters to encrypted array
						symbols[i] = letters.get(j);
						break;
					}
				}
			}
			return String.valueOf(symbols);
		}
		
		
		
	

}

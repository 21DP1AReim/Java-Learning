package LoginSys;

public class Main {

	public static void main(String[] args) {
		IDAndPasswords idAndPasswords = new IDAndPasswords();
		idAndPasswords.addInfo("Artis", "1234");

		
		
		LoginPage loginpage = new LoginPage(idAndPasswords.getLoginInfo());
		
	}

}

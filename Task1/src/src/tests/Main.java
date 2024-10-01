package tests;

public class Main {
	
	public static void main(String[] args) throws InterruptedException {
		
		InitialTests test = new InitialTests();
		
		try {
			test.testOneClient();
			test.testMultipleClient();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
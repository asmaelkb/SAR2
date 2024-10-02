package tests;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		InitialTests test = new InitialTests();

		try {
			test.testOneClient();
			test.testMultipleClients();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
package tests;

public class Main {
    
    public static void main(String[] args) {
        Client test = new Client();
        
        try {
            test.testOneClient();
            test.testMultipleClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
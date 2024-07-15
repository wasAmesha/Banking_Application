package myapp1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BankServer {
	private static final int PORT = 3000;
	private static BankDatabase bankDatabase;
    
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Bank server is running...");
            bankDatabase = new BankDatabase();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);
                ClientHandler clientHandler = new ClientHandler(clientSocket, bankDatabase);
                clientHandler.start();
            }
           
        }
	}

}

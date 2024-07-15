package myapp1;
import java.io.*;
import java.net.Socket;


public class ClientHandler extends Thread{
	private Socket clientSocket;
	private BankDatabase bankDatabase;
	
	public ClientHandler(Socket clientSocket, BankDatabase bankDatabase) {
        this.clientSocket = clientSocket;
        this.bankDatabase = bankDatabase;
    }
	@Override
	public void run() {
		try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Process client request
                // For simplicity, let's assume the client sends the account number and the operation (deposit or withdraw)
                String[] parts = line.split(" ");
                int accountNumber = Integer.parseInt(parts[0]);
                double amount = Double.parseDouble(parts[1]);
                String operation = parts[2];

                BankAccount account = bankDatabase.getAccount(accountNumber);
                if (account == null) {
                    writer.println("Account not found");
                    continue;
                }

                switch (operation) {
                    case "deposit":
                        account.deposit(amount);
                        writer.println("Deposit successful. New balance: " + account.getBalance());
                        break;
                    case "withdraw":
                        if (account.getBalance() < amount) {
                            writer.println("Insufficient funds");
                        } else {
                            account.withdraw(amount);
                            writer.println("Withdrawal successful. New balance: " + account.getBalance());
                        }
                        break;
                    default:
                        writer.println("Invalid operation");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	

	

}

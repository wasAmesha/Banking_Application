package myapp1;

import java.util.HashMap;
import java.util.Map;

public class BankDatabase {
	private Map<Integer, BankAccount> accounts;
	private Map<String, String> userCredentials;
	private Map<String, BankAccount> accountsByUsername;
	
	
	
	public BankDatabase() {
		this.userCredentials = new HashMap<>();
	    
	    // Populate user credentials (replace with actual username-password pairs)
	    userCredentials.put("user1", "pass123");
	    userCredentials.put("user2", "pass456");
		
		
		/*this.accounts = new HashMap<>();
		
		
		accounts.put(1, new BankAccount(001,1000));
		accounts.put(2, new BankAccount(002,2000));*/
		
		
		 this.accountsByUsername = new HashMap<>();
		    // Populate accountsByUsername with username-account mappings
		 accountsByUsername.put("user1", new BankAccount(1, 1000)); // Example
		 accountsByUsername.put("user2", new BankAccount(2, 2000)); // 
	}
	
	public synchronized BankAccount getAccountByUsername(String username) {
	    return accountsByUsername.get(username);
	}

	public synchronized BankAccount getAccount(int accountNumber) {
        return accounts.get(accountNumber);
    }
	
	
	public synchronized boolean authenticateUser(String username, String password) {
        // Check if the provided username exists and the password matches
        String storedPassword = userCredentials.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }


//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}

}






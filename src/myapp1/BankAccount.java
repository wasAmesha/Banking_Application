package myapp1;

public class BankAccount {
	private int accountNumber;
	private double balance;
	
	public BankAccount(int accountNumber,double initialBalance) {
		this.accountNumber=accountNumber;
		this.balance=initialBalance;
	}
	
	public synchronized void deposit(double amount) {
        balance += amount;
    }

    public synchronized void withdraw(double amount) {
        balance -= amount;
    }

    public synchronized double getBalance() {
        return balance;
    }

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}

}
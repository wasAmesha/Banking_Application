package myapp1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountUI extends JFrame {
    private BankAccount account;

    public AccountUI(BankAccount account) {
        this.account = account;

        setTitle("Account Interface");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // Add insets for spacing

        JButton depositButton = createButton("Deposit Money");
        depositButton.addActionListener(e -> {
            double amount = getAmountFromUser("Enter deposit amount:");
            if (amount >= 0) {
                account.deposit(amount);
                showMessage("Deposit successful. New balance: Rs." + account.getBalance());
            }
        });
        gbc.gridy = 0;
        panel.add(depositButton, gbc);

        JButton withdrawButton = createButton("Withdraw Money");
        withdrawButton.addActionListener(e -> {
            double amount = getAmountFromUser("Enter withdrawal amount:");
            if (amount >= 0) {
                if (account.getBalance() >= amount) {
                    account.withdraw(amount);
                    showMessage("Withdrawal successful. New balance: Rs." + account.getBalance());
                } else {
                    showMessage("Insufficient funds", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        gbc.gridy = 1;
        panel.add(withdrawButton, gbc);

        JButton checkBalanceButton = createButton("Check Balance");
        checkBalanceButton.addActionListener(e -> showMessage("Current balance: Rs. " + account.getBalance()));
        gbc.gridy = 2;
        panel.add(checkBalanceButton, gbc);

        add(panel);
        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 30)); // Set preferred size
        button.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font
        return button;
    }

    private double getAmountFromUser(String message) {
        try {
            return Double.parseDouble(JOptionPane.showInputDialog(this, message));
        } catch (NumberFormatException e) {
            showMessage("Invalid amount", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void showMessage(String message, int messageType) {
        JOptionPane.showMessageDialog(this, message, "Error", messageType);
    }

    // For testing purposes
    public static void main(String[] args) {
        // Create a sample BankAccount
        BankAccount account = new BankAccount(12345, 1000);

        // Show the Account UI
        SwingUtilities.invokeLater(() -> new AccountUI(account));
    }
}

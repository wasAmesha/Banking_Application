package myapp1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private BankDatabase bankDatabase;

    public LoginPage(BankDatabase bankDatabase) {
        this.bankDatabase = bankDatabase;

        frame = new JFrame();
        frame.setTitle("Login Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);

        // Use BorderLayout for the frame's content pane
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create a panel for labels and fields using GridBagLayout for better alignment
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        inputPanel.add(new JLabel("Username:"), gbc);
        gbc.gridy++;
        inputPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        usernameField = new JTextField(15);
        gbc.gridy++;
        inputPanel.add(usernameField, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridy++;
        inputPanel.add(passwordField, gbc);

        // Adjust gridwidth to make fields span across remaining columns
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        inputPanel.add(Box.createHorizontalStrut(0), gbc);

        panel.add(inputPanel, BorderLayout.CENTER);

        // Create a panel for the login button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (bankDatabase.authenticateUser(username, password)) {
                    frame.dispose(); // Close login page
                    showAccountUI(); // Show Account UI interface
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(loginButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
     // Create a panel for the registration link
        JPanel registrationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel registerLabel = new JLabel("Don't have an account? Register here.");
        registerLabel.setForeground(Color.BLUE);
        registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openRegistrationPage();
            }
        });
        registrationPanel.add(registerLabel);
        panel.add(registrationPanel, BorderLayout.NORTH);

        frame.add(panel);
        frame.setVisible(true);
    }
    private void openRegistrationPage() {
        frame.dispose();
        new RegistrationPage().display();
    }

    public void display() {
        frame.setVisible(true);
    }

    private void showAccountUI() {
        BankAccount account = bankDatabase.getAccountByUsername(usernameField.getText());
        AccountUI accountUI = new AccountUI(account);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                accountUI.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        BankDatabase bankDatabase = new BankDatabase();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginPage(bankDatabase).display();
            }
        });
    }
}


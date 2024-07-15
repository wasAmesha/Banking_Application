package myapp1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.net.Socket;
import java.net.UnknownHostException;


public class BankClient {
	private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 3000;
    
    private JTextField accountNumberField;
    private JTextField amountField;
    private JTextField operationField;
    private JTextArea responseArea;
    
    public BankClient() {
        JFrame frame = new JFrame();
        
        frame.setTitle("Bank Client");
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setSize(400,300);
        frame.setLocationRelativeTo(null);
        
        UIManager.put("Button.background", Color.LIGHT_GRAY);
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 14));
        UIManager.put("Button.border", BorderFactory.createEmptyBorder(5, 15, 5, 15));

        
        JPanel inputPanel =new JPanel(new GridLayout(4,2,5,5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.add(new JLabel("Account Number: "));
        accountNumberField =new JTextField();
        inputPanel.add(accountNumberField);
        inputPanel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        inputPanel.add(amountField);
        inputPanel.add(new JLabel("Operation (deposit/withdraw):"));
        operationField=new JTextField();
        inputPanel.add(operationField);
        
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		try {
					sendRequest();
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        });
        inputPanel.add(sendButton);
       
        
       
        this.responseArea = new JTextArea();
        this.responseArea.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(responseArea); // Wrapped the responseArea in a JScrollPane
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        frame.add(inputPanel,BorderLayout.NORTH);
        
        frame.add(new JScrollPane(this.responseArea),BorderLayout.CENTER);
        frame.setVisible(true);
    }
    
    public void sendRequest() throws UnknownHostException, IOException {
    	try( 
    			Socket socket = new Socket (SERVER_ADDRESS,PORT);
    			BufferedReader reader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
    			PrintWriter writer = new PrintWriter(socket.getOutputStream(),true)
    			
    		){
    		int accountNumber = Integer.parseInt(accountNumberField.getText());
    		
            double amount = Double.parseDouble(amountField.getText());
            String operation = operationField.getText();

            writer.println(accountNumber + " " + amount + " " + operation);
            String response = reader.readLine();
            responseArea.append("Server response: " + response + "\n");
    	}
    }
    
    public static void main(String[] args) {
        BankDatabase bankDatabase = new BankDatabase(); // Create an instance of BankDatabase
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginPage loginPage = new LoginPage(bankDatabase); // Pass the BankDatabase instance to LoginPage constructor
                loginPage.display(); // Display the login page
            }
        });
    }


}

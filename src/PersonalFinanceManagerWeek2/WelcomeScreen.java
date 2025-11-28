package PersonalFinanceManagerWeek2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// This class will act as the entry point before Login/Signup
public class WelcomeScreen extends JFrame implements ActionListener {

    // Define Black Background and Button Colors
    private static final Color BG_PURE_BLACK = Color.BLACK; 
    private static final Color PRIMARY_BUTTON_COLOR = new Color(80, 70, 200); // Prominent Purple/Blue
    private static final Color SECONDARY_BUTTON_COLOR = new Color(50, 40, 100); // Darker Button
    private static final Color TEXT_LIGHT = Color.WHITE;
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 28);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 18);

    JButton loginButton, signupButton;

    public WelcomeScreen() {
        setTitle("Personal Finance Manager");
        setBounds(500, 150, 500, 700); 
        setLocationRelativeTo(null);
        // This is the starting frame, so exiting the application is fine
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setLayout(null);
        
        getContentPane().setBackground(BG_PURE_BLACK);

        // --- 1. Title and Prompt Text ---
        
        // Main Title (Top Center)
        JLabel titleLabel = new JLabel("Personal Finance Manager");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_LIGHT);
        titleLabel.setBounds(30, 100, 440, 40); 
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel);

        // Main Prompt Text
        JLabel promptText = new JLabel("Check your budget before you buy!");
        promptText.setFont(new Font("Arial", Font.PLAIN, 18)); 
        promptText.setForeground(Color.GRAY);
        promptText.setBounds(30, 180, 440, 30); 
        promptText.setHorizontalAlignment(SwingConstants.CENTER);
        add(promptText);


        // --- 2. Action Buttons (Bottom of the Screen) ---

        // Button 1: I already have an account (Login)
        loginButton = new JButton("I already have an account");
        loginButton.setFont(BUTTON_FONT);
        loginButton.setBackground(PRIMARY_BUTTON_COLOR);
        loginButton.setForeground(TEXT_LIGHT);
        loginButton.setFocusPainted(false);
        loginButton.setBounds(100, 520, 300, 50); 
        loginButton.addActionListener(this);
        add(loginButton);
        
        // Button 2: Sign Up for a new account
        signupButton = new JButton("Sign Up");
        signupButton.setFont(BUTTON_FONT);
        signupButton.setBackground(SECONDARY_BUTTON_COLOR);
        signupButton.setForeground(TEXT_LIGHT);
        signupButton.setFocusPainted(false);
        signupButton.setBounds(100, 590, 300, 50);
        signupButton.addActionListener(this);
        add(signupButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == loginButton) {
            // Open Login screen
            setVisible(false);
            dispose(); // Dispose of WelcomeScreen
            new Login(); 
        } else if (ae.getSource() == signupButton) {
            // Open Signup screen
            setVisible(false);
            dispose(); // Dispose of WelcomeScreen
            new Signup(); 
        }
    }

    public static void main(String[] args) {
        // Start the application with the WelcomeScreen
        new WelcomeScreen();
    }
}
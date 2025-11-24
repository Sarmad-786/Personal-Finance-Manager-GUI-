package PersonalFinanceManagerWeek1;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame implements ActionListener {
    // ... other variables ...
    JButton googleLogin, passwordButton, loginButton; 
    JTextField tfusername;
    JPasswordField tfpassword;
    
    // Define Dark/Modern Colors
    private static final Color BG_DARK = new Color(18, 18, 25); 
    private static final Color WIDGET_DARK = new Color(25, 25, 35); 
    private static final Color BUTTON_BLUE = new Color(30, 100, 255); 
    private static final Color TEXT_LIGHT = new Color(220, 220, 220); 

    Login() {

        setSize(500, 650); 
        setLocationRelativeTo(null); 
        setLayout(null);
        getContentPane().setBackground(BG_DARK); 

        // Panel p1 (Top Image/Logo Panel) - MODIFIED: Image loading removed
        JPanel p1 = new JPanel();
        p1.setBackground(BG_DARK);
        p1.setBounds(0, 0, 500, 200); 
        p1.setLayout(null);
        add(p1);

        // *** ERROR FIX: IMAGE LOADING BLOCK IS COMMENTED OUT ***
        /* ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/login.png"));
        Image i2 = i1.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(175, 20, 150, 150);
        p1.add(image);
        */
        
        // --- TEMPORARY FIX: Add a simple Label instead of image for space ---
        JLabel tempLabel = new JLabel("Login Logo Area");
        tempLabel.setForeground(TEXT_LIGHT);
        tempLabel.setBounds(175, 80, 150, 30);
        tempLabel.setHorizontalAlignment(SwingConstants.CENTER);
        p1.add(tempLabel);


        // Panel p2 (Form Panel) - Rest of the code remains the same for structure
        JPanel p2 = new JPanel();
        p2.setLayout(null);
        p2.setBackground(BG_DARK);
        p2.setBounds(0, 200, 500, 450); 
        add(p2);
        
        int xOffset = 60;
        int width = 380;
        int yPos = 20;

        // --- 1. Continue with Google Button ---
        googleLogin = new JButton("G Continue with Google");
        googleLogin.setBounds(xOffset, yPos, width, 45);
        googleLogin.setBackground(WIDGET_DARK); 
        googleLogin.setForeground(TEXT_LIGHT);
        googleLogin.setFont(new Font("SAN_SERIF", Font.BOLD, 16));
        googleLogin.setBorder(BorderFactory.createLineBorder(WIDGET_DARK.brighter()));
        googleLogin.addActionListener(this);
        p2.add(googleLogin);
        yPos += 55;

        // --- 2. 'or' Separator (Stylized) ---
        JLabel orText = new JLabel("or");
        orText.setBounds(xOffset, yPos, width, 20);
        orText.setForeground(TEXT_LIGHT.darker());
        orText.setHorizontalAlignment(SwingConstants.CENTER);
        p2.add(orText);
        yPos += 30;

        // --- 3. Email Field Styling ---
        JLabel lblusername = new JLabel("Email");
        lblusername.setBounds(xOffset, yPos, width, 25);
        lblusername.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        lblusername.setForeground(TEXT_LIGHT); 
        p2.add(lblusername);
        yPos += 20;
        
        tfusername = new JTextField();
        tfusername.setBounds(xOffset, yPos, width, 50); 
        tfusername.setBackground(WIDGET_DARK); 
        tfusername.setForeground(TEXT_LIGHT); 
        tfusername.setCaretColor(TEXT_LIGHT); 
        tfusername.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        p2.add(tfusername);
        yPos += 60;


        // --- 4. Password Field Styling ---
        JLabel lblpassword = new JLabel("Password");
        lblpassword.setBounds(xOffset, yPos, width, 25);
        lblpassword.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        lblpassword.setForeground(TEXT_LIGHT); 
        p2.add(lblpassword);
        yPos += 20;

        tfpassword = new JPasswordField();
        tfpassword.setBounds(xOffset, yPos, width, 50); 
        tfpassword.setBackground(WIDGET_DARK); 
        tfpassword.setForeground(TEXT_LIGHT); 
        tfpassword.setCaretColor(TEXT_LIGHT); 
        tfpassword.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        p2.add(tfpassword);
        yPos += 80; 

        // --- 5. Log In Button ---
        loginButton = new JButton("Log In");
        loginButton.setBounds(xOffset, yPos, width, 50);
        loginButton.setBackground(BUTTON_BLUE); 
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        loginButton.setBorder(BorderFactory.createEmptyBorder());
        loginButton.addActionListener(this);
        p2.add(loginButton);
        yPos += 60;
        
        // --- 6. Forgot Password Link ---
        passwordButton = new JButton("Forgot Password?");
        passwordButton.setBounds(xOffset, yPos, width, 25);
        passwordButton.setBackground(BG_DARK); 
        passwordButton.setForeground(BUTTON_BLUE); 
        passwordButton.setFont(new Font("SAN_SERIF", Font.BOLD, 14));
        passwordButton.setBorder(BorderFactory.createEmptyBorder());
        passwordButton.addActionListener(this);
        p2.add(passwordButton);

        setVisible(true);
    }
    
    // Week 1: Dummy actionPerformed (no new windows/functions)
    public void actionPerformed(ActionEvent ae) {
        // Implementation for next week
        System.out.println(((JButton) ae.getSource()).getText() + " button clicked (No action in Week 1).");
    }

    // public static void main(String[] args) { new Login(); }
}
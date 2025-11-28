package PersonalFinanceManagerWeek2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame implements ActionListener {
    // Redefining buttons for the new vertical layout and purposes
    JButton googleLogin, passwordButton, loginButton; 
    
    JTextField tfusername;
    JPasswordField tfpassword;
    
    // Define Dark/Modern Colors
    private static final Color BG_DARK = new Color(18, 18, 25); // Very Dark Background (from image)
    private static final Color WIDGET_DARK = new Color(25, 25, 35); // Dark Input Field Background (from image)
    private static final Color BUTTON_BLUE = new Color(30, 100, 255); // The Blue Accent Color
    private static final Color TEXT_LIGHT = new Color(220, 220, 220); // Light Grey Text

    Login() {

        // Set up the main frame, adjusted size for vertical layout
        setSize(500, 650); 
        setLocationRelativeTo(null); 
        setLayout(null);
        getContentPane().setBackground(BG_DARK); // <<< SETTING FRAME BACKGROUND TO DARK

        // Panel p1 (Top Image/Logo Panel) - Uses dark background
        JPanel p1 = new JPanel();
        p1.setBackground(BG_DARK);
        p1.setBounds(0, 0, 500, 200); // Made full width at top
        p1.setLayout(null);
        add(p1);

        // Image/Logo
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/login.png"));
        Image i2 = i1.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(175, 20, 150, 150); // Centered
        p1.add(image);

        // Panel p2 (Form Panel) - Now fully uses the dark theme
        JPanel p2 = new JPanel();
        p2.setLayout(null);
        p2.setBackground(BG_DARK);
        p2.setBounds(0, 200, 500, 450); // Starts below p1
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

        // --- 3. Email Field Styling (Look Good) ---
        JLabel lblusername = new JLabel("Email");
        lblusername.setBounds(xOffset, yPos, width, 25);
        lblusername.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        lblusername.setForeground(TEXT_LIGHT); // Set label text to light color
        p2.add(lblusername);
        yPos += 20;
        
        tfusername = new JTextField();
        tfusername.setBounds(xOffset, yPos, width, 50); // Increased height
        tfusername.setBackground(WIDGET_DARK); // Dark field background
        tfusername.setForeground(TEXT_LIGHT); // Light text input
        tfusername.setCaretColor(TEXT_LIGHT); // Set blinking cursor color
        // Use a clean, non-intrusive border (no line/empty border)
        tfusername.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        p2.add(tfusername);
        yPos += 60;


        // --- 4. Password Field Styling (Look Good) ---
        JLabel lblpassword = new JLabel("Password");
        lblpassword.setBounds(xOffset, yPos, width, 25);
        lblpassword.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        lblpassword.setForeground(TEXT_LIGHT); // Set label text to light color
        p2.add(lblpassword);
        yPos += 20;

        tfpassword = new JPasswordField();
        tfpassword.setBounds(xOffset, yPos, width, 50); // Increased height
        tfpassword.setBackground(WIDGET_DARK); // Dark field background
        tfpassword.setForeground(TEXT_LIGHT); // Light text input
        tfpassword.setCaretColor(TEXT_LIGHT); // Set blinking cursor color
        // Use a clean, non-intrusive border (no line/empty border)
        tfpassword.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        p2.add(tfpassword);
        yPos += 80; // Adjusted spacing

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
        passwordButton.setBackground(BG_DARK); // Transparent appearance
        passwordButton.setForeground(BUTTON_BLUE); 
        passwordButton.setFont(new Font("SAN_SERIF", Font.BOLD, 14));
        passwordButton.setBorder(BorderFactory.createEmptyBorder());
        passwordButton.addActionListener(this);
        p2.add(passwordButton);


        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == loginButton) { // Log In Button
            String username = tfusername.getText();
            // NOTE: Add actual database check here for security
            setVisible(false);
            new Dashboard(username);

        } else if (ae.getSource() == googleLogin) { // Third-party login attempted
            JOptionPane.showMessageDialog(this, "Google login feature coming soon.");
            
        } else if (ae.getSource() == passwordButton) { // Forgot Password Link
            setVisible(false);
            new ForgetPassword();
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
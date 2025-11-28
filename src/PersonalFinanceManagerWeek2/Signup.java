package PersonalFinanceManagerWeek2;
import javax.swing.*;
import java.awt.*; 
import java.awt.event.*;


public class Signup extends JFrame implements ActionListener{ 
    JButton create , back;
    JTextField tfname, tfusername, tfanswer;
    JPasswordField tfpassword;
    Choice security;
    
    // Define Professional Colors
    private static final Color PANEL_ACCENT = new Color(0, 102, 204); // Deep Blue Accent (Used for buttons)
    private static final Color BG_DARK = Color.BLACK; // Frame background
    private static final Color PANEL_BG_FORM = new Color(35, 35, 35); // Dark Gray background for form area
    private static final Color FORM_FIELD_BG = new Color(30, 30, 30); // Dark grey field background
    private static final Color FORM_BORDER = new Color(80, 80, 80); // Lighter border for fields
    private static final Color TEXT_LIGHT = Color.WHITE; // White text

    Signup(){
        // 🚨 FIX 1: Adjusted bounds to remove right panel (width 480)
        setBounds(550, 200, 480, 450); 
        getContentPane().setBackground(BG_DARK); 
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // --- 1. Form Container (Now fills the whole frame width) ---
        JPanel p1 = new JPanel();
        p1.setBackground(PANEL_BG_FORM); // Use the slightly lighter dark grey for the form background
        p1.setBounds(0, 0, 480, 450); // Fills the entire frame
        p1.setLayout(null);
        add(p1);
        
        // --- 2. Title Label ---
        JLabel title = new JLabel("Create New Account");
        title.setBounds(50, 15, 300, 30);
        title.setFont(new Font("Tahoma", Font.BOLD, 24));
        title.setForeground(TEXT_LIGHT); // White text
        p1.add(title);
        
        
        // --- 3. Form Fields ---
        int labelX = 50;
        int fieldX = 190;
        int fieldWidth = 220; // Slightly increased field width for better aesthetics

        // Username
        JLabel lblusername = new JLabel("Username");
        lblusername.setBounds(labelX,70,125,25);
        lblusername.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblusername.setForeground(TEXT_LIGHT);
        p1.add(lblusername);
        
        tfusername = new JTextField();
        tfusername.setBounds(fieldX, 70, fieldWidth, 28);
        tfusername.setBackground(FORM_FIELD_BG);
        tfusername.setForeground(TEXT_LIGHT);
        tfusername.setCaretColor(TEXT_LIGHT);
        tfusername.setBorder(BorderFactory.createLineBorder(FORM_BORDER)); 
        p1.add(tfusername);
        
        // Name
        JLabel lblname = new JLabel("Full Name"); 
        lblname.setBounds(labelX,110,125,25);
        lblname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblname.setForeground(TEXT_LIGHT);
        p1.add(lblname);
        
        tfname = new JTextField();
        tfname.setBounds(fieldX, 110, fieldWidth, 28);
        tfname.setBackground(FORM_FIELD_BG);
        tfname.setForeground(TEXT_LIGHT);
        tfname.setCaretColor(TEXT_LIGHT);
        tfname.setBorder(BorderFactory.createLineBorder(FORM_BORDER)); 
        p1.add(tfname);
        
        // Password
        JLabel lblpassword = new JLabel("Password");
        lblpassword.setBounds(labelX,150,125,25);
        lblpassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblpassword.setForeground(TEXT_LIGHT);
        p1.add(lblpassword);
        
        tfpassword = new JPasswordField();
        tfpassword .setBounds(fieldX, 150, fieldWidth, 28);
        tfpassword.setBackground(FORM_FIELD_BG);
        tfpassword.setForeground(TEXT_LIGHT);
        tfpassword.setCaretColor(TEXT_LIGHT);
        tfpassword .setBorder(BorderFactory.createLineBorder(FORM_BORDER));
        p1.add(tfpassword );
        
        // Security Question Label
        JLabel lblsecurity = new JLabel("Security Question");
        lblsecurity.setBounds(labelX,190,140,25); 
        lblsecurity.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblsecurity.setForeground(TEXT_LIGHT);
        p1.add(lblsecurity);
        
        // Choice for Security Questions (Dropdown)
        security = new Choice();
        security.add("What is your mother's maiden name?"); 
        security.add("What was your first pet's name?");
        security.add("Your Lucky Number");
        security.add("Your childhood Superhero");
        security.setBounds(fieldX,190,fieldWidth,28); 
        security.setBackground(FORM_FIELD_BG); // Dark dropdown background
        security.setForeground(TEXT_LIGHT); // White dropdown text
        p1.add(security);
        
        // Answer
        JLabel lblanswer = new JLabel("Answer");
        lblanswer.setBounds(labelX,230,125,25);
        lblanswer.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblanswer.setForeground(TEXT_LIGHT);
        p1.add(lblanswer);
        
        tfanswer = new JTextField();
        tfanswer .setBounds(fieldX, 230, fieldWidth, 28);
        tfanswer.setBackground(FORM_FIELD_BG);
        tfanswer.setForeground(TEXT_LIGHT);
        tfanswer.setCaretColor(TEXT_LIGHT);
        tfanswer .setBorder(BorderFactory.createLineBorder(FORM_BORDER));
        p1.add(tfanswer );
        
        // --- Buttons ---
        
        // Create Button (Deep Blue filled)
        create = new JButton("Create Account");
        create.setBackground(PANEL_ACCENT);
        create.setForeground(Color.WHITE);
        create.setFont(new Font("Tahoma",Font.BOLD,14));
        // 🚨 FIX 2: Centered buttons in the 480px frame
        create.setBounds(70,320,160,35); // Wider button
        create.addActionListener(this); 
        p1.add(create);
        
        // Back Button (White filled, Blue border/text)
        back = new JButton("Back to Login");
        back.setBackground(Color.WHITE);
        back.setForeground(PANEL_ACCENT);
        back.setFont(new Font("Tahoma",Font.BOLD,14));
        // 🚨 FIX 3: Placed next to the Create button
        back.setBounds(250,320,160,35); // Wider button
        back.setBorder(BorderFactory.createLineBorder(PANEL_ACCENT)); 
        back.addActionListener(this); 
        p1.add(back);
        
        // --- 4. Right Image/Visual Area (REMOVED) ---
        // The visualSpace panel and its contents are removed.
        
        setVisible(true);
    }
    
    
    public void actionPerformed(ActionEvent ae){
        
        if(ae.getSource()== create){
            String username = tfusername.getText();
            String name = tfname.getText();
            String password = tfpassword.getText();
            String question = security.getSelectedItem();
            String answer = tfanswer.getText();
            
            if (username.isEmpty() || password.isEmpty() || name.isEmpty()) {
                 JOptionPane.showMessageDialog(null, "All fields are required.");
                 return;
            }
            
            JOptionPane.showMessageDialog(null, "Account Creation Success!");
            setVisible(false);
            dispose();
            new Login(); 
            
        }else if (ae.getSource()== back){
            setVisible(false);
            dispose();
            new Login(); 
        }
    }
    
    public static void main(String[]args){
        
        new Signup(); 
    }
}
package PersonalFinanceManagerWeek2;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class ForgetPassword extends JFrame implements ActionListener{
    
    JTextField tfusername, tfname, tfquestion, tfanswer, tfpassword ;
    JButton search, retrive, back;
    
    // Define Dark/Modern Colors (Consistent with Login/Signup)
    private static final Color BG_DARK = new Color(25, 25, 25); // Very Dark Background
    private static final Color WIDGET_DARK = new Color(40, 40, 40); // Dark Input Field Background
    private static final Color BUTTON_ACCENT = new Color(30, 100, 255); // The Blue Accent Color
    private static final Color TEXT_LIGHT = Color.WHITE; // Light Text
    private static final Font LABEL_FONT = new Font("Tahoma", Font.BOLD, 14);
    private static final Font FIELD_FONT = new Font("Tahoma", Font.PLAIN, 14);

    ForgetPassword(){
        
        // --- Frame Setup ---
        // Reduced width from 850 since the image is removed
        setBounds(450, 200, 600, 380); 
        getContentPane().setBackground(BG_DARK); // Set background to dark
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close this window, not the whole app
        
        // creating a panel (Form Panel)
        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(BG_DARK); // Ensure panel background is dark
        // Expanded panel bounds to fill the new, smaller frame width
        p1.setBounds(0, 0, 600, 380); 
        add(p1);
        
        // --- Title ---
        JLabel title = new JLabel("FORGOT PASSWORD");
        title.setBounds(40, 10, 400, 30);
        title.setFont(new Font("Tahoma", Font.BOLD, 20));
        title.setForeground(BUTTON_ACCENT);
        p1.add(title);
        
        // --- Form Fields ---
        int yPos = 60;
        int ySpacing = 40;
        int labelX = 40;
        int fieldX = 220;
        int fieldWidth = 200; // Increased width for better input visibility
        int buttonX = 430;

        // 1. Username
        JLabel lblusername = createLabel("Username", labelX, yPos);
        p1.add(lblusername);
        
        tfusername = createTextField(fieldX, yPos, fieldWidth);
        tfusername.setCaretColor(TEXT_LIGHT); // Set cursor color
        p1.add(tfusername);
        
        // Search Button (Accent style)
        search = createButton("Search", buttonX, yPos, BUTTON_ACCENT);
        p1.add(search);
        yPos += ySpacing;
        
        // 2. Name
        JLabel lblname = createLabel("Name", labelX, yPos);
        p1.add(lblname);
        
        tfname = createTextField(fieldX, yPos, fieldWidth);
        tfname.setEditable(false); // Should be read-only after search
        p1.add(tfname);
        yPos += ySpacing;
        
        // 3. Security Question
        JLabel lblquestion = createLabel("Security Question", labelX, yPos);
        p1.add(lblquestion);
        
        tfquestion = createTextField(fieldX, yPos, fieldWidth);
        tfquestion.setEditable(false); // Should be read-only after search
        p1.add(tfquestion);
        yPos += ySpacing;
        
        // 4. Answer
        JLabel lblanswer = createLabel("Answer", labelX, yPos);
        p1.add(lblanswer);
        
        tfanswer = createTextField(fieldX, yPos, fieldWidth);
        tfanswer.setCaretColor(TEXT_LIGHT);
        p1.add(tfanswer);
        
        // Retrive Button (Accent style)
        retrive = createButton("Retrieve", buttonX, yPos, BUTTON_ACCENT);
        p1.add(retrive);
        yPos += ySpacing;
        
        // 5. Password
        JLabel lblpassword = createLabel("Password", labelX, yPos);
        p1.add(lblpassword);
        
        tfpassword = createTextField(fieldX, yPos, fieldWidth);
        tfpassword.setEditable(false); // Should be read-only/disabled initially
        p1.add(tfpassword);
        yPos += ySpacing + 20;
        
        // 6. Back Button (Standard style, centered)
        back = createButton("Back to Login", 250, yPos, Color.DARK_GRAY);
        back.setBounds(250, yPos, 150, 35); // Adjusted size and position
        p1.add(back);
        
        setVisible(true);
    }
    
    // --- Helper Methods for Styling ---
    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 150, 25);
        label.setFont(LABEL_FONT);
        label.setForeground(TEXT_LIGHT);
        return label;
    }
    
    private JTextField createTextField(int x, int y, int width) {
        JTextField tf = new JTextField();
        tf.setBounds(x, y, width, 28); // Increased height for better visibility
        tf.setBackground(WIDGET_DARK);
        tf.setForeground(TEXT_LIGHT);
        tf.setBorder(BorderFactory.createLineBorder(WIDGET_DARK.brighter()));
        return tf;
    }

    private JButton createButton(String text, int x, int y, Color bg) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 100, 28);
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Tahoma", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.addActionListener(this);
        return button;
    }

    // --- Action Performed ---
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()== search){
            String username = tfusername.getText();
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a username to search.");
                return;
            }
            // Temporary Logic for Week 2
            tfname.setText("Shahroz Ali"); // Dummy Data
            tfquestion.setText("Your Lucky Number"); // Dummy Data
            tfname.setEditable(false);
            tfquestion.setEditable(false);
            JOptionPane.showMessageDialog(this, "User found. Please answer the security question.");
            
        }else if(ae.getSource()== retrive){
            String answer = tfanswer.getText();
            if (answer.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter an answer.");
                return;
            }
            // Temporary Logic for Week 2 (Simulating successful retrieval)
            tfpassword.setText("temporarypass123"); // Dummy Password
            tfpassword.setEditable(false);
            JOptionPane.showMessageDialog(this, "Password retrieved successfully.");
            
        }else if(ae.getSource()== back){    // Back button press
            setVisible(false);
            dispose();
            // 🚨 FIX: Explicitly call the Login constructor
            new Login(); 
        }
    }
    
    public static void main(String[]args){
        
        new ForgetPassword();
    }
}
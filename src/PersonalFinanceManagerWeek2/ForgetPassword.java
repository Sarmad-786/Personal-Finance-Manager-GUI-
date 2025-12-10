package PersonalFinanceManagerWeek2;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;


public class ForgetPassword extends JFrame implements ActionListener{
    
    JTextField tfusername, tfname, tfquestion, tfanswer, tfpassword ;
    JButton search, retrive, back;
    
    // Define Dark/Modern Colors (Consistent with Login/Signup)
    private static final Color BG_DARK = new Color(25, 25, 25); 
    private static final Color WIDGET_DARK = new Color(40, 40, 40); 
    private static final Color BUTTON_ACCENT = new Color(30, 100, 255); 
    private static final Color TEXT_LIGHT = Color.WHITE; 
    private static final Font LABEL_FONT = new Font("Tahoma", Font.BOLD, 14);
    private static final Font FIELD_FONT = new Font("Tahoma", Font.PLAIN, 14);

    ForgetPassword(){
        // --- GUI Setup (Unchanged) ---
        setBounds(450, 200, 600, 380); 
        getContentPane().setBackground(BG_DARK); 
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        
        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(BG_DARK); 
        p1.setBounds(0, 0, 600, 380); 
        add(p1);
        
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
        int fieldWidth = 200; 
        int buttonX = 430;

        JLabel lblusername = createLabel("Username", labelX, yPos);
        p1.add(lblusername);
        
        tfusername = createTextField(fieldX, yPos, fieldWidth);
        tfusername.setCaretColor(TEXT_LIGHT); 
        p1.add(tfusername);
        
        search = createButton("Search", buttonX, yPos, BUTTON_ACCENT);
        p1.add(search);
        yPos += ySpacing;
        
        JLabel lblname = createLabel("Name", labelX, yPos);
        p1.add(lblname);
        
        tfname = createTextField(fieldX, yPos, fieldWidth);
        tfname.setEditable(false); 
        p1.add(tfname);
        yPos += ySpacing;
        
        JLabel lblquestion = createLabel("Security Question", labelX, yPos);
        p1.add(lblquestion);
        
        tfquestion = createTextField(fieldX, yPos, fieldWidth);
        tfquestion.setEditable(false); 
        p1.add(tfquestion);
        yPos += ySpacing;
        
        JLabel lblanswer = createLabel("Answer", labelX, yPos);
        p1.add(lblanswer);
        
        tfanswer = createTextField(fieldX, yPos, fieldWidth);
        tfanswer.setCaretColor(TEXT_LIGHT);
        p1.add(tfanswer);
        
        retrive = createButton("Retrieve", buttonX, yPos, BUTTON_ACCENT);
        p1.add(retrive);
        yPos += ySpacing;
        
        JLabel lblpassword = createLabel("Password", labelX, yPos);
        p1.add(lblpassword);
        
        tfpassword = createTextField(fieldX, yPos, fieldWidth);
        tfpassword.setEditable(false); 
        p1.add(tfpassword);
        yPos += ySpacing + 20;
        
        back = createButton("Back to Login", 250, yPos, Color.DARK_GRAY);
        back.setBounds(250, yPos, 150, 35); 
        p1.add(back);
        
        setVisible(true);
    }
    
    // --- Helper Methods (Unchanged) ---
    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 150, 25);
        label.setFont(LABEL_FONT);
        label.setForeground(TEXT_LIGHT);
        return label;
    }
    
    private JTextField createTextField(int x, int y, int width) {
        JTextField tf = new JTextField();
        tf.setBounds(x, y, width, 28); 
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

    // --- Action Performed (DB Logic Added) ---
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()== search){
            String username = tfusername.getText();
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a username to search.");
                return;
            }
            
            try {
                Conn c = new Conn();
                String query = "SELECT * FROM user WHERE username = '"+username+"'";
                ResultSet rs = c.s.executeQuery(query);
                
                if (rs.next()) {
                    tfname.setText(rs.getString("name"));
                    tfquestion.setText(rs.getString("security_q"));
                    tfname.setEditable(false);
                    tfquestion.setEditable(false);
                    JOptionPane.showMessageDialog(this, "User found. Please answer the security question.");
                } else {
                    JOptionPane.showMessageDialog(this, "User not found in database.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }else if(ae.getSource()== retrive){
            String username = tfusername.getText();
            String answer = tfanswer.getText();
            
            if (username.isEmpty() || answer.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please search for a user and enter the answer.");
                return;
            }
            
            try {
                Conn c = new Conn();
                String query = "SELECT password FROM user WHERE username = '"+username+"' AND security_ans = '"+answer+"'";
                ResultSet rs = c.s.executeQuery(query);
                
                if (rs.next()) {
                    tfpassword.setText(rs.getString("password"));
                    tfpassword.setEditable(false);
                    JOptionPane.showMessageDialog(this, "Password retrieved successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Incorrect answer provided.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }else if(ae.getSource()== back){    
            setVisible(false);
            dispose();
            new Login(); 
        }
    }
    
    public static void main(String[]args){
        
        new ForgetPassword();
    }
}
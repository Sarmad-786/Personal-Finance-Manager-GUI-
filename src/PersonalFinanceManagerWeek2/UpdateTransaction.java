package PersonalFinanceManagerWeek2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class UpdateTransaction extends JFrame implements ActionListener{
    
    // Define Colors and Fonts
    private static final Color FRAME_BG_COLOR = Color.BLACK; // Entire frame background
    private static final Color PANEL_BG_COLOR = new Color(30, 30, 30); // Darker panel for content
    private static final Color PRIMARY_COLOR = new Color(0, 153, 204); // Bright Blue for primary action
    private static final Color INPUT_BG_COLOR = new Color(50, 50, 50); // Dark Gray for input fields
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color FIELD_TEXT_COLOR = Color.WHITE;
    
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 24);
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 14);
    private static final Font FIELD_FONT = new Font("Arial", Font.PLAIN, 14);

    // Global Decalaration
    JLabel labelusername, labelTransactionRef;
    JTextField tfAmount, tfCategory, tfNotes , tfDate, tfAccount, tfTransactionID, tfType;
    JButton update, back; 
    
    // Constructor
    UpdateTransaction(String username){
        
        // --- Frame Size Adjustment (No image, so smaller width) ---
        setBounds(550, 200, 450, 550); // Width is 450
        setLocationRelativeTo(null); 
        setLayout(null); 
        getContentPane().setBackground(FRAME_BG_COLOR); // Set main content pane background to BLACK
        
        // --- Input Panel for visual grouping ---
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(null);
        inputPanel.setBackground(PANEL_BG_COLOR); // Dark gray background
        inputPanel.setBounds(0, 0, 450, 550); // Fills the entire frame width
        add(inputPanel);

        // Heading of this frame
        JLabel text = new JLabel("UPDATE TRANSACTION DETAILS");
        text.setBounds(30, 20, 400, 35); 
        text.setFont(TITLE_FONT);
        text.setForeground(PRIMARY_COLOR);
        inputPanel.add(text);
        
        
        // label for username (User who recorded the transaction)
        JLabel lblusername = new JLabel("User");
        lblusername.setBounds(30,70,150,25);
        lblusername.setFont(LABEL_FONT);
        lblusername.setForeground(TEXT_COLOR);
        inputPanel.add(lblusername);
        
        // for answer that we will fetch from database
        labelusername = new JLabel(username);
        labelusername.setBounds(220,70,150,25);
        labelusername.setFont(FIELD_FONT);
        labelusername.setForeground(PRIMARY_COLOR);
        inputPanel.add(labelusername);
        
        // label for Transaction ID
        JLabel lblTransactionID = new JLabel("Transaction ID (Lookup)");
        lblTransactionID .setBounds(30,110,180,25);
        lblTransactionID.setFont(LABEL_FONT);
        lblTransactionID.setForeground(TEXT_COLOR);
        inputPanel.add(lblTransactionID );
        
        // text field for Transaction ID (used to lookup the transaction)
        tfTransactionID = new JTextField(); 
        tfTransactionID.setBounds(220,110,150,25);
        tfTransactionID.setText("TXN-10112025-001"); // Dummy data
        tfTransactionID.setBackground(INPUT_BG_COLOR);
        tfTransactionID.setForeground(FIELD_TEXT_COLOR);
        tfTransactionID.setFont(FIELD_FONT);
        inputPanel.add( tfTransactionID);
        
        // label for Amount
        JLabel lblAmount = new JLabel("Amount");
        lblAmount .setBounds(30,150,150,25);
        lblAmount.setFont(LABEL_FONT);
        lblAmount.setForeground(TEXT_COLOR);
        inputPanel.add(lblAmount );
        
        // text field for Amount
        tfAmount = new JTextField(); 
        tfAmount.setBounds(220,150,150,25);
        tfAmount.setText("5500"); // Dummy data
        tfAmount.setBackground(INPUT_BG_COLOR);
        tfAmount.setForeground(FIELD_TEXT_COLOR);
        tfAmount.setFont(FIELD_FONT);
        inputPanel.add(tfAmount);
        
        // label for Transaction Reference
        JLabel lblRef = new JLabel("Ref. ID"); 
        lblRef.setBounds(30,190,150,25);
        lblRef.setFont(LABEL_FONT);
        lblRef.setForeground(TEXT_COLOR);
        inputPanel.add(lblRef);
        
        // Display for Transaction Reference (Read-only reference)
        labelTransactionRef = new JLabel("Auto/Cheque-234"); 
        labelTransactionRef.setBounds(220,190,150,25);
        labelTransactionRef.setFont(FIELD_FONT);
        labelTransactionRef.setForeground(Color.GRAY);
        inputPanel.add(labelTransactionRef);
        
        // label for Type (Income/Expense)
        JLabel lblType = new JLabel("Type (Income/Expense)"); 
        lblType.setBounds(30,230,180,25);
        lblType.setFont(LABEL_FONT);
        lblType.setForeground(TEXT_COLOR);
        inputPanel.add(lblType);
        
        // text field for Type
        tfType = new JTextField(); 
        tfType.setBounds(220,230,150,25);
        tfType.setText("Expense"); // Dummy data
        tfType.setBackground(INPUT_BG_COLOR);
        tfType.setForeground(FIELD_TEXT_COLOR);
        tfType.setFont(FIELD_FONT);
        inputPanel.add(tfType);
        
        // label for Category
        JLabel lblCategory = new JLabel("Category"); 
        lblCategory .setBounds(30,270,150,25);
        lblCategory.setFont(LABEL_FONT);
        lblCategory.setForeground(TEXT_COLOR);
        inputPanel.add(lblCategory );
        
        // text field for Category
        tfCategory = new JTextField(); 
        tfCategory.setBounds(220,270,150,25);
        tfCategory.setText("Groceries"); // Dummy data
        tfCategory.setBackground(INPUT_BG_COLOR);
        tfCategory.setForeground(FIELD_TEXT_COLOR);
        tfCategory.setFont(FIELD_FONT);
        inputPanel.add(tfCategory);
        
        // label for Notes/Description
        JLabel lblNotes = new JLabel("Notes"); 
        lblNotes .setBounds(30,310,150,25);
        lblNotes.setFont(LABEL_FONT);
        lblNotes.setForeground(TEXT_COLOR);
        inputPanel.add(lblNotes );
        
        // text field for Notes/Description
        tfNotes = new JTextField(); 
        tfNotes.setBounds(220,310,150,25);
        tfNotes.setText("Weekly Shopping"); // Dummy data
        tfNotes.setBackground(INPUT_BG_COLOR);
        tfNotes.setForeground(FIELD_TEXT_COLOR);
        tfNotes.setFont(FIELD_FONT);
        inputPanel.add(tfNotes);
        
        // label for Account
        JLabel lblAccount = new JLabel("Account"); 
        lblAccount .setBounds(30,350,150,25);
        lblAccount.setFont(LABEL_FONT);
        lblAccount.setForeground(TEXT_COLOR);
        inputPanel.add(lblAccount );
        
        // text field for Account
        tfAccount = new JTextField(); 
        tfAccount.setBounds(220,350,150,25);
        tfAccount.setText("HDFC Savings"); // Dummy data
        tfAccount.setBackground(INPUT_BG_COLOR);
        tfAccount.setForeground(FIELD_TEXT_COLOR);
        tfAccount.setFont(FIELD_FONT);
        inputPanel.add(tfAccount);
        
        // label for Date
        JLabel lblDate = new JLabel("Date (YYYY-MM-DD)"); 
        lblDate .setBounds(30,390,150,25);
        lblDate.setFont(LABEL_FONT);
        lblDate.setForeground(TEXT_COLOR);
        inputPanel.add(lblDate );
        
        // text field for Date
        tfDate = new JTextField(); 
        tfDate.setBounds(220,390,150,25);
        tfDate.setText("2025-11-18"); // Dummy data
        tfDate.setBackground(INPUT_BG_COLOR);
        tfDate.setForeground(FIELD_TEXT_COLOR);
        tfDate.setFont(FIELD_FONT);
        inputPanel.add( tfDate);
        
        
        // Creating Update button
        update = new JButton("Update Transaction"); 
        update.setBackground(PRIMARY_COLOR);
        update.setForeground(Color.WHITE);
        update.setFont(LABEL_FONT);
        update.setFocusPainted(false);
        // FIX APPLIED: Increased width to 180 and adjusted X position
        update.setBounds(50, 460, 180, 35); 
        update.addActionListener(this); 
        inputPanel.add(update);
        
        // Creating back button
        back = new JButton("Back");
        back.setBackground(Color.DARK_GRAY);
        back.setForeground(Color.WHITE);
        back.setFont(LABEL_FONT);
        back.setFocusPainted(false);
        // FIX APPLIED: Adjusted X position to follow the wider update button
        back.setBounds(240, 460, 100, 35); 
        back.addActionListener(this);
        inputPanel.add(back);
        
        setVisible(true);
    }
    
    // --- Event Handler: actionPerformed (Event-Driven Programming) ---
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()== update){ // Handle Update button
            // --- DATA COLLECTION LOGIC (Updated to Transaction fields) ---
            String transactionId = tfTransactionID.getText();
            String amount = tfAmount.getText();
            String category = tfCategory.getText();
            String date = tfDate.getText();
            
            // Basic validation check
            if (amount.isEmpty() || category.isEmpty() || date.isEmpty()) {
                 JOptionPane.showMessageDialog(null, "Please fill all required fields (Amount, Category, Date).", "Error", JOptionPane.ERROR_MESSAGE);
                 return;
            }
            
            // Temporary Success Message
            JOptionPane.showMessageDialog(null, "Transaction ID " + transactionId + " Updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            setVisible(false);
            
        } else if(ae.getSource()== back){ // Handle Back button
            setVisible(false);
        }
    }
    
    public static void main(String[]args){
        new UpdateTransaction("Sarmad");
    }
}
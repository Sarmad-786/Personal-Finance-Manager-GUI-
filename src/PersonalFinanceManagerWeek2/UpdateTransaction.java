package PersonalFinanceManagerWeek2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.format.DateTimeParseException;

public class UpdateTransaction extends JFrame implements ActionListener{
    
    // Define Colors and Fonts
    private static final Color FRAME_BG_COLOR = Color.BLACK; 
    private static final Color PANEL_BG_COLOR = new Color(30, 30, 30); 
    private static final Color PRIMARY_COLOR = new Color(0, 153, 204); 
    private static final Color INPUT_BG_COLOR = new Color(50, 50, 50); 
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color FIELD_TEXT_COLOR = Color.WHITE;
    
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 24);
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 14);
    private static final Font FIELD_FONT = new Font("Arial", Font.PLAIN, 14);

    // Global Decalaration
    JLabel labelusername, labelTransactionRef;
    JTextField tfAmount, tfCategory, tfNotes , tfDate, tfAccount, tfTransactionID, tfType;
    JButton update, back; 
    private String username; 
    
    // New variables to hold original data for calculating balance difference
    private double originalAmount = 0.0;
    private int originalAccountId = -1;
    private String originalTransactionType = "";

    // Constructor
    UpdateTransaction(String username){
        this.username = username;
        
        // --- Frame Setup ---
        setBounds(550, 200, 450, 550); 
        setLocationRelativeTo(null); 
        setLayout(null); 
        getContentPane().setBackground(FRAME_BG_COLOR); 
        
        // --- Input Panel for visual grouping ---
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(null);
        inputPanel.setBackground(PANEL_BG_COLOR); 
        inputPanel.setBounds(0, 0, 450, 550); 
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
        
        // text field for Transaction ID (Lookup field)
        tfTransactionID = new JTextField(); 
        tfTransactionID.setBounds(220,110,150,25);
        tfTransactionID.setText("1"); // Use a small, default ID for lookup demo
        tfTransactionID.setBackground(INPUT_BG_COLOR);
        tfTransactionID.setForeground(FIELD_TEXT_COLOR);
        tfTransactionID.setFont(FIELD_FONT);
        inputPanel.add( tfTransactionID);
        
        // --- Lookup Button (New addition for user-initiated fetch) ---
        JButton lookup = new JButton("Lookup");
        lookup.setBounds(375, 110, 60, 25);
        lookup.addActionListener(this);
        lookup.setBackground(Color.DARK_GRAY);
        lookup.setForeground(TEXT_COLOR);
        lookup.setActionCommand("Lookup");
        inputPanel.add(lookup);
        
        
        // label for Amount
        JLabel lblAmount = new JLabel("Amount");
        lblAmount .setBounds(30,150,150,25);
        lblAmount.setFont(LABEL_FONT);
        lblAmount.setForeground(TEXT_COLOR);
        inputPanel.add(lblAmount );
        
        tfAmount = new JTextField(); 
        tfAmount.setBounds(220,150,150,25);
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
        
        tfType = new JTextField(); 
        tfType.setBounds(220,230,150,25);
        tfType.setEditable(false); // Type should generally not be changed easily
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
        
        tfCategory = new JTextField(); 
        tfCategory.setBounds(220,270,150,25);
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
        
        tfNotes = new JTextField(); 
        tfNotes.setBounds(220,310,150,25);
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
        
        tfAccount = new JTextField(); 
        tfAccount.setBounds(220,350,150,25);
        tfAccount.setBackground(INPUT_BG_COLOR);
        tfAccount.setForeground(FIELD_TEXT_COLOR);
        tfAccount.setFont(FIELD_FONT);
        tfAccount.setEditable(false); // Account name is read-only after lookup
        inputPanel.add(tfAccount);
        
        // label for Date
        JLabel lblDate = new JLabel("Date (YYYY-MM-DD)"); 
        lblDate .setBounds(30,390,150,25);
        lblDate.setFont(LABEL_FONT);
        lblDate.setForeground(TEXT_COLOR);
        inputPanel.add(lblDate );
        
        tfDate = new JTextField(); 
        tfDate.setBounds(220,390,150,25);
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
        update.setBounds(50, 460, 180, 35); 
        update.addActionListener(this); 
        inputPanel.add(update);
        
        // Creating back button
        back = new JButton("Back");
        back.setBackground(Color.DARK_GRAY);
        back.setForeground(Color.WHITE);
        back.setFont(LABEL_FONT);
        back.setFocusPainted(false);
        back.setBounds(240, 460, 100, 35); 
        back.addActionListener(this);
        inputPanel.add(back);
        
        setVisible(true);
        
        // Auto-load data upon opening (using the default ID '1')
        fetchTransactionData();
    }
    
    // --- DATABASE: Fetch Data based on Transaction ID ---
    private void fetchTransactionData() {
        String transIdStr = tfTransactionID.getText();
        if (transIdStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Transaction ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Conn c = new Conn();
            // Query to join transaction record with the account name
            String query = "SELECT t.*, a.account_name FROM transaction_record t JOIN account a ON t.account_id = a.account_id WHERE t.transaction_id = "+transIdStr+" AND t.username = '"+username+"'";
            ResultSet rs = c.s.executeQuery(query);
            
            if (rs.next()) {
                // Store original data
                originalAmount = rs.getDouble("amount");
                originalTransactionType = rs.getString("trans_type");
                originalAccountId = rs.getInt("account_id");
                
                // Fill form fields
                tfAmount.setText(String.valueOf(originalAmount));
                tfType.setText(originalTransactionType);
                tfCategory.setText(rs.getString("category"));
                tfNotes.setText(rs.getString("notes"));
                tfDate.setText(rs.getString("trans_date"));
                tfAccount.setText(rs.getString("account_name"));
                labelTransactionRef.setText(rs.getString("payment_method"));
                
                // Set success title
                setTitle("Update Transaction Details - ID: " + transIdStr);
            } else {
                JOptionPane.showMessageDialog(this, "Transaction ID not found for this user.", "Lookup Failed", JOptionPane.ERROR_MESSAGE);
                // Clear fields if lookup fails
                tfAmount.setText("");
                tfCategory.setText("");
                tfNotes.setText("");
                tfDate.setText("");
                tfAccount.setText("");
                labelTransactionRef.setText("");
                originalAmount = 0.0;
                originalAccountId = -1;
                originalTransactionType = "";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error during lookup: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // --- DATABASE: Update Logic ---
    private void updateTransactionInDB() {
        String transactionIdStr = tfTransactionID.getText();
        String newAmountStr = tfAmount.getText();
        String newCategory = tfCategory.getText();
        String newNotes = tfNotes.getText();
        String newDate = tfDate.getText();

        if (originalAccountId == -1) {
             JOptionPane.showMessageDialog(null, "Please lookup a valid transaction ID first.", "Error", JOptionPane.ERROR_MESSAGE);
             return;
        }
        
        try {
            double newAmount = Double.parseDouble(newAmountStr);
            Conn c = new Conn();

            // 1. Calculate Balance Difference
            // The logic must first reverse the effect of the OLD amount and then apply the NEW amount.
            
            double oldAmountEffect = originalTransactionType.equals("Income") ? originalAmount : -originalAmount;
            double newAmountEffect = originalTransactionType.equals("Income") ? newAmount : -newAmount;
            
            double balanceDifference = newAmountEffect - oldAmountEffect;
            
            // 2. Update Transaction Record
            String updateTransQuery = "UPDATE transaction_record SET amount = "+newAmount+", category = '"+newCategory+"', notes = '"+newNotes+"', trans_date = '"+newDate+"' WHERE transaction_id = "+transactionIdStr;
            c.s.executeUpdate(updateTransQuery);
            
            // 3. Update Account Balance
            String updateBalanceQuery = "UPDATE account SET current_balance = current_balance + "+balanceDifference+" WHERE account_id = "+originalAccountId;
            c.s.executeUpdate(updateBalanceQuery);

            JOptionPane.showMessageDialog(null, "Transaction ID " + transactionIdStr + " updated successfully and balance recalculated!", "Success", JOptionPane.INFORMATION_MESSAGE);
            setVisible(false);

        } catch (NumberFormatException e) {
             JOptionPane.showMessageDialog(null, "Invalid amount format.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException e) {
             JOptionPane.showMessageDialog(null, "Invalid date format. Use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // --- Event Handler: actionPerformed ---
    public void actionPerformed(ActionEvent ae){
        if(ae.getActionCommand().equals("Update Transaction")){ 
            // Basic validation check
            if (tfAmount.getText().isEmpty() || tfCategory.getText().isEmpty() || tfDate.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            updateTransactionInDB();
            
        } else if(ae.getActionCommand().equals("Lookup")){
            fetchTransactionData();
            
        } else if(ae.getSource()== back){ // Handle Back button
            setVisible(false);
            dispose();
        }
    }
    
    public static void main(String[]args){
        new UpdateTransaction("sarmad");
    }
}
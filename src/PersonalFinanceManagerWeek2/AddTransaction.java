package PersonalFinanceManagerWeek2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Vector;

public class AddTransaction extends JFrame implements ActionListener {
    
    // ... (Colors and Fonts) ...
    private static final Color PRIMARY_COLOR = new Color(0, 153, 204); 
    private static final Color ACCENT_COLOR = new Color(255, 153, 51);   
    private static final Color INPUT_BG_COLOR = new Color(50, 50, 50); 
    private static final Color TEXT_COLOR = Color.WHITE; 
    private static final Color FIELD_TEXT_COLOR = Color.WHITE; 
    private static final Color FRAME_BG_COLOR = Color.BLACK; 
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 26);
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 14);
    private static final Font FIELD_FONT = new Font("Arial", Font.PLAIN, 14);

    // Global Decalaration
    JLabel labelusername, labeltransactionID;
    JComboBox<String> comboPaymentMethod; // Using generics
    JComboBox<String> comboAccount;      // Using generics
    JTextField tfAmount, tfCategory, tfNotes , tfDate;
    JRadioButton rExpense, rIncome;
    JButton add, back;
    
    private HashMap<String, Integer> accountMap = new HashMap<>(); // To map Account Name to Account ID
    private String currentUsername;

    // Constructor
    AddTransaction(String username) {
        this.currentUsername = username;
        
        // --- Frame Setup ---
        setBounds(550, 200, 450, 550); 
        setLocationRelativeTo(null); 
        setLayout(null); 
        getContentPane().setBackground(FRAME_BG_COLOR); 
        
        // --- Input Panel for visual grouping ---
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(null);
        inputPanel.setBackground(new Color(30, 30, 30)); 
        inputPanel.setBounds(0, 0, 450, 550); 
        add(inputPanel);

        // --- Title ---
        JLabel title = new JLabel("Add New Transaction");
        title.setFont(TITLE_FONT);
        title.setForeground(PRIMARY_COLOR); 
        title.setBounds(30, 20, 350, 35); 
        inputPanel.add(title);
        
        // --- 1. User ---
        JLabel lblusername = new JLabel("User");
        lblusername.setBounds(30, 70, 150, 25); 
        lblusername.setFont(LABEL_FONT);
        lblusername.setForeground(TEXT_COLOR); 
        inputPanel.add(lblusername);
        
        labelusername = new JLabel(username);
        labelusername.setBounds(200, 70, 150, 25); 
        labelusername.setFont(FIELD_FONT);
        labelusername.setForeground(PRIMARY_COLOR); 
        inputPanel.add(labelusername);
        
        // --- 2. Payment Method ---
        JLabel lblType = new JLabel("Payment Method");
        lblType.setBounds(30, 110, 150, 25); 
        lblType.setFont(LABEL_FONT);
        lblType.setForeground(TEXT_COLOR); 
        inputPanel.add(lblType);
        
        comboPaymentMethod = new JComboBox<>(new String[]{"Cash", "Credit Card", "Bank Transfer", "Digital Wallet"}); // Changed to generic JComboBox
        comboPaymentMethod.setBounds(200, 110, 150, 25); 
        comboPaymentMethod.setBackground(PRIMARY_COLOR); 
        comboPaymentMethod.setForeground(FIELD_TEXT_COLOR);    
        comboPaymentMethod.setFont(FIELD_FONT);
        inputPanel.add(comboPaymentMethod);
        
        // --- 3. Amount ---
        JLabel lblAmount = new JLabel("Amount (₹ / $)");
        lblAmount.setBounds(30, 150, 150, 25); 
        lblAmount.setFont(LABEL_FONT);
        lblAmount.setForeground(TEXT_COLOR); 
        inputPanel.add(lblAmount);
        
        tfAmount = new JTextField();
        tfAmount.setBounds(200, 150, 150, 25); 
        tfAmount.setBackground(INPUT_BG_COLOR); 
        tfAmount.setForeground(FIELD_TEXT_COLOR); 
        tfAmount.setCaretColor(FIELD_TEXT_COLOR); 
        tfAmount.setFont(FIELD_FONT);
        inputPanel.add(tfAmount);
        
        // --- 4. Transaction ID/Reference ---
        JLabel lblID = new JLabel("Reference ID");
        lblID.setBounds(30, 190, 150, 25); 
        lblID.setFont(LABEL_FONT);
        lblID.setForeground(TEXT_COLOR); 
        inputPanel.add(lblID);
        
        labeltransactionID = new JLabel("Auto-Generated (TID-XXXX)");
        labeltransactionID.setBounds(200, 190, 180, 25); 
        labeltransactionID.setFont(new Font("Arial", Font.ITALIC, 12));
        labeltransactionID.setForeground(Color.LIGHT_GRAY); 
        inputPanel.add(labeltransactionID);
        
        // --- 5. Transaction Type (Income/Expense) ---
        JLabel lblCategoryType = new JLabel("Transaction Flow");
        lblCategoryType.setBounds(30, 230, 150, 25); 
        lblCategoryType.setFont(LABEL_FONT);
        lblCategoryType.setForeground(TEXT_COLOR); 
        inputPanel.add(lblCategoryType);
        
        rExpense = new JRadioButton("Expense");
        rExpense.setBounds(200, 230, 90, 25); 
        rExpense.setBackground(new Color(30, 30, 30)); 
        rExpense.setForeground(Color.RED.brighter()); 
        rExpense.setFont(LABEL_FONT);
        
        rIncome = new JRadioButton("Income");
        rIncome.setBounds(290, 230, 90, 25); 
        rIncome.setBackground(new Color(30, 30, 30)); 
        rIncome.setForeground(ACCENT_COLOR); 
        rIncome.setFont(LABEL_FONT);
        
        ButtonGroup bg = new ButtonGroup();
        bg.add(rExpense);
        bg.add(rIncome);
        rExpense.setSelected(true); 
        inputPanel.add(rExpense);
        inputPanel.add(rIncome);
        
        // --- 6. Category (e.g., Food, Salary) ---
        JLabel lblCategory = new JLabel("Category");
        lblCategory.setBounds(30, 270, 150, 25); 
        lblCategory.setFont(LABEL_FONT);
        lblCategory.setForeground(TEXT_COLOR); 
        inputPanel.add(lblCategory);
        
        tfCategory = new JTextField();
        tfCategory.setBounds(200, 270, 150, 25); 
        tfCategory.setBackground(INPUT_BG_COLOR); 
        tfCategory.setForeground(FIELD_TEXT_COLOR); 
        tfCategory.setCaretColor(FIELD_TEXT_COLOR); 
        tfCategory.setFont(FIELD_FONT);
        inputPanel.add(tfCategory);
        
        // --- 7. Notes/Description ---
        JLabel lblNotes = new JLabel("Description");
        lblNotes.setBounds(30, 310, 150, 25); 
        lblNotes.setFont(LABEL_FONT);
        lblNotes.setForeground(TEXT_COLOR); 
        inputPanel.add(lblNotes);
        
        tfNotes = new JTextField();
        tfNotes.setBounds(200, 310, 150, 25); 
        tfNotes.setBackground(INPUT_BG_COLOR); 
        tfNotes.setForeground(FIELD_TEXT_COLOR); 
        tfNotes.setCaretColor(FIELD_TEXT_COLOR); 
        tfNotes.setFont(FIELD_FONT);
        inputPanel.add(tfNotes);
        
        // --- 8. Account (MODIFIED: Initialize empty, populate later) ---
        JLabel lblAccount = new JLabel("Account/Bank");
        lblAccount.setBounds(30, 350, 150, 25); 
        lblAccount.setFont(LABEL_FONT);
        lblAccount.setForeground(TEXT_COLOR); 
        inputPanel.add(lblAccount);
        
        // Initialize JComboBox with an empty array/vector
        comboAccount = new JComboBox<>(); 
        comboAccount.setBounds(200, 350, 150, 25); 
        comboAccount.setBackground(PRIMARY_COLOR); 
        comboAccount.setForeground(FIELD_TEXT_COLOR); 
        comboAccount.setFont(FIELD_FONT);
        inputPanel.add(comboAccount);
        
        // --- FETCH ACCOUNTS NOW and populate JComboBox ---
        loadAccounts();
        
        // --- 9. Date ---
        JLabel lblDate = new JLabel("Date (YYYY-MM-DD)"); 
        lblDate.setBounds(30, 390, 150, 25); 
        lblDate.setFont(LABEL_FONT);
        lblDate.setForeground(TEXT_COLOR); 
        inputPanel.add(lblDate);
        
        tfDate = new JTextField();
        tfDate.setBounds(200, 390, 150, 25); 
        tfDate.setText(java.time.LocalDate.now().toString()); 
        tfDate.setBackground(INPUT_BG_COLOR); 
        tfDate.setForeground(FIELD_TEXT_COLOR); 
        tfDate.setCaretColor(FIELD_TEXT_COLOR); 
        tfDate.setFont(FIELD_FONT);
        inputPanel.add(tfDate);
        
        // --- Buttons ---
        add = new JButton("Add Transaction");
        add.setBackground(PRIMARY_COLOR);
        add.setForeground(Color.WHITE);
        add.setFont(LABEL_FONT);
        add.setFocusPainted(false); 
        add.setBounds(70, 460, 150, 35); 
        add.addActionListener(this); 
        inputPanel.add(add);
        
        back = new JButton("Back");
        back.setBackground(Color.DARK_GRAY); 
        back.setForeground(Color.WHITE);
        back.setFont(LABEL_FONT);
        back.setFocusPainted(false);
        back.setBounds(230, 460, 100, 35); 
        back.addActionListener(this);
        inputPanel.add(back);
        
        setVisible(true);
    }
    
    // --- DATABASE: Load Accounts ---
    private void loadAccounts() {
        try {
            Conn c = new Conn();
            String query = "SELECT account_id, account_name FROM account WHERE username = '"+currentUsername+"'";
            ResultSet rs = c.s.executeQuery(query);
            
            comboAccount.removeAllItems(); // Clear previous items

            while (rs.next()) {
                String accountName = rs.getString("account_name");
                int accountId = rs.getInt("account_id");
                accountMap.put(accountName, accountId);
                comboAccount.addItem(accountName); // Add item to JComboBox
            }
            
            if (accountMap.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No accounts found for this user. Please create one via Manage Accounts.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Error loading accounts: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    // Action Listener remains functional
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()== add){
            String username = labelusername.getText();
            String paymentMethod = (String)comboPaymentMethod.getSelectedItem();
            String amountStr = tfAmount.getText();
            String transactionType = rExpense.isSelected() ? "Expense" : "Income";
            String category = tfCategory.getText();
            String notes = tfNotes.getText();
            String accountName = (String)comboAccount.getSelectedItem(); // Get selected account name
            String date = tfDate.getText();
            
            if (amountStr.isEmpty() || category.isEmpty() || accountName == null || date.isEmpty()) {
                 JOptionPane.showMessageDialog(null, "Please fill all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
                 return;
            }
            
            if (accountMap.isEmpty() || accountName.isEmpty()) {
                 JOptionPane.showMessageDialog(null, "No valid account selected/available.", "Error", JOptionPane.ERROR_MESSAGE);
                 return;
            }
            
            try {
                double amount = Double.parseDouble(amountStr);
                
                // --- 1. Get account_id using the HashMap ---
                int accountId = accountMap.get(accountName);
                
                Conn c = new Conn();

                // 2. Insert Transaction
                String insertTransaction = "INSERT INTO transaction_record (username, account_id, trans_type, amount, category, payment_method, trans_date, notes) " +
                                           "VALUES ('"+username+"', "+accountId+", '"+transactionType+"', "+amount+", '"+category+"', '"+paymentMethod+"', '"+date+"', '"+notes+"')";
                c.s.executeUpdate(insertTransaction);
                
                // 3. Update Account Balance
                double balanceChange = transactionType.equals("Income") ? amount : -amount;
                String updateBalance = "UPDATE account SET current_balance = current_balance + "+balanceChange+" WHERE account_id = "+accountId;
                c.s.executeUpdate(updateBalance);

                JOptionPane.showMessageDialog(null, transactionType + " of " + amount + " added successfully and balance updated in " + accountName + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
                setVisible(false);
            
            } catch (NumberFormatException e) {
                 JOptionPane.showMessageDialog(null, "Invalid amount format.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else if(ae.getSource()== back){
            setVisible(false);
        }
    }
    
    public static void main(String[]args){
        new AddTransaction("sarmad"); 
    }
}
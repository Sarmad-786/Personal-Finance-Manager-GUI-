package PersonalFinanceManagerWeek2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*; // Import for Database operations

public class AddIncome extends JFrame implements ActionListener {
    Choice cCategory;
    JTextField tfAmount, tfSource;
    String username;
    JLabel labelusername, labelid, labelnumber, labelphone;
    JButton addIncome, clearFields, back;
    
    // Define Dark Theme Colors
    private static final Color BG_DARK = new Color(25, 25, 25);
    private static final Color PANEL_DARK = new Color(40, 40, 40);
    private static final Color TEXT_LIGHT = Color.WHITE;
    private static final Color ACCENT_GREEN = new Color(0, 150, 0);
    private static final Color ACCENT_BLUE = new Color(30, 100, 255);
    
    // --- New Global Variables for Account Data ---
    private int accountId = -1;
    private String accountName = "N/A";
    
    AddIncome(String username) {
        this.username = username;
        
        // --- Fetch User's First Account for Income Deposit ---
        fetchDefaultAccount(); 

        // --- Frame Setup (Unchanged) ---
        setTitle("Add Income / Deposit");
        setBounds(0, 0, 700, 600); 
        setLocationRelativeTo(null); 
        setLayout(null);
        getContentPane().setBackground(BG_DARK);

        // --- GUI Component Setup (Unchanged) ---
        int labelX = 150; 
        int valueX = 350; 
        int yStart = 70;
        int ySpacing = 40;

        // Heading
        JLabel text = new JLabel("RECORD NEW INCOME / DEPOSIT");
        text.setBounds(150, 10, 400, 30); 
        text.setFont(new Font("Tahoma", Font.BOLD, 22));
        text.setForeground(ACCENT_GREEN);
        add(text);

        // 1. User Account
        JLabel lblusername = createLabel("User Account", labelX, yStart);
        add(lblusername);

        labelusername = createValueLabel(username, valueX, yStart);
        add(labelusername);
        yStart += ySpacing;

        // 2. Income Category
        JLabel lblCategory = createLabel("Income Category", labelX, yStart);
        add(lblCategory);

        cCategory = new Choice();
        cCategory.add("Salary");
        cCategory.add("Freelance/Project");
        cCategory.add("Investment Return");
        cCategory.add("Gift/Other");
        // NEW CATEGORY: Loan Repayment or Loan Given
        cCategory.add("Loan Repayment (Received)");
        cCategory.add("Loan Given (Payable Asset)"); 
        cCategory.setBounds(valueX, yStart, 200, 20);
        cCategory.setBackground(PANEL_DARK);
        cCategory.setForeground(TEXT_LIGHT);
        add(cCategory);
        yStart += ySpacing;

        // 3. Amount
        JLabel lblAmount = createLabel("Amount (Rs)", labelX, yStart);
        add(lblAmount);

        tfAmount = new JTextField();
        tfAmount.setBounds(valueX, yStart, 200, 25);
        tfAmount.setBackground(PANEL_DARK);
        tfAmount.setForeground(ACCENT_GREEN);
        tfAmount.setCaretColor(TEXT_LIGHT);
        add(tfAmount);
        yStart += ySpacing;

        // 4. Source/Payer
        JLabel lblSource = createLabel("Source/Payer", labelX, yStart);
        add(lblSource);

        tfSource = new JTextField();
        tfSource.setBounds(valueX, yStart, 200, 25);
        tfSource.setBackground(PANEL_DARK);
        tfSource.setForeground(TEXT_LIGHT);
        tfSource.setCaretColor(TEXT_LIGHT);
        add(tfSource);
        yStart += ySpacing;
        
        // 5. Deposit Account 
        JLabel lblDepositAccount = createLabel("Deposit Account", labelX, yStart);
        add(lblDepositAccount);

        labelid = createValueLabel(accountName, valueX, yStart); 
        labelid.setForeground(ACCENT_BLUE); 
        add(labelid);
        yStart += ySpacing;

        // 6. Date
        JLabel lblDate = createLabel("Date (YYYY-MM-DD)", labelX, yStart);
        add(lblDate);

        labelnumber = createValueLabel(java.time.LocalDate.now().toString(), valueX, yStart); 
        add(labelnumber);
        yStart += ySpacing;

        // 7. Notes
        JLabel lblNotes = createLabel("Notes (Optional)", labelX, yStart);
        add(lblNotes);

        labelphone = createValueLabel("", valueX, yStart); 
        add(labelphone);
        yStart += ySpacing + 20;

        // --- Buttons (Unchanged) ---
        int buttonXStart = 150; 

        addIncome = new JButton("Add Income");
        addIncome.setBackground(ACCENT_GREEN);
        addIncome.setForeground(TEXT_LIGHT);
        addIncome.setBounds(buttonXStart, yStart, 120, 25);
        addIncome.addActionListener(this);
        add(addIncome);

        clearFields = new JButton("Clear Fields");
        clearFields.setBackground(ACCENT_BLUE);
        clearFields.setForeground(TEXT_LIGHT);
        clearFields.setBounds(buttonXStart + 140, yStart, 120, 25);
        clearFields.addActionListener(this);
        add(clearFields);

        back = new JButton("Back");
        back.setBackground(Color.DARK_GRAY);
        back.setForeground(TEXT_LIGHT);
        back.setBounds(buttonXStart + 280, yStart, 120, 25);
        back.addActionListener(this);
        add(back);

        setVisible(true);
    }
    
    // --- DATABASE: Fetch Default Account ---
    private void fetchDefaultAccount() {
        ResultSet rs = null;
        try {
            Conn c = new Conn();
            String query = "SELECT account_id, account_name FROM account WHERE username = '"+username+"' ORDER BY account_id LIMIT 1";
            rs = c.s.executeQuery(query);
            
            if (rs.next()) {
                accountId = rs.getInt("account_id");
                accountName = rs.getString("account_name");
            } else {
                 accountName = "No Account Found!";
                 JOptionPane.showMessageDialog(this, "No financial account found for this user. Please create one first.", "Setup Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    // Helper methods (Unchanged)
    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label.setBounds(x, y, 150, 25);
        label.setForeground(TEXT_LIGHT);
        return label;
    }

    private JLabel createValueLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label.setBounds(x, y, 200, 25);
        label.setForeground(TEXT_LIGHT);
        return label;
    }


    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addIncome) {
            
            if (accountId == -1) {
                 JOptionPane.showMessageDialog(null, "Cannot add income: No valid deposit account found.", "Error", JOptionPane.ERROR_MESSAGE);
                 return;
            }
            
            String amountStr = tfAmount.getText();
            String category = cCategory.getSelectedItem();
            String source = tfSource.getText();
            String date = labelnumber.getText(); 
            
            if (amountStr.isEmpty() || source.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill Amount and Source fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double amount = Double.parseDouble(amountStr);

                if (amount <= 0) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid amount greater than zero.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Conn c = new Conn();
                double balanceChange;
                String transType;
                
                // --- NEW LOGIC: Check for Asset/Liability transaction ---
                if (category.equals("Loan Given (Payable Asset)")) {
                    // This is money leaving your main account (Expense) but creating a receivable Asset (Loan)
                    transType = "Expense"; // Record as expense from your main account
                    balanceChange = -amount; // Decrease account balance
                    category = "Loan Given - " + source; // Modify category for tracking
                } else {
                    // Standard Income (e.g., Salary, Gift)
                    transType = "Income";
                    balanceChange = amount; // Increase account balance
                }

                // 1. Insert Transaction 
                String insertTransaction = "INSERT INTO transaction_record (username, account_id, trans_type, amount, category, payment_method, trans_date, notes) " +
                                           "VALUES ('"+username+"', "+accountId+", '"+transType+"', "+amount+", '"+category+"', 'Source: "+source+"', '"+date+"', 'Notes: "+source+"')";
                c.s.executeUpdate(insertTransaction);
                
                // 2. Update Account Balance 
                String updateBalance = "UPDATE account SET current_balance = current_balance + "+balanceChange+" WHERE account_id = "+accountId;
                c.s.executeUpdate(updateBalance);

                String message = String.format("Successfully recorded Rs %,.2f. Account: %s, Flow: %s", amount, accountName, category);
                JOptionPane.showMessageDialog(null, message, "Transaction Recorded", JOptionPane.INFORMATION_MESSAGE);
                
                // Clear fields after successful recording
                tfAmount.setText("");
                tfSource.setText("");

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number for Amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } else if (ae.getSource() == clearFields) {
            tfAmount.setText("");
            tfSource.setText("");
            cCategory.select(0);
        } else if (ae.getSource() == back) {
            setVisible(false);
            dispose();
        }
    }

    public static void main(String[] args) {
        new AddIncome("sarmad");
    }
}
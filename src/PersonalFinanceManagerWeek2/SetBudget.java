package PersonalFinanceManagerWeek2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*; 
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Renamed class to SetBudget
public class SetBudget extends JFrame implements ActionListener{
    Choice cCategory; 
    JTextField tfAmount; 
    String username;
    JLabel labelusername, labelAccount, labelBudgetPeriod, labelBudgetNote, labelTotalLimit; 
    JButton checkLimit, saveBudget, back; 
    
    // Define Dark Theme Colors
    private static final Color BG_DARK = new Color(25, 25, 25);
    private static final Color FIELD_BG = new Color(50, 50, 50);
    private static final Color TEXT_LIGHT = Color.WHITE;
    private static final Color ACCENT_BLUE = new Color(30, 100, 255);
    private static final Color ALERT_RED = new Color(255, 60, 60);
    
    // New Global Variables for Account Data
    private String linkedAccountName = "N/A";
    private String currentBudgetMonth = ""; 
    
    // Updated constructor to SetBudget
    SetBudget(String username){
        this.username = username;
        
        // Fetch live read-only data
        fetchDefaultAccount();
        
        // Calculate current month's budget start date (e.g., 2025-12-01)
        currentBudgetMonth = LocalDate.now().withDayOfMonth(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        // Frame setup
        setBounds(350,200,1100,500);
        setLocationRelativeTo(null); 
        setLayout(null);
        getContentPane().setBackground(BG_DARK); 
        
        // --- Heading ---
        JLabel text = new JLabel("SET MONTHLY BUDGET");
        text.setBounds(100,10,350,30);
        text.setFont(new Font("Tahoma",Font.BOLD, 24));
        text.setForeground(TEXT_LIGHT);
        add(text);
        
        // --- GUI Components Setup ---
        
        // FIX: Declare positioning variables here
        int labelX = 40;
        int valueX = 250;
        int yStart = 70; // Starting Y position
        int ySpacing = 40; // Spacing between rows

        // for username
        JLabel lblusername = createLabel("User Account", labelX, yStart);
        add(lblusername);
        
        // Display fetched username
        labelusername = new JLabel();
        labelusername.setFont(new Font("Tahoma",Font.PLAIN, 16));
        labelusername.setBounds(valueX,yStart,200,20);
        labelusername.setText(username); 
        labelusername.setForeground(TEXT_LIGHT);
        add(labelusername);
        yStart += ySpacing;
        
        // for select Category
        JLabel lblCategory = createLabel("Select Category", labelX, yStart);
        add(lblCategory);
        
        // Drop down for categories
        cCategory = new Choice();
        cCategory.add("Groceries");
        cCategory.add("Rent & Utilities");
        cCategory.add("Entertainment");
        cCategory.add("Transportation");
        cCategory.add("Investment/Savings");
        cCategory.setBounds(valueX,yStart,200,20);
        cCategory.setBackground(FIELD_BG);
        cCategory.setForeground(TEXT_LIGHT);
        add(cCategory);
        yStart += ySpacing;
        
        
        // for Budget Limit
        JLabel lblLimit = createLabel("Target Limit (Rs)", labelX, yStart);
        add(lblLimit);
        
        // text field for budget limit
        tfAmount = new JTextField("10000"); // Default starting value
        tfAmount.setBounds(valueX,yStart,200,25);
        tfAmount.setBackground(FIELD_BG);
        tfAmount.setForeground(TEXT_LIGHT);
        tfAmount.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(tfAmount);
        yStart += ySpacing;
        
        // for Account
        JLabel lblAccount = createLabel("Linked Account", labelX, yStart);
        add(lblAccount);
        
        // Display linked account (Live Data)
        labelAccount = new JLabel();
        labelAccount .setBounds(valueX,yStart,200,25);
        labelAccount.setText(linkedAccountName);
        labelAccount.setForeground(TEXT_LIGHT);
        add(labelAccount );
        yStart += ySpacing;
        
        // for Budget Period
        JLabel lblPeriod = createLabel("Budget Period", labelX, yStart);
        add(lblPeriod);
        
        // Display Budget Period (Live Data)
        labelBudgetPeriod = new JLabel();
        labelBudgetPeriod .setBounds(valueX,yStart,200,25);
        // Display Month/Year of the start date
        labelBudgetPeriod.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM yyyy"))); 
        labelBudgetPeriod.setForeground(TEXT_LIGHT);
        add(labelBudgetPeriod );
        yStart += ySpacing;
        
        // for Phone/Contact (Goal/Note)
        JLabel lblNote = createLabel("Goal/Note", labelX, yStart);
        add(lblNote);
        
        // Display Note (Dummy Data)
        labelBudgetNote = new JLabel();
        labelBudgetNote .setBounds(valueX,yStart,200,25);
        labelBudgetNote.setText("Cut spending by 10%");
        labelBudgetNote.setForeground(TEXT_LIGHT);
        add(labelBudgetNote );
        yStart += ySpacing;
        
        // for total limit
        JLabel lbltotal = createLabel("Total Budget Limit", labelX, yStart);
        add(lbltotal);
        
        // Display final calculated limit
        labelTotalLimit = new JLabel();
        labelTotalLimit .setBounds(valueX,yStart,200,25);
        labelTotalLimit.setForeground(ALERT_RED); // Highlighted in red/accent color
        labelTotalLimit.setFont(new Font("Tahoma",Font.BOLD, 18));
        add(labelTotalLimit );
        // yStart += ySpacing; // Not needed, jumping straight to buttons
        
        
        // --- Buttons (Professional Styling) ---
        
        int buttonY = 380;
        
        checkLimit = new JButton("Calculate Limit");
        checkLimit.setBackground(ACCENT_BLUE);
        checkLimit.setForeground(Color.WHITE);
        checkLimit.setBounds(60, buttonY, 140, 35);
        checkLimit.setFont(new Font("Tahoma",Font.BOLD, 14));
        checkLimit.setBorder(BorderFactory.createEmptyBorder());
        checkLimit.addActionListener(this);
        add(checkLimit);
        
        saveBudget = new JButton("Save Budget");
        saveBudget.setBackground(ACCENT_BLUE);
        saveBudget.setForeground(Color.WHITE);
        saveBudget.setBounds(220, buttonY, 120, 35);
        saveBudget.setFont(new Font("Tahoma",Font.BOLD, 14));
        saveBudget.setBorder(BorderFactory.createEmptyBorder());
        saveBudget.addActionListener(this);
        add(saveBudget);
        
        back = new JButton("Back");
        back.setBackground(Color.DARK_GRAY);
        back.setForeground(Color.WHITE);
        back.setBounds(360, buttonY, 100, 35);
        back.setFont(new Font("Tahoma",Font.BOLD, 14));
        back.setBorder(BorderFactory.createEmptyBorder());
        back.addActionListener(this);
        add(back);
        
        // Image on right side of frame (Placeholder for visualization)
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/bookpackage.jpg")); 
        Image i2 = i1.getImage().getScaledInstance(500, 300, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l12 = new JLabel(i3);
        l12.setBounds(550, 50, 500, 300);
        JLabel placeholder = new JLabel("Budget Visualization Area");
        placeholder.setBounds(650, 20, 300, 20);
        placeholder.setForeground(Color.GRAY);
        l12.add(placeholder);
        
        add(l12);
        
        setVisible(true);
    }
    
    // --- DATABASE: Fetch Default Account (Read-only) ---
    private void fetchDefaultAccount() {
        ResultSet rs = null;
        try {
            Conn c = new Conn();
            // Fetch the first account Name for the current user
            String query = "SELECT account_name FROM account WHERE username = '"+username+"' ORDER BY account_id LIMIT 1";
            rs = c.s.executeQuery(query);
            
            if (rs.next()) {
                linkedAccountName = rs.getString("account_name");
            } else {
                 linkedAccountName = "NO ACCOUNT FOUND";
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

    // --- Helper method to create standard dark-theme labels
    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label.setBounds(x, y, 150, 25);
        label.setForeground(TEXT_LIGHT);
        return label;
    }
    
    // --- Event Handler: actionPerformed (DB Insertion Logic) ---
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==checkLimit){
            String category = cCategory.getSelectedItem();
            double limit = 0; 
            try {
                double amount = Double.parseDouble(tfAmount.getText());
                
                // --- REQUIREMENT ADDED: Cut 10 percent ---
                limit = amount * 0.90; // 10% cut
                
                // Show Rs value in display label
                labelTotalLimit.setText("Rs " + (int)limit);
                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid amount for the budget limit.");
                labelTotalLimit.setText("Rs 0");
            }
            
        }
        else if(ae.getSource()== saveBudget){
            
            String category = cCategory.getSelectedItem();
            String amountStr = tfAmount.getText();
            String totalLimitStr = labelTotalLimit.getText();
            
            if (amountStr.isEmpty() || totalLimitStr.contains("Rs 0")) {
                 JOptionPane.showMessageDialog(null, "Please calculate the limit first.", "Input Error", JOptionPane.ERROR_MESSAGE);
                 return;
            }
            
            try {
                double targetLimit = Double.parseDouble(amountStr) * 0.90; // Use 90% of the input amount
                
                if (targetLimit <= 0) {
                     JOptionPane.showMessageDialog(null, "Budget amount must be greater than zero.", "Input Error", JOptionPane.ERROR_MESSAGE);
                     return;
                }
                
                Conn c = new Conn();
                ResultSet rs = null;
                
                // --- 1. Check if Budget already exists for this Category/Month ---
                String checkQuery = "SELECT * FROM budget WHERE username = '"+username+"' AND category = '"+category+"' AND budget_month = '"+currentBudgetMonth+"'";
                rs = c.s.executeQuery(checkQuery);

                if (rs.next()) {
                    // Budget exists, prompt to update
                    int confirm = JOptionPane.showConfirmDialog(this, "Budget for " + category + " already exists. Do you want to UPDATE it?", "Confirm Update", JOptionPane.YES_NO_OPTION);
                    
                    if (confirm == JOptionPane.YES_OPTION) {
                         String updateQuery = "UPDATE budget SET target_limit = "+targetLimit+" WHERE username = '"+username+"' AND category = '"+category+"' AND budget_month = '"+currentBudgetMonth+"'";
                         c.s.executeUpdate(updateQuery);
                         JOptionPane.showMessageDialog(null, "Budget for " + category + " UPDATED to Rs " + (int)targetLimit);
                    } else {
                        return; // Cancelled update
                    }
                } else {
                    // Budget does not exist, insert new
                    String insertQuery = "INSERT INTO budget (username, category, target_limit, budget_month) " +
                                         "VALUES ('"+username+"', '"+category+"', "+targetLimit+", '"+currentBudgetMonth+"')";
                    c.s.executeUpdate(insertQuery);
                    JOptionPane.showMessageDialog(null, "New Budget for " + category + " saved successfully at Rs " + (int)targetLimit);
                }
                
                setVisible(false);
                
            } catch (NumberFormatException e) {
                 JOptionPane.showMessageDialog(this, "Please enter a valid number for the budget amount.");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Database Error: Could not save budget: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        }
        else if(ae.getSource()== back){
            setVisible(false);
        }
    }
    
    public static void main(String[]args){
        new SetBudget("sarmad");
    }
}
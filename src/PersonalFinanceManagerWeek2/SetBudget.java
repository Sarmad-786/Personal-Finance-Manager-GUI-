package PersonalFinanceManagerWeek2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Renamed class to SetBudget
public class SetBudget extends JFrame implements ActionListener{
    Choice cCategory; // Changed from cpackage
    JTextField tfAmount; // Changed from tfpersons (now Budget Limit)
    String username;
    JLabel labelusername, labelAccount, labelBudgetPeriod, labelBudgetNote, labelTotalLimit; // Updated labels
    JButton checkLimit, saveBudget, back; 
    
    // Define Dark Theme Colors
    private static final Color BG_DARK = new Color(25, 25, 25);
    private static final Color FIELD_BG = new Color(50, 50, 50);
    private static final Color TEXT_LIGHT = Color.WHITE;
    private static final Color ACCENT_BLUE = new Color(30, 100, 255);
    private static final Color ALERT_RED = new Color(255, 60, 60);
    
    // Updated constructor to SetBudget
    SetBudget(String username){
        this.username = username;
        
        // Frame setup
        setBounds(350,200,1100,500);
        setLocationRelativeTo(null); 
        setLayout(null);
        getContentPane().setBackground(BG_DARK); // <<< SET BACKGROUND TO DARK
        
        JLabel text = new JLabel("SET MONTHLY BUDGET");
        text.setBounds(100,10,350,30);
        text.setFont(new Font("Tahoma",Font.BOLD, 24));
        text.setForeground(TEXT_LIGHT);
        add(text);
        
        // --- GUI Components Setup (Adapted for Budgeting) ---
        
        // for username
        JLabel lblusername = new JLabel("User Account");
        lblusername.setFont(new Font("Tahoma",Font.PLAIN, 16));
        lblusername.setBounds(40,70,150,20);
        lblusername.setForeground(TEXT_LIGHT);
        add(lblusername);
        
        // Display fetched username
        labelusername = new JLabel();
        labelusername.setFont(new Font("Tahoma",Font.PLAIN, 16));
        labelusername.setBounds(250,70,200,20);
        labelusername.setText(username); 
        labelusername.setForeground(TEXT_LIGHT);
        add(labelusername);
        
        // for select Category (Replaced 'Select Package')
        JLabel lblCategory = new JLabel("Select Category");
        lblCategory.setFont(new Font("Tahoma",Font.PLAIN, 16));
        lblCategory.setBounds(40,110,150,20);
        lblCategory.setForeground(TEXT_LIGHT);
        add(lblCategory);
        
        // Drop down for categories
        cCategory = new Choice();
        cCategory.add("Groceries");
        cCategory.add("Rent & Utilities");
        cCategory.add("Entertainment");
        cCategory.add("Transportation");
        cCategory.add("Investment/Savings");
        cCategory.setBounds(250,110,200,20);
        cCategory.setBackground(FIELD_BG);
        cCategory.setForeground(TEXT_LIGHT);
        add(cCategory);
        
        
        // for Budget Limit (Replaced 'Total Persons')
        JLabel lblLimit = new JLabel("Target Limit (Rs)");
        lblLimit.setFont(new Font("Tahoma",Font.PLAIN, 16));
        lblLimit.setBounds(40,150,150,25);
        lblLimit.setForeground(TEXT_LIGHT);
        add(lblLimit);
        
        // text field for budget limit
        tfAmount = new JTextField("10000"); // Default starting value
        tfAmount.setBounds(250,150,200,25);
        tfAmount.setBackground(FIELD_BG);
        tfAmount.setForeground(TEXT_LIGHT);
        tfAmount.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(tfAmount);
        
        // for Account (Replaced 'ID Type')
        JLabel lblAccount = new JLabel("Linked Account");
        lblAccount.setFont(new Font("Tahoma",Font.PLAIN, 16));
        lblAccount.setBounds(40,190,150,20);
        lblAccount.setForeground(TEXT_LIGHT);
        add(lblAccount);
        
        // Display linked account (Dummy Data)
        labelAccount = new JLabel();
        labelAccount .setBounds(250,190,200,25);
        labelAccount.setText("HDFC Savings");
        labelAccount.setForeground(TEXT_LIGHT);
        add(labelAccount );
        
        // for Budget Period (Replaced 'Number')
        JLabel lblPeriod = new JLabel("Budget Period");
        lblPeriod.setFont(new Font("Tahoma",Font.PLAIN, 16));
        lblPeriod.setBounds(40,230,150,25);
        lblPeriod.setForeground(TEXT_LIGHT);
        add(lblPeriod);
        
        // Display Budget Period (Dummy Data)
        labelBudgetPeriod = new JLabel();
        labelBudgetPeriod .setBounds(250,230,200,25);
        labelBudgetPeriod.setText("November 2025");
        labelBudgetPeriod.setForeground(TEXT_LIGHT);
        add(labelBudgetPeriod );
        
        // for Phone/Contact (Replaced 'Phone')
        JLabel lblNote = new JLabel("Goal/Note");
        lblNote.setFont(new Font("Tahoma",Font.PLAIN, 16));
        lblNote.setBounds(40,270,150,25);
        lblNote.setForeground(TEXT_LIGHT);
        add(lblNote);
        
        // Display Note (Dummy Data)
        labelBudgetNote = new JLabel();
        labelBudgetNote .setBounds(250,270,200,25);
        labelBudgetNote.setText("Cut spending by 10%");
        labelBudgetNote.setForeground(TEXT_LIGHT);
        add(labelBudgetNote );
        
        // for total limit
        JLabel lbltotal = new JLabel("Total Budget Limit");
        lbltotal.setFont(new Font("Tahoma",Font.PLAIN, 16));
        lbltotal.setBounds(40,310,150,25);
        lbltotal.setForeground(TEXT_LIGHT);
        add(lbltotal);
        
        // Display final calculated limit
        labelTotalLimit = new JLabel();
        labelTotalLimit .setBounds(250,310,200,25);
        labelTotalLimit.setForeground(ALERT_RED); // Highlighted in red/accent color
        labelTotalLimit.setFont(new Font("Tahoma",Font.BOLD, 18));
        add(labelTotalLimit );
        
        
        // --- Buttons (Professional Styling) ---
        
        // check limit button
        checkLimit = new JButton("Calculate Limit");
        checkLimit.setBackground(ACCENT_BLUE);
        checkLimit.setForeground(Color.WHITE);
        checkLimit.setBounds(60,380,140,35);
        checkLimit.setFont(new Font("Tahoma",Font.BOLD, 14));
        checkLimit.setBorder(BorderFactory.createEmptyBorder());
        checkLimit.addActionListener(this);
        add(checkLimit);
        
        // book package button (Renamed to Save Budget)
        saveBudget = new JButton("Save Budget");
        saveBudget.setBackground(ACCENT_BLUE);
        saveBudget.setForeground(Color.WHITE);
        saveBudget.setBounds(220,380,120,35);
        saveBudget.setFont(new Font("Tahoma",Font.BOLD, 14));
        saveBudget.setBorder(BorderFactory.createEmptyBorder());
        saveBudget.addActionListener(this);
        add(saveBudget);
        
        // back button
        back = new JButton("Back");
        back.setBackground(Color.DARK_GRAY);
        back.setForeground(Color.WHITE);
        back.setBounds(360,380,100,35);
        back.setFont(new Font("Tahoma",Font.BOLD, 14));
        back.setBorder(BorderFactory.createEmptyBorder());
        back.addActionListener(this);
        add(back);
        
        // Image on right side of frame (Placeholder for visualization)
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/bookpackage.jpg")); // Reusing image path
        Image i2 = i1.getImage().getScaledInstance(500, 300, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l12 = new JLabel(i3);
        l12.setBounds(550, 50, 500, 300);
        // Optional: Add a placeholder text over the image area
        JLabel placeholder = new JLabel("Budget Visualization Area");
        placeholder.setBounds(650, 20, 300, 20);
        placeholder.setForeground(Color.GRAY);
        l12.add(placeholder);
        
        add(l12);
        
        setVisible(true);
    }
    
    // --- Event Handler: actionPerformed (Event-Driven Programming) ---
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==checkLimit){
            String category = cCategory.getSelectedItem();
            double limit = 0; 
            try {
                // Get the limit amount and display it prominently
                double amount = Double.parseDouble(tfAmount.getText());
                
                // Simple dummy calculation (e.g., setting a buffer/rounding)
                limit = Math.round(amount * 1.05); // Add 5% buffer for display
                
                labelTotalLimit.setText("Rs " + (int)limit);
                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid amount for the budget limit.");
                labelTotalLimit.setText("Rs 0");
            }
            
        }
        else if(ae.getSource()== saveBudget){
            // Data collection is simplified since DB is skipped
            String category = cCategory.getSelectedItem();
            String amount = tfAmount.getText();
            String limit = labelTotalLimit.getText();
            
            // Temporary Success Message
            JOptionPane.showMessageDialog(null, "Budget for " + category + " saved successfully at " + limit);
            setVisible(false);
        }
        else if(ae.getSource()== back){
            setVisible(false);
        }
    }
    
    public static void main(String[]args){
        new SetBudget("Sarmad");
    }
}
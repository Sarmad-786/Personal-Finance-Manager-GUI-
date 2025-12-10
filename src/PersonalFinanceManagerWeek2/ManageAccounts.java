package PersonalFinanceManagerWeek2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*; // Import for Database operations

public class ManageAccounts extends JFrame implements ActionListener {
    
    JButton back;
    private String username; // Variable to store the logged-in username
    
    // Define Dark/Modern Colors
    private static final Color BG_DARK = new Color(25, 25, 25); 
    private static final Color PANEL_DARK = new Color(40, 40, 40); 
    private static final Color TEXT_LIGHT = Color.WHITE;
    private static final Color ACCENT_BLUE = new Color(30, 100, 255);
    private static final Color VALUE_POSITIVE = new Color(0, 200, 0); // Green
    private static final Color VALUE_NEGATIVE = new Color(255, 60, 60); // Red
    
    // Default Constructor (for testing without username)
    ManageAccounts() {
        this("TestUser"); 
    }
    
    // Updated Constructor to accept username
    ManageAccounts(String username){
        this.username = username;
        
        // Set Frame and Background
        setBounds(300, 150, 700, 500); 
        setLocationRelativeTo(null); 
        setLayout(null); 
        getContentPane().setBackground(BG_DARK); 
        
        // --- 1. Central Panel (Container for Account List) ---
        JPanel p1 = new JPanel();
        p1.setBackground(PANEL_DARK);
        p1.setBounds(20, 20, 650, 420); 
        p1.setLayout(null);
        add(p1);
        
        // --- 2. Header ---
        JLabel title = new JLabel("MANAGE FINANCIAL ACCOUNTS (" + username + ")");
        title.setFont(new Font("Tahoma", Font.BOLD, 24));
        title.setForeground(ACCENT_BLUE);
        title.setBounds(20, 10, 600, 30); // Increased width for username display
        p1.add(title);
        
        // --- 3. Account List Header ---
        int headerY = 50;
        JLabel header1 = new JLabel("ACCOUNT");
        JLabel header2 = new JLabel("BALANCE");
        JLabel header3 = new JLabel("TYPE"); // Changed from STATUS to TYPE
        
        header1.setBounds(40, headerY, 150, 20);
        header2.setBounds(250, headerY, 150, 20);
        header3.setBounds(450, headerY, 150, 20);
        
        header1.setForeground(TEXT_LIGHT);
        header2.setForeground(TEXT_LIGHT);
        header3.setForeground(TEXT_LIGHT);
        
        Font headerFont = new Font("Tahoma", Font.BOLD, 14);
        header1.setFont(headerFont);
        header2.setFont(headerFont);
        header3.setFont(headerFont);
        
        p1.add(header1);
        p1.add(header2);
        p1.add(header3);
        
        // Draw a separator line
        JSeparator sep = new JSeparator();
        sep.setBounds(20, headerY + 25, 610, 5);
        sep.setForeground(ACCENT_BLUE);
        p1.add(sep);

        // --- 4. Account Entries (LIVE Data Retrieval) ---
        
        int entryY = headerY + 40;
        int spacing = 30;
        
        try {
            Conn c = new Conn();
            String query = "SELECT * FROM account WHERE username = '"+username+"'";
            ResultSet rs = c.s.executeQuery(query);
            
            // Loop through the results and create a panel for each account
            while (rs.next()) {
                String accountName = rs.getString("account_name");
                String accountType = rs.getString("account_type");
                double balance = rs.getDouble("current_balance");
                
                String balanceStr = String.format("Rs %,.2f", balance);
                Color balanceColor = (balance >= 0) ? VALUE_POSITIVE : VALUE_NEGATIVE;
                
                // Add the dynamic account entry panel to p1
                p1.add(createAccountEntry(accountName, balanceStr, accountType, entryY, balanceColor));
                entryY += spacing;
            }
            
            if (entryY == headerY + 40) {
                 // No accounts found, display message
                 JLabel noAccount = new JLabel("No accounts found. Please add a new account.");
                 noAccount.setBounds(40, entryY, 400, 30);
                 noAccount.setForeground(Color.GRAY);
                 p1.add(noAccount);
                 entryY += spacing;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JLabel error = new JLabel("Database Error: Could not load accounts.");
            error.setBounds(40, entryY, 400, 30);
            error.setForeground(DANGER_RED);
            p1.add(error);
        }
        
        // --- 5. Back Button (Positioned based on final entryY, or fixed) ---
        back = new JButton("Back to Dashboard");
        back.setBackground(ACCENT_BLUE);
        back.setForeground(Color.WHITE);
        back.setFont(new Font("Tahoma", Font.BOLD, 14));
        // Fixed position at the bottom of p1
        back.setBounds(220, 360, 200, 35); 
        back.addActionListener(this);
        p1.add(back);
        
        setVisible(true);
    }
    
    // Helper method to create a single row entry for an account
    private JPanel createAccountEntry(String name, String balance, String status, int y, Color balanceColor) {
        JPanel rowPanel = new JPanel(null);
        rowPanel.setBackground(PANEL_DARK);
        rowPanel.setBounds(0, y, 650, 30);
        
        Font entryFont = new Font("Tahoma", Font.PLAIN, 14);
        
        // Account Name
        JLabel nameLabel = new JLabel(name);
        nameLabel.setBounds(40, 0, 200, 30);
        nameLabel.setForeground(TEXT_LIGHT);
        nameLabel.setFont(entryFont); 
        rowPanel.add(nameLabel);

        // Balance (Value)
        JLabel balanceLabel = new JLabel(balance);
        balanceLabel.setBounds(250, 0, 150, 30);
        balanceLabel.setForeground(balanceColor);
        balanceLabel.setFont(entryFont); 
        rowPanel.add(balanceLabel);

        // Status (Account Type)
        JLabel statusLabel = new JLabel(status);
        statusLabel.setBounds(450, 0, 150, 30);
        // Color based on whether it's Savings/Investment (Green) or Credit Card/Loan (Red)
        Color statusColor = status.contains("Savings") || status.contains("Investment") ? VALUE_POSITIVE : VALUE_NEGATIVE;
        statusLabel.setForeground(statusColor); 
        statusLabel.setFont(entryFont); 
        rowPanel.add(statusLabel);
        
        return rowPanel;
    }
    
    // --- Event Handler ---
    @Override
    public void actionPerformed(ActionEvent ae){
        if (ae.getSource() == back) {
            setVisible(false);
            dispose();
        }
    }
    
    public static void main(String[]args){
        // Use a dummy username for testing
        new ManageAccounts("sarmad");
    }
}
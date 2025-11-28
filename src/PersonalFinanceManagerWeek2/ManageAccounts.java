package PersonalFinanceManagerWeek2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Class structure adapted to be a static panel, removing Runnable interface and threading logic
public class ManageAccounts extends JFrame implements ActionListener {
    
    JButton back;
    
    // Define Dark/Modern Colors
    private static final Color BG_DARK = new Color(25, 25, 25); // Outer Black Background
    private static final Color PANEL_DARK = new Color(40, 40, 40); // Dark Gray Form Panel
    private static final Color TEXT_LIGHT = Color.WHITE;
    private static final Color ACCENT_BLUE = new Color(30, 100, 255);
    private static final Color VALUE_POSITIVE = new Color(0, 200, 0); // Green
    private static final Color VALUE_NEGATIVE = new Color(255, 60, 60); // Red
    
    ManageAccounts(){
        
        // Set Frame and Background
        setBounds(300, 150, 700, 500); // Adjusted size to fit content
        setLocationRelativeTo(null); 
        setLayout(null); // Retaining null layout
        getContentPane().setBackground(BG_DARK); 
        
        // --- 1. Central Panel (Container for Account List) ---
        JPanel p1 = new JPanel();
        p1.setBackground(PANEL_DARK);
        p1.setBounds(20, 20, 650, 420); 
        p1.setLayout(null);
        add(p1);
        
        // --- 2. Header ---
        JLabel title = new JLabel("MANAGE FINANCIAL ACCOUNTS");
        title.setFont(new Font("Tahoma", Font.BOLD, 24));
        title.setForeground(ACCENT_BLUE);
        title.setBounds(20, 10, 400, 30);
        p1.add(title);
        
        // --- 3. Account List Header ---
        int headerY = 50;
        JLabel header1 = new JLabel("ACCOUNT");
        JLabel header2 = new JLabel("BALANCE");
        JLabel header3 = new JLabel("STATUS");
        
        header1.setBounds(40, headerY, 150, 20);
        header2.setBounds(250, headerY, 150, 20);
        header3.setBounds(450, headerY, 150, 20);
        
        header1.setForeground(TEXT_LIGHT);
        header2.setForeground(TEXT_LIGHT);
        header3.setForeground(TEXT_LIGHT);
        
        // Using Bold font for column headers for prominence
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

        // --- 4. Account Entries (Dummy Data) ---
        
        int entryY = headerY + 40;
        int spacing = 30;
        
        // Account 1: Savings
        p1.add(createAccountEntry("HDFC Savings", "Rs 55,000", "Active", entryY, VALUE_POSITIVE));
        entryY += spacing;

        // Account 2: Credit Card (Negative Balance)
        p1.add(createAccountEntry("ICICI Credit Card", "Rs -8,500", "High Debt", entryY, VALUE_NEGATIVE));
        entryY += spacing;

        // Account 3: Investment
        p1.add(createAccountEntry("Investment Fund A", "Rs 1,20,000", "Performing", entryY, VALUE_POSITIVE));
        entryY += spacing;
        
        // Account 4: Cash
        p1.add(createAccountEntry("Cash Wallet", "Rs 1,500", "Active", entryY, TEXT_LIGHT));
        entryY += spacing;
        
        // Account 5: Loan
        p1.add(createAccountEntry("Personal Loan", "Rs -1,50,000", "Scheduled", entryY, VALUE_NEGATIVE));
        entryY += spacing;
        
        // --- 5. Back Button ---
        back = new JButton("Back to Dashboard");
        back.setBackground(ACCENT_BLUE);
        back.setForeground(Color.WHITE);
        back.setFont(new Font("Tahoma", Font.BOLD, 14));
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
        
        // Define consistent font for all labels and values in the entry row
        Font entryFont = new Font("Tahoma", Font.PLAIN, 14);
        
        // Account Name
        JLabel nameLabel = new JLabel(name);
        nameLabel.setBounds(40, 0, 200, 30);
        nameLabel.setForeground(TEXT_LIGHT);
        nameLabel.setFont(entryFont); // Consistent size/style
        rowPanel.add(nameLabel);

        // Balance (Value)
        JLabel balanceLabel = new JLabel(balance);
        balanceLabel.setBounds(250, 0, 150, 30);
        balanceLabel.setForeground(balanceColor);
        balanceLabel.setFont(entryFont); // Consistent size/style
        rowPanel.add(balanceLabel);

        // Status
        JLabel statusLabel = new JLabel(status);
        statusLabel.setBounds(450, 0, 150, 30);
        statusLabel.setForeground(balanceColor); 
        statusLabel.setFont(entryFont); // Consistent size/style
        rowPanel.add(statusLabel);
        
        return rowPanel;
    }
    
    // --- Event Handler ---
    @Override
    public void actionPerformed(ActionEvent ae){
        if (ae.getSource() == back) {
            setVisible(false);
        }
    }
    
    public static void main(String[]args){
        new ManageAccounts();
    }
}
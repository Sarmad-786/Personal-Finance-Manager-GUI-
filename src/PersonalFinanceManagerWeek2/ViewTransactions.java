package PersonalFinanceManagerWeek2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class ViewTransactions extends JFrame implements ActionListener{
    
    // Define Colors and Fonts (Fix applied here: renamed DATA_HIGHLIGHT_COLOR to ACCENT_COLOR)
    private static final Color FRAME_BG_COLOR = Color.BLACK; // Entire frame background
    private static final Color PANEL_BG_COLOR = new Color(30, 30, 30); // Darker panel for content
    private static final Color PRIMARY_COLOR = new Color(0, 153, 204); // Bright Blue for accent
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color ACCENT_COLOR = new Color(255, 153, 51); // Orange for Income/Data (RESOLVED ERROR)
    private static final Color EXPENSE_COLOR = new Color(255, 77, 77); // Red for Expense data
    
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 26);
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 14);
    private static final Font DATA_FONT = new Font("Arial", Font.PLAIN, 14);
    
    JButton back;
    
    ViewTransactions(String username){ 
        
        // --- Frame Setup ---
        setBounds(500, 180, 800, 480); 
        setLocationRelativeTo(null); 
        getContentPane().setBackground(FRAME_BG_COLOR);
        setLayout(null);
        
        // --- Input/Display Panel for structure ---
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(null);
        displayPanel.setBackground(PANEL_BG_COLOR); 
        displayPanel.setBounds(0, 0, 800, 480);
        add(displayPanel);

        // --- Title ---
        JLabel title = new JLabel("Transaction Details");
        title.setFont(TITLE_FONT);
        title.setForeground(PRIMARY_COLOR);
        title.setBounds(30, 20, 350, 35);
        displayPanel.add(title);
        
        // --- Horizontal Separator ---
        JSeparator separator = new JSeparator();
        separator.setBounds(30, 60, 740, 5);
        separator.setBackground(PRIMARY_COLOR);
        displayPanel.add(separator);

        // --- COLUMN 1: Transaction Details ---
        int y_start = 90;
        int y_spacing = 45;
        
        // 1. User
        JLabel lblusername = createLabel("User:", 30, y_start);
        displayPanel.add(lblusername);
        
        JLabel labelusername = createDataLabel(username, 220, y_start, TEXT_COLOR);
        displayPanel.add(labelusername);
        
        // 2. Transaction ID
        JLabel lblTransactionID = createLabel("Transaction ID:", 30, y_start + y_spacing * 1);
        displayPanel.add(lblTransactionID);
        
        JLabel labelTransactionID = createDataLabel("TXN-10112025-001", 220, y_start + y_spacing * 1, Color.LIGHT_GRAY);
        displayPanel.add(labelTransactionID);
        
        // 3. Amount
        JLabel lblAmount = createLabel("Amount:", 30, y_start + y_spacing * 2);
        displayPanel.add(lblAmount);
        
        // Use ACCENT_COLOR for highlight
        JLabel labelAmount = createDataLabel("₹ 5,500", 220, y_start + y_spacing * 2, ACCENT_COLOR); 
        displayPanel.add(labelAmount);
        
        // 4. Date
        JLabel lblDate = createLabel("Date:", 30, y_start + y_spacing * 3);
        displayPanel.add(lblDate);
        
        JLabel labelDate = createDataLabel("2025-11-18", 220, y_start + y_spacing * 3, TEXT_COLOR);
        displayPanel.add(labelDate);
        
        // 5. Type (Expense/Income)
        JLabel lblType = createLabel("Type:", 30, y_start + y_spacing * 4);
        displayPanel.add(lblType);
        
        // Use a variable to highlight the Type field based on value
        String typeValue = "Expense";
        // ACCENT_COLOR is now defined, resolving the error
        Color typeColor = typeValue.equals("Expense") ? EXPENSE_COLOR : ACCENT_COLOR;
        
        JLabel labelType = createDataLabel(typeValue, 220, y_start + y_spacing * 4, typeColor); 
        displayPanel.add(labelType);
        
        // --- COLUMN 2: Category and Account Details ---
        
        // 6. Category
        JLabel lblCategory = createLabel("Category:", 400, y_start);
        displayPanel.add(lblCategory);
        
        JLabel labelCategory = createDataLabel("Groceries", 630, y_start, TEXT_COLOR);
        displayPanel.add(labelCategory);
        
        // 7. Account
        JLabel lblAccount = createLabel("Account:", 400, y_start + y_spacing * 1);
        displayPanel.add(lblAccount);
        
        JLabel labelAccount = createDataLabel("United Bank Limit (UBL)", 630, y_start + y_spacing * 1, TEXT_COLOR);
        displayPanel.add(labelAccount);
        
        // 8. Payment Method
        JLabel lblMethod = createLabel("Payment Method:", 400, y_start + y_spacing * 2);
        displayPanel.add(lblMethod);
        
        JLabel labelMethod = createDataLabel("Credit Card", 630, y_start + y_spacing * 2, TEXT_COLOR);
        displayPanel.add(labelMethod);
        
        // 9. Notes (Extended height for better display of longer notes)
        JLabel lblNotes = createLabel("Notes:", 400, y_start + y_spacing * 3);
        displayPanel.add(lblNotes);
        
        JTextArea areaNotes = new JTextArea("Weekly shop at Safeway for essential items."); // Use JTextArea for better notes display
        areaNotes.setBounds(630, y_start + y_spacing * 3, 150, 60); 
        areaNotes.setWrapStyleWord(true);
        areaNotes.setLineWrap(true);
        areaNotes.setEditable(false);
        areaNotes.setBackground(PANEL_BG_COLOR); // Match panel background
        areaNotes.setForeground(TEXT_COLOR);
        areaNotes.setFont(DATA_FONT);
        displayPanel.add(areaNotes);
        
        // --- Back Button ---
        back = new JButton("Back");
        back.setBackground(PRIMARY_COLOR);
        back.setForeground(Color.WHITE);
        back.setFont(LABEL_FONT);
        back.setFocusPainted(false);
        back.setBounds(340, 370, 120, 35); 
        back.addActionListener(this);
        displayPanel.add(back);
        
        setVisible(true);
    }
    
    // Helper method to create formatted labels
    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 180, 25);
        label.setFont(LABEL_FONT);
        label.setForeground(TEXT_COLOR);
        return label;
    }
    
    // Helper method to create formatted data labels
    private JLabel createDataLabel(String text, int x, int y, Color color) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 200, 25);
        label.setFont(DATA_FONT);
        label.setForeground(color);
        return label;
    }
    
    public void actionPerformed(ActionEvent ae){
        // Back button functionality
        setVisible(false);
    }
    
    public static void main(String[]args){
        new ViewTransactions("Sarmad");
    }
}
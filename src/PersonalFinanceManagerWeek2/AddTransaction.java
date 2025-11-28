package PersonalFinanceManagerWeek2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddTransaction extends JFrame implements ActionListener {
    // Define Colors and Fonts
    private static final Color PRIMARY_COLOR = new Color(0, 153, 204); // Brighter Blue for contrast on black
    private static final Color ACCENT_COLOR = new Color(255, 153, 51);   // Brighter Orange for Income/Highlight
    private static final Color INPUT_BG_COLOR = new Color(50, 50, 50); // Dark Gray for input fields
    private static final Color TEXT_COLOR = Color.WHITE; // White text for labels and fields
    private static final Color FIELD_TEXT_COLOR = Color.WHITE; // White text for input fields
    private static final Color FRAME_BG_COLOR = Color.BLACK; // Entire frame background is BLACK
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 26);
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 14);
    private static final Font FIELD_FONT = new Font("Arial", Font.PLAIN, 14);

    // Global Decalaration
    JLabel labelusername, labeltransactionID;
    JComboBox comboType;
    JTextField tfAmount, tfCategory, tfNotes , tfDate, tfAccount;
    JRadioButton rExpense, rIncome;
    JButton add, back;
    
    // Constructor
    AddTransaction(String username) {
        
        // --- Frame Size Adjustment (No image, so smaller width) ---
        setBounds(550, 200, 450, 550); // Reduced width from 850 to 450
        setLocationRelativeTo(null); 
        setLayout(null); 
        getContentPane().setBackground(FRAME_BG_COLOR); // Set main content pane background to BLACK
        
        // --- Input Panel for visual grouping ---
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(null);
        // Input panel background is now a dark gray to stand out on black
        inputPanel.setBackground(new Color(30, 30, 30)); 
        // --- Expand inputPanel to fill the new frame width ---
        inputPanel.setBounds(0, 0, 450, 550); // Fills the entire frame width
        add(inputPanel);

        // --- Title ---
        JLabel title = new JLabel("Add New Transaction");
        title.setFont(TITLE_FONT);
        title.setForeground(PRIMARY_COLOR); // Title in primary color
        title.setBounds(30, 20, 350, 35); // Adjusted Y for better spacing
        inputPanel.add(title);
        
        // --- 1. User ---
        JLabel lblusername = new JLabel("User");
        lblusername.setBounds(30, 70, 150, 25); // Adjusted Y
        lblusername.setFont(LABEL_FONT);
        lblusername.setForeground(TEXT_COLOR); // White text
        inputPanel.add(lblusername);
        
        labelusername = new JLabel(username);
        labelusername.setBounds(200, 70, 150, 25); // Adjusted Y
        labelusername.setFont(FIELD_FONT);
        labelusername.setForeground(PRIMARY_COLOR); // Primary color for username
        inputPanel.add(labelusername);
        
        // --- 2. Transaction Type (Payment Method) ---
        JLabel lblType = new JLabel("Payment Method");
        lblType.setBounds(30, 110, 150, 25); // Adjusted Y
        lblType.setFont(LABEL_FONT);
        lblType.setForeground(TEXT_COLOR); // White text
        inputPanel.add(lblType);
        
        comboType = new JComboBox(new String[]{"Cash", "Credit Card", "Bank Transfer", "Digital Wallet"});
        comboType.setBounds(200, 110, 150, 25); // Adjusted Y
        comboType.setBackground(PRIMARY_COLOR); // Primary color background
        comboType.setForeground(FIELD_TEXT_COLOR);    // White text
        comboType.setFont(FIELD_FONT);
        inputPanel.add(comboType);
        
        // --- 3. Amount ---
        JLabel lblAmount = new JLabel("Amount (₹ / $)");
        lblAmount.setBounds(30, 150, 150, 25); // Adjusted Y
        lblAmount.setFont(LABEL_FONT);
        lblAmount.setForeground(TEXT_COLOR); // White text
        inputPanel.add(lblAmount);
        
        tfAmount = new JTextField();
        tfAmount.setBounds(200, 150, 150, 25); // Adjusted Y
        tfAmount.setBackground(INPUT_BG_COLOR); // Dark gray input field
        tfAmount.setForeground(FIELD_TEXT_COLOR); // White text
        tfAmount.setCaretColor(FIELD_TEXT_COLOR); // White cursor
        tfAmount.setFont(FIELD_FONT);
        inputPanel.add(tfAmount);
        
        // --- 4. Transaction ID/Reference ---
        JLabel lblID = new JLabel("Reference ID");
        lblID.setBounds(30, 190, 150, 25); // Adjusted Y
        lblID.setFont(LABEL_FONT);
        lblID.setForeground(TEXT_COLOR); // White text
        inputPanel.add(lblID);
        
        labeltransactionID = new JLabel("Auto-Generated (TID-XXXX)");
        labeltransactionID.setBounds(200, 190, 180, 25); // Adjusted Y
        labeltransactionID.setFont(new Font("Arial", Font.ITALIC, 12));
        labeltransactionID.setForeground(Color.LIGHT_GRAY); // Lighter gray for auto-generated text
        inputPanel.add(labeltransactionID);
        
        // --- 5. Category Type (Income/Expense) ---
        JLabel lblCategoryType = new JLabel("Transaction Flow");
        lblCategoryType.setBounds(30, 230, 150, 25); // Adjusted Y
        lblCategoryType.setFont(LABEL_FONT);
        lblCategoryType.setForeground(TEXT_COLOR); // White text
        inputPanel.add(lblCategoryType);
        
        rExpense = new JRadioButton("Expense");
        rExpense.setBounds(200, 230, 90, 25); // Adjusted Y
        rExpense.setBackground(new Color(30, 30, 30)); // Match panel bg
        rExpense.setForeground(Color.RED.brighter()); // Brighter red for visibility
        rExpense.setFont(LABEL_FONT);
        
        rIncome = new JRadioButton("Income");
        rIncome.setBounds(290, 230, 90, 25); // Adjusted Y
        rIncome.setBackground(new Color(30, 30, 30)); // Match panel bg
        rIncome.setForeground(ACCENT_COLOR); // Brighter accent color
        rIncome.setFont(LABEL_FONT);
        
        ButtonGroup bg = new ButtonGroup();
        bg.add(rExpense);
        bg.add(rIncome);
        rExpense.setSelected(true); 
        inputPanel.add(rExpense);
        inputPanel.add(rIncome);
        
        // --- 6. Category (e.g., Food, Salary) ---
        JLabel lblCategory = new JLabel("Category");
        lblCategory.setBounds(30, 270, 150, 25); // Adjusted Y
        lblCategory.setFont(LABEL_FONT);
        lblCategory.setForeground(TEXT_COLOR); // White text
        inputPanel.add(lblCategory);
        
        tfCategory = new JTextField();
        tfCategory.setBounds(200, 270, 150, 25); // Adjusted Y
        tfCategory.setBackground(INPUT_BG_COLOR); // Dark gray input field
        tfCategory.setForeground(FIELD_TEXT_COLOR); // White text
        tfCategory.setCaretColor(FIELD_TEXT_COLOR); // White cursor
        tfCategory.setFont(FIELD_FONT);
        inputPanel.add(tfCategory);
        
        // --- 7. Notes/Description ---
        JLabel lblNotes = new JLabel("Description");
        lblNotes.setBounds(30, 310, 150, 25); // Adjusted Y
        lblNotes.setFont(LABEL_FONT);
        lblNotes.setForeground(TEXT_COLOR); // White text
        inputPanel.add(lblNotes);
        
        tfNotes = new JTextField();
        tfNotes.setBounds(200, 310, 150, 25); // Adjusted Y
        tfNotes.setBackground(INPUT_BG_COLOR); // Dark gray input field
        tfNotes.setForeground(FIELD_TEXT_COLOR); // White text
        tfNotes.setCaretColor(FIELD_TEXT_COLOR); // White cursor
        tfNotes.setFont(FIELD_FONT);
        inputPanel.add(tfNotes);
        
        // --- 8. Account ---
        JLabel lblAccount = new JLabel("Account/Bank");
        lblAccount.setBounds(30, 350, 150, 25); // Adjusted Y
        lblAccount.setFont(LABEL_FONT);
        lblAccount.setForeground(TEXT_COLOR); // White text
        inputPanel.add(lblAccount);
        
        tfAccount = new JTextField();
        tfAccount.setBounds(200, 350, 150, 25); // Adjusted Y
        tfAccount.setBackground(INPUT_BG_COLOR); // Dark gray input field
        tfAccount.setForeground(FIELD_TEXT_COLOR); // White text
        tfAccount.setCaretColor(FIELD_TEXT_COLOR); // White cursor
        tfAccount.setFont(FIELD_FONT);
        inputPanel.add(tfAccount);
        
        // --- 9. Date ---
        JLabel lblDate = new JLabel("Date (YYYY-MM-DD)"); // Added format hint
        lblDate.setBounds(30, 390, 150, 25); // Adjusted Y
        lblDate.setFont(LABEL_FONT);
        lblDate.setForeground(TEXT_COLOR); // White text
        inputPanel.add(lblDate);
        
        tfDate = new JTextField();
        tfDate.setBounds(200, 390, 150, 25); // Adjusted Y
        tfDate.setText(java.time.LocalDate.now().toString()); 
        tfDate.setBackground(INPUT_BG_COLOR); // Dark gray input field
        tfDate.setForeground(FIELD_TEXT_COLOR); // White text
        tfDate.setCaretColor(FIELD_TEXT_COLOR); // White cursor
        tfDate.setFont(FIELD_FONT);
        inputPanel.add(tfDate);
        
        // --- Buttons ---
        add = new JButton("Add Transaction");
        add.setBackground(PRIMARY_COLOR);
        add.setForeground(Color.WHITE);
        add.setFont(LABEL_FONT);
        add.setFocusPainted(false); 
        add.setBounds(70, 460, 150, 35); // Adjusted Y for better spacing
        add.addActionListener(this); 
        inputPanel.add(add);
        
        back = new JButton("Back");
        back.setBackground(Color.DARK_GRAY); // Darker gray for back button
        back.setForeground(Color.WHITE);
        back.setFont(LABEL_FONT);
        back.setFocusPainted(false);
        back.setBounds(230, 460, 100, 35); // Adjusted Y
        back.addActionListener(this);
        inputPanel.add(back);
        
        // --- IMAGE REMOVED ---
        // ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/newcustomer.jpg"));
        // Image i2 = i1.getImage().getScaledInstance(450, 550, Image.SCALE_SMOOTH); 
        // ImageIcon i3 = new ImageIcon(i2);
        // JLabel image = new JLabel(i3);
        // image.setBounds(400, 0, 450, 550); 
        // add(image);
        
        setVisible(true);
    }
    
    // Action Listener remains functional
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()== add){
            // Data collection and saving logic
            String username = labelusername.getText();
            String paymentMethod = (String)comboType.getSelectedItem();
            String amount = tfAmount.getText();
            String transactionType = rExpense.isSelected() ? "Expense" : "Income";
            String category = tfCategory.getText();
            String notes = tfNotes.getText();
            String account = tfAccount.getText();
            String date = tfDate.getText();
            
            // Basic validation check
            if (amount.isEmpty() || category.isEmpty() || account.isEmpty() || date.isEmpty()) {
                 JOptionPane.showMessageDialog(null, "Please fill all required fields (Amount, Category, Account, Date).", "Error", JOptionPane.ERROR_MESSAGE);
                 return;
            }
            
            // Temporary Success Message
            JOptionPane.showMessageDialog(null, transactionType + " of " + amount + " added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            setVisible(false);
            
        } else if(ae.getSource()== back){
            setVisible(false);
        }
    }
    
    public static void main(String[]args){
        new AddTransaction("Sarmad"); 
    }
}
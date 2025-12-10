package PersonalFinanceManagerWeek2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class DeleteRecord extends JFrame implements ActionListener {
    JButton deleteButton, cancelButton;
    
    // Define Dark Theme Colors
    private static final Color BG_DARK = new Color(25, 25, 25);
    private static final Color PANEL_DARK = new Color(40, 40, 40);
    private static final Color TEXT_LIGHT = Color.WHITE;
    private static final Color ACCENT_RED = new Color(200, 50, 50); 
    private static final Color WARNING_ORANGE = new Color(255, 150, 0);

    // Input fields and selection for deletion
    private JTextField tfRecordId, tfDate;
    private Choice cRecordType;
    private String globalUsername;

    DeleteRecord(String username) {
        this.globalUsername = username;
        // --- GUI Setup (Unchanged) ---
        setTitle("DELETE FINANCIAL RECORD");
        setBounds(450, 180, 870, 450); 
        setLocationRelativeTo(null); 
        getContentPane().setBackground(BG_DARK);
        setLayout(null);
        
        // --- Heading ---
        JLabel heading = new JLabel("PERMANENTLY DELETE RECORD");
        heading.setFont(new Font("Tahoma", Font.BOLD, 24));
        heading.setForeground(ACCENT_RED);
        heading.setBounds(30, 20, 500, 30);
        add(heading);
        
        // --- Left Panel: Deletion Criteria ---
        
        int labelX = 30;
        int valueX = 250;
        int yStart = 80;
        int ySpacing = 40;
        
        // 1. User Account (Display only)
        JLabel lblusername = createLabel("User Account", labelX, yStart, TEXT_LIGHT, false);
        add(lblusername);
        JLabel labelusername = createLabel(username, valueX, yStart, TEXT_LIGHT, true);
        add(labelusername);
        yStart += ySpacing;
        
        // 2. Record Type to Delete
        JLabel lblRecordType = createLabel("Record Type", labelX, yStart, TEXT_LIGHT, false);
        add(lblRecordType);
        
        cRecordType = new Choice();
        cRecordType.add("Transaction");
        cRecordType.add("Account");
        cRecordType.add("Goal Entry");
        cRecordType.setBounds(valueX, yStart, 250, 25);
        cRecordType.setBackground(PANEL_DARK);
        cRecordType.setForeground(TEXT_LIGHT);
        add(cRecordType);
        yStart += ySpacing;
        
        // 3. Record ID / Reference
        JLabel lblRecordId = createLabel("Record ID / Name", labelX, yStart, TEXT_LIGHT, false);
        add(lblRecordId);
        
        tfRecordId = createTextField("Enter ID or Name", valueX, yStart);
        add(tfRecordId);
        yStart += ySpacing;
        
        // 4. Specific Date (Optional)
        JLabel lblDate = createLabel("Specific Date (YYYY-MM-DD)", labelX, yStart, TEXT_LIGHT, false);
        add(lblDate);
        
        tfDate = createTextField("Optional", valueX, yStart);
        add(tfDate);
        yStart += ySpacing + 30;
        
        // --- Warning Message (Center) ---
        
        JLabel warning1 = createLabel("WARNING: This action cannot be undone.", 30, yStart, WARNING_ORANGE, true);
        warning1.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(warning1);
        yStart += 30;
        
        // --- Buttons ---
        
        deleteButton = new JButton("DELETE NOW");
        deleteButton.setBackground(ACCENT_RED);
        deleteButton.setForeground(TEXT_LIGHT);
        deleteButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        deleteButton.setBounds(100, yStart + 30, 180, 40);
        deleteButton.addActionListener(this); 
        add(deleteButton);
        
        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.DARK_GRAY);
        cancelButton.setForeground(TEXT_LIGHT);
        cancelButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        cancelButton.setBounds(300, yStart + 30, 150, 40);
        cancelButton.addActionListener(this); 
        add(cancelButton);
        
        // --- Right Panel: Confirmation Placeholder ---
        
        JPanel confirmationPanel = new JPanel();
        confirmationPanel.setBackground(PANEL_DARK);
        confirmationPanel.setBounds(550, 80, 280, 300);
        confirmationPanel.setLayout(new GridBagLayout());
        
        JLabel iconLabel = createLabel("🗑️", 0, 0, TEXT_LIGHT, true); 
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 100));
        confirmationPanel.add(iconLabel);
        
        JLabel confirmationText = createLabel("Use caution when deleting financial history.", 0, 0, WARNING_ORANGE, false);
        confirmationText.setHorizontalAlignment(SwingConstants.CENTER);
        confirmationText.setFont(new Font("Tahoma", Font.ITALIC, 14));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 0, 0);
        confirmationPanel.add(confirmationText, gbc);
        add(confirmationPanel);
        
        setVisible(true); 
    }
    
    // --- Helper Methods ---
    private JLabel createLabel(String text, int x, int y, Color color, boolean isBold) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Tahoma", isBold ? Font.BOLD : Font.PLAIN, 16));
        label.setBounds(x, y, 250, 25);
        label.setForeground(color);
        return label;
    }
    
    private JTextField createTextField(String defaultText, int x, int y) {
        JTextField tf = new JTextField(defaultText);
        tf.setBounds(x, y, 250, 25);
        tf.setBackground(PANEL_DARK);
        tf.setForeground(TEXT_LIGHT);
        tf.setCaretColor(TEXT_LIGHT);
        return tf;
    }
    
    // --- Event Handler: actionPerformed ---
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == deleteButton){
            String recordType = cRecordType.getSelectedItem();
            String idOrName = tfRecordId.getText();

            int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete the " + recordType + " identified by: " + idOrName + "?", 
                "Confirm Permanent Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    Conn c = new Conn();
                    String query = "";
                    String successMessage = "";

                    if (recordType.equals("Transaction")) {
                        query = "DELETE FROM transaction_record WHERE transaction_id = '"+idOrName+"' AND username = '"+globalUsername+"'";
                        successMessage = "Transaction ID " + idOrName + " deleted.";
                    } else if (recordType.equals("Account")) {
                        query = "DELETE FROM account WHERE account_name = '"+idOrName+"' AND username = '"+globalUsername+"'";
                        successMessage = "Account " + idOrName + " deleted.";
                    } else if (recordType.equals("Goal Entry")) {
                        query = "DELETE FROM goals WHERE goal_name = '"+idOrName+"' AND username = '"+globalUsername+"'";
                        successMessage = "Goal " + idOrName + " deleted.";
                    }
                    
                    int deletedRows = c.s.executeUpdate(query);
                    if (deletedRows > 0) {
                        JOptionPane.showMessageDialog(null, successMessage, "Success", JOptionPane.INFORMATION_MESSAGE);
                        setVisible(false);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Record not found or ID/Name is incorrect.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Database Error during deletion: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (ae.getSource() == cancelButton) {
            setVisible(false);
            dispose();
        }
    }
    
    public static void main(String[] args) {
        new DeleteRecord("sarmad");
    }
}
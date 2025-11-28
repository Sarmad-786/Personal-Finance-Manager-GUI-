package PersonalFinanceManagerWeek2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
    
    AddIncome(String username) {
        this.username = username;

        // --- Frame Setup ---
        setTitle("Add Income / Deposit");
        
        // 1. Set the size (700 wide, 600 high). The position (0, 0) will be ignored later.
        setBounds(0, 0, 700, 600); 
        
        // 2. This command forces the frame to the absolute center of the user's screen.
        setLocationRelativeTo(null); 
        
        setLayout(null);
        getContentPane().setBackground(BG_DARK);

        // --- Centering Calculations (for components) ---
        // These coordinates already ensure the content is visually centered within the 700px width.
        int labelX = 150; // X for labels
        int valueX = 350; // X for input/value fields
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
        
        // 5. ID Type
        JLabel lblid = createLabel("ID Type", labelX, yStart);
        add(lblid);

        labelid = createValueLabel("National ID Card", valueX, yStart); 
        add(labelid);
        yStart += ySpacing;

        // 6. ID Number
        JLabel lblnumber = createLabel("ID Number", labelX, yStart);
        add(lblnumber);

        labelnumber = createValueLabel("123456789", valueX, yStart);
        add(labelnumber);
        yStart += ySpacing;

        // 7. Phone Number
        JLabel lblphone = createLabel("Phone", labelX, yStart);
        add(lblphone);

        labelphone = createValueLabel("0300-1234567", valueX, yStart);
        add(labelphone);
        yStart += ySpacing + 20;

        // --- Buttons (Centered below the fields) ---
        int buttonXStart = 150; 

        // Add Income Button
        addIncome = new JButton("Add Income");
        addIncome.setBackground(ACCENT_GREEN);
        addIncome.setForeground(TEXT_LIGHT);
        addIncome.setBounds(buttonXStart, yStart, 120, 25);
        addIncome.addActionListener(this);
        add(addIncome);

        // Clear Fields Button
        clearFields = new JButton("Clear Fields");
        clearFields.setBackground(ACCENT_BLUE);
        clearFields.setForeground(TEXT_LIGHT);
        clearFields.setBounds(buttonXStart + 140, yStart, 120, 25);
        clearFields.addActionListener(this);
        add(clearFields);

        // Back Button
        back = new JButton("Back");
        back.setBackground(Color.DARK_GRAY);
        back.setForeground(TEXT_LIGHT);
        back.setBounds(buttonXStart + 280, yStart, 120, 25);
        back.addActionListener(this);
        add(back);

        setVisible(true);
    }
    
    // Helper method for creating standard dark-theme labels
    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label.setBounds(x, y, 150, 25);
        label.setForeground(TEXT_LIGHT);
        return label;
    }

    // Helper method for creating standard dark-theme value labels
    private JLabel createValueLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label.setBounds(x, y, 200, 25);
        label.setForeground(TEXT_LIGHT);
        return label;
    }


    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addIncome) {
            try {
                String category = cCategory.getSelectedItem();
                String source = tfSource.getText();
                double amount = Double.parseDouble(tfAmount.getText());

                if (amount <= 0) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid amount greater than zero.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Simulate saving to a database
                String message = String.format("Successfully added Rs %.2f to your account!", amount);
                JOptionPane.showMessageDialog(null, message, "Income Recorded", JOptionPane.INFORMATION_MESSAGE);
                
                // Clear fields after successful recording
                tfAmount.setText("");
                tfSource.setText("");

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number for Amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
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
        new AddIncome("Sarmad");
    }
}
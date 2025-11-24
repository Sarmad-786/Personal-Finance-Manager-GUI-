package PersonalFinanceManagerWeek1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Dashboard extends JFrame implements ActionListener {
    
    // All buttons defined here
    JButton[] menuButtons = new JButton[15];
    String[] buttonLabels = {
        "Add Transaction", "View Transactions", "Update Transaction", "Check Reports", 
        "Set Budget", "View Budgets", "Manage Accounts", "Add Income", 
        "View Goals", "Set Goals", "Budget Analysis", "Calculator", 
        "Notepad", "About", "Delete Record" 
    };
    
    String username; 

    // Define Black and Grey colors
    private static final Color HEADER_BLACK = Color.BLACK;
    private static final Color BUTTON_DARK_GREY = new Color(30, 30, 30); // Use a darker grey for buttons
    private static final Color MAIN_CONTENT_LIGHT_GREY = new Color(220, 220, 220);
    private static final Color WELCOME_TEXT_DARK_GREY = new Color(51, 51, 51);

    public Dashboard(String username) {
        this.username = username;
        
        // --- 1. Frame Title ---
        setTitle("Personal Finance Manager - Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // **Main Frame Layout: Border Layout**
        setLayout(new BorderLayout(0, 0)); // No gaps

        // --- 1. NORTH PANEL (Header Panel) - Now includes the menu buttons ---
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // --- 2. CENTER PANEL (Main Content Area) ---
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BorderLayout());
        
        // Set main content background to light grey
        mainContentPanel.setBackground(MAIN_CONTENT_LIGHT_GREY); 

        // --- 2a. IMAGE PANEL (Center's Center) ---
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());
        imagePanel.setBackground(MAIN_CONTENT_LIGHT_GREY); 


        
        // --- 2b. HEADING PANEL (Placed OVER the Image Panel) ---
        JPanel headingPanel = new JPanel();
        headingPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        headingPanel.setBackground(Color.WHITE); 
        
        // Set text color to dark grey
        JLabel text = new JLabel("Personal Finance Manager (Logged in as: " + username + ")");
        text.setForeground(WELCOME_TEXT_DARK_GREY); 
        text.setFont(new Font("Raleway", Font.BOLD, 45)); 
        headingPanel.add(text);

        mainContentPanel.add(headingPanel, BorderLayout.NORTH);
        mainContentPanel.add(imagePanel, BorderLayout.CENTER);

        // Only add mainContentPanel to CENTER
        add(mainContentPanel, BorderLayout.CENTER); 

        setVisible(true);
    }
    
    // Helper method to create the Header Panel (unchanged)
    private JPanel createHeaderPanel() {
        JPanel p1 = new JPanel();
        p1.setBackground(HEADER_BLACK); 
        p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS)); 

        // --- 1. Top Bar (Dashboard Title) ---
        JPanel topBar = new JPanel();
        topBar.setBackground(HEADER_BLACK); 
        topBar.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10)); 
        


        JLabel l1 = new JLabel("Dashboard");
        l1.setFont(new Font("Tahoma", Font.BOLD, 30));
        l1.setForeground(Color.WHITE);
        topBar.add(l1);

        p1.add(topBar);

        // --- 2. Menu Buttons Bar (Below the Title) ---
        JPanel menuBar = createMenuBar();
        p1.add(menuBar);
        
        return p1;
    }
    
    // Helper method to create the Menu Bar (unchanged)
    private JPanel createMenuBar() {
        JPanel menuBar = new JPanel();
        menuBar.setBackground(BUTTON_DARK_GREY); 
        menuBar.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5)); 

        int buttonIndex = 0;
        for (String label : buttonLabels) {
            
            JButton button = new JButton(label);
            button.setBackground(HEADER_BLACK); 
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Tahoma", Font.PLAIN, 14)); 
            button.setMargin(new Insets(5, 10, 5, 10)); 
            button.addActionListener(this); 
            menuBar.add(button);
            menuButtons[buttonIndex++] = button;
        }

        return menuBar;
    }

    // Week 1: Action Listener sirf Console par print karega
    @Override
    public void actionPerformed(ActionEvent ae) {
        String clickedLabel = ((JButton) ae.getSource()).getText();
        System.out.println("Dashboard button clicked: " + clickedLabel);
        JOptionPane.showMessageDialog(this, "Button '" + clickedLabel + "' clicked (Week 1 functionality).");
        
        // Next Week: New window logic will go here
        
    }
    
    public static void main(String[] args) {
        new Dashboard("Sarmad"); 
    }
}
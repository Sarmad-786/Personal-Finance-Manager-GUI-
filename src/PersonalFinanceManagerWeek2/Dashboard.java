package PersonalFinanceManagerWeek2;

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
    private static final Color BUTTON_DARK_GREY = new Color(30, 30, 30); 
    private static final Color MAIN_CONTENT_LIGHT_GREY = new Color(220, 220, 220);
    private static final Color WELCOME_TEXT_DARK_GREY = new Color(51, 51, 51);

    public Dashboard(String username) {
        this.username = username;
        
        // --- 1. Frame Title ---
        setTitle("Personal Finance Manager - Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // **Main Frame Layout: Border Layout**
        setLayout(new BorderLayout(0, 0)); 

        // --- 1. NORTH PANEL (Header Panel) ---
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // --- 2. CENTER PANEL (Main Content Area) ---
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BorderLayout());
        mainContentPanel.setBackground(MAIN_CONTENT_LIGHT_GREY); 

        // --- 2a. IMAGE PANEL (Center's Center) ---
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());
        imagePanel.setBackground(MAIN_CONTENT_LIGHT_GREY); 

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/home.jpg"));
        Image i5 = i4.getImage().getScaledInstance(1650, 1000, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel image = new JLabel(i6);
        imagePanel.add(image, BorderLayout.CENTER);
        
        // --- 2b. HEADING PANEL (Placed OVER the Image Panel) ---
        JPanel headingPanel = new JPanel();
        headingPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        headingPanel.setBackground(Color.WHITE); 
        
        JLabel text = new JLabel("Personal Finance Manager");
        text.setForeground(WELCOME_TEXT_DARK_GREY); 
        text.setFont(new Font("Raleway", Font.BOLD, 45)); 
        headingPanel.add(text);

        mainContentPanel.add(headingPanel, BorderLayout.NORTH);
        mainContentPanel.add(imagePanel, BorderLayout.CENTER);

        add(mainContentPanel, BorderLayout.CENTER); 

        setVisible(true);
    }
    
    private JPanel createHeaderPanel() {
        JPanel p1 = new JPanel();
        p1.setBackground(HEADER_BLACK); 
        p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS)); 

        // --- 1. Top Bar (Dashboard Title) ---
        JPanel topBar = new JPanel();
        topBar.setBackground(HEADER_BLACK); 
        topBar.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10)); 
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/dashboard.png"));
        Image i2 = i1.getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT);
        JLabel icon = new JLabel(new ImageIcon(i2));
        topBar.add(icon);

        JLabel l1 = new JLabel("Dashboard");
        l1.setFont(new Font("Tahoma", Font.BOLD, 30));
        l1.setForeground(Color.WHITE);
        topBar.add(l1);

        p1.add(topBar);

        // --- 2. Menu Buttons Bar ---
        JPanel menuBar = createMenuBar();
        p1.add(menuBar);
        
        return p1;
    }
    
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

    @Override
    public void actionPerformed(ActionEvent ae) {
        String clickedLabel = ((JButton) ae.getSource()).getText();
        
        if(clickedLabel.equals("Add Transaction")) { 
            new AddTransaction(username);
        }
        else if(clickedLabel.equals("View Transactions"))
        {
            new ViewTransactions(username);
        }
        else if(clickedLabel.equals("Update Transaction"))
        {
            new UpdateTransaction(username);
        }
        else if(clickedLabel.equals("Check Reports")) 
        {
            new CheckReports(username); // Pass username
        }
        else if(clickedLabel.equals("Set Budget"))
        {
            new SetBudget(username);
        }
        else if(clickedLabel.equals("View Budgets"))
        {
            new ViewBudgets(username);
        }
        else if(clickedLabel.equals("Manage Accounts"))
        {
            new ManageAccounts(username); // Pass username
        }
        else if(clickedLabel.equals("Add Income"))
        {
            new AddIncome(username); 
        }
        else if(clickedLabel.equals("View Goals"))
        {
            new ViewGoals(username); 
        }
        else if(clickedLabel.equals("Set Goals"))
        {
            new SetGoals(username);
        }
        else if(clickedLabel.equals("Budget Analysis"))
        {
            new BudgetAnalysis(username); // Pass username
        }
        else if(clickedLabel.equals("Calculator")){
            try{
                Runtime.getRuntime().exec("calc.exe");
            }catch(Exception e){
                e.printStackTrace();
            } 
        }
        else if(clickedLabel.equals("Notepad")){
            try{
                Runtime.getRuntime().exec("notepad.exe");
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        else if(clickedLabel.equals("About")){
            new About();
        }
        else if(clickedLabel.equals("Delete Record")){
            new DeleteRecord(username); 
        }
    }
    
    public static void main(String[] args) {
        new Dashboard("sarmad"); 
    }
}
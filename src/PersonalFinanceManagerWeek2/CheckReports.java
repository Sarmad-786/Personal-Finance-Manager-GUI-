package PersonalFinanceManagerWeek2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 

// Renamed class to CheckReports
public class CheckReports extends JFrame implements ActionListener { 
    
    // Store the username, needed for new Report actions
    String username; 
    
    CheckReports(){
        // Using a dummy username as this constructor doesn't receive one
        this("TestUser"); 
    }
    
    // Updated constructor to accept username (best practice)
    CheckReports(String username){
        this.username = username;
        
        setBounds(450,200,900,600);
        setLocationRelativeTo(null); 
        setTitle("Financial Reports and Analysis"); // Updated Title
        
        // --- UPDATED: Financial Report Data Structures ---
        // Array Structure: {Title, Metric 1, Metric 2, Metric 3, Metric 4, Metric 5, Metric 6, Metric 7, Button Text, Special Note, Total Value, Graph Text Placeholder}
        String[] report1 = {"EXPENSE ANALYSIS","MONTHLY SPENDING BREAKDOWN","Housing/Utilities: Rs 36,000 (30%)","Food/Groceries: Rs 30,000 (25%)","Transportation: Rs 12,000 (10%)","Entertainment: Rs 6,000 (5%)","Savings Goal Progress: 90% Achieved","Debt Repayment Status: Good","VIEW FULL REPORT","HIGH SPENDING ALERT","Rs 120,000/-",
                            "Expense Category Breakdown:\n\n[Housing] ############ 30%\n[Food] ########## 25%\n[Transport] #### 10%\n[Entertainment] ## 5%\n"};
        String[] report2 = {"INCOME STATEMENT","INCOME VS EXPENSE OVERVIEW","Total Income: Rs 80,000","Total Expenses: Rs 60,000","Net Savings: Rs 20,000","Savings Rate: 25%","Avg Daily Spending: Rs 2,000","Budget Compliance: 95%","VIEW BUDGET REPORT","MONTHLY SUMMARY","Rs 80,000/-",
                            "Income vs Expense Flow:\n\nIncome: +++++++++++++++++++++\nExpense: ------------------\nNet: +++++\n"};
        String[] report3 = {"DEBT & ASSETS","NET WORTH CALCULATION","Total Assets: Rs 5,00,000","Total Liabilities: Rs 1,50,000","Net Worth: Rs 3,50,000","Debt-to-Income Ratio: 0.35","Investment Performance: +8.5%","Emergency Fund Status: 80% Full","VIEW ASSET DETAILS","ANNUAL PROJECTION","Rs 3,50,000/-",
                            "Asset Distribution:\n\n[Cash/Bank] ####### 30%\n[Investments] ############ 50%\n[Other] #### 20%\n"};
        
        JTabbedPane tab = new JTabbedPane();
        
        JPanel p1 = createReportPanel(report1);
        tab.addTab("Expenses", null, p1); 
        
        JPanel p2 = createReportPanel(report2);
        tab.addTab("Income/Savings", null, p2); 
        
        JPanel p3 = createReportPanel(report3);
        tab.addTab("Net Worth", null, p3); 
        add(tab);
        
        setVisible(true);
    }
    
    public JPanel createReportPanel(String[]pack){
        JPanel p1 = new JPanel();
        p1.setLayout(null);
        // Setting background to a very dark grey/black for professional look
        p1.setBackground(new Color(35, 35, 35)); 
        
        // --- Define Dark Theme Colors for Text ---
        Color HEADER_COLOR = new Color(30, 100, 255); // Blue Accent
        Color PRIMARY_TEXT = new Color(220, 220, 220); // Light Grey Text
        Color SECONDARY_TEXT = new Color(150, 150, 150); // Muted Grey
        Color POSITIVE_COLOR = new Color(0, 200, 0); // Green for positive/income
        Color ALERT_COLOR = new Color(255, 60, 60); // Red Alert/Expenses
        Color VALUE_COLOR = new Color(255, 170, 0); // Orange Value
        
        // Text/Metric configuration
        int labelX = 30;
        int labelW = 400;
        int labelH = 25;
        int yStart = 60;
        int ySpacing = 40;
        
        // l1 (Report Name)
        JLabel l1 = new JLabel(pack[0]);
        l1.setBounds(labelX, 5, labelW, 30);
        l1.setForeground(HEADER_COLOR); 
        l1.setFont(new Font("Tahoma",Font.BOLD,30));
        p1.add(l1);
        
        // l2 (Sub-heading)
        JLabel l2 = new JLabel(pack[1]);
        l2.setBounds(labelX, yStart, labelW, labelH); 
        l2.setForeground(SECONDARY_TEXT); 
        l2.setFont(new Font("Tahoma",Font.BOLD,18));
        p1.add(l2);
        yStart += ySpacing;
        
        // l3 to l8 (Metrics/Key Figures)
        
        // Metric 1 (Index 2)
        JLabel l3 = new JLabel(pack[2]);
        l3.setBounds(labelX, yStart, labelW, labelH);
        l3.setForeground(PRIMARY_TEXT); 
        l3.setFont(new Font("Tahoma",Font.PLAIN,18)); // Regular font
        p1.add(l3);
        yStart += ySpacing;
        
        // Metric 2 (Index 3)
        JLabel l4 = new JLabel(pack[3]);
        l4.setBounds(labelX, yStart, labelW, labelH);
        l4.setForeground(PRIMARY_TEXT);
        l4.setFont(new Font("Tahoma",Font.PLAIN,18));
        p1.add(l4);
        yStart += ySpacing;
        
        // Metric 3 (Index 4)
        JLabel l5 = new JLabel(pack[4]);
        l5.setBounds(labelX, yStart, labelW, labelH);
        l5.setForeground(PRIMARY_TEXT);
        l5.setFont(new Font("Tahoma",Font.PLAIN,18));
        p1.add(l5);
        yStart += ySpacing;
        
        // Metric 4 (Index 5)
        JLabel l6 = new JLabel(pack[5]);
        l6.setBounds(labelX, yStart, labelW, labelH);
        l6.setForeground(PRIMARY_TEXT);
        l6.setFont(new Font("Tahoma",Font.PLAIN,18));
        p1.add(l6);
        yStart += ySpacing;
        
        // Metric 5 (Index 6)
        JLabel l7 = new JLabel(pack[6]);
        // Use green for positive/savings goals
        l7.setForeground(POSITIVE_COLOR);
        l7.setBounds(labelX, yStart, labelW, labelH);
        l7.setFont(new Font("Tahoma",Font.PLAIN,18));
        p1.add(l7);
        yStart += ySpacing;
        
        // Metric 6 (Index 7)
        JLabel l8 = new JLabel(pack[7]);
        l8.setBounds(labelX, yStart, labelW, labelH);
        l8.setForeground(SECONDARY_TEXT);
        l8.setFont(new Font("Tahoma",Font.PLAIN,18));
        p1.add(l8);
        
        int buttonY = 430;
        
        // --- Action Button ---
        JButton bReport = new JButton(pack[8]); 
        bReport.setBounds(60, buttonY, 300, 35); 
        bReport.setBackground(HEADER_COLOR); // Deep Blue
        bReport.setForeground(Color.WHITE);
        bReport.setFont(new Font("Tahoma",Font.BOLD,18));
        bReport.addActionListener(this); 
        bReport.setActionCommand(pack[0]); 
        p1.add(bReport);
        
        // Special Note
        JLabel l10 = new JLabel(pack[9]);
        l10.setBounds(80, 480, 300, 30);
        l10.setForeground(ALERT_COLOR); // Red Alert
        l10.setFont(new Font("Tahoma",Font.BOLD,22));
        p1.add(l10);
        
        // Total Value
        JLabel l11 = new JLabel(pack[10]);
        l11.setBounds(500, 480, 300, 30);
        l11.setForeground(VALUE_COLOR); // Orange Value
        l11.setFont(new Font("Tahoma",Font.BOLD,25));
        p1.add(l11);
        
        // --- DYNAMIC GRAPH/DATA AREA (Using JTextArea and JScrollPane) ---
        JTextArea graphArea = new JTextArea(pack[11]);
        graphArea.setEditable(false);
        graphArea.setFont(new Font("Monospaced", Font.PLAIN, 16)); 
        // Dark theme colors for graph area
        graphArea.setForeground(POSITIVE_COLOR); // Green text for data visualization
        graphArea.setBackground(new Color(50, 50, 50)); // Darker background for data panel
        
        JScrollPane scrollPane = new JScrollPane(graphArea);
        scrollPane.setBounds(420, 20, 440, 450); // Adjusted height to fill right side
        // Title border in dark theme
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(HEADER_COLOR),
            "Visual Report Data (Graph Placeholder)",
            0, 0, // Position (LEFT, TOP)
            new Font("Tahoma", Font.BOLD, 14),
            PRIMARY_TEXT // White title text
        ));
        p1.add(scrollPane);
        
        return p1;
    }
    
    // --- Event Handler ---
    @Override
    public void actionPerformed(ActionEvent ae){
        String command = ae.getActionCommand();
        if (command.contains("REPORT") || command.contains("ASSETS") || command.contains("ANALYSIS") || command.contains("STATEMENT")) {
            JOptionPane.showMessageDialog(this, "Opening detailed report for: " + command);
        } else {
             setVisible(false);
        }
    }
    
    public static void main(String[] args){
        new CheckReports();
    }
}
package PersonalFinanceManagerWeek2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.sql.*; 

public class BudgetAnalysis extends JFrame implements ActionListener {
    JButton viewDetails, back;
    
    // Define Dark Theme Colors (Unchanged)
    private static final Color BG_DARK = new Color(25, 25, 25);
    private static final Color PANEL_DARK = new Color(40, 40, 40);
    private static final Color TEXT_LIGHT = Color.WHITE;
    private static final Color ACCENT_GREEN = new Color(0, 150, 0); 
    private static final Color WARNING_ORANGE = new Color(255, 150, 0); 
    private static final Color DANGER_RED = new Color(200, 50, 50); 
    private static final Color CHART_FILL_COLOR = new Color(50, 50, 50); 

    private double totalBudget = 0.00;
    private double totalSpending = 0.00;
    private String username; 

    BudgetAnalysis(String username) {
        this.username = username;
        
        fetchBudgetAndSpending(); 
        
        // --- Frame Setup (Unchanged) ---
        setTitle("Budget vs. Actual Spending Analysis for " + username);
        setBounds(400, 200, 900, 600);
        setLocationRelativeTo(null); 
        setLayout(null);
        getContentPane().setBackground(BG_DARK); 
        
        // --- Heading (Unchanged) ---
        JLabel text = new JLabel("MONTHLY BUDGET ANALYSIS");
        text.setFont(new Font("Tahoma", Font.BOLD, 24));
        text.setForeground(TEXT_LIGHT);
        text.setBounds(30, 20, 500, 30);
        add(text);
        
        // --- Left Panel: Summary Metrics (Unchanged, uses updated data) ---
        
        int labelX = 30;
        int valueX = 200; 
        int yStart = 80;
        int ySpacing = 40;
        
        // 1. User Account
        JLabel lblUsername = createLabel("User Account", labelX, yStart);
        add(lblUsername);
        JLabel labelUsername = createValueLabel(username, valueX, yStart, TEXT_LIGHT, false);
        add(labelUsername);
        yStart += ySpacing;
        
        // 2. Total Budget (LIVE DATA)
        JLabel lblBudget = createLabel("Total Budget (Rs)", labelX, yStart);
        add(lblBudget);
        JLabel labelBudget = createValueLabel(String.format("Rs %,.2f", totalBudget), valueX, yStart, WARNING_ORANGE, true);
        add(labelBudget);
        yStart += ySpacing;
        
        // 3. Total Spending (LIVE DATA)
        JLabel lblSpending = createLabel("Current Spending (Rs)", labelX, yStart);
        add(lblSpending);
        JLabel labelSpending = createValueLabel(String.format("Rs %,.2f", totalSpending), valueX, yStart, DANGER_RED, true);
        add(labelSpending);
        yStart += ySpacing;
        
        // 4. Remaining Balance
        JLabel lblRemaining = createLabel("Remaining Budget (Rs)", labelX, yStart);
        add(lblRemaining);
        
        double remaining = totalBudget - totalSpending;
        Color remainingColor = (remaining >= 0) ? ACCENT_GREEN : DANGER_RED;
        String remainingText = (remaining >= 0) ? String.format("Rs %,.2f", remaining) : String.format("OVER Budget by Rs %,.2f", Math.abs(remaining));
        
        JLabel labelRemaining = createValueLabel(remainingText, valueX, yStart, remainingColor, true);
        add(labelRemaining);
        yStart += ySpacing;
        
        // 5. Spending Percentage
        JLabel lblPercent = createLabel("Budget Usage", labelX, yStart);
        add(lblPercent);
        
        double percentage = (totalBudget > 0) ? (totalSpending / totalBudget) * 100 : 0; 
        // Handle case where totalBudget was placeholder 1.0 (no budget set)
        if (totalBudget == 1.0 && percentage > 0) percentage = 0;
        
        JLabel labelPercent = createValueLabel(String.format("%.1f%% Used", percentage), valueX, yStart, TEXT_LIGHT, false); 
        add(labelPercent);

        // 6. Status Message
        int statusY = 350;
        JLabel lblStatus = createLabel("Budget Status", labelX, statusY);
        add(lblStatus);
        
        String statusMessage;
        Color statusColor;
        if (percentage >= 100) {
            statusMessage = "CRITICAL: OVER BUDGET";
            statusColor = DANGER_RED;
        } else if (percentage >= 85) {
            statusMessage = "Warning: Approaching Limit";
            statusColor = WARNING_ORANGE; 
        } else {
            statusMessage = "Good: On Track";
            statusColor = ACCENT_GREEN;
        }
        
        JLabel labelStatus = createValueLabel(statusMessage, valueX, statusY, statusColor, true);
        labelStatus.setBounds(valueX, statusY, 200, 25); 
        add(labelStatus);
        
        // --- Buttons (Unchanged) ---
        viewDetails = new JButton("View Category Details");
        viewDetails.setBounds(50, 480, 200, 40);
        viewDetails.addActionListener(this);
        viewDetails.setBackground(ACCENT_GREEN);
        viewDetails.setForeground(Color.WHITE);
        viewDetails.setFont(new Font("Tahoma", Font.BOLD, 14));
        viewDetails.setFocusPainted(false);
        add(viewDetails); 
        
        back = new JButton("Back");
        back.setBounds(270, 480, 100, 40);
        back.addActionListener(this);
        back.setBackground(Color.DARK_GRAY);
        back.setForeground(Color.WHITE);
        back.setFont(new Font("Tahoma", Font.BOLD, 14));
        back.setFocusPainted(false);
        add(back); 
        
        // --- Right Panel: Budget Progress Visualization (Unchanged) ---
        BudgetBarPanel chartPanel = new BudgetBarPanel(totalBudget, totalSpending);
        chartPanel.setBounds(400, 80, 450, 400);
        add(chartPanel);
        
        setVisible(true);
    }
    
    // --- DATABASE INTEGRATION METHOD (FIXED) ---
    public void fetchBudgetAndSpending() {
        try {
            Conn c = new Conn();
            
            // 1. Fetch Total Budget (Uses existing statement c.s)
            String budgetQuery = "SELECT SUM(target_limit) AS TotalBudget FROM budget WHERE username = '"+username+"'";
            ResultSet rsBudget = c.s.executeQuery(budgetQuery);
            if (rsBudget.next()) {
                totalBudget = rsBudget.getDouble("TotalBudget");
            }
            // Close the first ResultSet immediately
            rsBudget.close(); 
            
            // 2. Fetch Total Spending (Reuses c.s, which is now safe since rsBudget is closed)
            String spendingQuery = "SELECT SUM(amount) AS TotalSpending FROM transaction_record WHERE username = '"+username+"' AND trans_type = 'Expense' AND trans_date >= DATE_SUB(CURDATE(), INTERVAL 1 MONTH)";
            ResultSet rsSpending = c.s.executeQuery(spendingQuery);
            if (rsSpending.next()) {
                totalSpending = rsSpending.getDouble("TotalSpending");
            }
            rsSpending.close();
            
            // Placeholder budget if totalBudget is genuinely zero (to avoid divide by zero exception in calculations)
            if (totalBudget <= 0) {
                 totalBudget = 1.0; 
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching budget data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    // --- Helper Methods (Unchanged) ---
    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label.setBounds(x, y, 200, 25);
        label.setForeground(TEXT_LIGHT);
        return label;
    }
    
    private JLabel createValueLabel(String text, int x, int y, Color color, boolean isBold) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Tahoma", isBold ? Font.BOLD : Font.PLAIN, 16));
        label.setBounds(x, y, 250, 25); 
        label.setForeground(color);
        return label;
    }
    
    // --- Action Listener (Unchanged) ---
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == viewDetails){
            JOptionPane.showMessageDialog(this, "Opening Detailed Spending Report...", "Action", JOptionPane.INFORMATION_MESSAGE);
        } else if(ae.getSource() == back){
            setVisible(false);
            dispose();
        }
    }
    
    // --- Custom JPanel for Budget Bar Visualization (Unchanged) ---
    class BudgetBarPanel extends JPanel {
        private double budget;
        private double spending;

        public BudgetBarPanel(double budget, double spending) {
            this.budget = budget;
            this.spending = spending;
            setBackground(PANEL_DARK);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int barHeight = 60;
            int padding = 40;
            int barWidth = width - 2 * padding;
            int barY = height / 2 - barHeight / 2;

            double ratio = spending / budget;
            
            // Correct ratio if budget is the placeholder 1.0
            if (budget == 1.0) ratio = 0; 
            
            int progressWidth = (int) (barWidth * Math.min(ratio, 1.0)); 

            // Determine color based on usage percentage
            Color progressColor;
            if (ratio >= 1.0) {
                progressColor = DANGER_RED;
            } else if (ratio >= 0.85) {
                progressColor = WARNING_ORANGE;
            } else {
                progressColor = ACCENT_GREEN;
            }
            
            // --- Drawing ---
            
            // 1. Draw the empty (remaining) bar track
            g2d.setColor(CHART_FILL_COLOR);
            g2d.fillRoundRect(padding, barY, barWidth, barHeight, 15, 15);
            g2d.setColor(TEXT_LIGHT);
            g2d.drawRoundRect(padding, barY, barWidth, barHeight, 15, 15);
            
            // 2. Draw the progress (spending) bar
            if (progressWidth > 0) {
                g2d.setColor(progressColor);
                g2d.fillRoundRect(padding, barY, progressWidth, barHeight, 15, 15);
            }
            
            // 3. Draw text overlay for percentage
            g2d.setColor(TEXT_LIGHT);
            g2d.setFont(new Font("Tahoma", Font.BOLD, 20));
            String percentText = String.format("%.1f%% Used", ratio * 100);
            if (budget == 1.0) percentText = "N/A (Set Budget First)";
                
            int textWidth = g2d.getFontMetrics().stringWidth(percentText);
            g2d.drawString(percentText, width / 2 - textWidth / 2, barY + barHeight / 2 + 8);
            
            // 4. Draw labels for Spending and Budget
            g2d.setFont(new Font("Tahoma", Font.PLAIN, 16));
            String spendingLabel = String.format("Spent: Rs %,.0f", spending);
            String budgetLabel = (budget == 1.0) ? "Budget: Rs 0" : String.format("Budget: Rs %,.0f", budget);
            
            g2d.setColor(progressColor);
            g2d.drawString(spendingLabel, padding, barY - 15);
            
            g2d.setColor(WARNING_ORANGE);
            g2d.drawString(budgetLabel, width - padding - g2d.getFontMetrics().stringWidth(budgetLabel), barY - 15);
            
            // 5. Handle Over Budget Case
            if (ratio > 1.0) {
                g2d.setColor(DANGER_RED);
                g2d.setFont(new Font("Tahoma", Font.BOLD, 18));
                String overText = "OVER BUDGET";
                g2d.drawString(overText, width / 2 - g2d.getFontMetrics().stringWidth(overText) / 2, barY + barHeight + 40);
            }
        }
    }

    public static void main(String[] args) {
        new BudgetAnalysis("sarmad"); 
    }
}
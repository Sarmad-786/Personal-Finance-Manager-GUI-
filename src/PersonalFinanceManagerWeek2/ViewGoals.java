package PersonalFinanceManagerWeek2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D; 
import java.sql.*; // Import for Database operations

public class ViewGoals extends JFrame implements ActionListener {
    JButton back;
    
    // Data variables initialized to zero (will be set from DB)
    private double targetAmount = 0.00; 
    private double startingBalance = 0.00; 
    private double currentBalance = 0.00; 
    private String goalName = "No Goal Selected";
    private int deadlineMonths = 0;
    
    private String username;
    
    // Define Dark Theme Colors
    private static final Color BG_DARK = new Color(25, 25, 25);
    private static final Color PANEL_DARK = new Color(40, 40, 40);
    private static final Color TEXT_LIGHT = Color.WHITE;
    private static final Color PRIMARY_FINANCE = new Color(0, 180, 0); // Green for positive/achieved
    private static final Color ACCENT_BLUE = new Color(30, 100, 255);
    private static final Color CHART_BAR_COLOR = PRIMARY_FINANCE; 
    private static final Color CHART_FILL_COLOR = new Color(50, 50, 50); 
    
    // Components that need updating after DB fetch
    private JLabel labelGoalType, labelTarget, labelStartValue, labelCurrentValue, labelAchieved, labelRemainingToTarget, labelDeadline, labelProgress, labelNotes;
    private GoalChartPanel chartPanel;

    ViewGoals(String username) {
        this.username = username;
        
        // 1. Fetch live data from database
        fetchGoalData(); 

        // --- Frame Setup ---
        setTitle("VIEW SAVINGS GOAL TRACKER");
        setBounds(400, 200, 1000, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_DARK); 
        setLayout(null);

        // --- Heading ---
        JLabel text = new JLabel("VIEW PERSONAL SAVINGS GOAL");
        text.setFont(new Font("Tahoma", Font.BOLD, 20));
        text.setForeground(ACCENT_BLUE);
        text.setBounds(60, 20, 400, 30);
        add(text);

        // --- Goal Details ---
        int labelX = 30;
        int valueX = 220;
        int yStart = 80;
        int ySpacing = 35;
        
        // 1. User
        JLabel lblusername = createLabel("User Account", labelX, yStart);
        add(lblusername);
        
        JLabel labelusername = createValueLabel(username, valueX, yStart, TEXT_LIGHT);
        add(labelusername);
        yStart += ySpacing;

        // 2. Goal Type (LIVE)
        JLabel lblGoalType = createLabel("Goal Type", labelX, yStart);
        add(lblGoalType);
        labelGoalType = createValueLabel(goalName, valueX, yStart, TEXT_LIGHT);
        add(labelGoalType);
        yStart += ySpacing;
        
        // 3. Target Amount (LIVE)
        JLabel lblTarget = createLabel("Target Amount (Rs)", labelX, yStart);
        add(lblTarget);
        labelTarget = createValueLabel(String.format("Rs %,.2f", targetAmount), valueX, yStart, ACCENT_BLUE, true);
        add(labelTarget);
        yStart += ySpacing;
        
        // 4. Starting Balance (LIVE)
        JLabel lblStartValue = createLabel("Starting Balance (Rs)", labelX, yStart);
        add(lblStartValue);
        labelStartValue = createValueLabel(String.format("Rs %,.2f", startingBalance), valueX, yStart, TEXT_LIGHT);
        add(labelStartValue);
        yStart += ySpacing;
        
        // 5. Current Balance (LIVE)
        JLabel lblCurrentValue = createLabel("Current Saved (Rs)", labelX, yStart);
        add(lblCurrentValue);
        labelCurrentValue = createValueLabel(String.format("Rs %,.2f", currentBalance), valueX, yStart, PRIMARY_FINANCE, true);
        add(labelCurrentValue);
        yStart += ySpacing;
        
        // 6. Amount Achieved
        JLabel lblAchieved = createLabel("Amount Needed", labelX, yStart);
        add(lblAchieved);
        double amountAchieved = currentBalance - startingBalance;
        labelAchieved = createValueLabel(String.format("Rs %,.2f", amountAchieved), valueX, yStart, PRIMARY_FINANCE, true);
        add(labelAchieved);
        yStart += ySpacing;

        // 7. Remaining to Target
        JLabel lblRemainingToTarget = createLabel("Remaining to Target", labelX, yStart);
        add(lblRemainingToTarget);
        double remainingToTarget = targetAmount - currentBalance;
        Color remainingColor = (remainingToTarget <= 0) ? PRIMARY_FINANCE : Color.ORANGE;
        labelRemainingToTarget = createValueLabel(String.format("Rs %,.2f", Math.max(0, remainingToTarget)), valueX, yStart, remainingColor, true);
        add(labelRemainingToTarget);
        yStart += ySpacing;
        
        // 8. Deadline (LIVE)
        JLabel lblDeadline = createLabel("Target Deadline", labelX, yStart);
        add(lblDeadline);
        labelDeadline = createValueLabel(deadlineMonths + " Months Remaining", valueX, yStart, TEXT_LIGHT);
        add(labelDeadline);
        yStart += ySpacing;
        
        // 9. Progress Percentage
        JLabel lblProgress = createLabel("Progress Percentage", labelX, yStart);
        add(lblProgress);
        double totalGoalRange = targetAmount - startingBalance;
        double progressPercentage = (totalGoalRange > 0) ? (amountAchieved / totalGoalRange) * 100 : 0;
        labelProgress = createValueLabel(String.format("%.1f%% Complete", progressPercentage), valueX, yStart, ACCENT_BLUE, true);
        add(labelProgress);
        yStart += ySpacing;
        
        // 10. Last Contribution Date (DUMMY)
        JLabel lblCheckin = createLabel("Last Contribution Date", labelX, yStart);
        add(lblCheckin);
        JLabel labelCheckin = createValueLabel("N/A (Check Txns)", valueX, yStart, TEXT_LIGHT);
        add(labelCheckin);
        yStart += ySpacing;

        // 11. Notes/Motivation (DUMMY)
        JLabel lblNotes = createLabel("Motivation Note", labelX, yStart);
        add(lblNotes);
        labelNotes = createValueLabel("Keep saving!", valueX, yStart, PRIMARY_FINANCE);
        add(labelNotes);
        yStart += ySpacing;


        // --- Back Button ---
        back = new JButton("Back");
        back.setBackground(Color.DARK_GRAY);
        back.setForeground(TEXT_LIGHT);
        back.setBounds(130, 500, 100, 25);
        back.addActionListener(this);
        add(back);
        
        // --- Custom Chart Panel (Right side) ---
        chartPanel = new GoalChartPanel(startingBalance, targetAmount, currentBalance); // Pass final DB values
        chartPanel.setBounds(450, 50, 500, 450);
        add(chartPanel);

        setVisible(true);
    }
    
    // --- DATABASE INTEGRATION METHOD ---
    private void fetchGoalData() {
        try {
            Conn c = new Conn();
            // Fetch the first goal set by the user
            String query = "SELECT goal_name, target_amount, starting_balance, deadline_months, current_saved " +
                           "FROM goals WHERE username = '"+username+"' ORDER BY goal_id LIMIT 1";
            ResultSet rs = c.s.executeQuery(query);
            
            if (rs.next()) {
                goalName = rs.getString("goal_name");
                targetAmount = rs.getDouble("target_amount");
                startingBalance = rs.getDouble("starting_balance");
                deadlineMonths = rs.getInt("deadline_months");
                currentBalance = rs.getDouble("current_saved");
                // The rest of the metrics will be calculated in the constructor using these values.
            } else {
                 JOptionPane.showMessageDialog(this, "No financial goals found for this user. Please set one first.", "Data Error", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching goal data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Helper methods (Unchanged)
    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label.setBounds(x, y, 180, 25);
        label.setForeground(TEXT_LIGHT);
        return label;
    }
    
    private JLabel createValueLabel(String text, int x, int y, Color color) {
        return createValueLabel(text, x, y, color, false);
    }
    
    private JLabel createValueLabel(String text, int x, int y, Color color, boolean isBold) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Tahoma", isBold ? Font.BOLD : Font.PLAIN, 16));
        label.setBounds(x, y, 250, 25);
        label.setForeground(color);
        return label;
    }

    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == back) {
            setVisible(false);
            dispose(); 
        }
    }
    
    public static void main(String[] args) {
        new ViewGoals("sarmad");
    }

    // --- Custom JPanel for the Goal Chart (Unchanged, uses live data passed in constructor) ---
    class GoalChartPanel extends JPanel {
        private double startValue;
        private double targetValue;
        private double currentValue;

        public GoalChartPanel(double start, double target, double current) {
            this.startValue = start;
            this.targetValue = target;
            this.currentValue = current;
            setBackground(PANEL_DARK); 
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int padding = 50; 
            int barHeight = 40;

            int barY = height / 2 - barHeight / 2; 
            int barX = padding;
            int maxBarWidth = width - 2 * padding;
            
            double goalRange = targetValue - startValue; 
            if (goalRange <= 0) goalRange = targetValue; 
            
            double achievedAmount = currentValue - startValue;
            if (achievedAmount < 0) achievedAmount = 0; 

            double progressRatio = achievedAmount / goalRange;
            if (progressRatio > 1) progressRatio = 1; 

            int progressWidth = (int) (maxBarWidth * progressRatio);

            // Draw the full range bar (from Start to Target)
            g2d.setColor(CHART_FILL_COLOR);
            g2d.fill(new RoundRectangle2D.Double(barX, barY, maxBarWidth, barHeight, 10, 10));
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.draw(new RoundRectangle2D.Double(barX, barY, maxBarWidth, barHeight, 10, 10));

            // Draw the progress part (Achieved portion)
            g2d.setColor(CHART_BAR_COLOR);
            g2d.fill(new RoundRectangle2D.Double(barX, barY, progressWidth, barHeight, 10, 10));


            // Draw markers and labels
            g2d.setColor(TEXT_LIGHT);
            g2d.setFont(new Font("Tahoma", Font.BOLD, 14));

            // Starting Balance Label 
            g2d.drawString(String.format("Start: Rs %,.0f", startValue), barX, barY - 10);
            
            // Target Amount Label 
            String targetText = String.format("Target: Rs %,.0f", targetValue);
            int targetTextWidth = g2d.getFontMetrics().stringWidth(targetText);
            g2d.drawString(targetText, barX + maxBarWidth - targetTextWidth, barY - 10);


            // Current Balance Marker Line and Label
            int currentMarkerX = barX + progressWidth;
            
            g2d.setColor(ACCENT_BLUE);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawLine(currentMarkerX, barY - 5, currentMarkerX, barY + barHeight + 5);
            g2d.fillOval(currentMarkerX - 5, barY + barHeight + 10, 10, 10);
            
            g2d.setFont(new Font("Tahoma", Font.BOLD, 16));
            String currentText = String.format("Current: Rs %,.0f", currentValue);
            int textWidth = g2d.getFontMetrics().stringWidth(currentText);
            
            // Adjust position for marker text
            int textX = currentMarkerX - textWidth / 2;
            if (currentMarkerX + textWidth / 2 > barX + maxBarWidth) textX = barX + maxBarWidth - textWidth - 5;
            if (currentMarkerX - textWidth / 2 < barX) textX = barX + 5;

            g2d.drawString(currentText, textX, barY + barHeight + 35);
            g2d.setStroke(new BasicStroke(1)); 
            
            // Add a title for the chart
            g2d.setColor(TEXT_LIGHT);
            g2d.setFont(new Font("Tahoma", Font.BOLD, 18));
            String chartTitle = "Savings Goal Progress (Rs)";
            int titleWidth = g2d.getFontMetrics().stringWidth(chartTitle);
            g2d.drawString(chartTitle, (width - titleWidth) / 2, padding - 10);
        }
    }
}
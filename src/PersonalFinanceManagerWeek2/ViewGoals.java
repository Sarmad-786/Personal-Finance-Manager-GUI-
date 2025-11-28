package PersonalFinanceManagerWeek2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D; 

public class ViewGoals extends JFrame implements ActionListener {
    JButton back;
    
    // Dummy Goal Data (Updated for Savings)
    private double targetAmount = 500000.00; // Rs
    private double startingBalance = 100000.00; // Rs
    private double currentBalance = 320000.00; // Rs
    
    // Define Dark Theme Colors
    private static final Color BG_DARK = new Color(25, 25, 25);
    private static final Color PANEL_DARK = new Color(40, 40, 40);
    private static final Color TEXT_LIGHT = Color.WHITE;
    private static final Color PRIMARY_FINANCE = new Color(0, 180, 0); // Green for positive/achieved
    private static final Color ACCENT_BLUE = new Color(30, 100, 255);
    private static final Color CHART_BAR_COLOR = PRIMARY_FINANCE; 
    private static final Color CHART_FILL_COLOR = new Color(50, 50, 50); // Darker fill for remaining
    
    ViewGoals(String username) {
        // --- Frame Setup ---
        setTitle("VIEW SAVINGS GOAL TRACKER");
        setBounds(400, 200, 1000, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_DARK); // Dark background
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

        // 2. Goal Type (e.g., Savings, Investment)
        JLabel lblGoalType = createLabel("Goal Type", labelX, yStart);
        add(lblGoalType);
        
        JLabel labelGoalType = createValueLabel("Dream Vacation Fund", valueX, yStart, TEXT_LIGHT);
        add(labelGoalType);
        yStart += ySpacing;
        
        // 3. Target Amount
        JLabel lblTarget = createLabel("Target Amount (Rs)", labelX, yStart);
        add(lblTarget);
        
        JLabel labelTarget = createValueLabel(String.format("Rs %,.2f", targetAmount), valueX, yStart, ACCENT_BLUE, true);
        add(labelTarget);
        yStart += ySpacing;
        
        // 4. Starting Balance
        JLabel lblStartValue = createLabel("Starting Balance (Rs)", labelX, yStart);
        add(lblStartValue);
        
        JLabel labelStartValue = createValueLabel(String.format("Rs %,.2f", startingBalance), valueX, yStart, TEXT_LIGHT);
        add(labelStartValue);
        yStart += ySpacing;
        
        // 5. Current Balance
        JLabel lblCurrentValue = createLabel("Current Balance (Rs)", labelX, yStart);
        add(lblCurrentValue);
        
        JLabel labelCurrentValue = createValueLabel(String.format("Rs %,.2f", currentBalance), valueX, yStart, PRIMARY_FINANCE, true);
        add(labelCurrentValue);
        yStart += ySpacing;
        
        // 6. Amount Achieved
        JLabel lblAchieved = createLabel("Amount Saved Since Start", labelX, yStart);
        add(lblAchieved);
        
        double amountAchieved = currentBalance - startingBalance;
        JLabel labelAchieved = createValueLabel(String.format("Rs %,.2f", amountAchieved), valueX, yStart, PRIMARY_FINANCE, true);
        add(labelAchieved);
        yStart += ySpacing;

        // 7. Remaining to Target
        JLabel lblRemainingToTarget = createLabel("Remaining to Target", labelX, yStart);
        add(lblRemainingToTarget);

        double remainingToTarget = targetAmount - currentBalance;
        Color remainingColor = (remainingToTarget <= 0) ? PRIMARY_FINANCE : Color.ORANGE;
        JLabel labelRemainingToTarget = createValueLabel(String.format("Rs %,.2f", Math.max(0, remainingToTarget)), valueX, yStart, remainingColor, true);
        add(labelRemainingToTarget);
        yStart += ySpacing;
        
        // 8. Deadline
        JLabel lblDeadline = createLabel("Target Deadline", labelX, yStart);
        add(lblDeadline);
        
        JLabel labelDeadline = createValueLabel("12 Months Remaining", valueX, yStart, TEXT_LIGHT);
        add(labelDeadline);
        yStart += ySpacing;
        
        // 9. Progress Percentage
        JLabel lblProgress = createLabel("Progress Percentage", labelX, yStart);
        add(lblProgress);
        
        double totalGoalRange = targetAmount - startingBalance;
        double progressPercentage = (totalGoalRange > 0) ? (amountAchieved / totalGoalRange) * 100 : 0;
        JLabel labelProgress = createValueLabel(String.format("%.1f%% Complete", progressPercentage), valueX, yStart, ACCENT_BLUE, true);
        add(labelProgress);
        yStart += ySpacing;
        
        // 10. Last Contribution Date
        JLabel lblCheckin = createLabel("Last Contribution Date", labelX, yStart);
        add(lblCheckin);
        
        JLabel labelCheckin = createValueLabel("15/11/2025", valueX, yStart, TEXT_LIGHT);
        add(labelCheckin);
        yStart += ySpacing;

        // 11. Notes/Motivation
        JLabel lblNotes = createLabel("Motivation Note", labelX, yStart);
        add(lblNotes);
        
        JLabel labelNotes = createValueLabel("Keep saving! You're 60% there.", valueX, yStart, PRIMARY_FINANCE);
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
        GoalChartPanel chartPanel = new GoalChartPanel(startingBalance, targetAmount, currentBalance);
        chartPanel.setBounds(450, 50, 500, 450);
        add(chartPanel);

        setVisible(true);
    }
    
    // Helper method to create standard dark-theme labels
    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label.setBounds(x, y, 180, 25);
        label.setForeground(TEXT_LIGHT);
        return label;
    }
    
    // Helper method to create standard dark-theme value labels
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
            dispose(); // Release system resources
        }
    }
    
    public static void main(String[] args) {
        new ViewGoals("sarmad");
    }

    // --- Custom JPanel for the Goal Chart (Modified for Savings) ---
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

            // --- Visualization logic ---
            
            // Re-think: A single bar from START to TARGET, with a marker for CURRENT.
            
            int barY = height / 2 - barHeight / 2; // Center the single bar vertically
            int barX = padding;
            int maxBarWidth = width - 2 * padding;
            
            // Total Goal Range (The baseline length of the bar)
            double goalRange = targetValue - startValue; 
            if (goalRange <= 0) goalRange = targetValue; // Handle cases where start >= target
            
            // Progress achieved relative to the goal range
            double achievedAmount = currentValue - startValue;
            if (achievedAmount < 0) achievedAmount = 0; // Cannot go below start for savings goal

            // Calculate the width of the achieved part
            double progressRatio = achievedAmount / goalRange;
            if (progressRatio > 1) progressRatio = 1; // Cap at 100%

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

            // Starting Balance Label (Left side of the bar)
            g2d.drawString(String.format("Start: Rs %,.0f", startValue), barX, barY - 10);
            
            // Target Amount Label (Right side of the bar)
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
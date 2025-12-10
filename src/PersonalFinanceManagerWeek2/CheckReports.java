package PersonalFinanceManagerWeek2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class CheckReports extends JFrame implements ActionListener {	
	
	String username;	
	
	// Default constructor (for testing)
	CheckReports(){
		this("TestUser");	
	}
	
	// Updated constructor to accept username (best practice)
	CheckReports(String username){
		this.username = username;
		
		setBounds(450,200,900,600);
		setLocationRelativeTo(null);	
		setTitle("Financial Reports and Analysis");	
		
		// 1. Fetch live data for each report category
		String[] report1 = fetchExpenseData();
		String[] report2 = fetchIncomeData();
		String[] report3 = fetchNetWorthData();
		
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

	// --- DATABASE INTEGRATION METHODS (FIXED) ---

	private String[] fetchExpenseData() {
		String[] defaultReport = {"EXPENSE ANALYSIS","NO DATA AVAILABLE","Rs 0.00","Rs 0.00","Rs 0.00","Rs 0.00","0% Achieved","No Debt","VIEW FULL REPORT","NO EXPENSE DATA","Rs 0/-",
								"Expense Category Breakdown:\n\n[Food] ## 0%\n[Rent] ## 0%\n[Other] ## 0%\n"};
        
        Conn c = new Conn();
        Statement s1 = null;
        ResultSet rs1 = null;
        Statement s2 = null;
        ResultSet rs2 = null;

		try {
			// Query 1: Total Expense for the month
			String totalExpenseQuery = "SELECT SUM(amount) AS Total, category, COUNT(category) AS Count " +
									   "FROM transaction_record WHERE username = '"+username+"' AND trans_type = 'Expense' " +
									   "AND trans_date >= DATE_SUB(CURDATE(), INTERVAL 1 MONTH) GROUP BY category ORDER BY Total DESC";
			
            s1 = c.c.createStatement();
			rs1 = s1.executeQuery(totalExpenseQuery);
			
			double totalExpense = 0.0;
			StringBuilder breakdown = new StringBuilder();
			
			// Calculate Total and build breakdown string
			while(rs1.next()) {
				totalExpense += rs1.getDouble("Total");
				String category = rs1.getString("category");
				double amount = rs1.getDouble("Total");
				breakdown.append(String.format("[%s]: Rs %,.0f\n", category, amount));
			}
			
			if (totalExpense == 0) return defaultReport;
			
            // Close the first result set and statement before running the second query
            rs1.close();
            s1.close();
            
			// Query 2: Fetch Top 3 categories for metrics display
            s2 = c.c.createStatement();
			rs2 = s2.executeQuery(totalExpenseQuery + " LIMIT 3");
			String[] topCategories = new String[3];
			int i = 0;
			while(rs2.next() && i < 3) {
				double percent = (rs2.getDouble("Total") / totalExpense) * 100;
				topCategories[i] = String.format("%s: Rs %,.0f (%.1f%%)", rs2.getString("category"), rs2.getDouble("Total"), percent);
				i++;
			}
			
			// Assemble the final report array (Unchanged)
			String[] finalReport = new String[12];
			finalReport[0] = "EXPENSE ANALYSIS";
			finalReport[1] = "MONTHLY SPENDING BREAKDOWN (Rs "+String.format("%,.0f", totalExpense)+")";
			finalReport[2] = topCategories[0] != null ? topCategories[0] : "Top Category: N/A";
			finalReport[3] = topCategories[1] != null ? topCategories[1] : "Second Category: N/A";
			finalReport[4] = topCategories[2] != null ? topCategories[2] : "Third Category: N/A";
			finalReport[5] = "Debt Repayment Status: Check Balance";
			finalReport[6] = "Savings Goal Progress: N/A";
			finalReport[7] = "Budget Compliance: Check Budget";
			finalReport[8] = "VIEW FULL REPORT";
			finalReport[9] = (totalExpense > 10000) ? "HIGH SPENDING ALERT" : "Spending Normal";
			finalReport[10] = "Rs " + String.format("%,.0f/-", totalExpense);
			finalReport[11] = "Expense Breakdown:\n\n" + breakdown.toString();

			return finalReport;

		} catch (SQLException e) {
			e.printStackTrace();
			return defaultReport;
		} finally {
            // Ensure all resources are closed in case of success or error
            try {
                if (rs1 != null) rs1.close();
                if (s1 != null) s1.close();
                if (rs2 != null) rs2.close();
                if (s2 != null) s2.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
	}

	private String[] fetchIncomeData() {
		String[] defaultReport = {"INCOME STATEMENT","NO DATA AVAILABLE","Total Income: Rs 0","Total Expenses: Rs 0","Net Savings: Rs 0","Savings Rate: 0%","Avg Daily Spending: Rs 0","Budget Compliance: N/A","VIEW BUDGET REPORT","NO ACTIVITY","Rs 0/-",
								"Income vs Expense Flow:\n\nIncome: 0\nExpense: 0\nNet: 0\n"};
        
        Conn c = new Conn();
        Statement sIncome = null;
        ResultSet rsIncome = null;
        Statement sExpense = null;
        ResultSet rsExpense = null;

		try {
			// Query 1: Total Income for the month
			String incomeQuery = "SELECT SUM(amount) AS TotalIncome FROM transaction_record WHERE username = '"+username+"' AND trans_type = 'Income' AND trans_date >= DATE_SUB(CURDATE(), INTERVAL 1 MONTH)";
			sIncome = c.c.createStatement();
			rsIncome = sIncome.executeQuery(incomeQuery);
			double totalIncome = rsIncome.next() ? rsIncome.getDouble("TotalIncome") : 0.0;
            rsIncome.close();
            sIncome.close();


			// Query 2: Total Expense for the month
			String expenseQuery = "SELECT SUM(amount) AS TotalExpense FROM transaction_record WHERE username = '"+username+"' AND trans_type = 'Expense' AND trans_date >= DATE_SUB(CURDATE(), INTERVAL 1 MONTH)";
            sExpense = c.c.createStatement();
			rsExpense = sExpense.executeQuery(expenseQuery);
			double totalExpense = rsExpense.next() ? rsExpense.getDouble("TotalExpense") : 0.0;
            rsExpense.close();
            sExpense.close();
			
			double netSavings = totalIncome - totalExpense;
			double savingsRate = (totalIncome > 0) ? (netSavings / totalIncome) * 100 : 0;
			
			// Assemble the final report array (Unchanged)
			String[] finalReport = new String[12];
			finalReport[0] = "INCOME STATEMENT";
			finalReport[1] = "MONTHLY CASH FLOW OVERVIEW";
			finalReport[2] = "Total Income: Rs " + String.format("%,.0f", totalIncome);
			finalReport[3] = "Total Expenses: Rs " + String.format("%,.0f", totalExpense);
			finalReport[4] = "Net Savings: Rs " + String.format("%,.0f", netSavings);
			finalReport[5] = "Savings Rate: " + String.format("%.1f%%", savingsRate);
			finalReport[6] = "Status: " + (netSavings >= 0 ? "SURPLUS" : "DEFICIT");
			finalReport[7] = "Budget Compliance: Check Budget";
			finalReport[8] = "VIEW CASH FLOW REPORT";
			finalReport[9] = (netSavings > 0) ? "POSITIVE BALANCE" : "MONITOR SPENDING";
			finalReport[10] = "Rs " + String.format("%,.0f/-", totalIncome);
			finalReport[11] = "Cash Flow:\n\nIncome: +" + totalIncome + "\nExpense: -" + totalExpense + "\nNet: " + netSavings;

			return finalReport;

		} catch (SQLException e) {
			e.printStackTrace();
			return defaultReport;
		} finally {
            // Note: Manual closing is already done inside the try block for local statements, but adding this final check for completeness.
            try {
                if (rsIncome != null) rsIncome.close();
                if (sIncome != null) sIncome.close();
                if (rsExpense != null) rsExpense.close();
                if (sExpense != null) sExpense.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
	}

	private String[] fetchNetWorthData() {
		String[] defaultReport = {"DEBT & ASSETS","NO DATA AVAILABLE","Total Assets: Rs 0","Total Liabilities: Rs 0","Net Worth: Rs 0","Debt-to-Income Ratio: N/A","Investment Performance: N/A","Emergency Fund Status: N/A","VIEW ASSET DETAILS","NO DATA","Rs 0/-",
								"Asset Distribution:\n\n[Cash] 0%\n[Debt] 0%\n"};

        Conn c = new Conn();
        Statement sAsset = null;
        ResultSet rsAsset = null;
        Statement sLiability = null;
        ResultSet rsLiability = null;
        
		try {
			// Query 1: Total Assets (Positive balances from accounts)
			String assetQuery = "SELECT SUM(CASE WHEN current_balance >= 0 THEN current_balance ELSE 0 END) AS TotalAssets FROM account WHERE username = '"+username+"'";
			sAsset = c.c.createStatement();
			rsAsset = sAsset.executeQuery(assetQuery);
			double totalAssets = rsAsset.next() ? rsAsset.getDouble("TotalAssets") : 0.0;
            rsAsset.close();
            sAsset.close();
			
			// Query 2: Total Liabilities (Negative balances from accounts, converted to positive for liability calculation)
			String liabilityQuery = "SELECT SUM(CASE WHEN current_balance < 0 THEN current_balance ELSE 0 END) AS TotalLiabilities FROM account WHERE username = '"+username+"'";
			sLiability = c.c.createStatement();
			rsLiability = sLiability.executeQuery(liabilityQuery);
			double totalLiabilities = Math.abs(rsLiability.next() ? rsLiability.getDouble("TotalLiabilities") : 0.0); // Make it positive
            rsLiability.close();
            sLiability.close();
			
			double netWorth = totalAssets - totalLiabilities;
			
			// Assemble the final report array (Unchanged)
			String[] finalReport = new String[12];
			finalReport[0] = "DEBT & ASSETS";
			finalReport[1] = "NET WORTH CALCULATION";
			finalReport[2] = "Total Assets: Rs " + String.format("%,.0f", totalAssets);
			finalReport[3] = "Total Liabilities: Rs " + String.format("%,.0f", totalLiabilities);
			finalReport[4] = "Net Worth: Rs " + String.format("%,.0f", netWorth);
			finalReport[5] = "Assets Ratio: " + String.format("%.1f%%", (totalAssets / (totalAssets + totalLiabilities)) * 100);
			finalReport[6] = "Debt Ratio: " + String.format("%.1f%%", (totalLiabilities / (totalAssets + totalLiabilities)) * 100);
			finalReport[7] = "Emergency Fund Status: N/A";
			finalReport[8] = "VIEW ASSET DETAILS";
			finalReport[9] = (netWorth >= 0) ? "POSITIVE NET WORTH" : "NEGATIVE NET WORTH";
			finalReport[10] = "Rs " + String.format("%,.0f/-", netWorth);
			finalReport[11] = "Net Worth Breakdown:\n\nAssets: " + totalAssets + "\nLiabilities: " + totalLiabilities;

			return finalReport;

		} catch (SQLException e) {
			e.printStackTrace();
			return defaultReport;
		} finally {
             // Ensure resources are closed
            try {
                if (rsAsset != null) rsAsset.close();
                if (sAsset != null) sAsset.close();
                if (rsLiability != null) rsLiability.close();
                if (sLiability != null) sLiability.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
	}


	// --- GUI Methods (createReportPanel and Helpers are UNCHANGED) ---
	
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
		l3.setFont(new Font("Tahoma",Font.PLAIN,18));
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
		
		// Metric 5 (Index 6) - Highlighted Green/Positive
		JLabel l7 = new JLabel(pack[6]);
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
		bReport.setBackground(HEADER_COLOR);	
		bReport.setForeground(Color.WHITE);
		bReport.setFont(new Font("Tahoma",Font.BOLD,18));
		bReport.addActionListener(this);	
		bReport.setActionCommand(pack[0]);	
		p1.add(bReport);
		
		// Special Note
		JLabel l10 = new JLabel(pack[9]);
		l10.setBounds(80, 480, 300, 30);
		l10.setForeground(ALERT_COLOR);	
		l10.setFont(new Font("Tahoma",Font.BOLD,22));
		p1.add(l10);
		
		// Total Value
		JLabel l11 = new JLabel(pack[10]);
		l11.setBounds(500, 480, 300, 30);
		l11.setForeground(VALUE_COLOR);	
		l11.setFont(new Font("Tahoma",Font.BOLD,25));
		p1.add(l11);
		
		// --- DYNAMIC GRAPH/DATA AREA (JTextArea) ---
		JTextArea graphArea = new JTextArea(pack[11]);
		graphArea.setEditable(false);
		graphArea.setFont(new Font("Monospaced", Font.PLAIN, 16));	
		graphArea.setForeground(POSITIVE_COLOR);	
		graphArea.setBackground(new Color(50, 50, 50));	
		
		JScrollPane scrollPane = new JScrollPane(graphArea);
		scrollPane.setBounds(420, 20, 440, 450);	
		scrollPane.setBorder(BorderFactory.createTitledBorder(
			BorderFactory.createLineBorder(HEADER_COLOR),
			"Visual Report Data (Live Metrics)",
			0, 0,	
			new Font("Tahoma", Font.BOLD, 14),
			PRIMARY_TEXT	
		));
		p1.add(scrollPane);
		
		return p1;
	}
	
	// --- Event Handler (Unchanged) ---
	@Override
	public void actionPerformed(ActionEvent ae){
		String command = ae.getActionCommand();
		if (command.contains("REPORT") || command.contains("ASSETS") || command.contains("ANALYSIS") || command.contains("STATEMENT")) {
			JOptionPane.showMessageDialog(this, "Opening detailed report for: " + command);
		} else {
			 setVisible(false);
		}
	}
	
	// --- Main method must pass a username now ---
	public static void main(String[] args){
		new CheckReports("sarmad");
	}
}
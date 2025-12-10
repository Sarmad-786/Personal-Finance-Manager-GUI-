package PersonalFinanceManagerWeek2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer; // Make sure this is imported

public class ViewTransactions extends JFrame implements ActionListener {
    
    // Define Colors and Fonts
    private static final Color FRAME_BG_COLOR = Color.BLACK; 
    private static final Color PANEL_BG_COLOR = new Color(30, 30, 30); 
    private static final Color PRIMARY_COLOR = new Color(0, 153, 204); 
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color ACCENT_COLOR = new Color(255, 153, 51); 
    private static final Color EXPENSE_COLOR = new Color(255, 77, 77); 
    
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 26);
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 14);
    
    JButton back;
    JTable table;
    DefaultTableModel tableModel;
    String username;
    
    ViewTransactions(String username){ 
        this.username = username;
        
        // --- Frame Setup ---
        setBounds(200, 100, 1000, 600); 
        setLocationRelativeTo(null); 
        getContentPane().setBackground(FRAME_BG_COLOR);
        setLayout(new BorderLayout()); 

        // --- Top Panel for Title and Back Button ---
        JPanel topPanel = new JPanel();
        topPanel.setLayout(null);
        topPanel.setPreferredSize(new Dimension(1000, 80));
        topPanel.setBackground(PANEL_BG_COLOR);
        
        JLabel title = new JLabel("All Transactions for: " + username);
        title.setFont(TITLE_FONT);
        title.setForeground(PRIMARY_COLOR);
        title.setBounds(30, 20, 400, 35);
        topPanel.add(title);
        
        back = new JButton("Back");
        back.setBackground(PRIMARY_COLOR);
        back.setForeground(Color.WHITE);
        back.setFont(LABEL_FONT);
        back.setBounds(850, 25, 100, 35); 
        back.addActionListener(this);
        topPanel.add(back);
        
        add(topPanel, BorderLayout.NORTH);

        // --- Center Panel for Table ---
        String[] columnNames = {"ID", "Type", "Amount", "Category", "Account", "Date", "Method", "Notes"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        
        // Set table style
        table.setBackground(PANEL_BG_COLOR);
        table.setForeground(TEXT_COLOR);
        table.setSelectionBackground(PRIMARY_COLOR.darker());
        table.setSelectionForeground(Color.WHITE);
        table.setFont(LABEL_FONT);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBackground(PANEL_BG_COLOR);
        
        add(scrollPane, BorderLayout.CENTER);
        
        // Load data immediately
        fetchTransactionData();
        
        setVisible(true);
    }
    
    // --- DATABASE INTEGRATION METHOD ---
    public void fetchTransactionData() {
        try {
            Conn c = new Conn();
            // Join transaction_record with account to get the account name
            String query = "SELECT t.transaction_id, t.trans_type, t.amount, t.category, a.account_name, t.trans_date, t.payment_method, t.notes " +
                           "FROM transaction_record t JOIN account a ON t.account_id = a.account_id WHERE t.username = '"+username+"' ORDER BY t.trans_date DESC";
            
            ResultSet rs = c.s.executeQuery(query);
            
            // Clear existing table data
            tableModel.setRowCount(0);

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("transaction_id"));
                row.add(rs.getString("trans_type"));
                row.add(String.format("Rs %,.2f", rs.getDouble("amount")));
                row.add(rs.getString("category"));
                row.add(rs.getString("account_name"));
                row.add(rs.getDate("trans_date"));
                row.add(rs.getString("payment_method"));
                row.add(rs.getString("notes"));
                
                tableModel.addRow(row);
            }

            if (tableModel.getRowCount() == 0) {
                 JOptionPane.showMessageDialog(this, "No transactions found for this user.", "Data Info", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching transaction data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        
        // Custom rendering for colors (e.g., coloring the Amount column)
        // FIX: The CustomRenderer must be static for JTable to recognize it easily outside the main class context.
        table.setDefaultRenderer(Object.class, new CustomRenderer());
    }

    // FIX: Added 'static' keyword to resolve the incompatible types compilation error.
    static class CustomRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            // Get the value from the "Type" column (index 1)
            String type = table.getModel().getValueAt(row, 1).toString(); 
            
            if (column == 2) { // Target the "Amount" column (index 2)
                if (type.equals("Expense")) {
                    c.setForeground(EXPENSE_COLOR);
                } else if (type.equals("Income")) {
                    c.setForeground(ACCENT_COLOR);
                }
            } else {
                 // Set default text color for other columns
                c.setForeground(TEXT_COLOR);
            }
            
            // Handle selection colors
            if (isSelected) {
                c.setBackground(PRIMARY_COLOR.darker());
            } else {
                c.setBackground(PANEL_BG_COLOR);
            }
            return c;
        }
    }
    
    // --- Action Listener ---
    public void actionPerformed(ActionEvent ae){
        if (ae.getSource() == back) {
            setVisible(false);
            dispose();
        }
    }
    
    public static void main(String[]args){
        new ViewTransactions("sarmad");
    }
}
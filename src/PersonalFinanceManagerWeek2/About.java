package PersonalFinanceManagerWeek2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class About extends JFrame implements ActionListener{
    About(){
        
        setBounds(600,200,500,550); 
        setLocationRelativeTo(null); 
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        
        // ABOUT Heading
        JLabel l1= new JLabel("ABOUT");
        l1.setBounds(150,10,100,40);
        l1.setForeground(Color.red);
        l1.setFont(new Font("Tahoma", Font.PLAIN,20));
        add(l1);
        
        // Project Description Text
        String s = "About Project\n"+
                "\n"+
                "The Personal Finance Manager is a comprehensive software solution designed to help individuals track, manage, and analyze their income, expenses, and overall financial health. This application provides users with tools to budget effectively, monitor spending habits, and work toward their savings goals.\n" +
                "\n" +
                "Advantages of the Personal Finance Manager:\n" +
                "1. Clear Financial Overview: Easily view all accounts, transactions, and balances in one centralized dashboard.\n" +
                "2. Effective Budgeting: Create and track detailed budgets for various categories like groceries, rent, and entertainment.\n" +
                "3. Spending Analysis: Generate reports and visualizations to identify where your money is going and uncover saving opportunities.\n" +
                "4. Goal Setting: Set and monitor progress towards specific financial objectives, such as saving for a down payment or retirement.\n" +
                "5. Secure Data: All personal and financial data is handled with strong security protocols.\n" +
                "6. Time-Saving: Automate transaction categorization and reporting, reducing the time spent on manual bookkeeping.\n" +
                "7. Improved Financial Health: Gain valuable insights needed to make informed decisions and build long-term wealth.\n" +
                "\n" ;
        
        
        // Text Area (uses AWT's TextArea)
        TextArea area = new TextArea(s,10,40,Scrollbar.VERTICAL);
        area.setEditable(false);
        area.setBounds(20,100,450,300);
        add(area);
        
        // Back Button (Event Handler Attached)
        JButton back = new JButton("Back");
        back.setBounds(200,420,100,25);
        back.addActionListener(this);
        add(back);
        
        setVisible(true);
        
    }
    
    
    public void actionPerformed(ActionEvent ae){
        // Back button functionality
        setVisible(false);
    }
    
    public static void main(String[]args){
        new About();
    }
}
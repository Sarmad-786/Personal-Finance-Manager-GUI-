DROP DATABASE IF EXISTS financial_manager_db;
CREATE DATABASE financial_manager_db;
USE financial_manager_db;
CREATE TABLE user (
    username VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    password VARCHAR(255) NOT NULL,
    security_q VARCHAR(200),
    security_ans VARCHAR(200)
);
CREATE TABLE account (
    account_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    account_name VARCHAR(100) NOT NULL, 
    account_type ENUM('Savings', 'Current', 'Credit Card', 'Investment', 'Loan', 'Cash') NOT NULL,
    current_balance DECIMAL(15, 2) DEFAULT 0.00,
    FOREIGN KEY (username) REFERENCES user(username) ON DELETE CASCADE
);
CREATE TABLE transaction_record (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    account_id INT, 
    trans_type ENUM('Income', 'Expense') NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    category VARCHAR(100) NOT NULL,
    payment_method VARCHAR(50), 
    trans_date DATE NOT NULL,
    notes VARCHAR(255),
    FOREIGN KEY (username) REFERENCES user(username) ON DELETE CASCADE
    -- Note: account_id foreign key is handled in application logic for simplicity in this version.
);


CREATE TABLE budget (
    budget_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    category VARCHAR(100) NOT NULL,
    target_limit DECIMAL(15, 2) NOT NULL,
    budget_month DATE NOT NULL,
    FOREIGN KEY (username) REFERENCES user(username) ON DELETE CASCADE
);

CREATE TABLE goals (
    goal_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    goal_name VARCHAR(100) NOT NULL,
    target_amount DECIMAL(15, 2) NOT NULL,
    starting_balance DECIMAL(15, 2) DEFAULT 0.00,
    deadline_months INT NOT NULL,
    current_saved DECIMAL(15, 2) DEFAULT 0.00,
    FOREIGN KEY (username) REFERENCES user(username) ON DELETE CASCADE
);

INSERT INTO user (username, name, email, password, security_q, security_ans)
VALUES ('sarmad', 'Sarmad Chughtai', 'sarmad@pfmt.com', 'mypass123', 'Your Lucky Number', '7');
INSERT INTO account (username, account_name, account_type, current_balance)
VALUES ('sarmad', 'HDFC Savings', 'Savings', 50000.00);


INSERT INTO transaction_record (username, account_id, trans_type, amount, category, payment_method, trans_date, notes)
VALUES ('sarmad', 1, 'Income', 50000.00, 'Salary', 'Bank Transfer', '2025-11-25', 'Monthly Salary'),
       ('sarmad', 1, 'Expense', 5000.00, 'Groceries', 'Digital Wallet', '2025-11-28', 'Weekly Shop');
       
INSERT INTO budget (username, category, target_limit, budget_month)
VALUES ('sarmad', 'Groceries', 10000.00, '2025-11-01');

INSERT INTO goals (username, goal_name, target_amount, starting_balance, deadline_months, current_saved)
VALUES ('sarmad', 'Dream Vacation Fund', 500000.00, 100000.00, 12, 320000.00);

select * from user
package atm_with_jdbc_and_jframe.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import atm_with_jdbc_and_jframe.db.entity.Account;
import atm_with_jdbc_and_jframe.db.entity.Customer;
import atm_with_jdbc_and_jframe.db.entity.Transaction;

import static atm_with_jdbc_and_jframe.db.entity.Account.generateNewAccountNumber;
import static atm_with_jdbc_and_jframe.ui.AtmFrame.DEPOSIT;
import static atm_with_jdbc_and_jframe.ui.AtmFrame.WITHDRAWAL;

public class DataSource {

    private String url = "jdbc:postgresql://localhost:5432/";
    private final String user = "postgres";
    private final String password = "123";

    private Connection conn = null;

    public DataSource() {
        conn = connect();
    }

    private Connection connect() {
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return conn;
    }

    public void createDatabaseIfNotExists(){
        String query="SELECT datname FROM pg_database";

        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(query);
            ResultSet res = pstmt.executeQuery();
//            res.next();
            boolean shAtmDbExists=false;
            while(res.next()){
                System.out.println(res.getString("datname"));
                if (res.getString("datname").contentEquals("shatmdb")){

                    shAtmDbExists=true;
                    break;
                }
            }

            if (!shAtmDbExists){
//                String createDbQuery="SELECT 'CREATE DATABASE shatmdb' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = ‘shatmdb’)\\gexec";

                String createDbQuery="CREATE DATABASE shatmdb";
                int i = conn.prepareStatement(createDbQuery).executeUpdate();
                url = "jdbc:postgresql://localhost:5432/shatmdb";
                System.out.println("i is:"+i);
                conn = DriverManager.getConnection(url, user, password);


//                ResultSet newRes=pstmt.executeQuery();
//                String newRes=pstmt.executeQuery();
//                while(newRes.next()){
//                    System.out.println("2"+newRes.getString("message"));
//                }
            }else{
                url = "jdbc:postgresql://localhost:5432/shatmdb";
                conn = DriverManager.getConnection(url, user, password);
            }
//            createTables();
        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    public DataSource createTables(){
        String createCustomers="CREATE TABLE IF NOT EXISTS customers " +
                "(id bigserial PRIMARY KEY, " +
                "national_code bigserial NOT NULL, " +
                "full_name VARCHAR(50) NOT NULL)";

        String createAccounts="CREATE TABLE IF NOT EXISTS accounts " +
                "(id bigserial PRIMARY KEY, customer_id bigserial NOT NULL," +
                "account_number bigserial UNIQUE ," +
                " pin_code VARCHAR (10) NOT NULL," +
                " balance bigserial ,FOREIGN KEY (customer_id) REFERENCES customers (id))";

        String createTransactions="CREATE TABLE IF NOT EXISTS transactions" +
                " (id bigserial PRIMARY KEY ," +
                "type VARCHAR(20) NOT NULL," +
                "status VARCHAR(20) NOT NULL," +
                "amount bigserial NOT NULL," +
                "source_account bigserial NOT NULL," +
                "destination_account bigserial," +
                "FOREIGN KEY (source_account) REFERENCES accounts (account_number)," +
                "FOREIGN KEY (destination_account) REFERENCES accounts (account_number))";
        PreparedStatement pstmt = null;
        System.out.println("create tables");
        try {
            pstmt=conn.prepareStatement(createCustomers);
            int res=pstmt.executeUpdate();
            System.out.println("res1: "+res);
            pstmt = conn.prepareStatement(createAccounts);
             res = pstmt.executeUpdate();
            System.out.println("res2: "+res);
            pstmt = conn.prepareStatement(createTransactions);
            res = pstmt.executeUpdate();
            System.out.println("res3: "+res);


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return this;
    }

    long getMaxId(String table) {
        String query = "";
        if (table.contentEquals("customers")){
            query="SELECT MAX(id) as maxvalue FROM customers";
        }else if(table.contentEquals("accounts")){
            query="SELECT MAX(id) as maxvalue FROM accounts";
        }else if (table.contentEquals("transactions")){
            query="SELECT MAX(id) as maxvalue FROM transactions";
        }

        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(query);
            ResultSet res = pstmt.executeQuery();

            res.next();
            return res.getLong("maxvalue");
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return -1;
    }

    public long insertCustomer(Customer customer) {

        String SQL = "INSERT INTO customers(id,full_name,national_code) "
                + "VALUES(?,?,?)";

        long id = 0;
        boolean customerExists = findCustomerByNationalCode(customer.getNationalCode());

        if (!customerExists){

            try {
                PreparedStatement pstmt = conn.prepareStatement(SQL,
                        Statement.RETURN_GENERATED_KEYS);
                long lastId = getMaxId("customers");

                if (lastId == 0) {
                    System.out.println(lastId);
                }
                pstmt.setLong(1, lastId + 1);
                pstmt.setString(2, customer.getFullName());
                pstmt.setLong(3,customer.getNationalCode());
//            pstmt.setLong(4, customer.getAccountId());

                int affectedRows = pstmt.executeUpdate();

                // check the affected rows
                if (affectedRows > 0) {
                    System.out.println("affectedRows > 0");
//                 get the ID back
                    try (ResultSet rs = pstmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            id = rs.getLong(1);
//                            customer.setId(id);
                            System.out.println("id: "+id);
                            return id;

//                            insertAccount(id);
                        }
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
                }

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }else{
            System.out.println("this national key is already exists:");
        }

        return id;
    }

    private void assignUniqueAccountNumber() {

    }

    public Account insertAccount(long customerId) {
        String SQL = "INSERT INTO accounts(id,customer_id,account_number,pin_code,balance) "
                + "VALUES(?,?,?,?,?)";
        int accountNumber=0;
        Account account=null;
        boolean accountExists=true;

//        System.out.println("accountExists? "+accountExists+"  **  accountNumber: "+accountNumber);
        while (accountExists){
            accountNumber=generateNewAccountNumber();
             account=findAccountByAccountNumber(accountNumber);
             if (account==null){
                 accountExists=false;
             }else{
                 accountExists=true;
             }
        }
        String pinCode=String.valueOf(new Random().nextInt(1000,9999));
        account=new Account();
        account.setAccountNumber(accountNumber);
        account.setBalance(0);
        account.setPinCode(pinCode);
        account.setCustomerId(customerId);


        long id = 0;


        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL,
                    Statement.RETURN_GENERATED_KEYS);
            long lastId = getMaxId("accounts");

            if (lastId == 0) {
                System.out.println(lastId);
            }
            pstmt.setLong  (1, lastId + 1);
            pstmt.setLong  (2, customerId);
            pstmt.setLong  (3, accountNumber);
            pstmt.setString(4, pinCode);
            pstmt.setLong  (5, 0);

            int affectedRows = pstmt.executeUpdate();

            // check the affected rows
            if (affectedRows > 0) {
//                 get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                        account.setId(id);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return account;
    }

    public Transaction insertTransaction(Transaction transaction) {
        String SQL = "INSERT INTO transactions(id,type,status,amount,source_account,destination_account) "
                + "VALUES(?,?,?,?,?,?)";
        System.out.println("insertTransaction 1");
        long id = 0;

        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL,
                    Statement.RETURN_GENERATED_KEYS);
            long lastId = getMaxId("transactions");

            if (lastId == 0) {
                System.out.println(lastId);
            }
            pstmt.setLong(1, lastId + 1);
            pstmt.setString(2, transaction.getType());
            pstmt.setString(3,transaction.getStatus());
            pstmt.setLong(4, transaction.getAmount());
            pstmt.setLong(5, transaction.getSourceAccountNumber());
            pstmt.setLong(6, transaction.getDestinationAccountNumber());
            System.out.println("insertTransaction 2");

            int affectedRows = pstmt.executeUpdate();

            // check the affected rows
            if (affectedRows > 0) {
//                 get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                        transaction.setId(id);
                        System.out.println("insertTransaction 3");
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return transaction;
    }

//    public int updateCustomersAccountId(long id, long accountId) throws SQLException {
//        String SQL = "UPDATE customers "
//                + "SET account_id = ? "
//                + "WHERE id = ?";
//
//        int affectedrows = 0;
//        PreparedStatement pstmt = conn.prepareStatement(SQL);
//
//        pstmt.setLong(1, accountId);
//        pstmt.setLong(2, id);
//
//        affectedrows = pstmt.executeUpdate();
//
//        return affectedrows;
//    }

    public int updateAccountsBalance(long id, long newBalance) throws SQLException {
        String SQL = "UPDATE accounts "
                + "SET balance = ? "
                + "WHERE id = ?";

        int affectedrows = 0;
        PreparedStatement pstmt = conn.prepareStatement(SQL);

        pstmt.setLong(1, newBalance);
        pstmt.setLong(2, id);

        affectedrows = pstmt.executeUpdate();

        return affectedrows;
    }

    public List<Transaction> getLastTenTransactions(long sourceAccountNumber) {

        List<Transaction> transactions = new ArrayList<>();
        String SQL = "SELECT * FROM ( SELECT * FROM transactions WHERE source_account = ? OR destination_account = ? ORDER BY id DESC LIMIT 10) sub ORDER BY id DESC";


        PreparedStatement pstmt = null;
        try {

            pstmt = conn.prepareStatement(SQL);
            pstmt.setLong(1,sourceAccountNumber);
            pstmt.setLong(2,sourceAccountNumber);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                //Display values
                long id = resultSet.getLong("id");
                String type = resultSet.getString("type");
                String status = resultSet.getString("status");
                long amount = resultSet.getLong("amount");
                long sourceAccount = resultSet.getLong("source_account");
                long destinationAccount = resultSet.getLong("destination_account");
                Transaction transaction = new Transaction(id,type,status,amount,sourceAccount,destinationAccount);

                transactions.add(transaction);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }



        return transactions;
    }

    public Account findAccountByAccountNumber(long accountNumber)  {
        String SQL = "SELECT  * from accounts "
                + "WHERE account_number = ?";
        Account account = null;

        try{
            PreparedStatement pstmt = conn.prepareStatement(SQL);

            pstmt.setLong(1, accountNumber);


            System.out.println("findAccount  *"+accountNumber);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String pinCode = resultSet.getString("pin_code");
                long balance = resultSet.getLong("balance");
                long customerId=resultSet.getLong("customer_id");
                account = new Account(id, customerId,accountNumber, pinCode, balance);
                System.out.println("findAccount resultSet: "+account.toString());
                return account;
            }
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
        }

        return account;
    }

    public boolean findCustomerByNationalCode(long nationalCode)  {
        String query="SELECT * from customers WHERE national_code = ?";
        Customer customer=null;
        try{
            PreparedStatement statement=conn.prepareStatement(query);
            statement.setLong(1,nationalCode);
            ResultSet resultSet=statement.executeQuery();

            while(resultSet.next()){
//                long id=resultSet.getLong("id");
//                String fullName=resultSet.getString("full_name");
//                customer=new Customer(id,nationalCode,fullName);
                return true;
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return false;
    }

    public boolean checkAccountsPin(long accountNumber, String pinCode) {
        String SQL = "SELECT  * from accounts "
                + "WHERE account_number = ? "
                + "And pin_code = ?";
        System.out.println("checkAccountsPin ");
        try{
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setLong(1, accountNumber);
            pstmt.setString(2, pinCode);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                System.out.println("checkAccountsPin pin is correct  ");
                return true;
            }
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return false;
    }

    public String depositOrWithdraw(long accountNumber,long value,String operation) {
        Account account=findAccountByAccountNumber(accountNumber);
        long balance=account.getBalance();
        Transaction transaction=new Transaction();
        transaction.setType(operation);
        transaction.setAmount(value);
        transaction.setSourceAccountNumber(accountNumber);
        transaction.setDestinationAccountNumber(accountNumber);


        String SQL = "UPDATE accounts "
                + "SET balance = ? "
                + "WHERE account_number = ?";
        if (operation.contentEquals(DEPOSIT)){
            balance += value;
        }else if (operation.contentEquals(WITHDRAWAL)){
            if (balance<=5 || (balance - value) < 5){
                transaction.setStatus("UNSUCCESSFUL");
                insertTransaction(transaction);
                return "Your balance is not enough";
            }else {
                balance -= value;
            }
        }

        int affectedrows = 0;

        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setLong(1, balance);
            pstmt.setLong(2, accountNumber);

            affectedrows = pstmt.executeUpdate();
            transaction.setStatus("SUCCESSFUL");
            insertTransaction(transaction);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Success";
    }
}

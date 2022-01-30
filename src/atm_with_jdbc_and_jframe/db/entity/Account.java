package atm_with_jdbc_and_jframe.db.entity;

import java.util.Random;

public class Account {
    private long id;
    private long accountNumber;
    private String pinCode;
    private long balance;
    private long customerId;

    public Account(long id,long customerId,long accountNumber, String pinCode,long balance){
        this.id=id;
        this.customerId=customerId;
        this.accountNumber=accountNumber;
        this.pinCode=pinCode;
        this.balance=balance;
    }

    public Account(){}

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public static int generateNewAccountNumber(){
        return new Random().nextInt(10000,99999);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountNumber=" + accountNumber +
                ", pinCode='" + pinCode + '\'' +
                ", balance=" + balance +
                ", customerId=" + customerId +
                '}';
    }
}

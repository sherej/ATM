package simple_atm;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private int accountNumber;
    private int pinCode;
    private long balance;
    private List<Transaction> transactions;

    public BankAccount(int accountNumber,int pinCode){
        this.accountNumber=accountNumber;
        this.pinCode=pinCode;
        this.balance=0;
        this.transactions=new ArrayList<>();
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getPinCode() {
        return pinCode;
    }

    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String withdrawalMoney(int value){
        long balance=this.getBalance();
        long amount=value;
        String status="Failed";
        String message="Account balance is not enough";
        if (balance>200){
            long afterWithdrawn=balance-value;
            if (afterWithdrawn > 200){
                this.setBalance(afterWithdrawn);
                status="Success";
                message= amount + " was paid";
            }else{
                 amount= value - (200-afterWithdrawn);
                if (amount > 0){
                    this.setBalance(200);
                    status="Success";
                    message= amount+" was paid";
                }
            }
        }
        addTransaction("Withdraw",status,(int)amount);
        return message;
    }

    public long depositMoney(int value){

        long newBalance=this.getBalance()+value;
        this.setBalance(newBalance);
        addTransaction("Deposite","Success",value);
        return this.getBalance();
    }
    private void addTransaction(String type,String status,int value){
        this.transactions.add(new Transaction(type,status,value,this.getAccountNumber()));
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Transaction> getLastTenTransactions() {
        List<Transaction> lastTen=new ArrayList<>();
        int counter=0;

        for (int i=getTransactions().size()-1;i>=0;i--){
            if(counter<10){
               lastTen.add(transactions.get(i));
               counter++;
            }
        }
        return lastTen;
    }
}

package simple_atm;

public class Transaction {
    private String type;
    private String status;
    private long amount;
    private int accountNumber;

    public Transaction(String type,String status,long amount,int accountNumber){
        this.type=type;
        this.status=status;
        this.amount=amount;
        this.accountNumber=accountNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", amount=" + amount +
//                ", accountNumber=" + accountNumber +
                '}';
    }
}

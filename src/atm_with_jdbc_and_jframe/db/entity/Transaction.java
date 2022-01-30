package atm_with_jdbc_and_jframe.db.entity;

public class Transaction {
    private long id;
    private String type;
    private String status;
    private long amount;
    private long sourceAccountNumber;
    private long destinationAccountNumber;

    public Transaction(long id,String type,String status,long amount,long sourceAccountNumber,long destinationAccountNumber){
        this.id=id;
        this.type=type;
        this.status=status;
        this.amount=amount;
        this.sourceAccountNumber=sourceAccountNumber;
        this.destinationAccountNumber=destinationAccountNumber;
    }

    public Transaction(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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


    public long getSourceAccountNumber() {
        return sourceAccountNumber;
    }

    public void setSourceAccountNumber(long sourceAccountNumber) {
        this.sourceAccountNumber = sourceAccountNumber;
    }

    public long getDestinationAccountNumber() {
        return destinationAccountNumber;
    }

    public void setDestinationAccountNumber(long destinationAccountNumber) {
        this.destinationAccountNumber = destinationAccountNumber;
    }

    @Override
    public String toString() {
        return  type +" * Amount: "+amount+" * Status: " + status ;
    }
}

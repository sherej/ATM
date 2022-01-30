package simple_atm;

public class Customer {
    private long nationalCode;
    private String nickName;
    private Address address;
    private BankAccount bankAccount;

    public Customer(long nationalCode,String nickName, int accountNumber,int pinCode){
        this.nationalCode=nationalCode;
        this.nickName=nickName;
        this.address=new Address("Tehran","jamejam",30);
        this.bankAccount=new BankAccount(accountNumber,pinCode);
    }

    public long getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(long nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

}

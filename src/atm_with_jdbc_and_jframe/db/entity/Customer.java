package atm_with_jdbc_and_jframe.db.entity;

public class Customer {
    private long id;
    private long nationalCode;
    private String fullName;

    public Customer(){}

    public Customer(long nationalCode,String fullName){
        this.nationalCode=nationalCode;
        this.fullName =fullName;
    }

    public long getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(long nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", nationalCode=" + nationalCode +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}

package simple_atm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bank {
    private List<Customer> customers ;
    public Bank(){
        customers=new ArrayList<>();
    }

    protected Customer getCustomer(int accountNumber){
        Customer customer=null;
            if (accountNumber != 0){
                if (customersSize()>0){
                    for (int i=0;i < customersSize();i++){
                         Customer tempCustomer=customers.get(i);
                        if (tempCustomer.getBankAccount().getAccountNumber() == accountNumber){
                             customer=tempCustomer;
                             break;
                        }
                    }
                }
            }
        return customer;
    }

    protected boolean checkNationalCode(long nationalCode) {
        boolean exist = false;
        if (customersSize() > 0) {
            for (int i = 0; i < customersSize(); i++) {
                Customer customer = customers.get(i);
                if (customer.getNationalCode() == nationalCode) {
                    exist = true;
                    break;
                }
            }
        }
        return exist;
    }

    private int customersSize(){
        return customers.size();
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    protected Customer createCustomer(long nationalCode, String nickName) {
        Random rand=new Random();
        boolean customerExists=true;
        int accountNumber=0;
        Customer customer;
        while(customerExists){
            accountNumber=rand.nextInt(10000,99999);
            customer=getCustomer(accountNumber);
            if (customer == null){
                customerExists=false;
            }
        }

        customer=new Customer(nationalCode,nickName,accountNumber
                ,rand.nextInt(1000,9999));

        addCustomer(customer);
        return customer;
    }

    private void addCustomer(Customer customer){
        customers.add(customer);
    }
}

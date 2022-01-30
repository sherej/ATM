package simple_atm;

import java.util.Scanner;

public class ATM {

    public static void main(String [] args){
        Scanner scanner=new Scanner(System.in);
        int atmFirstOptions;
        Bank bank=new Bank();
        do{
            System.out.println("........Welcome.......");
            System.out.println("Please Enter");
            System.out.println("1 for Sign in to your account.");
            System.out.println("or");
            System.out.println("2 for Create an account.");

            atmFirstOptions=scanner.nextInt();

            if(atmFirstOptions == 1){
                System.out.println("Please enter account number" +
                            " or type 0 for back to previous menu" );
                int accountNumber=scanner.nextInt();
                if (accountNumber != 0){
                    Customer customer=bank.getCustomer(accountNumber);
                    if (customer!=null){
                        int chance=3;
                        while(chance > 0){
                            System.out.println("Please enter pin code");
                            int accountPinCode=scanner.nextInt();
                            if (customer.getBankAccount().getPinCode() == accountPinCode){
                                chance=openAccess(customer);

                            }else{
                                System.out.println("Wrong code...");
                                chance = chance -1;
                            }
                        }
                    }else{
                        System.out.println("Account not found");
                    }
                }else{
                    atmFirstOptions=0;
                }

            }else if (atmFirstOptions == 2){
                long nationalCode ;
                System.out.println("Please enter your national code.");
                nationalCode=scanner.nextLong();
                boolean customerExists=bank.checkNationalCode(nationalCode);
                if (customerExists){
                    System.out.println("This national code already hase an account");
                }else{
                    System.out.println("Please enter your nick name.");
                    String nickName=scanner.next();

                    Customer customer=bank.createCustomer(nationalCode,nickName);
                    if (customer != null){
                        System.out.println(nickName + " welcome to our bank." +
                                "  ** national code: " + nationalCode+
                                "  ** account number: " + customer.getBankAccount().getAccountNumber()+
                                "  ** pin code: " + customer.getBankAccount().getPinCode());
                    }
                }
            }

        }while(atmFirstOptions != -1);

        System.out.println("ATM turned off");
    }

    private static int openAccess(Customer customer) {
        boolean continues=true;

        Scanner scanner=new Scanner(System.in);

        while(continues ){

            System.out.println("1- View my balance ");
            System.out.println("2- Withdraw cash ");
            System.out.println("3- Deposit funds ");
            System.out.println("4- View the last ten transactions ");
            System.out.println("5- Exit");

            int operationCode=scanner.nextInt();
            if (operationCode==1){
                System.out.println("Your balance is: "+customer.getBankAccount().getBalance());

            }else if (operationCode==2){
                System.out.println("Enter value");
                int value=scanner.nextInt();
                System.out.println(customer.getBankAccount().withdrawalMoney(value));

            }else if (operationCode ==3){
                System.out.println("Enter value");
                int value =scanner.nextInt();
                customer.getBankAccount().depositMoney(value);
                System.out.println("deposited: "+value+"    and balance: "+customer.getBankAccount().getBalance());
            }else if (operationCode==4){
                System.out.println(customer.getBankAccount().getLastTenTransactions());

            }else if(operationCode == 5){
                break;
            }
        }
        return 0;
    }
}

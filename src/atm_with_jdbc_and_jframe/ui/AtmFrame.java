package atm_with_jdbc_and_jframe.ui;

import atm_with_jdbc_and_jframe.db.DataSource;
import atm_with_jdbc_and_jframe.db.entity.Account;
import atm_with_jdbc_and_jframe.db.entity.Customer;
import atm_with_jdbc_and_jframe.db.entity.Transaction;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

public class AtmFrame extends JFrame implements ActionListener, DocumentListener, FocusListener {//

    DataSource dataSource;

    private final Container container;

    public static final String  FIRST = "FIRST",
                                REGISTRATION = "REGISTRATION",
                                WITHDRAWAL = "WITHDRAWAL",
                                DEPOSIT = "DEPOSIT",
                                BALANCE = "BALANCE",
                                LASTTEN = "LASTTEN",
                                MENU = "MENU",
                                ACCOUNTINFORMATION = "ACCOUNTINFORMATION",
                                TEXTBOX_OPERATION_NUMBER = "number",
                                TEXTBOX_OPERATION_BACK = "back",
                                TEXTBOX_OPERATION_CLEAR = "clear";

    public String currentOperation = FIRST;

    private JDesktopPane signUpDisplay,
                         depositDisplay,
                         menuDisplay,
                         withdrawalDisplay,
                         balanceDisplay,
                         firstDisplay,
                         lastTenDisplay,
                         accountInformationDisplay;

    private JButton numKeyOne  , numKeyTwo     , numKeyThree     , numKeyFour  ,
                    numKeyFive , numKeySix     , numKeySeven     , numKeyEight ,
                    numKeyNine , numKeyTwoZero , numKeyThreeZero , numKeyZero  ,
                    numKeyBack , numKeyOk      , numKeyClear     , rightOne    ,
                    rightTwo   , rightThree    , rightFour       , leftOne     ,
                    leftTwo    , leftThree     , leftFour;

    private JLabel firstDisplayErrorMessage, paidMoney,registrationErrorMessage;

    private JTextField tAccountNumber, nickNameBox, nationalCodeBox, pineCodeBox, customWithdrawalBox, depositValueBox;

    private int chance;
    private String accountNumber = "";
    private String focused = "tAccountName";

    public AtmFrame(DataSource dataSource) {
        this.dataSource = dataSource;
        setTitle("ATM");
        setBounds(300, 90, 615, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        container = getContentPane();
        container.setLayout(null);
        container.setBackground(new Color(90, 90, 90));

        createFirstDisplay();

        createButtons();

        createNumKeyDisplay();

        setVisible(true);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (registrationErrorMessage!=null) registrationErrorMessage.setText("");
        if (firstDisplayErrorMessage!=null) firstDisplayErrorMessage.setText("");
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }

    public void createFirstDisplay() {
        accountNumber = "";
        currentOperation = FIRST;

        firstDisplay = new JDesktopPane();
        firstDisplay.setBounds(100, 10, 400, 300);
        firstDisplay.setBackground(new Color(10, 10, 200));
        container.add(firstDisplay);

        JLabel enterCard = new JLabel("Enter 5 digit account number");
        enterCard.setFont(new Font("Arial", Font.PLAIN, 25));
        enterCard.setBounds(40, 10, 400, 30);
        enterCard.setForeground(new Color(255, 255, 255));
        firstDisplay.add(enterCard);

        tAccountNumber = new JTextField();
        tAccountNumber.setFont(new Font("Arial", Font.PLAIN, 15));
        tAccountNumber.setBackground(new Color(255, 255, 255));
        tAccountNumber.setBounds(150, 60, 100, 20);
        tAccountNumber.getDocument().addDocumentListener(this);
        tAccountNumber.addFocusListener(this);
        tAccountNumber.setName("tAccountNumber");
        firstDisplay.add(tAccountNumber);

        JLabel pineCodeLabel = new JLabel("Pine code");
        pineCodeLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        pineCodeLabel.setBounds(140, 110, 400, 30);
        pineCodeLabel.setForeground(new Color(255, 255, 255));
        firstDisplay.add(pineCodeLabel);

        pineCodeBox = new JTextField();
        pineCodeBox.setFont(new Font("Arial", Font.PLAIN, 15));
        pineCodeBox.setBackground(new Color(255, 255, 255));
        pineCodeBox.setBounds(150, 150, 100, 20);
        pineCodeBox.getDocument().addDocumentListener(this);
        pineCodeBox.addFocusListener(this);
        pineCodeBox.setName("pineCodeBox");
        firstDisplay.add(pineCodeBox);


        JLabel tCreateAccount = new JLabel("Create Account");
        tCreateAccount.setFont(new Font("Arial", Font.PLAIN, 25));
        tCreateAccount.setForeground(new Color(255, 255, 255));
        tCreateAccount.setBounds(10, 250, 200, 30);
        firstDisplay.add(tCreateAccount);

        firstDisplayErrorMessage = new JLabel("");
        firstDisplayErrorMessage.setFont(new Font("Arial", Font.PLAIN, 20));
        firstDisplayErrorMessage.setForeground(new Color(255, 0, 0));
        firstDisplayErrorMessage.setBounds(10, 200, 380, 30);
        firstDisplay.add(firstDisplayErrorMessage);
    }

    public void createButtons() {
        leftOne = new JButton();
        leftOne.setBounds(29, 10, 70, 70);
        leftOne.setText(">");
        leftOne.addActionListener(this);
        container.add(leftOne);

        leftTwo = new JButton();
        leftTwo.setBounds(29, 85, 70, 70);
        leftTwo.setText(">");
        leftTwo.addActionListener(this);
        container.add(leftTwo);

        leftThree = new JButton();
        leftThree.setBounds(29, 160, 70, 70);
        leftThree.setText(">");
        leftThree.addActionListener(this);
        container.add(leftThree);

        leftFour = new JButton();
        leftFour.setBounds(29, 235, 70, 70);
        leftFour.setText(">");
        leftFour.addActionListener(this);
        container.add(leftFour);

        rightOne = new JButton();
        rightOne.setBounds(501, 10, 70, 70);
        rightOne.setText("<");
        rightOne.addActionListener(this);
        container.add(rightOne);

        rightTwo = new JButton();
        rightTwo.setBounds(501, 85, 70, 70);
        rightTwo.setText("<");
        rightTwo.addActionListener(this);
        container.add(rightTwo);

        rightThree = new JButton();
        rightThree.setBounds(501, 160, 70, 70);
        rightThree.setText("<");
        rightThree.addActionListener(this);
        container.add(rightThree);

        rightFour = new JButton();
        rightFour.setBounds(501, 235, 70, 70);
        rightFour.setText("<");
        rightFour.addActionListener(this);
        container.add(rightFour);
    }

    public void createNumKeyDisplay() {
        JPanel numberKeyboard = new JPanel(new GridLayout(4, 4));
        numberKeyboard.setBorder(null);
        numberKeyboard.setBounds(100, 350, 400, 300);
        numberKeyboard.setBackground(new Color(200, 200, 200));

        container.add(numberKeyboard);
        numKeyOne = new JButton();
        numKeyOne.setText("1");
        numKeyOne.addActionListener(this);

        numKeyTwo = new JButton();
        numKeyTwo.setText("2");
        numKeyTwo.addActionListener(this);

        numKeyThree = new JButton();
        numKeyThree.setText("3");
        numKeyThree.addActionListener(this);

        numKeyFour = new JButton();
        numKeyFour.setText("4");
        numKeyFour.addActionListener(this);

        numKeyFive = new JButton();
        numKeyFive.setText("5");
        numKeyFive.addActionListener(this);

        numKeySix = new JButton();
        numKeySix.setText("6");
        numKeySix.addActionListener(this);

        numKeySeven = new JButton();
        numKeySeven.setText("7");
        numKeySeven.addActionListener(this);

        numKeyEight = new JButton();
        numKeyEight.setText("8");
        numKeyEight.addActionListener(this);

        numKeyNine = new JButton();
        numKeyNine.setText("9");
        numKeyNine.addActionListener(this);

        numKeyTwoZero = new JButton();
        numKeyTwoZero.setText("00");
        numKeyTwoZero.addActionListener(this);

        numKeyThreeZero = new JButton();
        numKeyThreeZero.setText("000");
        numKeyThreeZero.addActionListener(this);

        numKeyZero = new JButton();
        numKeyZero.setText("0");
        numKeyZero.addActionListener(this);

        numKeyBack = new JButton();
        numKeyBack.setText("←");
        numKeyBack.addActionListener(this);

        numKeyOk = new JButton();
        numKeyOk.setText("OK");
        numKeyOk.addActionListener(this);

        numKeyClear = new JButton();
        numKeyClear.setText("CLEAR");
        numKeyClear.addActionListener(this);

        numberKeyboard.add(numKeyOne);
        numberKeyboard.add(numKeyTwo);
        numberKeyboard.add(numKeyThree);
        numberKeyboard.add(numKeyBack);
        numberKeyboard.add(numKeyFour);
        numberKeyboard.add(numKeyFive);
        numberKeyboard.add(numKeySix);
        numberKeyboard.add(new JButton());
        numberKeyboard.add(numKeySeven);
        numberKeyboard.add(numKeyEight);
        numberKeyboard.add(numKeyNine);
        numberKeyboard.add(numKeyOk);
        numberKeyboard.add(numKeyTwoZero);
        numberKeyboard.add(numKeyZero);
        numberKeyboard.add(numKeyThreeZero);
        numberKeyboard.add(numKeyClear);
    }

    public void createSignUpDisplay() {
        currentOperation = REGISTRATION;
        signUpDisplay = new JDesktopPane();
        signUpDisplay.setBounds(100, 10, 400, 300);
        signUpDisplay.setBackground(new Color(10, 10, 200));
        container.add(signUpDisplay);

        JLabel nationalCodeLabel = new JLabel("National Code");
        nationalCodeLabel.setBounds(120, 10, 200, 30);
        nationalCodeLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        nationalCodeLabel.setForeground(new Color(255, 255, 255));
        signUpDisplay.add(nationalCodeLabel);

        nationalCodeBox = new JTextField();
        nationalCodeBox.setBounds(100, 40, 200, 30);
        nationalCodeBox.setFont(new Font("Arial", Font.PLAIN, 25));
        nationalCodeBox.addFocusListener(this);
        nationalCodeBox.setName("nationalCodeBox");
        nationalCodeBox.getDocument().addDocumentListener(this);
        signUpDisplay.add(nationalCodeBox);

        JLabel nickNameLabel = new JLabel("Nick name");
        nickNameLabel.setBounds(140, 100, 150, 30);
        nickNameLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        nickNameLabel.setForeground(new Color(255, 255, 255));
        signUpDisplay.add(nickNameLabel);

        nickNameBox = new JTextField();
        nickNameBox.setBounds(100, 140, 200, 30);
        nickNameBox.setFont(new Font("Arial", Font.PLAIN, 25));
        nickNameBox.addFocusListener(this);
        nickNameBox.setName("nickNameBox");
        signUpDisplay.add(nickNameBox);

        registrationErrorMessage = new JLabel("");
        registrationErrorMessage.setFont(new Font("Arial", Font.PLAIN, 20));
        registrationErrorMessage.setForeground(new Color(255, 0, 0));
        registrationErrorMessage.setBounds(10, 200, 380, 30);
        signUpDisplay.add(registrationErrorMessage);

        JLabel back = new JLabel("Back");
        back.setBounds(350, 240, 200, 30);
        back.setFont(new Font("Arial", Font.PLAIN, 20));
        back.setForeground(new Color(255, 255, 255));
        signUpDisplay.add(back);

    }

    public void createMenuDisplay() {
        currentOperation = MENU;
        menuDisplay = new JDesktopPane();
        menuDisplay.setBounds(100, 10, 400, 300);
        menuDisplay.setBackground(new Color(10, 10, 200));
        container.add(menuDisplay);

        JLabel balance = new JLabel("Balance");
        balance.setBounds(10, 25, 200, 30);
        balance.setFont(new Font("Arial", Font.PLAIN, 20));
        balance.setForeground(new Color(255, 255, 255));
        menuDisplay.add(balance);

        JLabel depositMoney = new JLabel("Deposit money");
        depositMoney.setBounds(10, 95, 200, 30);
        depositMoney.setFont(new Font("Arial", Font.PLAIN, 20));
        depositMoney.setForeground(new Color(255, 255, 255));
        menuDisplay.add(depositMoney);

        JLabel Withdrawal = new JLabel("Withdrawal");
        Withdrawal.setBounds(10, 165, 200, 30);
        Withdrawal.setFont(new Font("Arial", Font.PLAIN, 20));
        Withdrawal.setForeground(new Color(255, 255, 255));
        menuDisplay.add(Withdrawal);

        JLabel lastTen = new JLabel("Last ten transactions");
        lastTen.setBounds(210, 23, 200, 30);
        lastTen.setFont(new Font("Arial", Font.PLAIN, 20));
        lastTen.setForeground(new Color(255, 255, 255));
        menuDisplay.add(lastTen);

        JLabel exit = new JLabel("Exit");
        exit.setBounds(350, 240, 200, 30);
        exit.setFont(new Font("Arial", Font.PLAIN, 20));
        exit.setForeground(new Color(255, 255, 255));
        menuDisplay.add(exit);

    }

    public void createBalanceDisplay() {
        currentOperation = BALANCE;
        balanceDisplay = new JDesktopPane();
        balanceDisplay.setBounds(100, 10, 400, 300);
        balanceDisplay.setBackground(new Color(10, 10, 200));
        container.add(balanceDisplay);
        Account account = dataSource.findAccountByAccountNumber(Long.parseLong(accountNumber));

        JLabel balanceTitle = new JLabel("Your balance");
        balanceTitle.setBounds(120, 100, 200, 20);
        balanceTitle.setFont(new Font("Arial", Font.PLAIN, 25));
        balanceTitle.setForeground(new Color(255, 255, 255));
        balanceDisplay.add(balanceTitle);

        JLabel balanceValue = new JLabel(String.valueOf(account.getBalance()));
        balanceValue.setBounds(170, 150, 200, 20);
        balanceValue.setFont(new Font("Arial", Font.PLAIN, 25));
        balanceValue.setForeground(new Color(255, 255, 255));
        balanceDisplay.add(balanceValue);

        JLabel back = new JLabel("Back");
        back.setBounds(350, 240, 200, 30);
        back.setFont(new Font("Arial", Font.PLAIN, 20));
        back.setForeground(new Color(255, 255, 255));
        balanceDisplay.add(back);

    }

    public void createLastTenDisplay() {
        currentOperation = LASTTEN;
        lastTenDisplay = new JDesktopPane();
        lastTenDisplay.setBounds(100, 10, 400, 300);
        lastTenDisplay.setBackground(new Color(10, 10, 200));
        container.add(lastTenDisplay);
        int y = 5;
        List<Transaction> transactions = dataSource.getLastTenTransactions(Long.parseLong(accountNumber));

        for (int i = 0; i < transactions.size(); i++) {
            int index = i + 1;
            JLabel transactionLabel = new JLabel(String.valueOf(index) + ": " + transactions.get(i).toString());
            transactionLabel.setBounds(20, y, 380, 20);
            transactionLabel.setFont(new Font("Arial", Font.BOLD, 12));
            transactionLabel.setForeground(new Color(255, 255, 255));
            lastTenDisplay.add(transactionLabel);
            y = y + 22;
        }

        JLabel back = new JLabel("Back");
        back.setBounds(350, 240, 200, 30);
        back.setFont(new Font("Arial", Font.PLAIN, 20));
        back.setForeground(new Color(255, 255, 255));
        lastTenDisplay.add(back);
    }

    public void createWithdrawDisplay() {
        currentOperation = WITHDRAWAL;
        withdrawalDisplay = new JDesktopPane();
        withdrawalDisplay.setBounds(100, 10, 400, 300);
        withdrawalDisplay.setBackground(new Color(10, 10, 200));
        container.add(withdrawalDisplay);

        JLabel fifty = new JLabel("50");
        fifty.setBounds(10, 25, 200, 30);
        fifty.setFont(new Font("Arial", Font.PLAIN, 20));
        fifty.setForeground(new Color(255, 255, 255));
        withdrawalDisplay.add(fifty);

        JLabel hundred = new JLabel("100");
        hundred.setBounds(350, 23, 200, 30);
        hundred.setFont(new Font("Arial", Font.PLAIN, 20));
        hundred.setForeground(new Color(255, 255, 255));
        withdrawalDisplay.add(hundred);

        JLabel hundredFifty = new JLabel("150");
        hundredFifty.setBounds(10, 95, 200, 30);
        hundredFifty.setFont(new Font("Arial", Font.PLAIN, 20));
        hundredFifty.setForeground(new Color(255, 255, 255));
        withdrawalDisplay.add(hundredFifty);

        JLabel twoHundred = new JLabel("200");
        twoHundred.setBounds(350, 95, 200, 30);
        twoHundred.setFont(new Font("Arial", Font.PLAIN, 20));
        twoHundred.setForeground(new Color(255, 255, 255));
        withdrawalDisplay.add(twoHundred);

        customWithdrawalBox = new JTextField();
        customWithdrawalBox.setBounds(75, 130, 250, 30);
        customWithdrawalBox.setFont(new Font("Arial", Font.PLAIN, 20));
        customWithdrawalBox.addFocusListener(this);
        customWithdrawalBox.setName("customWithdrawalBox");
        withdrawalDisplay.add(customWithdrawalBox);

        paidMoney = new JLabel("");
        paidMoney.setFont(new Font("Arial", Font.PLAIN, 20));
        withdrawalDisplay.add(paidMoney);

        JLabel back = new JLabel("Back");
        back.setBounds(350, 240, 200, 30);
        back.setFont(new Font("Arial", Font.PLAIN, 20));
        back.setForeground(new Color(255, 255, 255));
        withdrawalDisplay.add(back);
    }

    public void createDepositDisplay() {
        currentOperation = DEPOSIT;
        depositDisplay = new JDesktopPane();
        depositDisplay.setBounds(100, 10, 400, 300);
        depositDisplay.setBackground(new Color(10, 10, 200));
        container.add(depositDisplay);

        depositValueBox = new JTextField();
        depositValueBox.setBounds(75, 100, 250, 30);
        depositValueBox.setFont(new Font("Arial", Font.PLAIN, 20));
        depositValueBox.addFocusListener(this);
        depositValueBox.setName("depositValueBox");
        depositDisplay.add(depositValueBox);

        JLabel back = new JLabel("Back");
        back.setBounds(350, 240, 200, 30);
        back.setFont(new Font("Arial", Font.PLAIN, 20));
        back.setForeground(new Color(255, 255, 255));
        depositDisplay.add(back);
    }

    private void createAccountInformationDisplay(Account account) {
        System.out.println("createAccountInformationDisplay");
        currentOperation = ACCOUNTINFORMATION;
        if (signUpDisplay != null) {
            container.remove(signUpDisplay);
        }
        container.repaint();
        accountInformationDisplay = new JDesktopPane();
        accountInformationDisplay.setBounds(100, 10, 400, 300);
        accountInformationDisplay.setBackground(new Color(10, 10, 200));
        container.add(accountInformationDisplay);

        JLabel accountNumberTitle = new JLabel("Account Number");
        accountNumberTitle.setBounds(120, 10, 200, 30);
        accountNumberTitle.setFont(new Font("Arial", Font.PLAIN, 20));
        accountNumberTitle.setForeground(new Color(255, 255, 255));
        accountInformationDisplay.add(accountNumberTitle);

        JLabel accountNumber = new JLabel(String.valueOf(account.getAccountNumber()));
        accountNumber.setBounds(165, 50, 200, 30);
        accountNumber.setFont(new Font("Arial", Font.PLAIN, 20));
        accountNumber.setForeground(new Color(255, 255, 255));
        accountInformationDisplay.add(accountNumber);

        JLabel pinCodeTitle = new JLabel("Pin Code");
        pinCodeTitle.setBounds(155, 110, 200, 30);
        pinCodeTitle.setFont(new Font("Arial", Font.PLAIN, 20));
        pinCodeTitle.setForeground(new Color(255, 255, 255));
        accountInformationDisplay.add(pinCodeTitle);

        JLabel pinCode = new JLabel(account.getPinCode());
        pinCode.setBounds(170, 150, 200, 30);
        pinCode.setFont(new Font("Arial", Font.PLAIN, 20));
        pinCode.setForeground(new Color(255, 255, 255));
        accountInformationDisplay.add(pinCode);

        JLabel back = new JLabel("Back");
        back.setBounds(350, 240, 200, 30);
        back.setFont(new Font("Arial", Font.PLAIN, 20));
        back.setForeground(new Color(255, 255, 255));
        accountInformationDisplay.add(back);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == leftOne) {
            if (currentOperation.contentEquals(MENU)) {
                recreateDisplay(BALANCE);
            } else if (currentOperation.contentEquals(WITHDRAWAL)) {
                withdrawal("50");
            }
        } else if (source == leftTwo) {
            if (currentOperation.contentEquals(MENU)) {
                recreateDisplay(DEPOSIT);
            } else if (currentOperation.contentEquals(WITHDRAWAL)) {

                withdrawal("150");
            }
        } else if (source == leftThree) {
            if (currentOperation.contentEquals(MENU)) {
                recreateDisplay(WITHDRAWAL);
            }
        } else if (source == leftFour) {
            if (currentOperation.contentEquals(FIRST)) {
                recreateDisplay(REGISTRATION);
            }
        } else if (source == rightOne) {
            if (currentOperation.contentEquals(MENU)) {
                recreateDisplay(LASTTEN);
            } else if (currentOperation.contentEquals(WITHDRAWAL)) {
                withdrawal("100");
            }
        } else if (source == rightTwo) {
            if (currentOperation.contentEquals(WITHDRAWAL)) {

                withdrawal("200");
            }
        } else if (source == rightThree) {

        } else if (source == rightFour) {
            if (currentOperation.contentEquals(MENU)) {
                recreateDisplay(FIRST);
            } else if (currentOperation.contentEquals(DEPOSIT) || currentOperation.contentEquals(WITHDRAWAL)
                    || currentOperation.contentEquals(BALANCE) || currentOperation.contentEquals(LASTTEN)) {
                recreateDisplay(MENU);
            } else if (currentOperation.contentEquals(REGISTRATION) || currentOperation.contentEquals(ACCOUNTINFORMATION)) {
                recreateDisplay(FIRST);
            }
        } else if (source == numKeyOne) {
            textBoxOperation("1", TEXTBOX_OPERATION_NUMBER);
        } else if (source == numKeyTwo) {
            textBoxOperation("2", TEXTBOX_OPERATION_NUMBER);
        } else if (source == numKeyThree) {
            textBoxOperation("3", TEXTBOX_OPERATION_NUMBER);
        } else if (source == numKeyFour) {
            textBoxOperation("4", TEXTBOX_OPERATION_NUMBER);
        } else if (source == numKeyFive) {
            textBoxOperation("5", TEXTBOX_OPERATION_NUMBER);
        } else if (source == numKeySix) {
            textBoxOperation("6", TEXTBOX_OPERATION_NUMBER);
        } else if (source == numKeySeven) {
            textBoxOperation("7", TEXTBOX_OPERATION_NUMBER);
        } else if (source == numKeyEight) {
            textBoxOperation("8", TEXTBOX_OPERATION_NUMBER);
        } else if (source == numKeyNine) {
            textBoxOperation("9", TEXTBOX_OPERATION_NUMBER);
        } else if (source == numKeyZero) {
            textBoxOperation("0", TEXTBOX_OPERATION_NUMBER);
        } else if (source == numKeyTwoZero) {
            textBoxOperation("00", TEXTBOX_OPERATION_NUMBER);
        } else if (source == numKeyThreeZero) {
            textBoxOperation("000", TEXTBOX_OPERATION_NUMBER);
        } else if (source == numKeyBack) {
            textBoxOperation("", TEXTBOX_OPERATION_BACK);
        } else if (source == numKeyClear) {
            textBoxOperation("", TEXTBOX_OPERATION_CLEAR);
        } else if (source == numKeyOk) {
            if (currentOperation.contentEquals(FIRST)) {
                signin();
            } else if (currentOperation.contentEquals(REGISTRATION)) {
                createAccount();
            } else if (currentOperation.contentEquals(DEPOSIT)) {
                long depositValue = Long.parseLong(depositValueBox.getText());
                dataSource.depositOrWithdraw(Long.parseLong(accountNumber), depositValue, DEPOSIT);
                recreateDisplay(MENU);
            } else if (currentOperation.contentEquals(WITHDRAWAL)) {
                String value = "";
                if (customWithdrawalBox != null) value = customWithdrawalBox.getText();
                withdrawal(value);
            }
        }
    }

    private void signin() {
        String pinCode = pineCodeBox != null ? pineCodeBox.getText():"";
        accountNumber = tAccountNumber != null ? tAccountNumber.getText():"";
        Account account = dataSource.findAccountByAccountNumber(Long.parseLong(accountNumber));
        if (account != null) {
            System.out.println("accountNumber: " + accountNumber);
            if (chance < 4) {
                if (dataSource.checkAccountsPin(Long.parseLong(accountNumber), pinCode)) {
                    pineCodeBox.setText("");
                    tAccountNumber.setText("");
                    chance = 0;
                    recreateDisplay(MENU);
                } else {
                    pineCodeBox.setText("");
                    chance++;
                    if (firstDisplayErrorMessage != null) firstDisplayErrorMessage.setText("The pin code is incorrect");
                    System.out.println("chance: " + chance + "  pin is wrong");
                    if (chance >= 3) {
                        accountNumber = "";
                        tAccountNumber.setText("");
                        chance = 0;
                        System.out.println("use 3 chance");
                        if (firstDisplayErrorMessage != null) firstDisplayErrorMessage.setText("You entered the wrong PIN code 3 times");
                    }
                }
            }
        } else {
            if (firstDisplayErrorMessage != null) firstDisplayErrorMessage.setText("This account number does not exists");
            System.out.println("This account number does not exists");
        }
    }

    private void withdrawal(String value) {
        String message = dataSource.depositOrWithdraw(Long.parseLong(accountNumber), Long.parseLong(value), WITHDRAWAL);
        if (paidMoney != null) {
            if (message.contentEquals("Success")) {
                paidMoney.setForeground(new Color(255, 255, 255));
                paidMoney.setBounds(100, 185, 200, 30);
                paidMoney.setText(value + " was paid");
            } else {
                paidMoney.setForeground(new Color(255, 0, 0));
                paidMoney.setBounds(80, 185, 300, 30);
                paidMoney.setText(message);
            }
        }
    }

    private void createAccount() {
        long nationalCode = Long.parseLong(nationalCodeBox.getText());
        String fullName = nickNameBox.getText();
        long customerId = dataSource.insertCustomer(new Customer(nationalCode, fullName));
        if (customerId > 0) {
            Account account = dataSource.insertAccount(customerId);
            if (nationalCodeBox != null) nationalCodeBox.setText("");
            if (nickNameBox != null) nickNameBox.setText("");
            System.out.println("Account: " + account.toString());
            createAccountInformationDisplay(account);
        } else {
            if (registrationErrorMessage != null) registrationErrorMessage.setText("This national code already has an account");
            System.out.println("This national code already has an account");
        }
    }

    private void recreateDisplay(String operation) {
        if (container != null) {
            if (firstDisplay != null) container.remove(firstDisplay);
            if (menuDisplay != null) container.remove(menuDisplay);
            if (depositDisplay != null) container.remove(depositDisplay);
            if (withdrawalDisplay != null) container.remove(withdrawalDisplay);
            if (balanceDisplay != null) container.remove(balanceDisplay);
            if (depositDisplay != null) container.remove(depositDisplay);
            if (lastTenDisplay != null) container.remove(lastTenDisplay);
            if (signUpDisplay != null) container.remove(signUpDisplay);
            if (accountInformationDisplay != null) container.remove(accountInformationDisplay);
            container.repaint();
        }
        switch (operation) {
            case FIRST -> createFirstDisplay();
            case REGISTRATION -> createSignUpDisplay();
            case MENU -> createMenuDisplay();
            case WITHDRAWAL -> createWithdrawDisplay();
            case DEPOSIT -> createDepositDisplay();
            case LASTTEN -> createLastTenDisplay();
            case BALANCE -> createBalanceDisplay();
        }
    }

    public void textBoxOperation(String number, String request) {
        String text = "";
        int length;
        if (request.contentEquals(TEXTBOX_OPERATION_CLEAR)) {
            if (tAccountNumber != null) tAccountNumber.setText("");
            if (pineCodeBox != null) pineCodeBox.setText("");
            if (customWithdrawalBox != null) customWithdrawalBox.setText("");
            if (depositValueBox != null) depositValueBox.setText("");
            if (nickNameBox != null) nickNameBox.setText("");
            if (nationalCodeBox != null) nationalCodeBox.setText("");
            if (firstDisplayErrorMessage != null) firstDisplayErrorMessage.setText("");
            if (paidMoney != null) paidMoney.setText("");
            if (registrationErrorMessage != null) registrationErrorMessage.setText("");
        } else {
            if (currentOperation.contentEquals(FIRST)) {
                if (focused.contentEquals("tAccountNumber")) {
                    if (tAccountNumber != null) {
                        text = tAccountNumber.getText();
                        length = text.length();
                        if (request.contentEquals(TEXTBOX_OPERATION_NUMBER)) {
                            tAccountNumber.setText(text + number);
                        } else if (request.contentEquals(TEXTBOX_OPERATION_BACK)) {
                            tAccountNumber.setText(length > 0 ? text.substring(0, length - 1) : "");
                        }
                    }
                } else if (focused.contentEquals("pineCodeBox")) {
                    if (pineCodeBox != null) {
                        text = pineCodeBox.getText();
                        length = text.length();
                        if (request.contentEquals(TEXTBOX_OPERATION_NUMBER)) {
                            pineCodeBox.setText(pineCodeBox.getText() + number);
                        } else if (request.contentEquals(TEXTBOX_OPERATION_BACK)) {
                            pineCodeBox.setText(length > 0 ? text.substring(0, length - 1) : "");
                        }
                    }
                }
            } else if (currentOperation.contentEquals(REGISTRATION)) {
                if (focused.contentEquals("nationalCodeBox")) {
                    if (nationalCodeBox != null) {
                        text = nationalCodeBox.getText();
                        length = text.length();
                        if (request.contentEquals(TEXTBOX_OPERATION_NUMBER)) {
                            nationalCodeBox.setText(nationalCodeBox.getText() + number);
                        } else if (request.contentEquals(TEXTBOX_OPERATION_BACK)) {
                            nationalCodeBox.setText(length > 0 ? text.substring(0, length - 1) : "");
                        }
                    }
                } else if (focused.contentEquals("nickNameBox")) {
                    if (nickNameBox != null) {
                        text = nickNameBox.getText();
                        length = text.length();
                        if (request.contentEquals(TEXTBOX_OPERATION_NUMBER)) {
                            nickNameBox.setText(nickNameBox.getText() + number);
                        } else if (request.contentEquals(TEXTBOX_OPERATION_BACK)) {
                            nickNameBox.setText(length > 0 ? text.substring(0, length - 1) : "");
                        }
                    }
                }
            } else if (currentOperation.contentEquals(DEPOSIT)) {
                if (focused.contentEquals("depositValueBox")) {
                    if (depositValueBox != null) {
                        text = depositValueBox.getText();
                        length = text.length();
                        if (request.contentEquals(TEXTBOX_OPERATION_NUMBER)) {
                            depositValueBox.setText(depositValueBox.getText() + number);
                        } else if (request.contentEquals(TEXTBOX_OPERATION_BACK)) {
                            depositValueBox.setText(length > 0 ? text.substring(0, length - 1) : "");
                        }
                    }
                }
            } else if (currentOperation.contentEquals(WITHDRAWAL)) {
                if (focused.contentEquals("customWithdrawalBox")) {
                    if (customWithdrawalBox != null) {
                        text = customWithdrawalBox.getText();
                        length = text.length();
                        if (request.contentEquals(TEXTBOX_OPERATION_NUMBER)) {
                            customWithdrawalBox.setText(customWithdrawalBox.getText() + number);
                        } else if (request.contentEquals(TEXTBOX_OPERATION_BACK)) {
                            customWithdrawalBox.setText(length > 0 ? text.substring(0, length - 1) : "");
                        }
                    }
                }
            }
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        focused = e.getComponent().getName();
    }

    @Override
    public void focusLost(FocusEvent e) {

    }
}

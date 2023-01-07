package net.froihofer.dsfinance.bank.client.utils;

import at.ac.csdc23vz_02.common.*;
import at.ac.csdc23vz_02.common.exceptions.BankServerException;
import net.froihofer.util.AuthCallbackHandler;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class UserInterface {

    private BankServer bankServer;
    private final Scanner in = new Scanner(System.in);
    private UserType userType = UserType.CUSTOMER;
    private Person loggedInUser;

    public UserInterface() {
    }

    public List<String> startLogin() {
        List<String> credentials = new ArrayList<>();
        setModuleHeadline("====== WELCOME ======");
        setModuleHeadline("Login");
        credentials.add(showInputElement("Username"));
        credentials.add(showInputElement("Password"));
        return credentials;
    }

    public void init(BankServer bankServer, List<String> credentials) throws BankServerException {
        this.bankServer = bankServer;
        if(login(credentials)) {
            showMainMenu();
        }
    }


    private void startRegisterProcess() {
        Customer customer = new Customer();
        Employee employee = new Employee();
        setModuleHeadline("Register");
        int output = showMenu(Arrays.asList("Customer", "Employee"));
        if(output == 1) {
            customer.setPerson(inputPerson());
            try {
                bankServer.createCustomer(customer);
                showResponseMessage("User Created.", MessageType.SUCCESS);
                showMainMenu();
            } catch (BankServerException e) {
                showResponseMessage("Something went wrong while trying to create Customer. Please see Stack Trace.", MessageType.ERROR);
                e.printStackTrace();
            }
        } else {
            employee.setPerson(inputPerson());
            try {
                bankServer.createEmployee(employee);
                showResponseMessage("User Created.", MessageType.SUCCESS);
                showMainMenu();
            } catch (BankServerException e) {
                showResponseMessage("Something went wrong while trying to create Customer. Please see Stack Trace.", MessageType.ERROR);
                e.printStackTrace();
            }
        }
    }

    private boolean login(List<String> credentials){
        try {
            int response = bankServer.login(credentials);
            if (response == 1) {
                //CUSTOMER_SUCCESS
                this.userType = UserType.CUSTOMER;
                this.loggedInUser = bankServer.getLoggedInUser();
                showResponseMessage("Successful Login!", MessageType.SUCCESS);
                showMainMenu();
                return true;
            } else if (response == 2){
                //EMPLOYEE_SUCCESS
                this.userType = UserType.EMPLOYEE;
                this.loggedInUser = bankServer.getLoggedInUser();
                showResponseMessage("Successful Login!", MessageType.SUCCESS);
                showMainMenu();
                return true;
            } else if (response == 3) {
                //LOGIN_FAILURE
                showResponseMessage("Wrong Password or Username!", MessageType.ERROR);
                return false;
            }
        } catch (BankServerException bankServerException) {
            showResponseMessage(bankServerException.getMessage(), MessageType.ERROR);
        }
        return false;
    }

    private void showMainMenu() throws BankServerException {

        if(this.userType == UserType.CUSTOMER) {
            //User is customer
            setModuleHeadline("=== Main Menu for Customers ===");
            int output = showMenu(Arrays.asList("Search available share",
                    "Buy Share",
                    "Sell Share",
                    "Show Depot",
                    "Manage personal Data",
                    "Logout"
                    ));
            switch (output) {
                case 1: searchAvailableShare(); break;
                case 2: buyShare(); break;
                case 3: sellShare(); break;
                case 4: showDepot(); break;
                case 5: managePersonalData(); break;
                case 6: logout(); break;
            }
        } else if(this.userType == UserType.EMPLOYEE){
            setModuleHeadline("=== Main Menu for Employees ===");
            int output = showMenu(Arrays.asList(
                    "Create Customer or Employee",
                    "Search Customer",
                    "Search Available share",
                    "Buy Share for Customer",
                    "Sell Share for Customer",
                    "Show Depot for Customer",
                    "Show available Budget from Bank at the Stock Exchange"));
            switch (output) {
                case 1: startRegisterProcess(); break;
                case 2: searchCustomer(); break;
                case 3: searchAvailableShare(); break;
                case 4: buyShareforCustomer(); break;
                case 5: sellShareforCustomer(); break;
                case 6: showDepotForCustomer(); break;
                case 7: showAvailableBudget(); break;
            }
        }
    }

    private void showAvailableBudget() {
    }

    private void showDepotForCustomer() throws BankServerException {
        setModuleHeadline("Show Depot for Customer");
        int id = Integer.parseInt(showInputElement("ID of Customer"));
        printDepot(id);
        endOfModuleChoices();
    }

    private List<Transaction> printDepot(int id) {
        Customer customer = bankServer.search_customer_with_id(id);
        List<Transaction> transactions = new ArrayList<>();
        if(!(customer.getId() == 0)) {
            showResponseMessage("Depot for " + customer.getFullName() + ":", MessageType.INFO);
            transactions = bankServer.listDepot(id);
            showTransactionsList(transactions);
            return transactions;
        } else {
            showResponseMessage("User was not found!", MessageType.ERROR);
        }
        return transactions;
    }

    private void sellShareforCustomer() throws BankServerException{
        setModuleHeadline("Sell Share for Customer");
        int id = Integer.parseInt(showInputElement("ID of Customer"));
        List<Transaction> transactions = printDepot(id);
        int transactionID = Integer.parseInt(showInputElement("Input ID of Share you want to sell"));
        int sharesToSell = Integer.parseInt(showInputElement("Amount of shares you want to sell"));
        int finalTransactionID = transactionID;
        Transaction transaction = transactions.stream()
                .filter(transaction1 -> transaction1.getID() == finalTransactionID)
                .findAny()
                .orElse(null);
        while (transaction == null) {
            showResponseMessage("No Transaction with the following ID found!", MessageType.ERROR);
            transactionID = Integer.parseInt(showInputElement("Input ID of Share you want to sell"));
            int finalTransactionID1 = transactionID;
            transaction = transactions.stream()
                    .filter(transaction1 -> finalTransactionID1 == transaction1.getID())
                    .findAny()
                    .orElse(null);
        }

        while (transaction.getShareCount() < sharesToSell) {
            showResponseMessage("You can only sell as much shares as you own.", MessageType.ERROR);
            sharesToSell = Integer.parseInt(showInputElement("Amount of shares you want to sell"));
        }
        BigDecimal success = bankServer.sell_for_customer(transaction.getStocksymbol() , id, sharesToSell, transactionID);

        if(success.intValue()>=0) {
            showResponseMessage("Sold share for " + sharesToSell + " x €" + success.intValue(), MessageType.SUCCESS);
        } else {
            showResponseMessage("Failed to sell share", MessageType.ERROR);
        }
        printDepot(id);
        endOfModuleChoices();
    }

    private void buyShareforCustomer() throws BankServerException {
        setModuleHeadline("Buy Share for Customer");
        String input = showInputElement("Share Symbol");
        int input1 = Integer.parseInt(showInputElement("User ID"));
        int input2 = Integer.parseInt(showInputElement("Amount"));

        BigDecimal success = bankServer.buy_for_customer(input,input1,input2);

        if(success.intValue()>=0) {
            showResponseMessage("Bought Share for " + input2 + " x €" + success.intValue(), MessageType.SUCCESS);
        } else {
            showResponseMessage("Failed to buy share", MessageType.ERROR);
        }
        endOfModuleChoices();
    }

    private void searchCustomer() throws BankServerException {
        setModuleHeadline("Search Customer");
        int output = showMenu(Arrays.asList(
                "Search by ID",
                "Search by Name"
        ));
        if(output == 1) {
            showResponseMessage("Only enter Integer Numbers!", MessageType.INFO);
            int id = Integer.parseInt(showInputElement("ID"));
            Customer customer = bankServer.search_customer_with_id(id);
            if(customer.getId() == 0) {
                showResponseMessage("No User Found!", MessageType.ERROR);
            } else {
                showResponseMessage("Customer found.", MessageType.SUCCESS);
                Person person = new Person(customer.getFirstName(), customer.getLastName(), customer.getUserName(), null);
                person.setId(customer.getId());
                showUserData(person);
            }
        } else {
            showResponseMessage("Please enter the Name", MessageType.INFO);
            String firstname = showInputElement("First Name");
            String lastname = showInputElement("Last Name");
            List<Customer> customers = bankServer.search_customer_with_name(firstname, lastname);
            if(customers.get(0).getId() == 0) {
                showResponseMessage("No User Found!", MessageType.ERROR);
            } else {
                showResponseMessage("Customers Found!", MessageType.SUCCESS);
                for (Customer customer : customers) {
                    Person person = new Person(customer.getFirstName(), customer.getLastName(), customer.getUserName(), null);
                    person.setId(customer.getId());
                    showUserData(person);
                }
            }
        }
        endOfModuleChoices();
    }

    private void logout() throws BankServerException {
        setModuleHeadline("Logout");
        showResponseMessage("Not implemented yet!", MessageType.ERROR);
        endOfModuleChoices();
    }

    private void managePersonalData() throws BankServerException {
        setModuleHeadline("Manage Personal Data");
        showUserData(this.loggedInUser);
        showResponseMessage("Available Options", MessageType.INFO);
        int output = showMenu(Arrays.asList(
                "Update Personal Information",
                "Set new Password",
                "Return to main menu"
        ));
        switch (output) {
            case 1: updatePersonalInformation(); break;
            case 2: resetPassword(); break;
            case 3: showMainMenu(); break;
        }
        endOfModuleChoices();
    }

    private void resetPassword() {
        String newPwFirst = showInputElement("new password");
        String newPwSecond = showInputElement("Repeat new password");

        while(!newPwFirst.equals(newPwSecond)) {
            showResponseMessage("Passwords do not match!", MessageType.ERROR);
            newPwFirst = showInputElement("new password");
            newPwSecond = showInputElement("Repeat new password");
        }
    }

    private void updatePersonalInformation() throws BankServerException {
        showResponseMessage("Update Personal Information", MessageType.INFO);
        int output = showMenu(Arrays.asList(
                "Update First Name",
                "Update Last Name"
        ));
        if(output == 1) {
            //Update First Name
            showResponseMessage("Change Last Name", MessageType.INFO);
            String newFirstName = showInputElement("New first Name");
            while(!checkIfInputWasCorrect()) {
                showResponseMessage("Change Last Name", MessageType.INFO);
                newFirstName = showInputElement("New first Name");
            }
            this.loggedInUser.setFirstName(newFirstName);
            bankServer.updateUser(this.loggedInUser);
        } else if(output == 2) {
            //Update Last Name
            showResponseMessage("Change Last Name", MessageType.INFO);
            String newLastName = showInputElement("New last Name");
            while(!checkIfInputWasCorrect()) {
                showResponseMessage("Change Last Name", MessageType.INFO);
                newLastName = showInputElement("New last Name");
            }
            this.loggedInUser.setLastName(newLastName);
            bankServer.updateUser(this.loggedInUser);
        }
    }

    private void showDepot() throws BankServerException {
        setModuleHeadline("Show personal Depot");
        showResponseMessage("Depot for " + loggedInUser.getFullName() + ":", MessageType.INFO);
        List<Transaction> transactionList = bankServer.listDepot();
        showTransactionsList(transactionList);
        endOfModuleChoices();
    }

    private void sellShare() throws BankServerException {
        setModuleHeadline("Sell Share");
        showResponseMessage("Depot for " + loggedInUser.getFullName() + ":", MessageType.INFO);
        List<Transaction> transactionList = bankServer.listDepot();
        showTransactionsList(transactionList);
        int transactionID = Integer.parseInt(showInputElement("Input ID of Share you want to sell"));
        int sharesToSell = Integer.parseInt(showInputElement("Amount of shares you want to sell"));
        int finalTransactionID = transactionID;
        Transaction transaction = transactionList.stream()
                .filter(transaction1 -> transaction1.getID() == finalTransactionID)
                .findAny()
                .orElse(null);
        while (transaction == null) {
            showResponseMessage("No Transaction with the following ID found!", MessageType.ERROR);
            transactionID = Integer.parseInt(showInputElement("Input ID of Share you want to sell"));
            int finalTransactionID1 = transactionID;
            transaction = transactionList.stream()
                    .filter(transaction1 -> finalTransactionID1 == transaction1.getID())
                    .findAny()
                    .orElse(null);
        }

        while (transaction.getShareCount() < sharesToSell) {
            showResponseMessage("You can only sell as much shares as you own.", MessageType.ERROR);
            sharesToSell = Integer.parseInt(showInputElement("Amount of shares you want to sell"));
        }
        BigDecimal success = bankServer.sell(transaction.getStocksymbol(), sharesToSell, transactionID);

        if(success.intValue()>=0) {
            showResponseMessage("Sold share for " + sharesToSell + " x €" + success.intValue(), MessageType.SUCCESS);
        } else {
            showResponseMessage("Failed to sell share", MessageType.ERROR);
        }

        transactionList = bankServer.listDepot();
        showTransactionsList(transactionList);

        endOfModuleChoices();
    }

    private void buyShare() throws BankServerException {
        setModuleHeadline("Buy Share");

        String input = showInputElement("Symbol");
        int input2 = Integer.parseInt(showInputElement("Amount"));

        BigDecimal success = bankServer.buy(input,input2);

        if(success.intValue()>=0) {
            showResponseMessage("Bought Share for " + input2 + " x " + success.intValue(), MessageType.SUCCESS);
        } else {
            showResponseMessage("Failed to buy share", MessageType.ERROR);
        }
        endOfModuleChoices();
    }

    private void searchAvailableShare() throws BankServerException {
        setModuleHeadline("Search for available Shares");
        String input = showInputElement("Name a Stock to be shown");
        List<Stock> output = bankServer.listStock(input);
        if(output.isEmpty()) {
            showResponseMessage("No Stocks found!", MessageType.INFO);
        } else {
            showListing(output);
        }
        endOfModuleChoices();
    }

    private Integer showMenu(List<String> menuChoices) {
        int response;
        int counter = 1;
        for (String menuChoice : menuChoices) {
            System.out.println(counter + ") " + menuChoice);
            counter++;
        }
        response = scan();

        while (!checkInput(menuChoices.size() + 1, response)) {
            System.out.println("Invalid Input!");
            response = scan();
        }

        return response;
    }

    private void showListing(List<Stock> list) {
        int count = 1;
        for(Stock stockElement : list) {
            System.out.println();
            System.out.println(count + ") " + "\n" + stockElement.toString());
            count++;
        }
    }

    private void showUserData(Person person) {
        System.out.println("First Name: " + MessageType.INFO.getCode() + person.getFirstName() + MessageType.RESET.getCode());
        System.out.println("Last Name:  " + MessageType.INFO.getCode() + person.getLastName() + MessageType.RESET.getCode());
        System.out.println("Username:   " + MessageType.INFO.getCode() + person.getUserName() + MessageType.RESET.getCode());
        if(!(person.getId() == null) && (person.getId() == 0)) {
            System.out.println("ID:        " + MessageType.INFO.getCode() + person.getId() + MessageType.RESET.getCode());
        }
    }

    private String showInputElement(String description) {
        System.out.print(description + ": ");
        String s = in.nextLine();
        if(!Objects.equals(s, "")) {
            if(s.contains(" ")) {
                s = s.replace(" ", "");
            }
            return s;
        }
        return "n/a";
    }

    private Integer scan(){
        System.out.print("\n> ");
        String s = in.nextLine();
        if(s.matches("[0-9]+")) {
            return Integer.parseInt(s);
        }
        return -1;
    }

    private boolean checkInput(int size, int input) {
        return input < size && input > 0;
    }

    private void setModuleHeadline(String headline) {
        System.out.println(MessageType.HEADLINE.getCode() + "\n" + headline + ":\n" + MessageType.RESET.getCode());
    }

    public void showResponseMessage(String message, MessageType messageType) {
        System.out.println(messageType.getCode() + "\n" + message + "\n" + MessageType.RESET.getCode());
    }

    private void endOfModuleChoices() throws BankServerException {
        setModuleHeadline("Module completed. Select next step");
        int output = showMenu(Arrays.asList("Run Module again","Return to main Menu", "End Application"));
        if(output == 1) {
            searchAvailableShare();
        } else if (output == 2) {
            showMainMenu();
        } else if (output == 3) {
        }
    }

    private Person inputPerson() {
        Person person = new Person();
        person.setFirstName(showInputElement("First Name"));
        person.setLastName(showInputElement("Last Name"));
        person.setUserName(showInputElement("Username"));
        String pwFirst = showInputElement("Password");
        String pwSecond = showInputElement("Repeat Password");
        while(!pwFirst.equals(pwSecond)) {
            showResponseMessage("Passwords do not match!", MessageType.ERROR);
            pwFirst = showInputElement("Password");
            pwSecond = showInputElement("Repeat Password");
        }
        person.setPassword(pwFirst);
        return person;
    }

    private boolean checkIfInputWasCorrect() {
        showResponseMessage("Is this input correct?", MessageType.INFO);
        String output = showInputElement("Y/N");
        return output.equalsIgnoreCase("Y");
    }

    private void showTransactionsList(List<Transaction> transactionList) {
        int count = 1;
        BigDecimal totalSum = BigDecimal.valueOf(0);
        if(!transactionList.isEmpty()) {
            for (Transaction transaction : transactionList) {
                totalSum = totalSum.add((transaction.getBuyPrice() == null) ? BigDecimal.valueOf(0) : transaction.getBuyPrice());
                showResponseMessage(count + ") "
                        + transaction.getCompanyName()
                        +  " - " + transaction.getStocksymbol()
                        + " - €" + ((transaction.getBuyPrice() == null) ? "NaN" : transaction.getBuyPrice().toString())
                        + " - # of owned Shares: " + transaction.getShareCount()
                        + " - ID: " + transaction.getID() , MessageType.RESET);
            }
            showResponseMessage("=========================================\nTotal Amount of owned assets: €" + totalSum, MessageType.INFO);
        } else {
            showResponseMessage("No bought Stocks found!", MessageType.ERROR);
        }
    }

}

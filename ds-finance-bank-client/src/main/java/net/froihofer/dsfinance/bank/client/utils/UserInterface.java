package net.froihofer.dsfinance.bank.client.utils;

import at.ac.csdc23vz_02.common.*;
import at.ac.csdc23vz_02.common.exceptions.BankServerException;
import net.froihofer.dsfinance.bank.client.BankClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Is used for user interaction with the CLI to use the BankServer functionalities.
 */
public class UserInterface {

    /**
     * BankServer Object
     */
    private BankServer bankServer;

    /**
     * used to read user input
     */
    private final Scanner in = new Scanner(System.in);

    /**
     * used to set user type to customer
     */
    private UserType userType = UserType.CUSTOMER;

    /**
     * Person Object
     */
    private Person loggedInUser;

    /**
     * BankServer Object
     */
    private BankClient bankClient;


    /**
     * constructor
     */
    public UserInterface() {
    }


    /**
     * Used to list start screen options
     * @return
     */
    public List<String> startLogin() {
        List<String> credentials = new ArrayList<>();
        setModuleHeadline("====== WELCOME ======");
        setModuleHeadline("Login");
        credentials.add(showInputElement("Username"));
        credentials.add(showInputElement("Password"));
        return credentials;
    }
    /**
     * Used to initialize Main Menu
     * @param bankServer
     * @param credentials
     * @param bankClient1
     * @throws BankServerException inherited from showMainMenu()
     */
    public void init(BankServer bankServer, List<String> credentials, BankClient bankClient1) throws BankServerException {
        this.bankClient = bankClient1;
        this.bankServer = bankServer;
        if(login(credentials)) {
            showMainMenu();
        }
    }
    /**
     * used to start register process
     * @throws BankServerException inherited from endOfModuleChoices()
     */

    private void startRegisterProcess() throws BankServerException {
        Customer customer = new Customer();
        Employee employee = new Employee();
        setModuleHeadline("Register");
        int output = showMenu(Arrays.asList("Customer", "Employee"));
        if(output == 1) {
            setModuleHeadline("Register Customer");
            customer.setPerson(inputPerson(UserType.CUSTOMER));
            try {
                bankServer.createCustomer(customer);
                showResponseMessage("User Created.", MessageType.SUCCESS);
                endOfModuleChoices();
            } catch (BankServerException e) {
                showResponseMessage(e.getMessage(), MessageType.ERROR);
                endOfModuleChoices();
            }
        } else {
            setModuleHeadline("Register Employee");
            employee.setPerson(inputPerson(UserType.EMPLOYEE));
            try {
                bankServer.createEmployee(employee);
                showResponseMessage("User Created.", MessageType.SUCCESS);
                endOfModuleChoices();
            } catch (BankServerException e) {
                showResponseMessage(e.getMessage(), MessageType.ERROR);
                endOfModuleChoices();
            }
        }
    }
    /**
     * Used to log user in
     * @param credentials
     * @return
     */

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
    /**
     * Shows main menu
     * @throws BankServerException
     */
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
                case 5: managePersonalData(this.loggedInUser); break;
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
                    "Show available Budget from Bank at the Stock Exchange",
                    "Logout"));
            switch (output) {
                case 1: startRegisterProcess(); break;
                case 2: searchCustomer(); break;
                case 3: searchAvailableShare(); break;
                case 4: buyShareforCustomer(); break;
                case 5: sellShareforCustomer(); break;
                case 6: showDepotForCustomer(); break;
                case 7: showAvailableBudget(); break;
                case 8: logout(); break;
            }
        }
    }
    /**
     * Shows available budget
     * @throws BankServerException inherited from endOfModuleChoices()
     */
    private void showAvailableBudget() throws BankServerException {
        setModuleHeadline("Available Budget from Bank at Stock Exchange");
        double availBudget = bankServer.getAvailableBudget();
        BigDecimal bigDecimal = new BigDecimal(availBudget);
        showResponseMessage("Available Budget: €" + bigDecimal.setScale(2, RoundingMode.HALF_EVEN), MessageType.INFO);
        endOfModuleChoices();
    }
    /**
     * shows depot for customer
     * @throws BankServerException inherited from endOfModuleChoices()
     */
    private void showDepotForCustomer() throws BankServerException {
        setModuleHeadline("Show Depot for Customer");
        int id = Integer.parseInt(showInputElement("ID of Customer"));
        printDepot(id);
        endOfModuleChoices();
    }
    /**
     * prints depot
     * @param id
     * @return
     * @throws BankServerException
     */
    private List<Transaction> printDepot(int id) throws BankServerException {
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

    /**
     * used to sell share for customer
     * @throws BankServerException inherited from endOfModuleChoices()
     */
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
    /**
     * used to buy share for customer
     * @throws BankServerException inherited from endOfModuleChoices()
     */
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
    /**
     * used to search for customer
     * @throws BankServerException inherited from endOfModuleChoices()
     */
    private void searchCustomer() throws BankServerException {
        setModuleHeadline("Search Customer");
        int output = showMenu(Arrays.asList(
                "Search by ID and edit User",
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
                Person person = new Person(customer.getFirstName(), customer.getLastName(), customer.getUserName(), customer.getPassword(), customer.getZip(), customer.getCountry(), customer.getStreet(), customer.getNumber(), customer.getCity());
                person.setId(customer.getId());
                showUserData(person);
                showResponseMessage("Next possible actions", MessageType.INFO);
                int output1 = showMenu(Arrays.asList(
                        "Edit user",
                        "Return back to main menu")
                        );
                if(output1 == 1) {
                    managePersonalData(person);
                } else {
                    showMainMenu();
                }
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
                    Person person = new Person(customer.getFirstName(), customer.getLastName(), customer.getUserName(), customer.getPassword(),customer.getZip(), customer.getCountry(), customer.getStreet(), customer.getNumber(), customer.getCity());
                    person.setId(customer.getId());
                    showUserData(person);
                }
            }
        }
        endOfModuleChoices();
    }
    /**
     * used to log out
     * @throws BankServerException inherited from endOfModuleChoices()
     */
    private void logout() throws BankServerException {
        setModuleHeadline("Logout");
        bankClient.run();
        showResponseMessage("Not implemented yet!", MessageType.ERROR);
        endOfModuleChoices();
    }
    /**
     * used to manage personal data
     * @param person
     * @throws BankServerException inherited from endOfModuleChoices()
     */
    private void managePersonalData(Person person) throws BankServerException {
        setModuleHeadline("Manage Personal Data");
        showUserData(person);
        showResponseMessage("Available Options", MessageType.INFO);
        int output = showMenu(Arrays.asList(
                "Update Personal Information",
                "Delete User",
                "Return to main menu"
        ));
        switch (output) {
            case 1: updatePersonalInformation(person); break;
            case 2: deleteUser(person); break;
            case 3: showMainMenu(); break;
        }
        endOfModuleChoices();
    }
    /**
     * used to delete user
     * @param person
     * @throws BankServerException inherited from deleteUser()
     */

    private void deleteUser(Person person) throws BankServerException {
        setModuleHeadline("Delete User: " + person.getFullName());
        if(this.userType == UserType.CUSTOMER) {
            showResponseMessage("Cannot delete own User. Please consult an Employee!", MessageType.ERROR);
        } else {
            showResponseMessage("Are you sure you want to delete the User: " + person.getFullName(), MessageType.INFO);
            String output = showInputElement("Y/N");
            if(output.equalsIgnoreCase("Y")) {
                if(bankServer.deleteUser(person)) {
                    showResponseMessage("User successfully deleted!", MessageType.SUCCESS);
                } else {
                    showResponseMessage("Please sell all Stocks before trying to delete User!", MessageType.ERROR);
                }
            }
        }



    }
    /**
     * used to reset password
     * @param person
     * @throws BankServerException inherited by updateUser()
     */
    private void resetPassword(Person person) throws BankServerException {
        showResponseMessage("Reset Password", MessageType.INFO);
        String newPwFirst = showInputElement("New password");
        String newPwSecond = showInputElement("Repeat new password");

        while(!newPwFirst.equals(newPwSecond)) {
            showResponseMessage("Passwords do not match!", MessageType.ERROR);
            newPwFirst = showInputElement("New password");
            newPwSecond = showInputElement("Repeat new password");
        }

        person.setPassword(newPwSecond);

        if(bankServer.updateUser(person)) {
            showResponseMessage("Successful Password reset!", MessageType.SUCCESS);
        }else {
            showResponseMessage("Failed Password reset - please try again", MessageType.ERROR);
        }

    }
    /**
     * used to update personal information
     * @param person
     * @throws BankServerException inherited by endOfModuleChoices()
     */
    private void updatePersonalInformation(Person person) throws BankServerException {
        showResponseMessage("Update Personal Information", MessageType.INFO);
        int output = showMenu(Arrays.asList(
                "Update First Name",
                "Update Last Name",
                "Change Password"
        ));
        if(output == 1) {
            //Update First Name
            showResponseMessage("Change First Name", MessageType.INFO);
            String newFirstName = showInputElement("New first Name");
            while(!checkIfInputWasCorrect()) {
                showResponseMessage("Change First Name", MessageType.INFO);
                newFirstName = showInputElement("New first Name");
            }
            person.setFirstName(newFirstName);
            bankServer.updateUser(person);
            showResponseMessage("First Name changed.", MessageType.SUCCESS);
        } else if(output == 2) {
            //Update Last Name
            showResponseMessage("Change Last Name", MessageType.INFO);
            String newLastName = showInputElement("New last Name");
            while(!checkIfInputWasCorrect()) {
                showResponseMessage("Change Last Name", MessageType.INFO);
                newLastName = showInputElement("New last Name");
            }
            person.setLastName(newLastName);
            bankServer.updateUser(person);
            showResponseMessage("Last Name changed.", MessageType.SUCCESS);
        } else if(output == 3) {
            resetPassword(person);
        }
        endOfModuleChoices();
    }

    /**
     * method to display bought stocks of this user
     * @throws BankServerException inherits it from endOfModuleChoices()
     */
    private void showDepot() throws BankServerException {
        setModuleHeadline("Show personal Depot");
        showResponseMessage("Depot for " + loggedInUser.getFullName() + ":", MessageType.INFO);
        List<Transaction> transactionList = bankServer.listDepot();
        showTransactionsList(transactionList);
        endOfModuleChoices();
    }

    /**
     * method to sell a bought share by inputing the ID of the bought share and the amount
     * @throws BankServerException inherits it from endOfModuleChoices()
     */
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

    /**
     * method to buy a share by inputing the symbol and the amount
     * @throws BankServerException inherits this from endOfModuleChoices()
     */
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

    /**
     * Method to look for available stocks by symbol
     * @throws BankServerException inherits this exception from endOfModuleChoices()
     */
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

    /**
     * method to display the menu choices = methods
     * @param menuChoices list of methods available to the user
     * @return returns the response of the user (0-9) depending on what method the user wants to access
     */
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

    /**
     * method to list available stocks, called in method searchAvailableShare() to search by symbol
     * @param list this list contains the available stocks
     */
    private void showListing(List<Stock> list) {
        int count = 1;
        for(Stock stockElement : list) {
            System.out.println();
            System.out.println(count + ") " + "\n" + stockElement.toString());
            count++;
        }
    }

    /**
     * Method to display a created persons data either for searching or managing the information
     * @param person is the person we want the information of
     */
    private void showUserData(Person person) {
        if(!(person.getId() == null) && !(person.getId() == 0)) {
            System.out.println("ID:         " + MessageType.INFO.getCode() + person.getId() + MessageType.RESET.getCode());
        }
        System.out.println("First Name: " + MessageType.INFO.getCode() + person.getFirstName() + MessageType.RESET.getCode());
        System.out.println("Last Name:  " + MessageType.INFO.getCode() + person.getLastName() + MessageType.RESET.getCode());
        System.out.println("Username:   " + MessageType.INFO.getCode() + person.getUserName() + MessageType.RESET.getCode());
        System.out.println();
        if(!(person.getStreet() == null) && !(person.getNumber() == null)) {
            System.out.println("Street/Number:   " + MessageType.INFO.getCode() + person.getStreet() + " " + person.getNumber() + MessageType.RESET.getCode());
        }
        if(!(person.getCity() == null)) {
            System.out.println("City:   " + MessageType.INFO.getCode() + person.getCity() + MessageType.RESET.getCode());
        }
        if(!(person.getZip() == null)) {
            System.out.println("ZIP:   " + MessageType.INFO.getCode() + person.getZip() + MessageType.RESET.getCode());
        }
        if(!(person.getCountry() == null)) {
            System.out.println("Country:   " + MessageType.INFO.getCode() + person.getCountry() + MessageType.RESET.getCode());
        }
    }

    /**
     * Shows the user what input is wanted (username:, etc.)
     * @param description is the String that indicates what input is needed
     * @return returns input of the user
     */
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

    /**
     * Scans the input of the user (0-9) to choose the right method in the UI
     * @return returns the user input
     */
    private Integer scan(){
        System.out.print("\n> ");
        String s = in.nextLine();
        if(s.matches("[0-9]+")) {
            return Integer.parseInt(s);
        }
        return -1;
    }


    /**
     * This method checks the size of the user inputs
     * @param size defines the maximum size of the input
     * @param input defines the number of letters/numbers in the user input
     * @return
     */
    private boolean checkInput(int size, int input) {
        return input < size && input > 0;
    }

    /**
     * Is used to define the headlines in the user interface
     * @param headline is the string that is written in the headline
     */
    private void setModuleHeadline(String headline) {
        System.out.println(MessageType.HEADLINE.getCode() + "\n" + headline + ":\n" + MessageType.RESET.getCode());
    }

    /**
     * Method is used to respond to the user
     * @param message Responds with a string indicating what to do next
     * @param messageType Defines the message type of the response (Error, Info, etc.)
     */
    public void showResponseMessage(String message, MessageType messageType) {
        System.out.println(messageType.getCode() + "\n" + message + "\n" + MessageType.RESET.getCode());
    }

    /**
     * This implies the end of a method
     * @throws BankServerException inherited from showMainMenu()
     */
    private void endOfModuleChoices() throws BankServerException {
        setModuleHeadline("Module completed. Select next step");
        int output = showMenu(Arrays.asList("Return to main Menu", "End Application"));
        if(output == 1) {
            showMainMenu();
        } else if (output == 2) {
        }
    }

    /**
     * Method to create a person
     * @return returns that created person
     */
    private Person inputPerson(UserType userType) {
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
        if(userType == UserType.CUSTOMER) {
            person.setStreet(showInputElement("Street"));
            String housenumber = showInputElement("Number");
            while(housenumber.contains("[1-9/]+")) {
                showResponseMessage("Please only enter Numbers or /!", MessageType.ERROR);
                housenumber = showInputElement("Number");
            }
            person.setNumber(housenumber);
            String zip = showInputElement("ZIP");
            while (zip.contains("[0-9]+")) {
                showResponseMessage("Please only enter Numbers!", MessageType.ERROR);
                zip = showInputElement("ZIP");
            }
            person.setZip(zip);
            person.setCity(showInputElement("City"));
            person.setCountry(showInputElement("Country"));
        }

        return person;
    }

    /**
     * confirms the input of the user
     * @return this return value lets the user move on if the input is "Y"
     */
    private boolean checkIfInputWasCorrect() {
        showResponseMessage("Is this input correct?", MessageType.INFO);
        String output = showInputElement("Y/N");
        return output.equalsIgnoreCase("Y");
    }

    /**
     * Shows all transactions, with the corresponding name, symbol, number of owned shares, and ID of the one who owns it
     * @param transactionList the list where the transactions are contained in
     */
    private void showTransactionsList(List<Transaction> transactionList) {
        int count = 1;
        double totalSum = 0;
        if(!transactionList.isEmpty()) {
            for (Transaction transaction : transactionList) {
                totalSum += (transaction.getBuyPrice().doubleValue() * transaction.getShareCount());
                showResponseMessage(count + ") "
                        + transaction.getCompanyName()
                        +  " - " + transaction.getStocksymbol()
                        + " - Current Price per Stock: €" + ((transaction.getBuyPrice() == null) ? "NaN" : transaction.getBuyPrice().toString())
                        + " - # of owned Shares: " + transaction.getShareCount()
                        + " - ID: " + transaction.getID() , MessageType.RESET);
                count++;
            }
            showResponseMessage("=========================================\nCurrent Value of owned assets: €" + totalSum, MessageType.INFO);
        } else {
            showResponseMessage("No bought Stocks found!", MessageType.ERROR);
        }
    }

}

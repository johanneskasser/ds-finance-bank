package net.froihofer.dsfinance.bank.client.utils;

import at.ac.csdc23vz_02.common.BankServer;
import at.ac.csdc23vz_02.common.Customer;
import at.ac.csdc23vz_02.common.Employee;
import at.ac.csdc23vz_02.common.Stock;
import at.ac.csdc23vz_02.common.exceptions.BankServerException;

import javax.annotation.security.RolesAllowed;
import java.util.*;

public class UserInterface {

    private BankServer bankServer;
    private final Scanner in = new Scanner(System.in);

    public UserInterface() {
    }

    public List<String> startLogin() {
        List<String> credentials = new ArrayList<>();
        setModuleHeadline("Login");
        credentials.add(showInputElement("Username"));
        credentials.add(showInputElement("Password"));
        return credentials;
    }

    public void init(BankServer bankServer, List<String> credentials) throws BankServerException {
        this.bankServer = bankServer;
        if(login(credentials)) {
            showMainMenu(UserType.CUSTOMER);
        }
        setModuleHeadline("====== WELCOME ======");
    }


    private void startRegisterProcess() {
        Customer customer = new Customer();
        Employee employee = new Employee();
        setModuleHeadline("Register");
        int output = showMenu(Arrays.asList("Customer", "Employee"));
        if(output == 1) {
            customer.setFirstName(showInputElement("First Name"));
            customer.setLastName(showInputElement("Last Name"));
            customer.setUserName(showInputElement("Username"));
            String pwFirst = showInputElement("Password");
            String pwSecond = showInputElement("Repeat Password");
            while(!pwFirst.equals(pwSecond)) {
                showResponseMessage("Passwords do not match!", MessageType.ERROR);
                pwFirst = showInputElement("Password");
                pwSecond = showInputElement("Repeat Password");
            }
            customer.setPassword(pwFirst);

            try {
                bankServer.createCustomer(customer);
                showResponseMessage("User Created.", MessageType.SUCCESS);
                showMainMenu(UserType.EMPLOYEE);
            } catch (BankServerException e) {
                showResponseMessage("Something went wrong while trying to create Customer. Please see Stack Trace.", MessageType.ERROR);
                e.printStackTrace();
            }
        } else {
            employee.setFirstName(showInputElement("First Name"));
            employee.setLastName(showInputElement("Last Name"));
            employee.setUserName(showInputElement("Username"));
            String pwFirst = showInputElement("Password");
            String pwSecond = showInputElement("Repeat Password");
            while(!pwFirst.equals(pwSecond)) {
                showResponseMessage("Passwords do not match!", MessageType.ERROR);
                pwFirst = showInputElement("Password");
                pwSecond = showInputElement("Repeat Password");
            }
            employee.setPassword(pwFirst);

            try {
                bankServer.createEmployee(employee);
                showResponseMessage("User Created.", MessageType.SUCCESS);
                showMainMenu(UserType.EMPLOYEE);
            } catch (BankServerException e) {
                showResponseMessage("Something went wrong while trying to create Customer. Please see Stack Trace.", MessageType.ERROR);
                e.printStackTrace();
            }
        }
    }

    private boolean login(List<String> credentials){
        try {
            if (bankServer.login(credentials)) {
                showResponseMessage("Successful Login!", MessageType.SUCCESS);
                //TODO: Go To Main Menu corresponding to the employee/customer
                showMainMenu(UserType.CUSTOMER);
                return true;
            } else {
                showResponseMessage("Wrong Password or Username!", MessageType.ERROR);
            }
        } catch (BankServerException bankServerException) {
            showResponseMessage("User does not exist!", MessageType.ERROR);
        }
        return false;
    }

    private void showMainMenu(UserType userType) throws BankServerException {
        if(userType == UserType.CUSTOMER) {
            //User is customer
            setModuleHeadline("=== Main Menu for Customers ===");
            int output = showMenu(Arrays.asList("Search available share",
                    "Buy Share",
                    "Sell Share",
                    "Show Depot",
                    "Manage personal Data",
                    "Logout"));
            switch (output) {
                case 1: searchAvailableShare(); break;
                case 2: buyShare(); break;
                case 3: sellShare(); break;
                case 4: showDepot(); break;
                case 5: managePersonalData(); break;
                case 6: logout(); break;
            }
        } else if(userType == UserType.EMPLOYEE){
            //TODO: User is Employee
            setModuleHeadline("=== Main Menu for Employees ===");
            int output = showMenu(Arrays.asList("Create Customer or Employee",
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

    private void showDepotForCustomer() {
    }

    private void sellShareforCustomer() {
    }

    private void buyShareforCustomer() {
    }

    private void searchCustomer() {
    }

    private void logout() throws BankServerException {
        setModuleHeadline("Logout");
        showResponseMessage("Not implemented yet!", MessageType.ERROR);
        endOfModuleChoices();
    }

    private void managePersonalData() throws BankServerException {
        setModuleHeadline("Manage Personal Data");
        showResponseMessage("Not implemented yet!", MessageType.ERROR);
        endOfModuleChoices();
    }

    private void showDepot() throws BankServerException {
        setModuleHeadline("Show personal Depot");
        showResponseMessage("Not implemented yet!", MessageType.ERROR);
        endOfModuleChoices();
    }

    private void sellShare() throws BankServerException {
        setModuleHeadline("Sell Share");
        showResponseMessage("Not implemented yet!", MessageType.ERROR);
        endOfModuleChoices();
    }

    private void buyShare() throws BankServerException {
        setModuleHeadline("Buy Share");
        showResponseMessage("Not implemented yet!", MessageType.ERROR);
        endOfModuleChoices();
    }

    private void searchAvailableShare() throws BankServerException {
        setModuleHeadline("Search for available Shares");
        String input = showInputElement("Name a Stock to be shown");
        try {
            List<Stock> output = bankServer.listStock(input);
            if(output.isEmpty()) {
                showResponseMessage("No Stocks found!", MessageType.INFO);
            } else {
                showListing(output);
            }
            endOfModuleChoices();
        } catch (BankServerException bankServerException) {
            showResponseMessage("Failed to read Stocks!", MessageType.ERROR);
        }
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
            showMainMenu(UserType.CUSTOMER);
        }
    }

}

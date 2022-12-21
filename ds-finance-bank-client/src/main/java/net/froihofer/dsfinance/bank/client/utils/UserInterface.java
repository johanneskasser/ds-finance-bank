package net.froihofer.dsfinance.bank.client.utils;

import at.ac.csdc23vz_02.common.BankServer;
import at.ac.csdc23vz_02.common.Customer;
import at.ac.csdc23vz_02.common.exceptions.BankServerException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class UserInterface {

    private final BankServer bankServer;
    private final Scanner in = new Scanner(System.in);

    public UserInterface(BankServer bankServer) {
        this.bankServer = bankServer;
    }

    public void init(){
        setModuleHeadline("====== WELCOME ======");
        setModuleHeadline("Login or Register");
        int output = showMenu(Arrays.asList("Login", "Register"));
        if(output == 1) {
            startLoginProcess();
        } else if (output == 2) {
            startRegisterProcess();
        }
    }

    private void startRegisterProcess() {
        Customer customer = new Customer();
        setModuleHeadline("Registering new Client");
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
            startLoginProcess();
        } catch (BankServerException e) {
            showResponseMessage("Something went wrong while trying to create Customer. Please see Stack Trace.", MessageType.ERROR);
            e.printStackTrace();
        }
    }

    private void startLoginProcess(){
        Customer customer = new Customer();
        boolean successfulLogin = false;
        setModuleHeadline("Login");

        while(!successfulLogin) {
            customer.setUserName(showInputElement("Username"));
            customer.setPassword(showInputElement("Password"));
            try {
                if(bankServer.login(customer)) {
                    showResponseMessage("Successful Login!", MessageType.SUCCESS);
                    successfulLogin = true;
                    //TODO: Go To Main Menu corresponding to the employee/customer
                    showMainMenu(UserType.CUSTOMER);
                } else {
                    showResponseMessage("Wrong Password or Username!", MessageType.ERROR);
                }
            } catch (BankServerException bankServerException){
                showResponseMessage("User does not exist!", MessageType.ERROR);
            }

        }
    }

    private void showMainMenu(UserType userType) {
        if(userType == UserType.CUSTOMER) {
            //User is customer
            setModuleHeadline("=== Main Menu for Customers ===");
            int output = showMenu(Arrays.asList("Search available share", "Buy Share", "Sell Share", "Show Depot", "Manage personal Data", "Logout"));
            switch (output) {
                case 1: searchAvailableShare(); break;
                case 2: buyShare(); break;
                case 3: sellShare(); break;
                case 4: showDepot(); break;
                case 5: managePersonalData(); break;
                case 6: logout(); break;
            }
        } else {
            //TODO: User is Employee
        }
    }

    private void logout() {
    }

    private void managePersonalData() {
        showResponseMessage("Not implemented yet!", MessageType.ERROR);
    }

    private void showDepot() {
        showResponseMessage("Not implemented yet!", MessageType.ERROR);
    }

    private void sellShare() {
        showResponseMessage("Not implemented yet!", MessageType.ERROR);
    }

    private void buyShare() {
        showResponseMessage("Not implemented yet!", MessageType.ERROR);
    }

    private void searchAvailableShare() {

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

    private void showResponseMessage(String message, MessageType messageType) {
        System.out.println(messageType.getCode() + "\n" + message + "\n" + MessageType.RESET.getCode());
    }
}

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
        System.out.println("\n====== WELCOME ======");
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
            System.out.println("\nPasswords do not match!\n");
            pwFirst = showInputElement("Password");
            pwSecond = showInputElement("Repeat Password");
        }
        customer.setPassword(pwFirst);

        try {
            bankServer.createCustomer(customer);
            System.out.println("User Created.");
            startLoginProcess();
        } catch (BankServerException e) {
            System.err.println("Something went wrong while trying to create Customer. Please see Stack Trace.");
            e.printStackTrace();
        }
    }

    private void startLoginProcess(){
        //TODO
        setModuleHeadline("Login");
        System.err.println("Not Implemented yet");
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
        System.out.println("\n" + headline + ":\n");
    }
}

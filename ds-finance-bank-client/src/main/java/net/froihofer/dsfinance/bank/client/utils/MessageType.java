package net.froihofer.dsfinance.bank.client.utils;


/**
 * Declaration on Message Types in Console for different Response Messages used in UserInterface.java
 */
//We have five MessageTypes as above. The code is used to change the color of text displayed in the console.
public enum MessageType {
    SUCCESS("\u001B[32m"),
    ERROR("\u001B[31m"),
    INFO("\u001B[34m"),
    HEADLINE("\u001B[36m"),
    RESET("\u001B[0m");

    private final String code;

    //The constructor is called when a new enum value is created and sets the initial state of the enum
    // value by passing in the necessary values as arguments.
    MessageType(String code) {
        this.code = code;
    }

    //return the code associated with the particular enum value.
    public String getCode(){
        return this.code;
    }

}

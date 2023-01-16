package net.froihofer.dsfinance.bank.client.utils;


/**
 * Declaration on Message Types in Console for different Response Messages used in UserInterface.java
 */
public enum MessageType {
    /**
     * Enum value for a success message, represented by the color green.
     */
    SUCCESS("\u001B[32m"),
    /**
     * Enum value for an error message, represented by the color red.
     */
    ERROR("\u001B[31m"),
    /**
     * Enum value for an informational message, represented by the color blue.
     */
    INFO("\u001B[34m"),
    /**
     * Enum value for a headline message, represented by the color cyan.
     */
    HEADLINE("\u001B[36m"),
    /**
     * Enum value to reset the console color.
     */
    RESET("\u001B[0m");

    private final String code;

    /**
     * Constructor for the MessageType enum
     * @param code the string code associated with the message type
     */
    MessageType(String code) {
        this.code = code;
    }

    /**
     * Getter for the code field
     * @return the code associated with the message type
     */
    public String getCode(){
        return this.code;
    }
}

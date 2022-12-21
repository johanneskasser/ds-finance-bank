package net.froihofer.dsfinance.bank.client.utils;


/**
 * Declaration on Message Types in Console for different Response Messages used in UserInterface.java
 */
public enum MessageType {
    SUCCESS("\u001B[32m"),
    ERROR("\u001B[31m"),
    INFO("\u001B[34m"),
    HEADLINE("\u001B[36m"),
    RESET("\u001B[0m");

    private final String code;

    MessageType(String code) {
        this.code = code;
    }

    public String getCode(){
        return this.code;
    }

}

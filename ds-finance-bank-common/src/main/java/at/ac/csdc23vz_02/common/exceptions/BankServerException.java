package at.ac.csdc23vz_02.common.exceptions;

/**
 * The BankServerException class represents an exception that can be thrown by a bank server.
 */
public class BankServerException extends Exception {

    /**
     * Field for the specific type of exception
     */
    protected final BankServerExceptionType bankServerExceptionType;

    /**
     * Constructor for creating a BankServerException with a message and an exception type.
     * @param message the error message for the exception
     * @param bankServerExceptionType the specific type of exception
     */
    public BankServerException(String message, BankServerExceptionType bankServerExceptionType){
        super(message);
        this.bankServerExceptionType = bankServerExceptionType;
    }

}

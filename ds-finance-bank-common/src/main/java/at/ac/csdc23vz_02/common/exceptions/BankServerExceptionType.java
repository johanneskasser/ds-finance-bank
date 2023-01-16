package at.ac.csdc23vz_02.common.exceptions;

/**
 * Class that defines the types of exceptions that are thrown
 */
public enum BankServerExceptionType {
    /**
     * Defines the session fault and gives it the code 1 and displays the String Session Fault
     */
    SESSION_FAULT(1, "Session Fault"),
    /**
     * Defines the webservice fault and gives it the code 2 and displays the String Webservice Fault
     */
    WEBSERVICE_FAULT(2, "Webservice Fault"),
    /**
     * Defines the database fault and gives it the code 3 and displays the String Database Fault
     */
    DATABASE_FAULT(3, "Database Fault"),
    /**
     * Defines the transaction fault and gives it the code 4 and displays the String Transaction Fault
     */
    TRANSACTION_FAULT(4, "Transaction Fault"),
    /**
     * Defines the webserver fault and gives it the code 5 and displays the String Webserver Fault
     */
    WEBSERVER_FAULT(5, "Webserver Fault");


    /**
     * Defines the integer code that is used by the faults above
     */
    private final Integer code;
    /**
     * Defines the String message that is used to display Strings by the faults above
     */
    private final String message;

    /**
     * Constructor for this class
     * @param code error code
     * @param message error message
     */
    BankServerExceptionType(final Integer code, final String message) {
        this.code = code;
        this.message = message;
    }
}

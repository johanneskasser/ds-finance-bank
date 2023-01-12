package at.ac.csdc23vz_02.common.exceptions;

public enum BankServerExceptionType {
    SESSION_FAULT(1, "Session Fault"),
    WEBSERVICE_FAULT(2, "Webservice Fault"),
    DATABASE_FAULT(3, "Database Fault"),
    TRANSACTION_FAULT(4, "Transaction Fault"),
    WEBSERVER_FAULT(5, "Webserver Fault");


    private final Integer code;
    private final String message;

    BankServerExceptionType(final Integer code, final String message) {
        this.code = code;
        this.message = message;
    }
}

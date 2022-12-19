package at.ac.csdc23vz_02.common.exceptions;

public class BankServerException extends Exception {

    protected final BankServerExceptionType bankServerExceptionType;

    public BankServerException(String message, BankServerExceptionType bankServerExceptionType){
        super(message);
        this.bankServerExceptionType = bankServerExceptionType;
    }

}

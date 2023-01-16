package at.ac.csdc23vz_02.bankserver.util;

/**
 * Declaration on Login Types for different Response Messages used in BankServerImpl.java
 */
public enum LoginType {
    /**
     * Successfull Customer Login
     */
    CUSTOMER_SUCCESS(1),

    /**
     * Successfull Employee Login
     */
    EMPLOYEE_SUCCESS(2),

    /**
     * Login Failure
     */
    LOGIN_FAILURE(3);

    /**
     * Login Status
     */
    private final int code;

    /**
     * constructor
     * @param code Status to be set
     */
    LoginType(int code) {
        this.code = code;
    }

    /**
     * Getter of Login Status
     * @return return Login Status as int
     */
    public int getCode() {
        return code;
    }
}

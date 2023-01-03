package at.ac.csdc23vz_02.bankserver.util;

public enum LoginType {
    CUSTOMER_SUCCESS(1),
    EMPLOYEE_SUCCESS(2),
    LOGIN_FAILURE(3);

    private final int code;

    LoginType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

package at.ac.csdc23vz_02.bankserver.util;

import at.ac.csdc23vz_02.common.exceptions.BankServerException;
import at.ac.csdc23vz_02.common.exceptions.BankServerExceptionType;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Used to calculate, compare and store PasswordHashes
 */
public class PWHash {

    /**
     * Used to initialise the Hashing-Algorithm
     */
    private MessageDigest messageDigest;

    /**
     * Base64 Encoder
     */
    Base64.Encoder enc = Base64.getEncoder();

    /**
     * Base64 Decoder
     */
    Base64.Decoder dec = Base64.getDecoder();

    /**
     * Constructor
     */
    public PWHash() {
    }

    /**
     * initialise the Hashing-Algorithm
     * @throws BankServerException throws an Exception if the used Hashing Algorithm doesn't exist
     */
    public void init() throws BankServerException {
        try {
            this.messageDigest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new BankServerException(noSuchAlgorithmException.getMessage(), BankServerExceptionType.SESSION_FAULT);
        }
    }

    /**
     *
     * @param password the to be hashed cleartext password
     * @return returns a string-list with list(0)=salt and list(1)=hashed password
     * @throws BankServerException inherited from init()
     */
    public List<String> createSaltAndHashPassword(String password) throws BankServerException {
        List<String> list = new ArrayList<>();

        if(this.messageDigest == null) {
            //Check if required tools have been initialized
            init();
        }
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        this.messageDigest.update(salt);

        list.add(enc.encodeToString(salt));

        byte[] hashedPW = this.messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));

        list.add(enc.encodeToString(hashedPW));

        return list;
    }

    /**
     * Used to compare the hashed password with the cleartext password (which will be hashed with the input salt)
     * @param salt the used salt for the input password-hash
     * @param passwordHash the password-hash that should be compared with the cleartext password
     * @param password the cleartext password that should be compared with the password-hash
     * @return returns a boolean - true=password is correct & false=password is incorrect
     * @throws BankServerException inherited from init()
     */
    public boolean checkPassword(String salt, String passwordHash, String password) throws BankServerException {
        if(this.messageDigest == null) {
            //Check if required tools have been initialized
            init();
        }

        byte[] saltBytes = dec.decode(salt);

        this.messageDigest.update(saltBytes);

        byte[] hashedPW = this.messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));

        return passwordHash.equals(enc.encodeToString(hashedPW));
    }
}

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

public class PWHash {
    private MessageDigest messageDigest;
    Base64.Encoder enc = Base64.getEncoder();
    Base64.Decoder dec = Base64.getDecoder();

    public PWHash() {
    }

    public void init() throws BankServerException {
        try {
            this.messageDigest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new BankServerException(noSuchAlgorithmException.getMessage(), BankServerExceptionType.SESSION_FAULT);
        }
    }

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

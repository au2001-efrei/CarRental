package net.efrei.s6.databases.aurelducyoni.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordHash {

    private static final BCrypt.Hasher BCRYPT = BCrypt.withDefaults();
    private static final BCrypt.Verifyer VERIFYER = BCrypt.verifyer();

    private PasswordHash() {}

    public static String hash(String password) {
        return BCRYPT.hashToString(12, password.toCharArray());
    }

    public static boolean verify(String hash, String password) {
        return VERIFYER.verify(password.toCharArray(), hash).verified;
    }

}

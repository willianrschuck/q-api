package br.edu.ifsul.modelo;

import br.edu.ifsul.encryption.StringEncryptor;

public class LoginParameters {
    private static final String TYPE_STUDDENT = "1";

    private final StringEncryptor encryptor;

    private final String encryptedUserType;
    private String encryptedUsername;
    private String encryptedPassword;

    public LoginParameters(StringEncryptor rsa) {
        this.encryptor = rsa;
        this.encryptedUserType = this.encryptor.encrypt(TYPE_STUDDENT); // By default only students will use this system
    }

    public void insertPassword(String password) {
        this.encryptedPassword = encryptor.encrypt(password);
    }

    public void insertUsername(String username) {
        this.encryptedUsername = encryptor.encrypt(username);
    }

    public String getEncryptedUsername() {
        return encryptedUsername;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public String getEncryptedUserType() {
        return encryptedUserType;
    }

}

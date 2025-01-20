package org.BookRecommender;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

public class SecurityUtils {
    private static final String AES_key = "askd021_dja_351:";
    private static final int AES_BLOCK_SIZE = 16;
    /**
     * Genera una password di tipo String casuale di lunghezza 10;
     * @return una String password del metodo toString dell'array password.
     */
    public String genera()
    {
        int length = 10;
        String simboli = "-/.^&*_!@%=+>)";
        String lettere_maiuscole = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lettere_minuscole = "abcdefghijklmnopqrstuvwxyz";
        String numeri = "0123456789";

        String unioneSet = lettere_maiuscole + lettere_minuscole +
                numeri + simboli;
        Random random = new Random();

        char[] password = new char[length];

        for (int i = 0; i < length; i++) {
            password[i] =
                    unioneSet.charAt(random.nextInt(unioneSet.length()));

        }
        return new String(password);
    }

    /**
     * Fornisce la password crittata
     * @param pass la password da crittare
     * @return la password cifrata in base 64 usando AES
     */
    public String encrypt(String pass) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(AES_key.getBytes("UTF-8"), "AES");

        // Crea un IV casuale
        SecureRandom random = new SecureRandom();
        byte[] iv = new byte[AES_BLOCK_SIZE];
        random.nextBytes(iv);

        // Cifratura in modalità CBC
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

        // Cifra i dati
        byte[] passCrittata = cipher.doFinal(pass.getBytes("UTF-8"));

        // Concatenare IV e dati cifrati
        byte[] encryptedDataWithIv = new byte[iv.length + passCrittata.length];
        System.arraycopy(iv, 0, encryptedDataWithIv, 0, iv.length);
        System.arraycopy(passCrittata, 0, encryptedDataWithIv, iv.length, passCrittata.length);

        return Base64.getEncoder().encodeToString(encryptedDataWithIv);
    }
    /**
     * Decritta la password precedentemente crittata in AES-18
     * @param passEncrypted Password crittografata
     * @return Password decrittata
     */
    public String decrypt(String passEncrypted) throws Exception {   
        SecretKeySpec secretKey = new SecretKeySpec(AES_key.getBytes("UTF-8"), "AES");

        byte[] encryptedDataWithIv = Base64.getDecoder().decode(passEncrypted);

        if (encryptedDataWithIv.length < AES_BLOCK_SIZE) {
            throw new IllegalArgumentException("I dati cifrati non sono validi o sono troppo corti.");
        }

        byte[] iv = new byte[AES_BLOCK_SIZE]; 
        System.arraycopy(encryptedDataWithIv, 0, iv, 0, iv.length);

        byte[] passEncryptedData = new byte[encryptedDataWithIv.length - AES_BLOCK_SIZE];
        System.arraycopy(encryptedDataWithIv, AES_BLOCK_SIZE, passEncryptedData, 0, passEncryptedData.length);

        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

        byte[] passDecrittata = cipher.doFinal(passEncryptedData);

        return new String(passDecrittata);
    }
}

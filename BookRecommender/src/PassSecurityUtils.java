import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

// Insieme di metodi utili per rendere la gestione della password più sicura
public class PassSecurityUtils {
    // Nota: la chiave è di 16 byte, quindi userà AES-128 (può anche essere lunga 24 o 32 byte per altre versioni)
    private static final String AES_key = "askd021_dja_351:";    // Chiave per crittaggio e decrittaggio di AES
    private static final int AES_BLOCK_SIZE = 16;  // AES usa blocchi di 16 byte

    public String genera()  // Genera una password sicura randomica di 10 caratteri
    {
        // Set usato per generare la password
        int length = 10;    // Lunghezza della password finale
        String simboli = "-/.^&*_!@%=+>)";
        String lettere_maiuscole = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lettere_minuscole = "abcdefghijklmnopqrstuvwxyz";
        String numeri = "0123456789";

        // Insieme dei set
        String unioneSet = lettere_maiuscole + lettere_minuscole +
                numeri + simboli;
        Random random = new Random();

        char[] password = new char[length];

        // Generazione di una password randomica usando l'insieme dei set
        for (int i = 0; i < length; i++) {
            password[i] =
                    unioneSet.charAt(random.nextInt(unioneSet.length()));

        }
        return new String(password); // char[] toString
    }

    public String encrypt(String pass) throws Exception {    // Crittaggio pass usando AES; usando IV genererà sempre blocchi da 16 byte (fix bug decrypt)
        // Creazione oggetto SecretKeySpec con key
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

        // Restituisci la stringa cifrata in Base64
        return Base64.getEncoder().encodeToString(encryptedDataWithIv);
    }

    public String decrypt(String passEncrypted) throws Exception {   // Decritta password
        // Creazione oggetto SecretKeySpec con la stessa chiave
        SecretKeySpec secretKey = new SecretKeySpec(AES_key.getBytes("UTF-8"), "AES");

        // Decodifichiamo la stringa Base64 in byte[]
        byte[] encryptedDataWithIv = Base64.getDecoder().decode(passEncrypted);

        // Verifica se i dati cifrati contengono almeno 16 byte per l'IV
        if (encryptedDataWithIv.length < AES_BLOCK_SIZE) {
            throw new IllegalArgumentException("I dati cifrati non sono validi o sono troppo corti.");
        }

        // Estraiamo l'IV (i primi 16 byte)
        byte[] iv = new byte[AES_BLOCK_SIZE]; // AES_BLOCK_SIZE = 16
        System.arraycopy(encryptedDataWithIv, 0, iv, 0, iv.length);

        // Estraiamo il testo cifrato (il resto dei dati, dopo l'IV)
        byte[] passEncryptedData = new byte[encryptedDataWithIv.length - AES_BLOCK_SIZE];
        System.arraycopy(encryptedDataWithIv, AES_BLOCK_SIZE, passEncryptedData, 0, passEncryptedData.length);

        // Inizializziamo il cipher in modalità di decrittazione con il corretto IV
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

        // Decifriamo la password
        byte[] passDecrittata = cipher.doFinal(passEncryptedData);

        // Restituiamo la password decifrata come stringa
        return new String(passDecrittata);
    }

}


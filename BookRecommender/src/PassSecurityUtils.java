import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Random;

// Insieme di metodi utili per rendere la gestione della password più sicura
public class PassSecurityUtils {
    private static final String AES_key = "askd021_dja";    // Chiave per crittaggio e decrittaggio di AES
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
        for (int i = 0; i < length; i++)
        {
            password[i] =
                    unioneSet.charAt(random.nextInt(unioneSet.length()));

        }
        return new String(password); // char[] toString
    }

    public String encrypt(String pass) throws Exception {    // Crittaggio pass usando AES
        // Creazione oggetto SecretKeySpec con key
        SecretKeySpec secretKey = new SecretKeySpec(AES_key.getBytes(), "AES");

        // Cifratore in modalità cifratura
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        // Cifraggio
        byte[] passCrittata = cipher.doFinal(pass.getBytes());

        // Return password cifrata in Base64
        return Base64.getEncoder().encodeToString(passCrittata);
    }

    public String decrypt(String passEncrypted) throws Exception {   // Decritta password
        // Creazione oggetto SecretKeySpec con la stessa key
        SecretKeySpec secretKey = new SecretKeySpec(AES_key.getBytes(), "AES");

        // Inizializziamo il cipher in modalità di decifratura
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        // Decifriamo la password (prima decodifichiamo da Base64)
        byte[] passDecoded = Base64.getDecoder().decode(passEncrypted);
        byte[] passDecrittata = cipher.doFinal(passDecoded);

        // Restituiamo la password decifrata come stringa
        return new String(passDecrittata);
    }
}

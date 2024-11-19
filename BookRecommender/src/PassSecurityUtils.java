import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

/**
* Classe che mette a disposizione metodi per la creazione di password sicure e crittazione/decritazione
* Chiave di 16 byte, utilizza AES-128 con la stessa chiave per crittografare e decrittografare la password
* AES_key specifica la chiave di crittaggio/decrittaggio (askd021_dja_351:) (final String)
* AES_BLOCK_SIZE specifica la lunghezza dei blocchi (16 byte) (final int)
*/
public class PassSecurityUtils {
    private static final String AES_key = "askd021_dja_351:";   
    private static final int AES_BLOCK_SIZE = 16;  
/**
* Genera una password di tipo String casuale di lunghezza 10
* Prende in consideazione come candidati tutti i caratteri dell'alfabeto sia maiuscoli che minuscoli (String lettere_maiuscole, lettere_minuscole)
* I caratteri speciali (String simboli) candidati sono: -/.^&*_!@%=+>)
* I caratteri numerici candidati sono le cifre 0123456789
* Per la generazione della password, viene creato prima un oggetto di tipo String concatenando le String di caratteri candidati
* Viene costruito un Array di char con dimensione lenght (10), ad ogni iterazione viene scelto un carattere in posizione casuale della stringa unioneSet +
* + e viene inserito nella posizione dell'array password a cui si riferisce il ciclo. Si ripete il processo fino a riempire tutte le posizioni dell'array.
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
* @param pass (tipo String)
* @return la stringa cifrata in base 64
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
* @param passWncrypted (String), cioè la password crittografata
* @return String passDecrittata
    */
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


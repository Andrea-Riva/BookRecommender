import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.server.UID;
import java.util.Objects;

public class GetLoggedUser {
    /**
     *
     * @param titoloConsiglio String che rappresenta il titolo del libro per il quale si vuole cercare l'utente che ha pubblicato suggerimenti a riguardo
     * @return un oggetto LoggedUser che rappresenta l'utente che ha pubblicato i consigli relativi al parametro titoloConsiglio
     * @throws Exception Se l'ID dell'utente non è stato trovato tra i suggerimenti
     */
    public LoggedUser getLoggedUserFromConsiglio (String titoloConsiglio) throws Exception {
        String uId = null;
        String filePath = "src/data/ConsigliLibri.dati.csv";
        String line;
        // Get user ID
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while((line = br.readLine()) != null) {
                String[] lineClear = line.split("; ");
                if(lineClear[1].equals(titoloConsiglio)) {
                    uId = lineClear[0]; // ID dell'utente pubblicatore dei consigli
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new IOException(e);
        }
        if(Objects.isNull(uId)) {   // Se l'utente non ha pubblicato consigli relativi a quel libro
            throw new Exception("L'utente non ha pubblicato consigli relative al libro " + titoloConsiglio);    // Solleva eccezione
        }
        // Se uId è stato trovato
        filePath = "src/data/utenti.dati.csv";  // Cambio file per la ricerca dell'utente
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while((line = br.readLine()) != null) {
                String[] lineClear = line.split(";");
                if(lineClear[0].equals(uId)) {  // Se gli ID corrispondono
                    return new LoggedUser(lineClear[0], lineClear[1], lineClear[2], lineClear[3], lineClear[4]);
                }
            }
        }
        return null;
    }
}

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class GetLoggedUser {
    /**
     * Metodo che permette di cercare un utente dato il suo ID univoco
     * @param id ID dell'utente da cercare
     * @return Un oggetto LoggedUser che rappresenta l'utente il quale ID è uguale all'ID preso come parametro
     * @throws IOException Eccezione sollevata da BufferedReader
     */
    public LoggedUser getLoggedUserFromId(String id) throws IOException {
        String filePath = "src/data/utenti.dati.csv";   // Dataset di tutti gli utenti loggati
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] lineClear = line.split("; ");
                if(lineClear[0].equals(id)) {   // Se gli ID corrispondono
                    return new LoggedUser(lineClear[0], lineClear[1],
                            lineClear[2], lineClear[3], lineClear[4]);  // Crea e returna l'utente
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new IOException(e);
        }
        return null;
    }
}
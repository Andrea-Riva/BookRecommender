import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception {
        User baseUser = new User(); // Utente non loggato
        Scanner s = new Scanner(System.in);
        // Debugging ricerca libri
        /*

        ArrayList<Libro> solution = baseUser.cercaLibroByDataAutore("1985-04-01");
        System.out.println("Libri trovati per la data 1985-04-01:");
        for(Libro l : solution) {
            System.out.println(l.toString());
            System.out.println("\n");
        }

        solution = baseUser.cercaLibroByAutore("J.K. Rowling");
        System.out.println("Tutti i libri trovati di J.K. Rowling:");
        for(Libro l : solution) {
            System.out.println(l.toString());
            System.out.println("\n");
        }

        System.out.println("Cerca libro per titolo ragione e sentimento: " + baseUser.cercaLibroByTitolo("Ragione e sentimento").toString());

         */

        //Debugging registrazione utente con crittaggio password
        //baseUser.register();

        //Debudding login con decrittaggio password
        System.out.print("Inserisci mail: ");
        String mail = s.nextLine();
        System.out.print("Inserisci password: ");
        String password = s.nextLine();
        System.out.println(baseUser.login(mail, password).toString());
    }
}
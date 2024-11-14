import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        User baseUser = new User();

        ArrayList<Libro> solution = baseUser.cercaLibroByData("1985-04-01");
        for(Libro l : solution) {
            System.out.println(l.toString());
            System.out.println("\n");
        }
    }
}
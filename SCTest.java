import java.io.*;
import java.util.*;

public class SCTest {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        String input = "";

        System.out.printf("Test the scanner, type some input and press enter, \"quit\" to quit:\n");

        while (input != "quit") {
            System.out.printf("Current input string: %s\n", input);
            System.out.printf("Waiting for input: ");
            try {
                input = sc.next();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            //System.out.printf("sc.hasNext(): %b\n", sc.hasNext());
            //System.out.printf("sc.hasNextInt(): %b\n", sc.hasNextInt());
            //System.out.printf("sc.hasNextLine(): %b\n", sc.hasNextLine());
        }
        sc.close();
    }
}

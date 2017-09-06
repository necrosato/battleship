import java.net.*;
import java.io.*;
import java.awt.*;
import java.util.Scanner;
import java.util.Random;

public class BattleshipClient implements Runnable {

    public static String server_host = "www.naookiesato.com";
    public static int server_port = 6666;
    
    protected DataInputStream i;
    protected DataOutputStream o;
    protected Thread listener;

    public BattleshipClient(InputStream i, OutputStream o) {
        this.i = new DataInputStream( new BufferedInputStream(i) );
        this.o = new DataOutputStream( new BufferedOutputStream(o) );
        listener = new Thread(this); //create a thread this way, or have ChatClient extent Thread
        listener.start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                String line = i.readUTF();
                System.out.printf(line + "\n");
            }
        } catch (IOException e) { 
            e.printStackTrace();
        } finally {
            listener = null;
            try { 
                o.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        try {
            o.writeUTF(message);
            o.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void promptTurn(Scanner sc) {
        int row, col;
        System.out.printf("Input a letter and number (eg \"E 5\") for row and col: ");
        row = getRow(sc);
        col = getCol(sc);
    }

    public static int getRow(Scanner sc) {
        int num = -1;
        while (num < 0 || num > 9) {
            try { 
                String s = sc.next().toUpperCase();
                num = (int) s.charAt(0) - 65;
            }
            catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return num;
    }

    public static int getCol(Scanner sc) {
        int num = -1;
        while (num < 0 || num > 9) {
            try { 
                String s = sc.next();
                num = Integer.parseInt(s);
            }
            catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return num;
    }

    public static void main(String args[]) throws IOException {
        Socket s = new Socket();
        s.connect(new InetSocketAddress(server_host, server_port));
        System.out.println("new socket created, connected to " + s.getInetAddress().toString());

        BattleshipClient client = new BattleshipClient(s.getInputStream(), s.getOutputStream());

        System.out.println("new client created, connected at " + s.getLocalAddress().toString());

        Scanner sc = new Scanner(System.in);
        String m = "";
        boolean won = false;
        boolean turn = false;

        while (!won) {
            try {
                m = sc.nextLine();
            } catch (Exception e) {
                if (m.isEmpty()) {
                    System.out.println("Empty message");
                }
            }
            client.sendMessage(m);
        }
    }
}

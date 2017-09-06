//package battleship;

import java.net.*;
import java.io.*;
import java.util.*;

enum Cell {
    OPEN (-1),
    MISS (0),
    HIT (1),
    DESTROYER (2),
    SUBMARINE (3),
    CRUISER (3),
    BATTLESHIP (4),
    CARRIER (5),
    SUNK (6);

    private int cell_id;
    Cell(int cell_id) {this.cell_id = cell_id;}
    public int getCellId(){return cell_id;}
}

class Board {

    private int size = 10;
    private int[][] grid;

    public Board() {
        this.grid = new int[this.size][];
        for (int i = 0; i < this.size; i++) {
            this.grid[i] = new int[this.size];
            for (int j = 0; j < this.size; j++)
                this.grid[i][j] = Cell.OPEN.getCellId();
        }
    }

    public int getSize() {
        return this.size;
    }

    public int[][] getGrid() {
        return this.grid;
    }

    public void setGrid(int i, int j, Cell c) {
        this.grid[i][j] = c.getCellId();
    }

    public String getPlayerBoard() {
        String board = "";
        board += String.format("  ");
        for (int i = 0; i < this.size; i++)
            board += String.format(" %d", i);
        board += String.format("\n");
        for (int i = 0; i < this.size; i++) {
            board += String.format(" %s", Character.toString((char) (65+i)));
            for (int j = 0; j < this.size; j++) {
                if (this.checkPosition(i, j, Cell.MISS))
                    board += String.format(" .");
                else if (this.checkPosition(i, j, Cell.OPEN))
                    board += String.format(" .");
                else if (this.checkPosition(i, j, Cell.HIT))
                    board += String.format(" ■");
                else if (this.checkPosition(i, j, Cell.SUNK))
                    board += String.format(" □");
                else 
                    board += String.format(" %d", this.grid[i][j]);
            }
            board += String.format("\n");
        }
        return board;
    }

    public String getOpponentBoard() {
        String board = "";
        board += String.format("  ");
        for (int i = 0; i < this.size; i++)
            board += String.format(" %d", i);
        board += String.format("\n");
        for (int i = 0; i < this.size; i++) {
            board += String.format(" %s", Character.toString((char) (65+i)));
            for (int j = 0; j < this.size; j++) {
                if (this.checkPosition(i, j, Cell.MISS))
                    board += String.format(" o");
                else if (this.checkPosition(i, j, Cell.HIT))
                    board += String.format(" ■");
                else if (this.checkPosition(i, j, Cell.SUNK))
                    board += String.format(" □");
                else 
                    board += String.format(" .");
            }
            board += String.format("\n");
        }
        return board;
    }

    private boolean checkPosition(int i, int j, Cell c) {
        if (this.grid[i][j] == c.getCellId())
            return true;
        return false;
    }
}

class BattleshipPlayer extends Thread {
    protected Socket s;
    protected DataInputStream i;
    protected DataOutputStream o;
    protected Board b;
    protected int life = 17;

    public BattleshipPlayer(Socket s) throws IOException {
        this.s = s;
        this.i = new DataInputStream(new BufferedInputStream(s.getInputStream()));
        this.o = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
        this.b = new Board();
    }

    @Override
    public void run() {
    }
}

public class BattleshipServer {

    public static String server_host = "10.0.0.45";
    public static int server_port = 6666;


    public static void main(String args[]) throws IOException {
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(server_host, server_port));
        System.out.println("server socket created, inet address: " + server.getInetAddress());
        BattleshipPlayer player1;
        BattleshipPlayer player2;
        Socket client;

        client = server.accept();
        System.out.println("Accepted from " + client.getInetAddress());
        player1 = new BattleshipPlayer(client);

        client = server.accept();
        System.out.println("Accepted from " + client.getInetAddress());
        player2 = new BattleshipPlayer(client);
    }
}

package battleship;

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

    public void promptTurn(Scanner sc) {
        int row, col;
        this.printPlayerBoard();
        System.out.printf("Input a letter and number (eg \"E 5\") for row and col: ");
        row = Battleship.getRow(sc);
        System.out.println(row);
        col = Battleship.getCol(sc);
        System.out.println(col);
        //TODO
    }

    public void printPlayerBoard() {
        System.out.printf("  ");
        for (int i = 0; i < this.size; i++)
            System.out.printf(" %d", i);
        System.out.printf("\n");
        for (int i = 0; i < this.size; i++) {
            System.out.printf(" %s", Character.toString((char) (65+i)));
            for (int j = 0; j < this.size; j++) {
                if (this.checkPosition(i, j, Cell.MISS))
                    System.out.printf(" .");
                else if (this.checkPosition(i, j, Cell.OPEN))
                    System.out.printf(" .");
                else if (this.checkPosition(i, j, Cell.HIT))
                    System.out.printf(" ■");
                else if (this.checkPosition(i, j, Cell.SUNK))
                    System.out.printf(" □");
                else 
                    System.out.printf(" %d", this.grid[i][j]);
            }
            System.out.printf("\n");
        }
    }

    public void printOpponentBoard() {
        System.out.printf("  ");
        for (int i = 0; i < this.size; i++)
            System.out.printf(" %d", i);
        System.out.printf("\n");
        for (int i = 0; i < this.size; i++) {
            System.out.printf(" %s", Character.toString((char) (65+i)));
            for (int j = 0; j < this.size; j++) {
                if (this.checkPosition(i, j, Cell.MISS))
                    System.out.printf(" o");
                else if (this.checkPosition(i, j, Cell.HIT))
                    System.out.printf(" ■");
                else if (this.checkPosition(i, j, Cell.SUNK))
                    System.out.printf(" □");
                else 
                    System.out.printf(" .");
            }
            System.out.printf("\n");
        }
    }

    private boolean checkPosition(int i, int j, Cell c) {
        if (this.grid[i][j] == c.getCellId())
            return true;
        return false;
    }
}

public class Battleship {
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

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        Board b = new Board();
        
        b.promptTurn(sc);
        sc.close();
    }
}

 

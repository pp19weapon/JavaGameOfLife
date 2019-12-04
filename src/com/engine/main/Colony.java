package com.engine.main;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class Colony {
    private int posX = 0;
    private int posY = 0;

    private int sizeX = 0;
    private int sizeY = 0;

    private int aliveCells = 0;

    public int getPosX() { return posX; }
    public int getPosY() { return posY; }

    public int getSizeX() { return sizeX; }
    public int getSizeY() { return sizeY; }

    public int getAliveCells() { return aliveCells; }

    private LinkedList<LinkedList<Cell>> colonyCells = new LinkedList<LinkedList<Cell>>();

    public Colony (String pathToColonyFile, int startingPosX, int startingPosY) throws IOException {
        this.posX = startingPosX;
        this.posY = startingPosY;

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(pathToColonyFile);

        if (inputStream == null) {
            System.out.println("Failed to load " + pathToColonyFile + "!");
            return;
        }

        try (Scanner scanner = new Scanner(inputStream)) {
            this.sizeX = scanner.nextInt();
            this.sizeY = scanner.nextInt();

            for (int y = 0; y < sizeY; y++){
                LinkedList<Cell> tmpRow = new LinkedList<Cell>();
                for (int x = 0; x < sizeX; x++){
                    if (scanner.hasNextInt()) {
                        Cell.STATE nextCellState = (scanner.nextInt() == 1 ? Cell.STATE.ALIVE : Cell.STATE.DEAD);
                        tmpRow.add(new Cell(x, y, nextCellState));
                        if (nextCellState == Cell.STATE.ALIVE){
                            aliveCells++;
                        }
                    }
                }
                colonyCells.add(tmpRow);
            }
        }
    }

    public Cell getCell(int x, int y){
        return colonyCells.get(y).get(x);
    }
}
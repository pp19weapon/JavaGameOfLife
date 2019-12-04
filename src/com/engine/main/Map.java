package com.engine.main;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class Map {
    private int cellSize = 2;
    private int mapSize = 1;

    private Color aliveCellColor = Color.WHITE;
    private Color deadCellColor = Color.DARK_GRAY;

    private LinkedList<LinkedList<Cell>> cells = new LinkedList<LinkedList<Cell>>();
    //private LinkedList<Cell> deadCells = new LinkedList<Cell>();
    //private LinkedList<Cell> aliveCells = new LinkedList<Cell>();

    private LinkedList<Colony> colonies = new LinkedList<Colony>();

    public int getCellSize() { return cellSize; }
    public int getMapSize() { return mapSize; }
    public Cell getCell(int x, int y) { return cells.get(y).get(x); }

    public Map(int cellSize, int mapSize) {
        this.cellSize = cellSize;
        this.mapSize = mapSize;

        //Fill up the map with dead cells
        for (int y = 0; y < mapSize; y++){
            LinkedList<Cell> tmpRow = new LinkedList<Cell>();
            for (int x = 0; x < mapSize; x++){
                tmpRow.add(new Cell(x, y, Cell.STATE.DEAD));
            }
            cells.add(tmpRow);
        }
    }

    public void Draw(Graphics graphics){
        for (LinkedList<Cell> row : cells){
            for (Cell tmpCell : row){
                if (tmpCell.getState() == Cell.STATE.ALIVE) {
                    graphics.setColor(aliveCellColor);
                    graphics.fillRect(tmpCell.getPosX() * cellSize, tmpCell.getPosY() * cellSize, cellSize, cellSize);
                } else {
                    graphics.setColor(deadCellColor);
                    graphics.fillRect(tmpCell.getPosX() * cellSize, tmpCell.getPosY() * cellSize, cellSize, cellSize);
                }
            }
        }
    }

    public ArrayList<Cell> getAdjacentCells (Cell centerCell){
        ArrayList<Cell> adjacentCells = new ArrayList<Cell>();

        //Go from top left to bottom right
        for (int y = centerCell.getPosY() - 1; y <= centerCell.getPosY() + 1; y++) {
            for (int x = centerCell.getPosX() - 1; x <= centerCell.getPosX() + 1; x++) {
                //Check for center and ignore it
                if (!((y == centerCell.getPosY()) && (x == centerCell.getPosX()))) {
                    //Check if in bounds
                    if ((x >= 0) && (y >= 0) && (x < mapSize) && (y < mapSize)) {
                        adjacentCells.add(getCell(x, y));
                    }
                }
            }
        }
        return adjacentCells;
    }

    public int getNumberOfAdjacentAliveCells (Cell centerCell){
        int aliveCellCounter = 0;
        ArrayList<Cell> adjacentCells = getAdjacentCells(centerCell);

        for (Cell cell : adjacentCells){
            if (cell.getState() == Cell.STATE.ALIVE){
                aliveCellCounter++;
            }
        }
        return aliveCellCounter;
    }

    public void setCellState(int x, int y, Cell.STATE newState) {
        getCell(x, y).setState(newState);
    }

    public void addColony(Colony colony){
        colonies.add(colony);
    }

    public void loadColonies(){
        for (Colony colony : colonies){
            for (int y = 0; y < colony.getSizeY(); y++){
                for (int x = 0; x < colony.getSizeX(); x++){
                    Cell.STATE colonyCellState = colony.getCell(x, y).getState();
                    getCell(colony.getPosX() + x, colony.getPosY() + y).setState(colonyCellState);
                }
            }
        }
    }

}

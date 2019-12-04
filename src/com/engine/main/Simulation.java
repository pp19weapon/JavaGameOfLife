package com.engine.main;

import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;

public class Simulation {
    public int generation = 0;

    //Place dying cells here to be killed at the end of the tick
    private LinkedList<Cell> dyingCells = new LinkedList<Cell>();
    //Cells to be placed
    private LinkedList<Cell> tobePlacedCells = new LinkedList<Cell>();

    private int currentAliveCells = 0;
    private int mapSize = 100;
    private int cellSize = 16;
    private Map map;

    /** GETTERS **/
    public int getCurrentAliveCells() { return currentAliveCells; }
    public int getMapSize() { return mapSize; }
    public int getCellSize() { return cellSize; }

    public Map getMap() {
        if (map != null){
            return map;
        } else {
            System.out.println("ERROR: Can't find map pointer!");
            return null;
        }
    }

    public Simulation(int mapSize, int cellSize){
        this.mapSize = mapSize;
        this.cellSize = cellSize;

        map = new Map(cellSize, mapSize);

        StartSimulation();
    }

    private void StartSimulation(){
        //Place starting colony
        try {
            Colony testColony = new Colony("test.colony", mapSize / 2, mapSize / 2);
            Colony colony2 = new Colony("test.colony", 50, 50);
            Colony colony3 = new Colony("test.colony", mapSize - 50, mapSize - 50);
            currentAliveCells += testColony.getAliveCells();
            currentAliveCells += colony2.getAliveCells();
            currentAliveCells += colony3.getAliveCells();
            map.addColony(testColony);
            map.addColony(colony2);
            map.addColony(colony3);
        } catch (IOException e){
            e.printStackTrace();
        }

        map.loadColonies();
    }

    public void Tick(){
        //Mark cells for dying or to be born
        for (int y = 0; y < mapSize; y++){
            for (int x = 0; x < mapSize; x++){
                Cell tmpCell = map.getCell(x, y);

                //Check for dying cells
                if (tmpCell.getState() == Cell.STATE.ALIVE){
                    int numberOfAliveAdjCells = map.getNumberOfAdjacentAliveCells(tmpCell);
                    if ((numberOfAliveAdjCells < 2) || (numberOfAliveAdjCells > 3)){
                        dyingCells.add(tmpCell);
                    }

                //Check for to be born cells
                } else if (tmpCell.getState() == Cell.STATE.DEAD){
                    int numberOfAliveAdjCells = map.getNumberOfAdjacentAliveCells(tmpCell);
                    if (numberOfAliveAdjCells == 3){
                        tobePlacedCells.add(tmpCell);
                    }
                }
            }
        }

        //Place new modifications
        for (Cell cell : dyingCells){
            cell.setState(Cell.STATE.DEAD);
        }
        currentAliveCells -= dyingCells.size();

        for (Cell cell : tobePlacedCells){
            cell.setState(Cell.STATE.ALIVE);
        }
        currentAliveCells += tobePlacedCells.size();

        System.out.println("Generation " + generation + ": dead -> " + dyingCells.size() + "; born -> " + tobePlacedCells.size() + " --- ALIVE: " + currentAliveCells);

        dyingCells.clear();
        tobePlacedCells.clear();

        generation++;
    }

    public void Draw(Graphics graphics){
        map.Draw(graphics);
    }
}
